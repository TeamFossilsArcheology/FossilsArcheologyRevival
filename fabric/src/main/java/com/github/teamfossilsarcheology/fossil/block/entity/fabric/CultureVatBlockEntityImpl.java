package com.github.teamfossilsarcheology.fossil.block.entity.fabric;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.CultureVatBlock;
import com.github.teamfossilsarcheology.fossil.block.entity.CultureVatBlockEntity;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.fabric.block.entity.FabricEnergyContainerBlockEntity;
import com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu;
import com.github.teamfossilsarcheology.fossil.recipe.CultureVatRecipe;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CultureVatBlockEntityImpl extends FabricEnergyContainerBlockEntity implements CultureVatBlockEntity {

    private static final int[] SLOTS_FOR_UP = new int[]{CultureVatMenu.INPUT_SLOT_ID}; //Input
    private static final int[] SLOTS_FOR_SIDES = new int[]{CultureVatMenu.FUEL_SLOT_ID}; //Fuel
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
                    return (int) energyStorage.amount;
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

    public CultureVatBlockEntityImpl(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CULTURE_VAT.get(), blockPos, blockState);
    }

    public static BlockEntity get(BlockPos pos, BlockState state) {
        return new CultureVatBlockEntityImpl(pos, state);
    }

    public static int getItemFuelTime(ItemStack stack) {
        return ModRecipes.getCultureVatFuelValue(stack.getItem());
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        boolean wasActive = cookingProgress > 0;
        boolean dirty = false;

        if (cookingProgress == 0 && fuel == ItemStack.EMPTY && canProcess()) {
            ItemStack fuelStack = items.get(CultureVatMenu.FUEL_SLOT_ID);
            litDuration = litTime = getItemFuelTime(fuelStack);
            fuel = fuelStack.copy();
            dirty = true;

            if (!fuelStack.isEmpty()) {
                if (fuelStack.getItem().hasCraftingRemainingItem()) {
                    items.set(CultureVatMenu.FUEL_SLOT_ID, new ItemStack(fuelStack.getItem().getCraftingRemainingItem()));
                } else {
                    fuelStack.shrink(1);
                }
            }
        }

        if (canProcess(fuel)) {
            if (isProcessing()) {
                cookingProgress++;
                if (cookingProgress >= CultureVatMenu.CULTIVATION_TIME) {
                    cookingProgress = 0;
                    createItem();
                }
            }
        } else if (cookingProgress != 0) {
            cookingProgress = 0;
        }

        if (isProcessing()) {
            if (!FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY) || energyStorage.getAmount() > 0) {
                energyStorage.amount -= FossilConfig.getInt(FossilConfig.MACHINE_ENERGY_USAGE);
                --litTime;
            }
        }
        if (!isProcessing() && fuel != ItemStack.EMPTY) {
            ItemStack fuelStack = items.get(CultureVatMenu.FUEL_SLOT_ID);
            litDuration = litTime = getItemFuelTime(fuelStack);
            fuel = fuelStack.copy();
            dirty = true;

            if (!fuelStack.isEmpty()) {
                if (fuelStack.getItem().hasCraftingRemainingItem()) {
                    items.set(CultureVatMenu.FUEL_SLOT_ID, new ItemStack(fuelStack.getItem().getCraftingRemainingItem()));
                } else {
                    fuelStack.shrink(1);
                }
            }
        }

        if (wasActive != cookingProgress > 0) {
            dirty = true;
            state = state.setValue(CultureVatBlock.ACTIVE, cookingProgress > 0);
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
        if (!input.isEmpty()) {
            if (input.is(ModItemTags.FOSSIL_SAPLINGS)) {
                return CultureVatBlock.EmbryoType.TREE;
            } else if (input.is(ModItemTags.DNA_PLANTS)) {
                return CultureVatBlock.EmbryoType.PLANT;
            } else if (input.is(ModItemTags.DNA_LIMBLESS)) {
                return CultureVatBlock.EmbryoType.LIMBLESS;
            } else if (input.is(ModItemTags.DNA_INSECTS)) {
                return CultureVatBlock.EmbryoType.INSECT;
            }
        }
        return CultureVatBlock.EmbryoType.GENERIC;
    }

    private boolean isValidInput(ItemStack inputStack, ItemStack fuelStack) {
        CultureVatRecipe recipe = ModRecipes.getCultureVatRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(inputStack, fuelStack), level);
        if (recipe != null) {
            ItemStack output = items.get(CultureVatMenu.OUTPUT_SLOT_ID);
            return output.isEmpty() || output.sameItem(recipe.getResultItem());
        }
        return false;
    }

    protected boolean canProcess(ItemStack fuelStack) {
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY) && energyStorage.amount < FossilConfig.getInt(FossilConfig.MACHINE_ENERGY_USAGE)) {
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
            ItemStack result = recipe.getResultItem();
            ItemStack output = items.get(CultureVatMenu.OUTPUT_SLOT_ID);
            if (output.isEmpty()) {
                items.set(CultureVatMenu.OUTPUT_SLOT_ID, result);
            } else if (output.sameItem(result)) {
                output.grow(result.getCount());
            }
            this.items.get(CultureVatMenu.INPUT_SLOT_ID).shrink(1);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return new TranslatableComponent("container.fossil.culture_vat");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new CultureVatMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public boolean canPlaceItem(int index, @NotNull ItemStack stack) {
        if (index == CultureVatMenu.OUTPUT_SLOT_ID) {
            return false;
        }
        if (index == CultureVatMenu.FUEL_SLOT_ID) {
            return ModRecipes.isCultureVatFuel(stack.getItem());
        }
        return ModRecipes.getCultureVatRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(true, items.get(CultureVatMenu.INPUT_SLOT_ID)), level) != null;
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
        return direction != Direction.DOWN || index != CultureVatMenu.FUEL_SLOT_ID;
    }
}
