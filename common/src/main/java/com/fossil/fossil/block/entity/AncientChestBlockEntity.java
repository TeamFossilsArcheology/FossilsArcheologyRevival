package com.fossil.fossil.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AncientChestBlockEntity extends BlockEntity {
    private int state;
    private int lidTimer;

    public AncientChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANCIENT_CHEST.get(), blockPos, blockState);
    }

    private static void tick(Level level, BlockPos pos, BlockState state, AncientChestBlockEntity blockEntity) {
        if (blockEntity.state != 3) {
            if (blockEntity.lidTimer > 0) {
                blockEntity.lidTimer++;
            }
            if (blockEntity.lidTimer >= 91) {
                blockEntity.state = 3;
                if (!level.isClientSide) {
                    blockEntity.state = 0;
                    blockEntity.lidTimer = 0;
                    //TODO: Spawn item
                }
            }
        } else {
            if (blockEntity.lidTimer > 0) {
                blockEntity.lidTimer--;
            }
            if (blockEntity.lidTimer == 0) {
                blockEntity.state = 0;
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
