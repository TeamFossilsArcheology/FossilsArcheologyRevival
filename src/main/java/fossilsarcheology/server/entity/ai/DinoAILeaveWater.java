package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoricSwimming;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class DinoAILeaveWater extends EntityAIBase {
    private final EntityPrehistoricSwimming dino;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private final double movementSpeed;
    private final World world;

    public DinoAILeaveWater(EntityPrehistoricSwimming thedinoIn, double movementSpeedIn) {
        this.dino = thedinoIn;
        this.movementSpeed = movementSpeedIn;
        this.world = thedinoIn.world;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (!dino.isInWater() || !dino.shouldLeaveWater()) {
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

    public void updateTask() {
        double dist = this.dino.getDistance(this.shelterX + 0.5F, this.shelterY + 1.5F, this.shelterZ + 0.5F);
        BlockPos blockpos = new BlockPos(this.shelterX, this.shelterY, this.shelterZ);
        if(shelterY > this.dino.getEntityBoundingBox().minY){
            this.dino.getJumpHelper().setJumping();
            this.dino.motionY += 0.25F;
            this.dino.motionX += (Math.signum(blockpos.getX() + 0.5D - this.dino.posX) * 0.5D - this.dino.motionX) * 0.100000000372529;
            this.dino.motionZ += (Math.signum(blockpos.getZ() + 0.5D - this.dino.posZ) * 0.5D - this.dino.motionZ) * 0.100000000372529;
        }
        this.dino.getMoveHelper().setMoveTo(this.shelterX + 0.5F, this.shelterY + 1.5F, this.shelterZ + 0.5F, this.movementSpeed);
    }

    public boolean shouldContinueExecuting() {
        return this.dino.isInWater();
    }

    public void startExecuting() {
        this.dino.getNavigator().tryMoveToXYZ(this.shelterX + 0.5F, this.shelterY + 1.5F, this.shelterZ + 0.5F, this.movementSpeed);
    }

    @Nullable
    private Vec3d findPossibleShelter() {
        Random random = this.dino.getRNG();
        BlockPos blockpos = new BlockPos(this.dino.posX, this.dino.posY, this.dino.posZ);

        for (int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, 1 + random.nextInt(6), random.nextInt(20) - 10);
            if (this.world.getBlockState(blockpos1).isOpaqueCube() && world.isAirBlock(blockpos1.up()) && blockpos1.getY() >= blockpos.getY()) {
                return new Vec3d((double) blockpos1.getX(), (double) blockpos1.getY(), (double) blockpos1.getZ());
            }
        }

        return null;
    }
}