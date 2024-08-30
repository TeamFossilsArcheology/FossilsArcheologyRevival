package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlocking;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DinoHurtByTargetGoal extends HurtByTargetGoal {
    public DinoHurtByTargetGoal(Prehistoric dino) {
        super(dino);
        if (dino instanceof PrehistoricFlocking) {
            setAlertOthers();
        }
    }

    @Override
    public boolean canUse() {
        if (((Prehistoric) mob).aiResponseType() == PrehistoricEntityInfoAI.Response.SCARED) {
            return false;
        }
        return super.canUse();
    }

    @Override
    protected void alertOthers() {
        if (mob instanceof PrehistoricFlocking flocking) {
            double d = this.getFollowDistance();
            AABB aABB = AABB.unitCubeFromLowerCorner(mob.position()).inflate(d, 10.0, d);
            List<? extends PrehistoricFlocking> list = mob.level.getEntitiesOfClass(flocking.getClass(), aABB, EntitySelector.NO_SPECTATORS);
            for (PrehistoricFlocking other : list) {
                if (mob == other || other.getTarget() != null || other.isAlliedTo(mob.getLastHurtByMob())) {
                    continue;
                }
                if (flocking.isPartOfSameFlock(other)) {
                    alertOther(other, mob.getLastHurtByMob());
                }
            }
        } else {
            super.alertOthers();
        }
    }
}
