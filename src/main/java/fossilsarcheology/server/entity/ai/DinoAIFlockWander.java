package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityGallimimus;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoricFlocking;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.*;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DinoAIFlockWander extends EntityAIBase {
    protected final EntityPrehistoricFlocking entity;
    private int navigateTimer;
    private int groupTimer;

    public DinoAIFlockWander(EntityPrehistoricFlocking creatureIn) {
        this.entity = creatureIn;
        this.groupTimer = this.resetTimer(entity);
    }

    protected int resetTimer(EntityPrehistoricFlocking dino) {
        return 10 + dino.getRNG().nextInt(200) % 20;
    }


    public boolean shouldExecute() {
        if (!entity.shouldWander || entity.isMovementBlockedSoft()) {
            return false;
        }
        if (this.entity.isGroupLeader()) {
            return false;
        } else if (this.entity.hasGroupLeader()) {
            return true;
        } else if (this.groupTimer > 0) {
            --this.groupTimer;
            return false;
        } else {
            this.groupTimer = this.resetTimer(this.entity);
            double dist = 100D;
            List<EntityPrehistoricFlocking> lvt_2_1_ = this.entity.world.getEntitiesWithinAABB(entity.getClass(), entity.getEntityBoundingBox().grow(dist, dist, dist), (dino) -> {
                return dino.canGroupGrow() || !dino.hasGroupLeader();
            });
            EntityPrehistoricFlocking lvt_3_1_ = lvt_2_1_.stream().filter(EntityPrehistoricFlocking::canGroupGrow).findAny().orElse(this.entity);
            lvt_3_1_.createFromStream(lvt_2_1_.stream().filter((fish) -> {
                return !fish.hasGroupLeader();
            }));
            return this.entity.hasGroupLeader();
        }
    }

    public boolean shouldContinueExecuting() {
        return this.entity.hasGroupLeader() && this.entity.inRangeOfGroupLeader();
    }

    public void startExecuting() {
        this.navigateTimer = 0;
    }

    public void resetTask() {
        this.entity.leaveGroup();
    }

    public void updateTask() {
        super.updateTask();
        if (--this.navigateTimer <= 0) {
            this.navigateTimer = 25;
            this.entity.moveToGroupLeader();
        }
    }
}