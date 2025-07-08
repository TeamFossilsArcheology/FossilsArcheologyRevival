package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AnuMeleePhase extends AbstractAnuPhaseInstance {
    private final MeleeAttackGoal attackGoal;
    private boolean attackGoalActive;

    public AnuMeleePhase(AnuBoss anuBoss) {
        super(anuBoss);
        this.attackGoal = new MeleeAttackGoal(anu, 1.2, false);
    }

    @Override
    public void doClientTick() {
        if (anu.isWeak()) {
            for (int i = 0; i < 2; ++i) {
                anu.level().addParticle(ParticleTypes.ELECTRIC_SPARK, anu.getRandomX(0.5), anu.getRandomY(), anu.getRandomZ(0.5), 0, 0, 0);
            }
        }
    }

    @Override
    public void doServerTick() {
        if (attackGoalActive && attackGoal.canContinueToUse()) {
            attackGoal.tick();
        } else if (attackGoal.canUse()) {
            attackGoal.start();
            attackGoalActive = true;
        } else {
            attackGoal.stop();
            attackGoalActive = false;
        }
        switchPhaseByHealth();
    }

    @Override
    protected void switchPhaseByHealth() {
        AnuPhase newPhase = AnuPhase.byFraction(anu.getHealth() / anu.getMaxHealth());
        if (newPhase == AnuPhase.DEFENSE || anu.level().getGameTime() > endTime) {
            anu.phaseSystem.setPhase(newPhase, TIMEOUT);
        }
    }

    @Override
    public AnuPhase getPhase() {
        return AnuPhase.MELEE;
    }

    @Override
    public void end() {
        if (attackGoalActive) {
            attackGoal.stop();
            attackGoalActive = false;
        }
        anu.setWeak(false);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.ANU_LAUGH.get();
    }

    @Override
    public boolean isFlying() {
        return false;
    }
}
