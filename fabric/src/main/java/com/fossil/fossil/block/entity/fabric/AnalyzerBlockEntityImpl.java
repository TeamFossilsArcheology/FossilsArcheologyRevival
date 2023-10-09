package com.fossil.fossil.block.entity.fabric;

import com.fossil.fossil.block.custom_blocks.AnalyzerBlock;
import com.fossil.fossil.block.entity.AnalyzerBlockEntity;
import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.fabric.block.entity.FabricContainerBlockEntity;
import com.fossil.fossil.inventory.AnalyzerMenu;
import com.fossil.fossil.inventory.CustomSimpleContainer;
import com.fossil.fossil.recipe.AnalyzerRecipe;
import com.fossil.fossil.recipe.ModRecipes;
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

public class AnalyzerBlockEntityImpl extends FabricContainerBlockEntity implements AnalyzerBlockEntity {

    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}; //Input
    private static final int[] SLOTS_FOR_DOWN = new int[]{9, 10, 11, 12}; //Output
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
            return 3;
        }
    };
    protected NonNullList<ItemStack> items = NonNullList.withSize(13, ItemStack.EMPTY);
    private int rawIndex = -1;
    public AnalyzerBlockEntityImpl(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANALYZER.get(), blockPos, blockState);
    }

    public static BlockEntity get(BlockPos pos, BlockState state) {
        return new AnalyzerBlockEntityImpl(pos, state);
    }

    private static int getFuelTime(ItemStack stack) {
        return 100;
    }

    public static boolean isFuel(ItemStack stack) {
        return getFuelTime(stack) > 0;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        boolean fueled = isProcessing();
        boolean dirty = false;
        if (isProcessing()) {
            --litTime;
        }

        if (litTime == 0 && canProcess()) {
            litDuration = litTime = 100;
            dirty = true;
        }
        if (isProcessing() && canProcess()) {
            ++cookingProgress;

            if (cookingProgress == 200) {
                cookingProgress = 0;
                createItem();
                dirty = true;
            }
        } else {
            cookingProgress = 0;
        }
        if (fueled != isProcessing()) {
            dirty = true;
            state = state.setValue(AnalyzerBlock.ACTIVE, isProcessing());
            level.setBlock(pos, state, 3);
        }
        if (dirty) {
            setChanged(level, pos, state);
        }
    }

    private boolean isAnalyzable(ItemStack itemStack) {
        return ModRecipes.getAnalyzerRecipeForItem(new CustomSimpleContainer(9, itemStack), level) != null;
    }

    @Override
    protected boolean canProcess() {
        int spaceIndex = -1;
        rawIndex = -1;
        boolean flag = false;
        for (int slot = 0; slot < 9; ++slot) {
            if (!items.get(slot).isEmpty() && isAnalyzable(items.get(slot))) {
                rawIndex = slot;
                flag = true;
                break;
            }
        }
        if (rawIndex == -1 || !flag) {
            return false;
        } else {
            for (int slot = 12; slot > 8; --slot) {
                if (items.get(slot).isEmpty()) {
                    spaceIndex = slot;
                    break;
                }
            }
            return spaceIndex != -1 && rawIndex != -1;
        }
    }

    @Override
    protected void createItem() {
        if (canProcess()) {
            ItemStack input = items.get(rawIndex);
            AnalyzerRecipe recipe = ModRecipes.getAnalyzerRecipeForItem(new CustomSimpleContainer(9, input), level);
            if (recipe == null) {
                return;
            }
            ItemStack output = recipe.assemble(this).copy();
            if (output.getCount() > 1) {
                output.setCount(1 + level.random.nextInt(output.getCount() - 1));
            }
            if (!output.isEmpty()) {
                for (int slot = 9; slot < 13; slot++) {
                    ItemStack itemStack = items.get(slot);
                    if (itemStack.isEmpty()) {
                        items.set(slot, output);
                        break;
                    } else if (itemStack.sameItem(output) && itemStack.getCount() + output.getCount() < 64) {
                        itemStack.setCount(itemStack.getCount() + output.getCount());
                        break;
                    }
                }
            }
            input.shrink(1);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public int getIngredientsSize() {
        return SLOTS_FOR_UP.length;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return new TranslatableComponent("container.fossil.analyzer");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new AnalyzerMenu(containerId, inventory, this, dataAccess);
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
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return slot < SLOTS_FOR_DOWN[0];
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return side == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_UP;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction direction) {
        return direction != Direction.DOWN || slot >= SLOTS_FOR_DOWN[0];
    }
}
