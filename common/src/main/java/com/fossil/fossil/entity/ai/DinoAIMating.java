package com.fossil.fossil.entity.ai;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Gender;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

// I know that its superclass sounds weird
public class DinoAIMating extends NearestAttackableTargetGoal<Prehistoric> {
    private final Prehistoric dinosaur;

    public DinoAIMating(Prehistoric dinosaur) {
        super(dinosaur, Prehistoric.class, 10, true, true, livingEntity -> {
            if (!(livingEntity instanceof Animal animal)) return false;
            return animal.canMate(dinosaur);
        });
        this.dinosaur = dinosaur;
        setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (!Fossil.CONFIG_OPTIONS.dinosaurBreeding) return false;
        if (!dinosaur.isAdult()) return false;
        if (dinosaur.ticksTillMate > 0) return false;
        if (dinosaur.getMood() <= 50) return false;

        double cramDist = getFollowDistance();
        AABB area = new AABB(dinosaur.getX() - cramDist,
                dinosaur.getY() - cramDist / 2,
                dinosaur.getZ() - cramDist,

                dinosaur.getX() + cramDist,
                dinosaur.getY() + cramDist,
                dinosaur.getZ() + cramDist);
        List<Prehistoric> sameTypes = dinosaur.level.getEntitiesOfClass(Prehistoric.class, area);
        if (sameTypes.size() > dinosaur.getMaxPopulation()) {
            dinosaur.ticksTillMate = dinosaur.getRandom().nextInt(6000) + 6000;
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (!super.canContinueToUse()) return false;
        if (!canUse()) return false;
        if (this.target == null) return true;

        Prehistoric target = (Prehistoric) this.target;

        double distance = dinosaur.getBbWidth() * 8.0F * dinosaur.getBbWidth() * 8.0F + target.getBbWidth();
        if (dinosaur.distanceToSqr(target.getX(), target.getBoundingBox().minY, target.getZ()) <= distance && target.isOnGround() && dinosaur.isOnGround()) {
            Prehistoric female = null;
            if (dinosaur.getGender() == Gender.FEMALE) female = dinosaur;
            else if (target.getGender() == Gender.FEMALE) female = target;
            female.procreate(dinosaur);
            dinosaur.ticksTillMate = dinosaur.getRandom().nextInt(6000) + 6000;
            target.ticksTillMate = target.getRandom().nextInt(12000) + 24000;
            return false;
        }
        return true;
    }
}
