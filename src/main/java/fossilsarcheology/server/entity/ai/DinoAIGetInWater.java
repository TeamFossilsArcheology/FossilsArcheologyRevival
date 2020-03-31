package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoricSwimming;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class DinoAIGetInWater extends EntityAIBase {
    protected final EntityPrehistoricSwimming dino;
    private final double movementSpeed;
    private final World world;
    private double shelterX;
    private double shelterY;
    private double shelterZ;

    public DinoAIGetInWater(EntityPrehistoricSwimming dino, double movementSpeedIn) {
        this.dino = dino;
        this.movementSpeed = movementSpeedIn;
        this.world = dino.world;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (dino.isBeingRidden() || !dino.shouldEnterWater() || dino instanceof EntityTameable && ((EntityTameable) dino).isSitting() || dino.isInWater() || dino.getAttackTarget() != null && !dino.getAttackTarget().isInWater()) {
            return false;
        } else {
            Vec3d vec3d = this.findPossibleShelter();

            if (vec3d == null) {
                return false;
            } else {
                this.shelterX = vec3d.x;
                this.shelterY = vec3d.y;
                this.shelterZ = vec3d.z;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.dino.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.dino.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    @Nullable
    protected Vec3d findPossibleShelter() {
        Random random = this.dino.getRNG();
        BlockPos blockpos = new BlockPos(this.dino.posX, this.dino.getEntityBoundingBox().minY, this.dino.posZ);

        for (int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

            if (this.world.getBlockState(blockpos1).getMaterial() == Material.WATER) {
                return new Vec3d((double) blockpos1.getX() + 0.5D, blockpos1.getY(), (double) blockpos1.getZ() + 0.5D);
            }
        }

        return null;
    }
}
