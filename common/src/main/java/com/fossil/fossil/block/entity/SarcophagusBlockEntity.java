package com.fossil.fossil.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SarcophagusBlockEntity extends BlockEntity {
    private int state;
    private int doorTimer;

    public SarcophagusBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.SARCOPHAGUS.get(), blockPos, blockState);
    }

    private static void tick(Level level, BlockPos pos, BlockState state, SarcophagusBlockEntity blockEntity) {
        if (blockEntity.state != 3) {
            if (blockEntity.doorTimer > 0) {
                blockEntity.doorTimer++;
            }
            if (blockEntity.doorTimer >= 91) {
                blockEntity.state = 3;
                if (!level.isClientSide) {
                    //TODO: Spawn Anu
                }
            }
        } else {
            if (blockEntity.doorTimer > 0) {
                blockEntity.doorTimer--;
            }
            if (blockEntity.doorTimer == 0) {
                blockEntity.state = 0;
            }
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SarcophagusBlockEntity blockEntity) {
        tick(level, pos, state, blockEntity);
    }
    public static void clientTick(Level level, BlockPos pos, BlockState state, SarcophagusBlockEntity blockEntity) {
        tick(level, pos, state, blockEntity);
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDoorTimer(int doorTimer) {
        this.doorTimer = doorTimer;
    }

    public int getState() {
        return state;
    }

    public int getDoorTimer() {
        return doorTimer;
    }
}
