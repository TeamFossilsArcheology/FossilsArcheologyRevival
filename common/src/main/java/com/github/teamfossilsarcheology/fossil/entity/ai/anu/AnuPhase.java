package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.google.common.collect.ImmutableList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public enum AnuPhase {
    MELEE(AnuMeleePhase.class, 1),
    FLIGHT(AnuFlightPhase.class, 0.66f),
    LANDING(AnuLandingPhase.class, -1),
    DEFENSE(AnuDefensePhase.class, 0.2f);

    private static final List<AnuPhase> BY_DAMAGE;

    static {
        BY_DAMAGE = Stream.of(AnuPhase.values()).sorted(Comparator.comparingDouble(mode -> mode.healthFraction)).collect(ImmutableList.toImmutableList());
    }

    private final Class<? extends AnuPhaseInstance> instanceClass;
    private final float healthFraction;

    AnuPhase(Class<? extends AnuPhaseInstance> clazz, float f) {
        this.instanceClass = clazz;
        this.healthFraction = f;
    }

    public static boolean isLower(AnuPhase oldPhase, AnuPhase newPhase) {
        for (AnuPhase anuPhase : BY_DAMAGE) {
            if (anuPhase == oldPhase) return true;
            if (anuPhase == newPhase) return false;
        }
        return true;
    }

    public static AnuPhase byFraction(float fraction) {
        for (AnuPhase mode : BY_DAMAGE) {
            if (fraction >= mode.healthFraction) continue;
            return mode;
        }
        return MELEE;
    }

    public AnuPhaseInstance createInstance(AnuBoss anu) {
        try {
            return instanceClass.getConstructor(AnuBoss.class).newInstance(anu);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
