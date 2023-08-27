package com.fossil.fossil.entity.ai;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Gender;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DinoAIMating extends Goal {
    private final double speedModifier;
    private final Prehistoric male;
    private Prehistoric female;

    public DinoAIMating(Prehistoric dinosaur, double speed) {
        this.male = dinosaur;
        this.speedModifier = speed;
    }

    @Override
    public boolean canUse() {
        if (!FossilConfig.isEnabled("breedingDinos")) {
            return false;
        }
        if (male.getGender() != Gender.MALE || !male.isAdult() || male.getMatingTick() > 0 || male.moodSystem.getMood() <= 50) {
            return false;
        }
        List<? extends Prehistoric> sameTypes = male.getNearbySpeciesMembers(64);
        if (sameTypes.size() > male.data().maxPopulation()) {
            male.setMatingTick(male.getRandom().nextInt(6000) + 6000);
            return false;
        }
        double shortestDistance = Double.MAX_VALUE;
        for (Prehistoric sameType : sameTypes) {
            if (!male.canMate(sameType) || male.distanceToSqr(sameType) > shortestDistance) {
                continue;
            }
            female = sameType;
            shortestDistance = male.distanceToSqr(female);
        }
        return female != null;
    }

    @Override
    public boolean canContinueToUse() {
        return female.isAlive() && male.canMate(female);
    }

    @Override
    public void stop() {
        female = null;
    }

    @Override
    public void tick() {
        male.getLookControl().setLookAt(female, 10, male.getMaxHeadXRot());
        male.getNavigation().moveTo(female, speedModifier);
        if (male.closerThan(female, male.getBbWidth() * 1.5)) {
            female.procreate(male);
            male.setMatingTick(male.getRandom().nextInt(6000) + 6000);
            female.setMatingTick(male.getRandom().nextInt(12000) + 24000);
        }
    }

    @Nullable
    public Prehistoric getPartner() {
        return female;
    }
}
