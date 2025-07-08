package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
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
    private final double speedModifier;
    private final double sprintModifier;
    private final float startDistanceSqr;
    private final float sprintDistanceSqr;
    private final float stopDistanceSqr;
    private final float teleportDistanceSqr;
    private final boolean canFly;
    private boolean shouldSprint;
    private LivingEntity owner;
    private float oldWaterCost;
    private int timeToRecalcPath;

    public DinoFollowOwnerGoal(Prehistoric dino, double sprintModifier, float startDistance, float stopDistance, boolean canFly) {
        this(dino, sprintModifier, startDistance, stopDistance, 18, canFly);
    }


    public DinoFollowOwnerGoal(Prehistoric dino, double sprintModifier, float startDistance, float stopDistance, float teleportDistance, boolean canFly) {
        this.dino = dino;
        this.speedModifier = 1;
        this.sprintModifier = sprintModifier;
        this.startDistanceSqr = startDistance * startDistance;
        this.sprintDistanceSqr = startDistanceSqr + 3;
        this.stopDistanceSqr = stopDistance * stopDistance;
        this.teleportDistanceSqr = teleportDistance * teleportDistance;
        this.canFly = canFly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity currentOwner = dino.getOwner();
        if (currentOwner == null || currentOwner.isSpectator()) {
            return false;
        } else if (dino.getCurrentOrder() != OrderType.FOLLOW) {
            return false;
        } else if (dino.distanceToSqr(currentOwner) < startDistanceSqr) {
            return false;
        }
        this.owner = currentOwner;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (dino.getNavigation().isDone() || dino.getCurrentOrder() != OrderType.FOLLOW) {
            return false;
        }
        return dino.distanceToSqr(owner) > stopDistanceSqr;
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        oldWaterCost = dino.getPathfindingMalus(BlockPathTypes.WATER);
        dino.setPathfindingMalus(BlockPathTypes.WATER, 0);
        shouldSprint = dino.distanceToSqr(owner) >= sprintDistanceSqr;
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
        --timeToRecalcPath;
        if (timeToRecalcPath > 0) {
            return;
        }
        timeToRecalcPath = adjustedTickDelay(10);
        if (dino.distanceToSqr(owner) >= sprintDistanceSqr) {
            shouldSprint = true;
        }
        if (!dino.isLeashed() && dino.distanceToSqr(owner) >= teleportDistanceSqr) {
            teleportToOwner();
            return;
        }
        dino.getNavigation().moveTo(owner, shouldSprint ? sprintModifier : speedModifier);
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
        Level level = dino.level();
        NodeEvaluator nodeEvaluator = dino.getNavigation().getNodeEvaluator();
        BlockPathTypes type = nodeEvaluator.getBlockPathType(level, teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
        if (type == BlockPathTypes.WATER && dino.getPathfindingMalus(type) == 0) {
            return level.noCollision(dino, dino.getBoundingBox().move(teleportPos.subtract(dino.blockPosition())));
        }
        if (type != BlockPathTypes.WALKABLE) {
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
