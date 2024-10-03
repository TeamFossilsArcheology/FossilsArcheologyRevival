package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.BubbleBlowerBlock;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BubbleBlowerBlockEntity extends BlockEntity {
    private final AABB area;

    public BubbleBlowerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.BUBBLE_BLOWER.get(), blockPos, blockState);
        Vec3 pos = Vec3.atCenterOf(blockPos);
        this.area = new AABB(pos, pos).inflate(1.5);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState blockState, BubbleBlowerBlockEntity blockEntity) {
        if (blockState.getValue(BubbleBlowerBlock.ACTIVE)) {
            for (Prehistoric prehistoric : level.getEntitiesOfClass(Prehistoric.class, blockEntity.area)) {
                prehistoric.moodSystem.useToy(15);
            }
        }
    }
}
