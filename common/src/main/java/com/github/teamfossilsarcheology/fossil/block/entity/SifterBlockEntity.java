package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.SifterBlock;
import com.github.teamfossilsarcheology.fossil.inventory.SifterMenu;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SifterBlockEntity extends MachineContainerBlockEntity {

    private static final int[] SLOTS_FOR_UP = new int[]{0}; //Input
    private static final int[] SLOTS_FOR_DOWN = new int[]{1, 2, 3, 4, 5}; //Output
    private final ContainerData dataAccess = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return cookingProgress;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> cookingProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    protected NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);

    public SifterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SIFTER.get(), blockPos, blockState);
    }

    public static BlockEntity get(BlockPos pos, BlockState state) {
        return new SifterBlockEntity(pos, state);
    }

    @Override
    public ContainerData getDataAccess() {
        return dataAccess;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SifterBlockEntity blockEntity) {
        blockEntity.serverTick(level, pos, state);
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        boolean wasProcessing = cookingProgress > 0;
        boolean dirty = false;

        if (canProcess()) {
            cookingProgress++;
            if (cookingProgress >= SifterMenu.SIFTER_DURATION) {
                cookingProgress = 0;
                createItem();
                dirty = true;
            }
        } else {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, SifterMenu.SIFTER_DURATION);
        }

        if (wasProcessing != cookingProgress > 0) {
            dirty = true;
            state = state.setValue(SifterBlock.ACTIVE, cookingProgress > 0);
            level.setBlock(pos, state, 3);
        }

        if (dirty) {
            setChanged(level, pos, state);
        }
    }


    public boolean isSiftable(ItemStack stack) {
        return ModRecipes.getSifterRecipeForItem(new SimpleContainer(stack), level) != null;
    }

    protected boolean canProcess() {
        ItemStack itemStack = items.get(0);
        if (!itemStack.isEmpty()) {
            if (isSiftable(itemStack)) {
                for (int outputIndex = 5; outputIndex > 0; outputIndex--) {
                    if (items.get(outputIndex).isEmpty()) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    protected void createItem() {
        if (canProcess()) {
            ItemStack result = ModRecipes.getSifterRecipeForItem(new SimpleContainer(items.get(0)), level)
                    .assemble(this, level.registryAccess()).copy();
            for (int slot = 1; slot <= 5; slot++) {
                ItemStack stackInSlot = items.get(slot);
                if (ItemStack.isSameItem(stackInSlot, result) && stackInSlot.getCount() + result.getCount() < 64) {
                    stackInSlot.grow(result.getCount());
                    if (items.get(0).getCount() > 1) {
                        items.get(0).shrink(1);
                    } else {
                        items.set(0, ItemStack.EMPTY);
                    }
                    return;
                }
            }
            for (int slot = 1; slot <= 5; slot++) {
                ItemStack stackInSlot = items.get(slot);
                if (stackInSlot.isEmpty()) {
                    items.set(slot, result);
                    if (items.get(0).getCount() > 1) {
                        items.get(0).shrink(1);
                    } else {
                        items.set(0, ItemStack.EMPTY);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.fossil.sifter");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new SifterMenu(containerId, inventory, this, dataAccess);
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
        return slot == 0 && isSiftable(stack);
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
        return direction != Direction.UP && slot >= SLOTS_FOR_DOWN[0];
    }
}
