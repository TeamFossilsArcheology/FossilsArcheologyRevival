package com.github.teamfossilsarcheology.fossil.block.entity.fabric;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.WorktableBlock;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.block.entity.WorktableBlockEntity;
import com.github.teamfossilsarcheology.fossil.fabric.block.entity.FabricContainerBlockEntity;
import com.github.teamfossilsarcheology.fossil.inventory.WorktableMenu;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import com.github.teamfossilsarcheology.fossil.recipe.WorktableRecipe;
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
        return ModRecipes.getWorktableFuelValue(stack.getItem());
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
                    cookingTotalTime = timeToSmelt(items.get(WorktableMenu.INPUT_SLOT_ID), items.get(WorktableMenu.FUEL_SLOT_ID));
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

    private ItemStack checkSmelt(ItemStack input, ItemStack fuel) {
        WorktableRecipe recipeWorktable = ModRecipes.getWorktableRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(input, fuel), level);
        if (recipeWorktable != null) {
            return recipeWorktable.getResultItem();
        }
        return ItemStack.EMPTY;
    }

    protected boolean canProcess() {
        if (items.get(WorktableMenu.INPUT_SLOT_ID).isEmpty()) {
            return false;
        } else {
            ItemStack result = checkSmelt(items.get(WorktableMenu.INPUT_SLOT_ID), items.get(WorktableMenu.FUEL_SLOT_ID));
            return !result.isEmpty() && (items.get(WorktableMenu.OUTPUT_SLOT_ID).isEmpty() || items.get(
                    WorktableMenu.OUTPUT_SLOT_ID).sameItem(result) && (items.get(WorktableMenu.OUTPUT_SLOT_ID).getCount() < 64 && items.get(WorktableMenu.OUTPUT_SLOT_ID).getCount() < items.get(WorktableMenu.OUTPUT_SLOT_ID).getMaxStackSize() || items.get(
                    WorktableMenu.OUTPUT_SLOT_ID).getCount() < result.getMaxStackSize()));
        }
    }

    protected void createItem() {
        if (canProcess()) {
            ItemStack var1 = checkSmelt(items.get(WorktableMenu.INPUT_SLOT_ID), items.get(WorktableMenu.FUEL_SLOT_ID));

            if (items.get(WorktableMenu.OUTPUT_SLOT_ID).isEmpty()) {
                items.set(WorktableMenu.OUTPUT_SLOT_ID, var1.copy());
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

    private int getItemBurnTime(ItemStack possibleFuel) {
        if (!possibleFuel.isEmpty()) {
            WorktableRecipe recipeWorktable = ModRecipes.getWorktableRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(items.get(WorktableMenu.INPUT_SLOT_ID), possibleFuel), level);
            if (recipeWorktable != null) {
                return getItemFuelTime(possibleFuel);
            }
        }
        return 0;
    }

    private int timeToSmelt(ItemStack input, ItemStack fuel) {
        var recipe = ModRecipes.getWorktableRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(input, fuel), level);
        if (recipe != null) {
            return recipe.getDuration();
        }
        return WorktableMenu.DEFAULT_DURATION;
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
            if (!items.get(WorktableMenu.FUEL_SLOT_ID).isEmpty()) {
                cookingTotalTime = timeToSmelt(stack, items.get(WorktableMenu.FUEL_SLOT_ID));
            }
            cookingProgress = 0;
            setChanged();
        } else if (slot == WorktableMenu.FUEL_SLOT_ID && !bl) {
            if (!items.get(WorktableMenu.INPUT_SLOT_ID).isEmpty()) {
                cookingTotalTime = timeToSmelt(items.get(WorktableMenu.INPUT_SLOT_ID), stack);
            }
        }
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
        return direction != Direction.DOWN || slot != WorktableMenu.FUEL_SLOT_ID;
    }
}
