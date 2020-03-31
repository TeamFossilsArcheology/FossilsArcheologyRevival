package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DinoAIFleeBattle extends EntityAIBase {
    protected final EntityPrehistoric prehistoric;
    protected double speed;
    protected double randPosX;
    protected double randPosY;
    protected double randPosZ;

    public DinoAIFleeBattle(EntityPrehistoric prehistoric, double speedIn) {
        this.prehistoric = prehistoric;
        this.speed = speedIn;
        this.setMutexBits(0);
    }

    public boolean shouldExecute() {
        if (this.prehistoric.getRevengeTarget() == null || !this.prehistoric.isFleeing()) {
            return false;
        } else {
            return this.findRandomPosition();
        }
    }

    protected boolean findRandomPosition() {
        Entity e = this.prehistoric.getRevengeTarget();
        Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.prehistoric, 25, 10, new Vec3d(e.posX, e.posY, e.posZ));

        if (vec3d == null) {
            return false;
        } else {
            this.randPosX = vec3d.x;
            this.randPosY = vec3d.y;
            this.randPosZ = vec3d.z;
            return true;
        }
    }

    public void startExecuting() {
        this.prehistoric.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed + 0.25D);
    }

    public boolean shouldContinueExecuting() {
        return !this.prehistoric.getNavigator().noPath() && prehistoric.isFleeing();
    }
}