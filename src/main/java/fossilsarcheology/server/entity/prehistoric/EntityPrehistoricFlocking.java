package fossilsarcheology.server.entity.prehistoric;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public abstract class EntityPrehistoricFlocking extends EntityPrehistoric {

    private int groupSize = 1;
    private EntityPrehistoricFlocking groupLeader;

    public EntityPrehistoricFlocking(World world, PrehistoricEntityType type, double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed, double baseArmor, double maxArmor) {
        super(world, type, baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed, baseArmor, maxArmor);
    }

    public boolean doesFlock() {
        return true;
    }

    public void leaveGroup() {
        this.groupLeader.decreaseGroupSize();
        this.groupLeader = null;
    }

    protected boolean hasNoLeader() {
        return !this.hasGroupLeader();
    }

    public boolean hasGroupLeader() {
        return this.groupLeader != null && this.groupLeader.isEntityAlive();
    }

    private void increaseGroupSize() {
        ++this.groupSize;
    }

    private void decreaseGroupSize() {
        --this.groupSize;
    }

    public boolean canGroupGrow() {
        return this.isGroupLeader() && this.groupSize < this.getMaxGroupSize();
    }

    public boolean isGroupLeader() {
        return this.groupSize > 1;
    }

    public boolean inRangeOfGroupLeader() {
        return this.getDistanceSq(this.groupLeader) <= 121.0D;
    }

    public void moveToGroupLeader() {
        if (this.hasGroupLeader() ) {
            Vec3d vec = RandomPositionGenerator.getLandPos(this.groupLeader, 5, 7);
            if(this.getDistanceSq(this.groupLeader) <= 44.0D){
                vec = RandomPositionGenerator.getLandPos(this, 6, 7);
            }
            if(vec != null){
                this.getNavigator().tryMoveToXYZ(vec.x, vec.y, vec.z, 1);

            }
        }

    }

    public EntityPrehistoricFlocking createAndSetLeader(EntityPrehistoricFlocking leader) {
        this.groupLeader = leader;
        leader.increaseGroupSize();
        return leader;
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        if (livingdata == null) {
            livingdata = new EntityPrehistoricFlocking.GroupData(this);
        } else {
            this.createAndSetLeader(((EntityPrehistoricFlocking.GroupData)livingdata).groupLeader);
        }

        return livingdata;
    }

    public void createFromStream(Stream<EntityPrehistoricFlocking> stream) {
        stream.limit((long)(this.getMaxGroupSize() - this.groupSize)).filter((fishe) -> {
            return fishe != this;
        }).forEach((fishe) -> {
            fishe.createAndSetLeader(this);
        });
    }

    protected abstract int getMaxGroupSize();

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isGroupLeader() && this.world.rand.nextInt(200) == 1) {
            List<EntityPrehistoricFlocking> lvt_1_1_ = this.world.getEntitiesWithinAABB(this.getClass(), this.getEntityBoundingBox().grow(getGroupDist(), getGroupDist(), getGroupDist()));
            if (lvt_1_1_.size() <= 1) {
                this.groupSize = 1;
            }
        }
    }

    protected double getGroupDist(){
        return 32.0D;
    }

    public boolean shouldFollowFlock() {
        return !this.isSleeping() && !this.isSitting() && this.getOrderType() != OrderType.FOLLOW;
    }

    public boolean shouldWanderInFlock() {
        return shouldFollowFlock() && this.getAttackTarget() == null;
    }

    public static class GroupData implements IEntityLivingData {
        public final EntityPrehistoricFlocking groupLeader;

        public GroupData(EntityPrehistoricFlocking groupLeaderIn) {
            this.groupLeader = groupLeaderIn;
        }
    }
}
