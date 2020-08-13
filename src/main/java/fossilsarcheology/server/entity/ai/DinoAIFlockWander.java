package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityGallimimus;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DinoAIFlockWander<T extends EntityPrehistoric> extends EntityAIBase {
    protected final T entity;
    protected final int xzDist;
    protected double speed;
    protected double x;
    protected double y;
    protected double z;
    protected int executionChance;
    protected boolean mustUpdate;
    private int FLOCK_DISTANCE = 32;
    private Random speciesRand;
    private float possibleYawChange;
    private float baseSpeed;

    public DinoAIFlockWander(T creatureIn, int distance, float speed) {
        this(creatureIn, 1, distance, speed);
    }

    public DinoAIFlockWander(T creatureIn, int chance, int distance, float baseSpeed) {
        this.entity = creatureIn;
        this.xzDist = distance;
        this.executionChance = chance;
        this.setMutexBits(1);
        this.speciesRand = new Random(creatureIn.getClass().hashCode());
        this.baseSpeed = baseSpeed;
        this.speed = baseSpeed;
    }

    public boolean shouldExecute() {
        if (!entity.shouldWander || entity.isMovementBlockedSoft() || !entity.doesFlock()) {
            return false;
        }

        if(speciesRand.nextInt(9) == 0){
            Vec3d vec3d = this.getPosition();
            if (vec3d == null) {
                return false;
            } else {
                this.x = vec3d.x;
                this.y = vec3d.y;
                this.z = vec3d.z;
                this.mustUpdate = false;
                return true;
            }
        }
        return false;
    }

    @Nullable
    protected Vec3d getPosition() {
        Vec3d countedVec = new Vec3d(0, 0, 0);
        double tracingScale = 10;
        int counted = 0;
        int i = (int) Math.floor(entity.posX);
        int j = (int) Math.floor(entity.posY);
        int k = (int) Math.floor(entity.posZ);
        List<EntityPrehistoric> mobList = entity.world.getEntitiesWithinAABB(EntityPrehistoric.class, new AxisAlignedBB((double) i - FLOCK_DISTANCE, (double) j - FLOCK_DISTANCE, (double) k - FLOCK_DISTANCE, (double) i + FLOCK_DISTANCE, (double) j + FLOCK_DISTANCE, (double) k + FLOCK_DISTANCE));
        possibleYawChange = speciesRand.nextFloat() * 360 - 180F;
        for (EntityPrehistoric prehistoric : mobList) {
            if (prehistoric.doesFlock() && prehistoric.getClass() == entity.getClass()) {
                counted++;
                countedVec = countedVec.add(getVectorForRotation(prehistoric.rotationPitch, prehistoric.rotationYaw + possibleYawChange)).add(prehistoric.getPositionVector());
            }
        }
        Vec3d avgVec = new Vec3d(countedVec.x / (double) counted, countedVec.y / (double) counted, countedVec.z / (double) counted);
        EntityPrehistoric closestTo = this.entity;
        EntityPrehistoric furthestTo = this.entity;
        for (EntityPrehistoric prehistoric : mobList) {
            if (closestTo.getDistance(avgVec.x, avgVec.y, avgVec.z) > prehistoric.getDistance(avgVec.x, avgVec.y, avgVec.z)) {
                closestTo = prehistoric;
            }
            if (furthestTo.getDistance(avgVec.x, avgVec.y, avgVec.z) < furthestTo.getDistance(avgVec.x, avgVec.y, avgVec.z)) {
                furthestTo = prehistoric;
            }
        }
        Vec3d heightVec = closestTo.getPositionVector().add(0, closestTo.height, 0);
        Vec3d target = RandomPositionGenerator.findRandomTargetBlockTowards(furthestTo, 13, 7, heightVec.add(avgVec.scale(tracingScale)));
        if (target != null) {
            double distance = entity.getDistance(target.x, target.y, target.z);
            speed = baseSpeed + Math.max(distance * 0.0075F, 0.5F);
            entity.world.setBlockState(new BlockPos(target).down(), Blocks.DIAMOND_BLOCK.getDefaultState());
        }
        return target;
    }

    public boolean shouldContinueExecuting() {
        return !this.entity.getNavigator().noPath();
    }

    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
    }

    public void resetTask() {
    }

    public void makeUpdate() {
        this.mustUpdate = true;
    }

    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }

    private Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }
}