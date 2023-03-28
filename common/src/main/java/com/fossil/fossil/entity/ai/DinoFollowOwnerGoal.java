package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.OrderType;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;

import java.util.EnumSet;

/**
 * Custom Implementation of {@link FollowOwnerGoal} which starts even if the pet is sitting or sleeping and uses a different teleport check for
 * swimming or flying dinos.
 */
public class DinoFollowOwnerGoal extends Goal {
    private final Prehistoric dino;
    private LivingEntity owner;
    private final double speedModifier;
    private final float startDistance;
    private final float stopDistance;
    private final boolean canFly;
    private float oldWaterCost;
    private int timeToRecalcPath;

    public DinoFollowOwnerGoal(Prehistoric dino, double speedModifier, float startDistance, float stopDistance, boolean canFly) {
        this.dino = dino;
        this.speedModifier = speedModifier;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.canFly = canFly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = dino.getOwner();
        if (owner == null || owner.isSpectator()) {
            return false;
        } else if (dino.currentOrder != OrderType.FOLLOW) {
            return false;
        } else if (dino.hasPassenger(dino.getOwner())) {
            return false;
        } else if (dino.distanceToSqr(owner) < (startDistance * startDistance)) {
            return false;
        }
        this.owner = owner;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (dino.getNavigation().isDone()) {
            return false;
        }
        return !(dino.distanceToSqr(owner) <= (stopDistance * stopDistance));
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        oldWaterCost = dino.getPathfindingMalus(BlockPathTypes.WATER);
        dino.setPathfindingMalus(BlockPathTypes.WATER, 0);
        dino.setOrderedToSit(false);
        if (dino.isSleeping()) {
            dino.setSleeping(false);
        }
    }

    @Override
    public void stop() {
        owner = null;
        dino.getNavigation().stop();
        dino.setPathfindingMalus(BlockPathTypes.WATER, oldWaterCost);
    }

    @Override
    public void tick() {
        dino.getLookControl().setLookAt(owner, 10, dino.getMaxHeadXRot());
        if (dino.isOrderedToSit() || --timeToRecalcPath > 0) {
            return;
        }
        timeToRecalcPath = adjustedTickDelay(10);
        boolean move = dino.getNavigation().moveTo(owner, speedModifier);
        if (move) {
            return;
        }
        if (!dino.isLeashed() && dino.distanceToSqr(owner) >= 144) {
            teleportToOwner();
        }
    }

    private void teleportToOwner() {
        BlockPos ownerBlockPos = owner.blockPosition();
        for (int i = 0; i < 10; i++) {
            int x = ownerBlockPos.getX() + randomIntInclusive(-3, 3);
            int y = ownerBlockPos.getY() + randomIntInclusive(-1, 1);
            int z = ownerBlockPos.getZ() + randomIntInclusive(-3, 3);
            if (!maybeTeleportTo(x, y, z)) continue;
            return;
        }
    }

    private boolean maybeTeleportTo(int x, int y, int z) {
        if (Math.abs(x - owner.getX()) < 2 && Math.abs(z - owner.getZ()) < 2) {
            return false;
        }
        if (!canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        dino.moveTo(x + 0.5, y, z + 0.5, dino.getYRot(), dino.getXRot());
        dino.getNavigation().stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos teleportPos) {
        Level level = dino.getLevel();
        NodeEvaluator nodeEvaluator = dino.getNavigation().getNodeEvaluator();
        if (nodeEvaluator.getBlockPathType(level, teleportPos.getX(), teleportPos.getY(), teleportPos.getZ()) != BlockPathTypes.WALKABLE) {
            return false;
        }
        if (!canFly && level.getBlockState(teleportPos).getBlock() instanceof LeavesBlock) {
            return false;
        }
        return level.noCollision(dino, dino.getBoundingBox().move(teleportPos.subtract(dino.blockPosition())));
    }

    private int randomIntInclusive(int min, int max) {
        return dino.getRandom().nextInt(max - min + 1) + min;
    }


}
