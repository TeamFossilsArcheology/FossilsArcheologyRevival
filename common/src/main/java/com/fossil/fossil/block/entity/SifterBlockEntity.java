package com.fossil.fossil.block.entity;

import com.fossil.fossil.block.custom_blocks.SifterBlock;
import com.fossil.fossil.inventory.SifterMenu;
import com.fossil.fossil.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SifterBlockEntity extends CustomBlockEntity {
    private static final int[] SLOTS_FOR_DOWN = new int[]{1, 2, 3, 4, 5};
    private static final int[] SLOTS_FOR_UP = new int[]{0};

    protected NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
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

    public SifterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SIFTER.get(), blockPos, blockState);
    }


    public static EnumSiftType getSiftTypeFromStack(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem) {
            BlockState blockState = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            if (blockState.getMaterial() == Material.SAND && !(blockState.getBlock() instanceof ConcretePowderBlock)) {
                return EnumSiftType.SAND;
            }
            if (blockState.getMaterial() == Material.DIRT) {
                return EnumSiftType.GROUND;
            }
        }
        return EnumSiftType.NONE;
    }


    public static void serverTick(Level level, BlockPos pos, BlockState state, SifterBlockEntity blockEntity) {
        boolean fueled = blockEntity.isProcessing();
        boolean dirty = false;
        if (blockEntity.isProcessing()) {
            --blockEntity.litTime;
        }

        if (blockEntity.litTime == 0 && blockEntity.canProcess()) {
            blockEntity.litDuration = blockEntity.litTime = 100;
            dirty = true;
        }

        if (blockEntity.isProcessing() && blockEntity.canProcess()) {
            ++blockEntity.cookingProgress;

            if (blockEntity.cookingProgress == 200) {
                blockEntity.cookingProgress = 0;
                blockEntity.createItem();
                dirty = true;
            }
        } else {
            blockEntity.cookingProgress = 0;
        }

        if (fueled != blockEntity.isProcessing()) {
            dirty = true;
            state = state.setValue(SifterBlock.ACTIVE, blockEntity.isProcessing());
            level.setBlock(pos, state, 3);
        }

        if (dirty) {
            setChanged(level, pos, state);
        }
    }

    public boolean isSiftable(ItemStack stack) {
        return ModRecipes.getSifterRecipeForItem(stack, level) != null;
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
            ItemStack result = ModRecipes.getSifterRecipeForItem(items.get(0), level).assemble(this).copy();
            for (int slot = 1; slot < 5; slot++) {
                ItemStack stackInSlot = items.get(slot);
                if (!stackInSlot.isEmpty()) {
                    if (stackInSlot.sameItem(result) && stackInSlot.getCount() + result.getCount() < 64) {
                        stackInSlot.grow(result.getCount());
                        if (items.get(0).getCount() > 1) {
                            items.get(0).shrink(1);
                        } else {
                            items.set(0, ItemStack.EMPTY);
                        }
                        break;
                    }
                } else {
                    items.set(slot, result);
                    items.get(0).shrink(1);
                    break;
                }
            }
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return new TranslatableComponent("container.fossil.sifter");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new SifterMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }


    @Override
    public int @NotNull [] getSlotsForFace(Direction side) {
        return side == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_UP;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction != Direction.DOWN || index != 1 || stack.is(Items.BUCKET);
    }

    public enum EnumSiftType {
        NONE, GROUND, SAND
    }
}
