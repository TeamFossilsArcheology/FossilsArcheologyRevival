package com.fossil.fossil.entity.ai;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Gender;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DinoAIMating extends BreedGoal {

    public DinoAIMating(Prehistoric dinosaur, double speed) {
        super(dinosaur, speed, Prehistoric.class);
    }

    @Override
    public boolean canUse() {
        if (!FossilConfig.isEnabled("breedingDinos")) return false;
        Prehistoric dinosaur = (Prehistoric) animal;
        if (!dinosaur.isAdult()) return false;
        if (dinosaur.ticksTillMate > 0) return false;
        if (dinosaur.getMood() <= 50) return false;

        AABB area = AABB.ofSize(dinosaur.position(), 32, 32, 32);

        List<Prehistoric> sameTypes = dinosaur.level.getEntitiesOfClass(Prehistoric.class, area);
        if (sameTypes.size() > dinosaur.getMaxPopulation()) {
            dinosaur.ticksTillMate = dinosaur.getRandom().nextInt(6000) + 6000;
            return false;
        }

        return super.canUse();
    }

    @Override
    protected void breed() {
        Prehistoric self = (Prehistoric) animal;
        Prehistoric partner = (Prehistoric) this.partner;
        Prehistoric female = null;

        if (self.getGender() == Gender.FEMALE) female = self;
        else if (partner.getGender() == Gender.FEMALE) female = partner;
        female.procreate(self);
        self.ticksTillMate = self.getRandom().nextInt(6000) + 6000;
        partner.ticksTillMate = partner.getRandom().nextInt(12000) + 24000;
    }

    @Nullable
    public Prehistoric getPartner() {
        return (Prehistoric) this.partner;
    }
}
