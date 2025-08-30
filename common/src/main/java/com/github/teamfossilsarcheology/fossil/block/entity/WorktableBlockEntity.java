package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.CustomEntityBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.WorktableBlock;
import com.github.teamfossilsarcheology.fossil.inventory.WorktableMenu;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import com.github.teamfossilsarcheology.fossil.recipe.WorktableRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorktableBlockEntity extends MachineContainerBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{WorktableMenu.INPUT_SLOT_ID}; //Input
    private static final int[] SLOTS_FOR_SIDES = new int[]{WorktableMenu.INPUT_SLOT_ID, WorktableMenu.FUEL_SLOT_ID}; //Input, Fuel
    private static final int[] SLOTS_FOR_DOWN = new int[]{WorktableMenu.OUTPUT_SLOT_ID}; //Output
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
                    return cookingTotalTime;
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
                case 3 -> cookingTotalTime = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private ItemStack fuel = ItemStack.EMPTY;

    public WorktableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WORKTABLE.get(), blockPos, blockState);
    }

    public static int getItemFuelTime(ItemStack stack) {
        return ModRecipes.getWorktableFuelValue(stack.getItem());
    }

    @Override
    public ContainerData getDataAccess() {
        return dataAccess;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WorktableBlockEntity blockEntity) {
        blockEntity.serverTick(level, pos, state);
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        boolean wasProcessing = cookingProgress > 0;
        boolean dirty = false;
        if (litTime > 0) {
            --litTime;
        }

        if (canProcess() && (litTime == 0 || (litTime > 0 && !canProcess(fuel)))) {
            ItemStack fuelStack = items.get(WorktableMenu.FUEL_SLOT_ID);
            litDuration = litTime = getItemFuelTime(fuelStack);
            cookingTotalTime = timeToSmelt(items.get(WorktableMenu.INPUT_SLOT_ID), fuelStack);
            fuel = fuelStack.copy();
            if (litTime > 0) {
                dirty = true;
                fuelStack.shrink(1);
                if (fuelStack.isEmpty() && fuelStack.getItem().hasCraftingRemainingItem()) {
                    items.set(WorktableMenu.FUEL_SLOT_ID, new ItemStack(fuelStack.getItem().getCraftingRemainingItem()));
                }
            }
        }

        if (litTime > 0 && canProcess(fuel)) {
            cookingProgress++;
            if (cookingProgress >= cookingTotalTime) {
                cookingProgress = 0;
                createItem();
                dirty = true;
            }
        }
        if (litTime == 0 && cookingProgress > 0 || litTime > 0 && cookingProgress > 0 && !canProcess(fuel)) {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, cookingTotalTime);
        }

        if (wasProcessing != cookingProgress > 0) {
            dirty = true;
            state = state.setValue(WorktableBlock.ACTIVE, cookingProgress > 0);
            level.setBlock(pos, state, 3);
        }

        if (dirty) {
            setChanged(level, pos, state);
        }
    }

    private ItemStack checkSmelt(ItemStack input, ItemStack fuel) {
        WorktableRecipe recipeWorktable = ModRecipes.getWorktableRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(input, fuel), level);
        if (recipeWorktable != null) {
            return recipeWorktable.getResultItem(level.registryAccess());
        }
        return ItemStack.EMPTY;
    }

    protected boolean canProcess(ItemStack fuelStack) {
        if (items.get(WorktableMenu.INPUT_SLOT_ID).isEmpty()) {
            return false;
        } else {
            ItemStack result = checkSmelt(items.get(WorktableMenu.INPUT_SLOT_ID), fuelStack);
            return !result.isEmpty() && (items.get(WorktableMenu.OUTPUT_SLOT_ID).isEmpty() || ItemStack.isSameItem(items.get(WorktableMenu.OUTPUT_SLOT_ID), result)
                    && (items.get(WorktableMenu.OUTPUT_SLOT_ID).getCount() < 64 && items.get(WorktableMenu.OUTPUT_SLOT_ID).getCount() < items.get(WorktableMenu.OUTPUT_SLOT_ID).getMaxStackSize() || items.get(
                    WorktableMenu.OUTPUT_SLOT_ID).getCount() < result.getMaxStackSize()));
        }
    }

    protected boolean canProcess() {
        return canProcess(items.get(WorktableMenu.FUEL_SLOT_ID));
    }

    protected void createItem() {
        if (canProcess(fuel)) {
            ItemStack result = checkSmelt(items.get(WorktableMenu.INPUT_SLOT_ID), fuel);

            ItemStack input = items.get(WorktableMenu.INPUT_SLOT_ID);
            if (items.get(WorktableMenu.OUTPUT_SLOT_ID).isEmpty()) {
                items.set(WorktableMenu.OUTPUT_SLOT_ID, result.copy());
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(input), items.get(WorktableMenu.OUTPUT_SLOT_ID));
            } else if (items.get(WorktableMenu.OUTPUT_SLOT_ID).getItem() == result.getItem()) {
                items.get(WorktableMenu.OUTPUT_SLOT_ID).grow(result.getCount());
            }

            if (input.getItem().hasCraftingRemainingItem()) {
                items.set(WorktableMenu.INPUT_SLOT_ID, new ItemStack(input.getItem().getCraftingRemainingItem()));
            } else {
                input.shrink(1);
            }

            if (input.getCount() <= 0) {
                items.set(WorktableMenu.INPUT_SLOT_ID, ItemStack.EMPTY);
            }
        }
    }

    public int timeToSmelt(ItemStack input, ItemStack fuel) {
        var recipe = ModRecipes.getWorktableRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(input, fuel), level);
        if (recipe != null) {
            return recipe.getDuration();
        }
        return WorktableMenu.DEFAULT_DURATION;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.fossil.worktable");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new WorktableMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack itemStack = items.get(slot);
        boolean sameItem = (!stack.isEmpty() && ItemStack.isSameItem(stack, itemStack) && ItemStack.isSameItemSameTags(stack, itemStack));
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        if (slot == WorktableMenu.INPUT_SLOT_ID && !sameItem) {
            cookingProgress = 0;
            level.setBlock(getBlockPos(), getBlockState().setValue(CustomEntityBlock.ACTIVE, false), 3);
        }
        setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        if (slot == WorktableMenu.OUTPUT_SLOT_ID) {
            return false;
        }
        if (slot == WorktableMenu.FUEL_SLOT_ID) {
            return ModRecipes.isWorktableFuel(stack.getItem());
        }
        return ModRecipes.getWorktableRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(true, stack), getLevel()) != null;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return side == Direction.DOWN ? SLOTS_FOR_DOWN : (side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction direction) {
        return direction != Direction.UP && slot == WorktableMenu.OUTPUT_SLOT_ID;
    }
}
