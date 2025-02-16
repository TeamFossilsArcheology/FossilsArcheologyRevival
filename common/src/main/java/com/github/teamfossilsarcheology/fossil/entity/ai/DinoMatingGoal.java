package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class DinoMatingGoal extends Goal {
    private final double speedModifier;
    private final Prehistoric male;
    private Prehistoric female;

    public DinoMatingGoal(Prehistoric dinosaur, double speed) {
        this.male = dinosaur;
        this.speedModifier = speed;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!FossilConfig.isEnabled(FossilConfig.BREEDING_DINOS)) {
            return false;
        }
        if (male.getGender() != Gender.MALE || !male.isAdult() || male.getMatingCooldown() > 0 || male.moodSystem.getMood() <= 50) {
            return false;
        }
        List<? extends Prehistoric> sameTypes = male.getNearbySpeciesMembers(64);
        if (sameTypes.size() + 1 >= male.data().maxPopulation()) {
            male.setMatingCooldown(male.getRandom().nextInt(5000) + 5000);
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
            male.procreate(female);
            male.setMatingCooldown(male.getRandom().nextInt(6000) + 6000);
            female.setMatingCooldown(male.getRandom().nextInt(12000) + 24000);
        }
    }

    @Nullable
    public Prehistoric getPartner() {
        return female;
    }
}
