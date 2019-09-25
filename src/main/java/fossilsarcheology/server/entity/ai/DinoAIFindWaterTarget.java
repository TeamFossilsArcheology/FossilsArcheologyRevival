package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoricSwimming;
import fossilsarcheology.server.entity.prehistoric.OrderType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DinoAIFindWaterTarget extends EntityAIBase {
	protected final DinoAIFindWaterTarget.Sorter fleePosSorter;
	private final EntityCreature mob;
	private final int range;
	private final boolean avoidAttacker;

	public DinoAIFindWaterTarget(EntityCreature mob, int range, boolean avoidAttacker) {
		this.mob = mob;
		this.range = range;
		this.avoidAttacker = avoidAttacker;
		this.setMutexBits(1);
		fleePosSorter = new Sorter(mob);
	}

	@Override
	public boolean shouldExecute() {
		if (!this.mob.isInWater() || this.mob.isRiding() || this.mob instanceof EntityPrehistoricSwimming && (((EntityPrehistoricSwimming) this.mob).currentOrder != OrderType.WANDER || ((EntityPrehistoricSwimming) this.mob).shouldLeaveWater() )) {
			return false;
		}
		if (this.mob.getRNG().nextFloat() < 0.5F) {
			Path path = this.mob.getNavigator().getPath();
			if (!this.mob.getNavigator().noPath() && !isDirectPathBetweenPoints(this.mob, this.mob.getPositionVector(), new Vec3d(path.getFinalPathPoint().x, path.getFinalPathPoint().y, path.getFinalPathPoint().z)) || path != null && path.getFinalPathPoint() != null &&  this.mob.getDistanceSq(path.getFinalPathPoint().x, path.getFinalPathPoint().y, path.getFinalPathPoint().z) < 3) {
				this.mob.getNavigator().clearPath();
			}
			if (this.mob.getNavigator().noPath()) {
				BlockPos vec3 = this.findWaterTarget();
				if (vec3 != null) {
					this.mob.getNavigator().tryMoveToXYZ(vec3.getX(), vec3.getY(), vec3.getZ(), 1.0);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean shouldContinueExecuting() {
		return false;
	}

	public BlockPos findWaterTarget() {
		Random rand = this.mob.getRNG();
		if (this.mob.getAttackTarget() == null || this.mob.getAttackTarget().isDead) {
			for(int i = 0; i < 20; i++){
				BlockPos randPos = this.mob.getPosition().add(rand.nextInt(16) - 8, rand.nextInt(8) - 4, rand.nextInt(16) - 8);
				if (this.mob.world.getBlockState(randPos).getMaterial() == Material.WATER && isDirectPathBetweenPoints(mob, this.mob.getPositionVector(), new Vec3d(randPos.getX() + 0.5, randPos.getY() + 0.5, randPos.getZ() + 0.5))) {
					return randPos;
				}
			}
		} else {
			BlockPos blockpos1 = new BlockPos(this.mob.getAttackTarget());
			return new BlockPos((double) blockpos1.getX(), (double) blockpos1.getY(), (double) blockpos1.getZ());
		}
		return null;
	}


	public boolean isDirectPathBetweenPoints(Entity entity, Vec3d vec1, Vec3d vec2) {
		RayTraceResult movingobjectposition = entity.world.rayTraceBlocks(vec1, new Vec3d(vec2.x, vec2.y + (double) entity.height * 0.5D, vec2.z), false, true, false);
		return movingobjectposition == null || movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK;
	}

	public class Sorter implements Comparator<BlockPos> {
		private BlockPos pos;

		public Sorter(Entity theEntityIn) {
			this.pos = theEntityIn.getPosition();
		}

		//further; more prefered.
		@Override
		public int compare(BlockPos p_compare_1_, BlockPos p_compare_2_) {
			this.pos = DinoAIFindWaterTarget.this.mob.getPosition();
			double d0 = this.pos.distanceSq(p_compare_1_);
			double d1 = this.pos.distanceSq(p_compare_2_);
			return Double.compare(d1, d0);
		}
	}
}
