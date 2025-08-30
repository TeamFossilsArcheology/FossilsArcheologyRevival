package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlocking;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DinoHurtByTargetGoal extends HurtByTargetGoal {
    public DinoHurtByTargetGoal(Prehistoric dino) {
        super(dino);
    }

    @Override
    public void start() {
        if (((Prehistoric) mob).aiResponseType() == PrehistoricEntityInfoAI.Response.SCARED) {
            alertOthers();
            stop();
        } else {
            super.start();
        }
    }

    @Override
    protected void alertOthers() {
        if (mob instanceof PrehistoricFlocking flocking) {
            double d = this.getFollowDistance();
            AABB aABB = AABB.unitCubeFromLowerCorner(mob.position()).inflate(d, 10.0, d);
            List<? extends PrehistoricFlocking> list = mob.level().getEntitiesOfClass(flocking.getClass(), aABB, EntitySelector.NO_SPECTATORS);
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

    @Override
    protected void alertOther(Mob mob, LivingEntity target) {
        if (mob instanceof PrehistoricFlocking flocking && flocking.aiResponseType() == PrehistoricEntityInfoAI.Response.SCARED) {
            flocking.setFlockAttacked(target);
        } else {
            super.alertOther(mob, target);
        }
    }
}
