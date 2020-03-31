package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.EntityFishBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FishAIFindWaterTarget extends EntityAIBase {
    private final EntityFishBase mob;

    public FishAIFindWaterTarget(EntityFishBase mob) {
        this.mob = mob;
    }

    @Override
    public boolean shouldExecute() {
        if (!this.mob.isInWater()) {
            return false;
        }
        if (this.mob.getRNG().nextFloat() < 0.5F) {
            Path path = this.mob.getNavigator().getPath();
            if (!this.mob.getNavigator().noPath() && !isDirectPathBetweenPoints(this.mob, this.mob.getPositionVector(), new Vec3d(path.getFinalPathPoint().x, path.getFinalPathPoint().y, path.getFinalPathPoint().z)) || path != null && path.getFinalPathPoint() != null && this.mob.getDistanceSq(path.getFinalPathPoint().x, path.getFinalPathPoint().y, path.getFinalPathPoint().z) < 3) {
                this.mob.getNavigator().clearPath();
            }
            if (this.mob.getNavigator().noPath()) {
                BlockPos vec3 = this.findWaterTarget();
                if (vec3 != null) {
                    this.mob.getNavigator().tryMoveToXYZ(vec3.getX() + 0.5D, vec3.getY() + 0.5D, vec3.getZ() + 0.5D, 1.0);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDirectPathBetweenPoints(Entity entity, Vec3d vec1, Vec3d vec2) {
        RayTraceResult movingobjectposition = entity.world.rayTraceBlocks(vec1, new Vec3d(vec2.x, vec2.y + (double) entity.height * 0.5D, vec2.z), false, true, false);
        return movingobjectposition == null || movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    public BlockPos findWaterTarget() {
        Random rand = this.mob.getRNG();
        if (this.mob.getAttackTarget() == null) {
            for (int i = 0; i < 10; i++) {
                BlockPos randPos = this.mob.getPosition().add(rand.nextInt(16) - 8, rand.nextInt(8) - 4, rand.nextInt(16) - 8);
                if (this.mob.world.getBlockState(randPos).getMaterial() == Material.WATER && this.mob.isDirectPathBetweenPoints(this.mob.getPositionVector(), new Vec3d(randPos.getX() + 0.5, randPos.getY() + 0.5, randPos.getZ() + 0.5))) {
                    return randPos;
                }
            }
        } else {
            BlockPos blockpos1;
            blockpos1 = new BlockPos(this.mob.getAttackTarget());
            if (this.mob.world.getBlockState(blockpos1).getMaterial() == Material.WATER) {
                return blockpos1;
            }
        }
        return null;
    }
}
