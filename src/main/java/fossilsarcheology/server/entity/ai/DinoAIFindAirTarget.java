package fossilsarcheology.server.entity.ai;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoricFlying;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;


public class DinoAIFindAirTarget extends EntityAIBase {
    private final EntityPrehistoricFlying prehistoric;

    public DinoAIFindAirTarget(EntityPrehistoricFlying prehistoric) {
        this.prehistoric = prehistoric;
    }

    @Override
    public boolean shouldExecute() {
        if (prehistoric != null) {
            if (!prehistoric.isFlying()) {
                return false;
            }
            if (prehistoric.isSitting()) {
                return false;
            }
            if (prehistoric.isChild()) {
                return false;
            }
            if (prehistoric.getOwner() != null && prehistoric.getPassengers().contains(prehistoric.getOwner())) {
                return false;
            }
            if (prehistoric.airTarget != null && (prehistoric.getDistanceSquared(new Vec3d(prehistoric.airTarget.getX(), prehistoric.posY, prehistoric.airTarget.getZ())) > 3 || EntityPrehistoricFlying.isTargetBlocked(prehistoric, new Vec3d(prehistoric.airTarget)))) {
                prehistoric.airTarget = null;
            }

            if (prehistoric.airTarget != null) {
                return false;
            } else {
                BlockPos pos = this.findAirTarget();

                if (pos == null) {
                    return false;
                } else {
                    prehistoric.airTarget = pos;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return prehistoric.airTarget != null && !EntityPrehistoricFlying.isTargetBlocked(prehistoric, new Vec3d(prehistoric.airTarget));
    }

    public BlockPos findAirTarget() {
        if (prehistoric.canSleep() && !prehistoric.isOverWater()) {
            for (int i = 0; i < 10; i++) {
                BlockPos pos = EntityPrehistoricFlying.getBlockInView(prehistoric);
                if (pos != null && prehistoric.world.getBlockState(pos).getMaterial() == Material.AIR && !EntityPrehistoricFlying.isTargetBlocked(prehistoric, new Vec3d(pos))) {
                    while (prehistoric.world.getBlockState(pos).getMaterial() == Material.AIR && pos.getY() > 3) {
                        pos = pos.down();
                    }
                    return pos.up();
                }
            }
        }
        if (prehistoric.getAttackTarget() == null) {
            return prehistoric.generateAirTarget();
        } else {
            BlockPos pos = new BlockPos((int) prehistoric.getAttackTarget().posX, (int) prehistoric.getAttackTarget().posY, (int) prehistoric.getAttackTarget().posZ);
            if (prehistoric.world.getBlockState(pos).getMaterial() == Material.AIR) {
                return pos;
            }
        }
        return prehistoric.getPosition();
    }
}
