package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AncientChestBlockEntity extends BlockEntity {
    public static final int STATE_LOCKED = 0;
    public static final int STATE_UNLOCKED = 1;
    public static final int STATE_OPENING = 2;
    public static final int STATE_CLOSING = 3;
    public static final int STATE_EVENT = 1;
    private int state;
    private int lidTimer;

    public AncientChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANCIENT_CHEST.get(), blockPos, blockState);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == STATE_EVENT) {
            state = type;
            if (type == STATE_OPENING) {
                lidTimer = 1;
            }
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
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
                    itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                            new ItemStack(ModItems.ANCIENT_HELMET.get()), 0, 0.1, 0);
                    level.addFreshEntity(itemEntity);
                    itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                            new ItemStack(ModItems.ANCIENT_JAVELIN.get()), 0, 0.1, 0);
                    level.addFreshEntity(itemEntity);
                    ExperienceOrb.award((ServerLevel)level, Vec3.atCenterOf(pos), 200);
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getLidTimer() {
        return lidTimer;
    }

    public void setLidTimer(int lidTimer) {
        this.lidTimer = lidTimer;
    }
}
