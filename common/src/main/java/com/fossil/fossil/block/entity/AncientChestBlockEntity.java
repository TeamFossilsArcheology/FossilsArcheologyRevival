package com.fossil.fossil.block.entity;

import com.fossil.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AncientChestBlockEntity extends BlockEntity {
    public static final int STATE_LOCKED = 0;
    public static final int STATE_UNLOCKED = 1;
    public static final int STATE_OPENING = 2;
    public static final int STATE_CLOSING = 3;
    private int state;
    private int lidTimer;

    public AncientChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANCIENT_CHEST.get(), blockPos, blockState);
    }

    private static void tick(Level level, BlockPos pos, BlockState state, AncientChestBlockEntity blockEntity) {
        if (blockEntity.state != STATE_CLOSING) {
            if (blockEntity.lidTimer > 0) {
                blockEntity.lidTimer++;
            }
            if (blockEntity.lidTimer >= 91) {
                blockEntity.state = STATE_CLOSING;
                if (!level.isClientSide) {
                    ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                            new ItemStack(ModItems.ANCIENT_CLOCK.get()), 0, 0.1, 0);
                    level.addFreshEntity(itemEntity);
                }
            }
        } else {
            if (blockEntity.lidTimer > 0) {
                blockEntity.lidTimer--;
            }
            if (blockEntity.lidTimer == 0) {
                blockEntity.state = STATE_LOCKED;
            }

        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AncientChestBlockEntity blockEntity) {
        tick(level, pos, state, blockEntity);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AncientChestBlockEntity blockEntity) {
        tick(level, pos, state, blockEntity);
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setLidTimer(int lidTimer) {
        this.lidTimer = lidTimer;
    }

    public int getState() {
        return state;
    }

    public int getLidTimer() {
        return lidTimer;
    }
}
