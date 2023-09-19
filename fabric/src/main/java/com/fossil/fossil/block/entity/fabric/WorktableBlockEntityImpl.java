package com.fossil.fossil.block.entity.fabric;

import com.fossil.fossil.block.custom_blocks.WorktableBlock;
import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.block.entity.WorktableBlockEntity;
import com.fossil.fossil.fabric.block.entity.FabricContainerBlockEntity;
import com.fossil.fossil.inventory.WorktableMenu;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.recipe.WorktableRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorktableBlockEntityImpl extends FabricContainerBlockEntity implements WorktableBlockEntity {

    private static final int[] SLOTS_FOR_UP = new int[]{WorktableMenu.INPUT_SLOT_ID}; //Input
    private static final int[] SLOTS_FOR_SIDES = new int[]{WorktableMenu.FUEL_SLOT_ID}; //Fuel
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
    public WorktableBlockEntityImpl(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WORKTABLE.get(), blockPos, blockState);
    }

    public static BlockEntity get(BlockPos pos, BlockState state) {
        return new WorktableBlockEntityImpl(pos, state);
    }

    public static int getItemFuelTime(ItemStack stack) {
        Integer fuel = ModRecipes.WORKTABLE_FUEL_VALUES.get(stack.getItem());
        if (fuel != null) {
            return fuel;
        }
        return 0;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        boolean var1 = isProcessing();
        boolean dirty = false;

        if (isProcessing()) {
            --litTime;
        }

        ItemStack fuel = items.get(WorktableMenu.FUEL_SLOT_ID);
        if (isProcessing() || !fuel.isEmpty() && !items.get(WorktableMenu.INPUT_SLOT_ID).isEmpty()) {

            if (!(isProcessing()) && canProcess()) {
                litDuration = litTime = getItemBurnTime(items.get(WorktableMenu.FUEL_SLOT_ID));
                if (litTime > 1) {
                    dirty = true;
                    if (!fuel.isEmpty()) {
                        Item item = fuel.getItem();
                        fuel.shrink(1);
                        if (fuel.isEmpty()) {
                            Item item2 = item.getCraftingRemainingItem();
                            items.set(WorktableMenu.FUEL_SLOT_ID, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                        }
                    }
                }
            }
            if (isProcessing() && canProcess()) {
                cookingProgress++;
                if (cookingProgress == cookingTotalTime) {
                    cookingProgress = 0;
                    cookingTotalTime = timeToSmelt();
                    createItem();
                    dirty = true;
                }
            } else {
                cookingProgress = 0;
            }
        } else if (!(isProcessing()) && cookingProgress > 0) {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, cookingTotalTime);
        }

        if (var1 != isProcessing()) {
            dirty = true;
            state = state.setValue(WorktableBlock.ACTIVE, isProcessing());
            level.setBlock(pos, state, 3);
        }

        if (dirty) {
            setChanged(level, pos, state);
        }
    }

    private ItemStack checkSmelt(ItemStack itemstack) {
        WorktableRecipe recipeWorktable = ModRecipes.getWorktableRecipeForItem(itemstack, level);
        if (recipeWorktable != null) {
            return recipeWorktable.getOutput().copy();
        }
        return new ItemStack(ModItems.POTTERY_SHARD.get());
    }

    protected boolean canProcess() {
        if (items.get(WorktableMenu.INPUT_SLOT_ID).isEmpty()) {
            return false;
        } else {
            ItemStack var1 = checkSmelt(items.get(WorktableMenu.INPUT_SLOT_ID));
            return var1 != null && !var1.isEmpty() && (items.get(WorktableMenu.OUTPUT_SLOT_ID).isEmpty() || (items.get(
                    WorktableMenu.OUTPUT_SLOT_ID).sameItem(var1) && (items.get(WorktableMenu.OUTPUT_SLOT_ID).getCount() < 64
                    && items.get(WorktableMenu.OUTPUT_SLOT_ID).getCount() < items.get(WorktableMenu.OUTPUT_SLOT_ID).getMaxStackSize() || items.get(
                    WorktableMenu.OUTPUT_SLOT_ID).getCount() < var1.getMaxStackSize())));
        }
    }

    protected void createItem() {
        if (canProcess()) {
            ItemStack var1 = checkSmelt(items.get(WorktableMenu.INPUT_SLOT_ID));

            if (items.get(WorktableMenu.OUTPUT_SLOT_ID).isEmpty()) {
                if (var1 != null) {
                    items.set(WorktableMenu.OUTPUT_SLOT_ID, var1.copy());
                }
            } else if (items.get(WorktableMenu.OUTPUT_SLOT_ID).getItem() == var1.getItem()) {
                items.get(WorktableMenu.OUTPUT_SLOT_ID).grow(var1.getCount());
            }

            if (items.get(WorktableMenu.INPUT_SLOT_ID).getItem().hasCraftingRemainingItem()) {
                items.set(WorktableMenu.INPUT_SLOT_ID, new ItemStack(items.get(WorktableMenu.INPUT_SLOT_ID).getItem().getCraftingRemainingItem()));
            } else {
                items.get(WorktableMenu.INPUT_SLOT_ID).shrink(1);
            }

            if (items.get(WorktableMenu.INPUT_SLOT_ID).getCount() <= 0) {
                items.set(WorktableMenu.INPUT_SLOT_ID, ItemStack.EMPTY);
            }
        }
    }

    private int getItemBurnTime(ItemStack itemstack) {
        if (!itemstack.isEmpty()) {
            Item var2 = itemstack.getItem();
            WorktableRecipe recipeWorktable = ModRecipes.getWorktableRecipeForItem(items.get(WorktableMenu.INPUT_SLOT_ID), level);
            if (recipeWorktable != null) {
                return var2 == recipeWorktable.getFuel().getItem() ? getItemFuelTime(recipeWorktable.getFuel()) : 0;
            }
        }
        return 0;
    }

    private int timeToSmelt() {
        ItemStack item = items.get(WorktableMenu.FUEL_SLOT_ID);
        if (item.isEmpty()) {
            return 3000;
        }
        return 300;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return new TranslatableComponent("container.fossil.worktable");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new WorktableMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack itemStack = items.get(slot);
        boolean bl = (!stack.isEmpty() && stack.sameItem(itemStack) && ItemStack.tagMatches(stack, itemStack));
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        if (slot == WorktableMenu.INPUT_SLOT_ID && !bl) {
            cookingTotalTime = timeToSmelt();
            cookingProgress = 0;
            setChanged();
        }
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        if (slot == WorktableMenu.OUTPUT_SLOT_ID) {
            return false;
        }
        if (slot == WorktableMenu.FUEL_SLOT_ID) {
            return ModRecipes.WORKTABLE_FUEL_VALUES.containsKey(stack.getItem());
        }
        return true;
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
        return direction != Direction.DOWN || slot != WorktableMenu.FUEL_SLOT_ID;
    }
}
