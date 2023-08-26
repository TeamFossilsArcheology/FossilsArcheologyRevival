package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.config.FossilConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class Pterosaurs extends Prehistoric implements FlyingAnimal {

    public Pterosaurs(EntityType<? extends Pterosaurs> entityType, Level level, boolean isMultiPart) {
        super(entityType, level, isMultiPart);
    }


    public @Nullable BlockPos getBlockInView() {
        float radius = -(random.nextInt(20) + 6.3f);
        float neg = random.nextBoolean() ? 1 : -1;
        float angle = (0.01745329251F * yBodyRot) + 3.15F + (random.nextFloat() * neg);
        double extraX = radius * Mth.sin((float) (Math.PI + angle));
        double extraZ = radius * Mth.cos(angle);
        BlockPos radialPos = new BlockPos(getX() + extraX, 0, getZ() + extraZ);
        BlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, radialPos);
        int distFromGround = (int) getY() - ground.getY();
        BlockPos newPos = radialPos.above(distFromGround > 16 ? (int) Math.min(FossilConfig.getInt("flyingTargetMaxHeight"), getY() + random.nextInt(16) - 8) : (int) getY() + random.nextInt(16) + 1);
        if (!isTargetBlocked(Vec3.atCenterOf(newPos)) && distanceToSqr(Vec3.atCenterOf(newPos)) > 6) {
            return newPos;
        }
        return null;
    }

    public boolean isTargetBlocked(Vec3 target) {
        if (target != null) {
            BlockHitResult hitResult = level.clip(new ClipContext(position(), target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            return !level.isEmptyBlock(new BlockPos(hitResult.getLocation())) || !level.isEmptyBlock(hitResult.getBlockPos());
        }
        return false;
    }

    public @Nullable BlockPos generateAirTarget() {
        BlockPos pos = null;
        for (int i = 0; i < 10; i++) {
            pos = getBlockInView();
            if (pos != null && level.isEmptyBlock(pos) && !isTargetBlocked(Vec3.atCenterOf(pos))) {
                return pos;
            }
        }
        return pos;
    }

    public abstract ServerAnimationInfo getTakeOffAnimation();
}
