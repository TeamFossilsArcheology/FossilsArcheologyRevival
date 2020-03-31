package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityMeganeura;
import fossilsarcheology.server.entity.prehistoric.OrderType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MeganeuraAIFollowOwner extends EntityAIBase {
    private final EntityMeganeura tameable;
    private final double followSpeed;
    World world;
    float maxDist;
    float minDist;
    private EntityLivingBase owner;
    private int timeToRecalcPath;
    private float oldWaterCost;

    public MeganeuraAIFollowOwner(EntityMeganeura tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
        this.tameable = tameableIn;
        this.world = tameableIn.world;
        this.followSpeed = followSpeedIn;
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.tameable.getOwner();

        if (entitylivingbase == null) {
            return false;
        } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).isSpectator()) {
            return false;
        }
        if (this.tameable.currentOrder != OrderType.FOLLOW) {
            return false;
        } else if (this.tameable.getDistanceSq(entitylivingbase) < (double) (this.minDist * this.minDist)) {
            return false;
        } else {
            this.owner = entitylivingbase;
            return true;
        }
    }

    public boolean shouldContinueExecuting() {
        return this.tameable.getMoveHelper().action != EntityMoveHelper.Action.WAIT || this.tameable.getDistanceSq(this.owner) > (double) (this.maxDist * this.maxDist) && !this.tameable.isSitting();
    }

    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tameable.getPathPriority(PathNodeType.WATER);
        this.tameable.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    public void resetTask() {
        this.owner = null;
        this.tameable.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
        this.tameable.getMoveHelper().action = EntityMoveHelper.Action.WAIT;
    }

    private boolean isEmptyBlock(BlockPos pos) {
        IBlockState iblockstate = this.world.getBlockState(pos);
        return iblockstate.getMaterial() == Material.AIR || !iblockstate.isFullCube();
    }

    @SuppressWarnings("deprecation")
    public void updateTask() {
        this.tameable.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, (float) this.tameable.getVerticalFaceSpeed());

        if (!this.tameable.isSitting()) {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;

                this.tameable.getMoveHelper().setMoveTo(this.owner.posX, this.owner.posY + this.owner.getEyeHeight(), this.owner.posZ, 0.25D);
                if (!this.tameable.getLeashed()) {
                    if (this.tameable.getDistanceSq(this.owner) >= 50.0D) {
                        int i = MathHelper.floor(this.owner.posX) - 2;
                        int j = MathHelper.floor(this.owner.posZ) - 2;
                        int k = MathHelper.floor(this.owner.getEntityBoundingBox().minY);

                        for (int l = 0; l <= 4; ++l) {
                            for (int i1 = 0; i1 <= 4; ++i1) {
                                if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isEmptyBlock(new BlockPos(i + l, k, j + i1)) && this.isEmptyBlock(new BlockPos(i + l, k + 1, j + i1))) {
                                    this.tameable.setLocationAndAngles((float) (i + l) + 0.5F, (double) k + 1.5, (float) (j + i1) + 0.5F, this.tameable.rotationYaw, this.tameable.rotationPitch);
                                    this.tameable.getMoveHelper().action = EntityMoveHelper.Action.WAIT;
                                    return;
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}