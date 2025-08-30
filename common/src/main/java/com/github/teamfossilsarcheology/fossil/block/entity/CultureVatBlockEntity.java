package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.CultureVatBlock;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu;
import com.github.teamfossilsarcheology.fossil.recipe.CultureVatRecipe;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CultureVatBlockEntity extends EnergyContainerBlockEntity {

    private static final int[] SLOTS_FOR_UP = new int[]{CultureVatMenu.INPUT_SLOT_ID}; //Input
    private static final int[] SLOTS_FOR_SIDES = new int[]{CultureVatMenu.INPUT_SLOT_ID, CultureVatMenu.FUEL_SLOT_ID}; //Input, Fuel
    private static final int[] SLOTS_FOR_DOWN = new int[]{CultureVatMenu.OUTPUT_SLOT_ID}; //Output
    private final ContainerData dataAccess = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return litTime;
                }
                case 1 -> {
                    return litDuration;
                }
                case 2 -> {
                    return cookingProgress;
                }
                case 3 -> {
                    return energyStorage.getEnergy();
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> litTime = value;
                case 1 -> litDuration = value;
                case 2 -> cookingProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private ItemStack fuel = ItemStack.EMPTY;

    public CultureVatBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CULTURE_VAT.get(), blockPos, blockState);
    }

    public static int getItemFuelTime(ItemStack stack) {
        return ModRecipes.getCultureVatFuelValue(stack.getItem());
    }

    @Override
    public ContainerData getDataAccess() {
        return dataAccess;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ListTag listTag = tag.getList("FuelItem", Tag.TAG_COMPOUND);
        if (!listTag.isEmpty()) {
            fuel = ItemStack.of(listTag.getCompound(0));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ListTag listTag = new ListTag();
        listTag.add(fuel.save(new CompoundTag()));
        tag.put("FuelItem", listTag);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CultureVatBlockEntity blockEntity) {
        blockEntity.serverTick(level, pos, state);
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY) && energyStorage.getEnergy() <= 0) {
            if (cookingProgress > 0) {
                cookingProgress = Mth.clamp(cookingProgress - 2, 0, CultureVatMenu.CULTIVATION_DURATION);
            }
            return;
        }
        int prevCookingProgress = cookingProgress;
        boolean wasFueled = litTime > 0;
        boolean wasProcessing = cookingProgress > 0;
        boolean dirty = false;
        if (litTime > 0) {
            --litTime;
        }

        if (canProcess() && (litTime == 0 || (litTime > 0 && !canProcess(fuel)))) {
            ItemStack fuelStack = items.get(CultureVatMenu.FUEL_SLOT_ID);
            litDuration = litTime = getItemFuelTime(fuelStack);
            fuel = fuelStack.copy();
            if (litTime > 0) {
                dirty = true;
                fuelStack.shrink(1);
                if (fuelStack.isEmpty() && fuelStack.getItem().hasCraftingRemainingItem()) {
                    items.set(CultureVatMenu.FUEL_SLOT_ID, new ItemStack(fuelStack.getItem().getCraftingRemainingItem()));
                }
            }
        }

        if (litTime > 0 && canProcess(fuel)) {
            cookingProgress++;
            if (cookingProgress >= CultureVatMenu.CULTIVATION_DURATION) {
                cookingProgress = 0;
                createItem();
                dirty = true;
            }
        }
        if (prevCookingProgress != cookingProgress && FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            energyStorage.extractEnergy(FossilConfig.getInt(FossilConfig.MACHINE_ENERGY_USAGE));
        }
        if (litTime == 0 && cookingProgress > 0 || litTime > 0 && cookingProgress > 0 && !canProcess(fuel)) {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, CultureVatMenu.CULTIVATION_DURATION);
        }

        if (wasFueled != litTime > 0) {
            dirty = true;
            state = state.setValue(CultureVatBlock.ACTIVE, litTime > 0);
            level.setBlock(pos, state, 3);
        }

        if (wasProcessing != cookingProgress > 0) {
            dirty = true;
            state = state.setValue(CultureVatBlock.EMBRYO, getDNAType());
            level.setBlock(pos, state, 3);
        }

        if (dirty) {
            setChanged(level, pos, state);
        }

        if (cookingProgress == 3001 && level.getRandom().nextInt(100) < FossilConfig.getInt(FossilConfig.CULTURE_VAT_FAIL_CHANCE)) {
            ModBlocks.CULTURE_VAT.get().onFailedCultivation(level, pos);
        }
    }

    public CultureVatBlock.EmbryoType getDNAType() {
        ItemStack input = items.get(CultureVatMenu.INPUT_SLOT_ID);
        if (input.isEmpty()) {
            return CultureVatBlock.EmbryoType.NONE;
        }
        if (input.is(ModItemTags.FOSSIL_SAPLINGS)) {
            return CultureVatBlock.EmbryoType.TREE;
        } else if (input.is(ModItemTags.DNA_PLANTS)) {
            return CultureVatBlock.EmbryoType.PLANT;
        } else if (input.is(ModItemTags.DNA_LIMBLESS)) {
            return CultureVatBlock.EmbryoType.LIMBLESS;
        } else if (input.is(ModItemTags.DNA_INSECTS)) {
            return CultureVatBlock.EmbryoType.INSECT;
        }
        return CultureVatBlock.EmbryoType.GENERIC;
    }

    private boolean isValidInput(ItemStack inputStack, ItemStack fuelStack) {
        CultureVatRecipe recipe = ModRecipes.getCultureVatRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(inputStack, fuelStack), level);
        if (recipe != null) {
            ItemStack output = items.get(CultureVatMenu.OUTPUT_SLOT_ID);
            return output.isEmpty() || ItemStack.isSameItem(output, recipe.getResultItem(level.registryAccess()));
        }
        return false;
    }

    protected boolean canProcess(ItemStack fuelStack) {
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY) && energyStorage.getEnergy() <= FossilConfig.getInt(FossilConfig.MACHINE_ENERGY_USAGE)) {
            return false;
        }
        ItemStack inputStack = items.get(CultureVatMenu.INPUT_SLOT_ID);
        if (!inputStack.isEmpty() && !fuelStack.isEmpty()) {
            return isValidInput(inputStack, fuelStack);
        }
        return false;
    }

    protected boolean canProcess() {
        return canProcess(items.get(CultureVatMenu.FUEL_SLOT_ID));
    }

    protected void createItem() {
        if (canProcess(fuel)) {
            ItemStack inputStack = items.get(CultureVatMenu.INPUT_SLOT_ID);
            CultureVatRecipe recipe = ModRecipes.getCultureVatRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(inputStack, fuel), level);
            ItemStack result = recipe.getResultItem(level.registryAccess());
            ItemStack output = items.get(CultureVatMenu.OUTPUT_SLOT_ID);
            if (output.isEmpty()) {
                items.set(CultureVatMenu.OUTPUT_SLOT_ID, result);
            } else if (ItemStack.isSameItem(output, result)) {
                output.grow(result.getCount());
            }
            this.items.get(CultureVatMenu.INPUT_SLOT_ID).shrink(1);
        }
    }

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.fossil.culture_vat");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new CultureVatMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack current = items.get(slot);
        boolean sameItems = !stack.isEmpty() && ItemStack.isSameItem(stack, current) && ItemStack.isSameItemSameTags(stack, current);
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        if (slot == CultureVatMenu.INPUT_SLOT_ID && !sameItems) {
            cookingProgress = 0;
            level.setBlock(getBlockPos(), getBlockState().setValue(CultureVatBlock.EMBRYO, getDNAType()), 3);
            setChanged();
        }
    }

    @Override
    public boolean canPlaceItem(int index, @NotNull ItemStack stack) {
        if (index == CultureVatMenu.OUTPUT_SLOT_ID) {
            return false;
        }
        if (index == CultureVatMenu.FUEL_SLOT_ID) {
            return ModRecipes.isCultureVatFuel(stack.getItem());
        }
        return ModRecipes.getCultureVatRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(true, stack), level) != null;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return side == Direction.DOWN ? SLOTS_FOR_DOWN : (side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
        return direction != Direction.UP && index == CultureVatMenu.OUTPUT_SLOT_ID;
    }
}
