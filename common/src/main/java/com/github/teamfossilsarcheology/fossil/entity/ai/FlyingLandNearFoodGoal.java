package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.block.entity.FeederBlockEntity;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * This goal just tries to land the mob near a feeder. Afterward {@link FlyingEatFromFeederGoal} will do the rest
 */
public class FlyingLandNearFoodGoal extends MoveToFoodGoal {
    protected final PrehistoricFlying dino;
    private final int chunkRadius;
    private Vec3 targetPos;

    public FlyingLandNearFoodGoal(PrehistoricFlying dino) {
        super(dino, 1, 32);
        this.dino = dino;
        this.chunkRadius = 2;
    }

    @Override
    public boolean canUse() {
        if (!dino.isFlying()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (!dino.isFlying()) {
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    protected boolean createPath() {
        return targetPos != null;
    }

    @Override
    protected void moveMobToBlock() {
        dino.moveTo(targetPos, true, true);
    }

    @Override
    public void tick() {
    }

    @Override
    protected boolean findNearestBlock() {
        BlockPos mobPos = dino.blockPosition();
        //chunkRadius of 2 is 25 chunks. Should not be too slow
        Optional<BlockPos> target = ChunkPos.rangeClosed(new ChunkPos(mobPos), chunkRadius)
                .flatMap(chunkPos -> dino.level().getChunk(chunkPos.x, chunkPos.z).getBlockEntities().entrySet().stream())
                .filter(this::isValidTarget)
                .map(Map.Entry::getKey)
                .min(Comparator.comparingInt(pos -> pos.distManhattan(mobPos)));
        if (target.isPresent()) {
            targetPos = Vec3.atCenterOf(target.get()).add(0, 1, 0);
            return true;
        }
        return false;
    }

    private boolean isValidTarget(Map.Entry<BlockPos, BlockEntity> entry) {
        return entry.getValue() instanceof FeederBlockEntity feeder && !feeder.isEmpty(dino.data().diet()) && Util.canSeeFood(dino, entry.getKey());
    }
}
