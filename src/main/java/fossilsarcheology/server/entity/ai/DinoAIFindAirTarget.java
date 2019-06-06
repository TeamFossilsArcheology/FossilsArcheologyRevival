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

	public static BlockPos getBlockInView(EntityPrehistoricFlying dinosaur) {
		float radius = 0.75F * (0.7F * 4) * -3 - dinosaur.getRNG().nextInt(20);
		float neg = dinosaur.getRNG().nextBoolean() ? 1 : -1;
		float angle = (0.01745329251F * dinosaur.renderYawOffset) + 3.15F + (dinosaur.getRNG().nextFloat() * neg);
		double extraX = (double) (radius * MathHelper.sin((float) (Math.PI + angle)));
		double extraZ = (double) (radius * MathHelper.cos(angle));
		BlockPos radialPos = new BlockPos(dinosaur.posX + extraX, 0, dinosaur.posZ + extraZ);
		BlockPos ground = dinosaur.world.getHeight(radialPos);
		int distFromGround = (int) dinosaur.posY - ground.getY();
		BlockPos newPos = radialPos.up(distFromGround > 16 ? (int) Math.min(Revival.CONFIG_OPTIONS.flyingTargetMaxHeight, dinosaur.posY + dinosaur.getRNG().nextInt(16) - 8) : (int) dinosaur.posY + dinosaur.getRNG().nextInt(16) + 1);
        if (!isTargetBlocked(dinosaur, new Vec3d(newPos)) && dinosaur.getDistanceSqToCenter(newPos) > 6) {
			return newPos;
		}
		return null;
	}

	public static boolean isTargetBlocked(Entity entity, Vec3d target) {
		if (target != null) {
			RayTraceResult rayTrace = entity.world.rayTraceBlocks(new Vec3d(entity.getPosition()), target, false);
			if (rayTrace != null && rayTrace.hitVec != null) {
				BlockPos sidePos = rayTrace.getBlockPos();
				BlockPos pos = new BlockPos(rayTrace.hitVec);
				if (!entity.world.isAirBlock(pos) || !entity.world.isAirBlock(sidePos) ) {
					return true;
				}
			}
		}
		return false;
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
			if (prehistoric.airTarget != null && (prehistoric.getDistanceSquared(new Vec3d(prehistoric.airTarget.getX(), prehistoric.posY, prehistoric.airTarget.getZ())) > 3  || isTargetBlocked(prehistoric, new Vec3d(prehistoric.airTarget)))) {
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
		return prehistoric.airTarget != null && !isTargetBlocked(prehistoric, new Vec3d(prehistoric.airTarget));
	}

	public BlockPos findAirTarget() {
		if (prehistoric.getAttackTarget() == null) {
			for (int i = 0; i < 10; i++) {
				BlockPos pos = getBlockInView(prehistoric);
				if (pos != null && prehistoric.world.getBlockState(pos).getMaterial() == Material.AIR && !isTargetBlocked(prehistoric, new Vec3d(pos))) {
					return pos;
				}
			}
		} else {
			BlockPos pos = new BlockPos((int) prehistoric.getAttackTarget().posX, (int) prehistoric.getAttackTarget().posY, (int) prehistoric.getAttackTarget().posZ);
			if (prehistoric.world.getBlockState(pos).getMaterial() == Material.AIR) {
				return pos;
			}
		}
		return prehistoric.getPosition();
	}
}
