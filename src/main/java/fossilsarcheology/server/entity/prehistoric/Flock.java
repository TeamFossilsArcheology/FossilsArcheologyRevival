package fossilsarcheology.server.entity.prehistoric;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flock {
	public final List<EntityPrehistoric> flockMembers = new ArrayList<>();
	public EntityPrehistoric flockLeader;
	public PrehistoricEntityType type;
	private BlockPos leaderTarget;

	public void createFlock(EntityPrehistoric creator) {
		flockMembers.add(creator);
		flockLeader = creator;

	}

	public void onUpdate() {
		if (flockLeader == null || flockLeader.isDead) {
			setNewLeader();
		}
		for (EntityPrehistoric member : flockMembers) {
			if (member != null && flockLeader != null && member.shouldFollowFlock()) {
				if ((member.getNavigator().noPath() || leaderTarget != null && member.getDistanceSqToCenter(leaderTarget) < 5) && member != this.flockLeader && leaderTarget != null) {
					BlockPos newPos = getGroundedPos(member.world, new BlockPos(leaderTarget.getX() + member.getRNG().nextInt(10) - 5, leaderTarget.getY() + 6, leaderTarget.getZ() + member.getRNG().nextInt(10) - 5));
					double dist = member.getDistanceSqToCenter(newPos);
					double speedMod = Math.min(0.25F, dist * 0.01);
					member.getNavigator().tryMoveToXYZ(newPos.getX() + 0.5D, newPos.getY() + 0.5D, newPos.getZ() + 0.5D, 1 + speedMod);
				}
			}
		}
		if (flockLeader != null) {
			if (!flockLeader.isMovementBlocked() && (flockLeader.getNavigator().noPath() || leaderTarget != null && flockLeader.getDistanceSqToCenter(leaderTarget) < 5) && flockLeader.shouldWanderInFlock()) {
				Vec3d vec = RandomPositionGenerator.findRandomTarget(flockLeader, 18, 7);
				if(vec != null){
					leaderTarget = new BlockPos(vec);
					this.flockLeader.getNavigator().tryMoveToXYZ(leaderTarget.getX() + 0.5D, leaderTarget.getY() + 0.5D, leaderTarget.getZ() + 0.5D, 1);
				}
			}
		}
		findNewMembers();
	}

	public void findNewMembers() {
		List list = this.flockLeader.world.getEntitiesWithinAABB(EntityPrehistoric.class, this.flockLeader.getEntityBoundingBox().expand(35, 10, 35));
		EntityPrehistoric entity1 = null;
		double d0 = Double.MAX_VALUE;

        for (Object aList : list) {
            EntityPrehistoric entity2 = (EntityPrehistoric) aList;
            if (entity2 != this.flockLeader && !this.flockMembers.contains(entity2) && entity2.type == this.flockLeader.type) {
                if(entity2.getDistance(this.flockLeader) < 100){
					this.flockMembers.add(entity2);
					entity2.flockObj = this;
				}
            }
        }
	}

	public void setNewLeader() {
		if(flockMembers.size() > 0){
			flockLeader = flockMembers.get(new Random().nextInt(flockMembers.size()));
		}else{
			disband();
		}
	}

	public void disband() {
		flockMembers.clear();
	}

	public static BlockPos getGroundedPos(World world, BlockPos pos) {
		BlockPos current = pos;
		while(world.isAirBlock(current.down())){
			current = current.down();
		}
		return current;
	}
}
