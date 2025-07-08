package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public final class AnuPhaseSystem {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final AnuBoss anu;
    private final AnuPhaseInstance[] phases = new AnuPhaseInstance[AnuPhase.values().length];
    @NotNull
    private AnuPhaseInstance currentPhase;

    public AnuPhaseSystem(AnuBoss anuBoss) {
        this.anu = anuBoss;
        currentPhase = getPhase(AnuPhase.MELEE);
    }

    public void setPhase(AnuPhase phase) {
        setPhase(phase, -1);
    }

    public void setPhase(AnuPhase phase, int duration) {
        if (phase != currentPhase.getPhase()) {
            currentPhase.end();

            currentPhase = getPhase(phase);
            if (!anu.level().isClientSide) {
                anu.getEntityData().set(AnuBoss.DATA_PHASE, phase.ordinal());
            }

            LOGGER.debug("Anu is now in phase {} on the {}", phase, anu.level().isClientSide ? "client" : "server");
            currentPhase.begin(duration);
        }
    }

    @NotNull
    public AnuPhaseInstance getCurrentPhase() {
        return currentPhase;
    }

    public AnuPhaseInstance getPhase(AnuPhase phase) {
        int i = phase.ordinal();
        if (phases[i] == null) {
            phases[i] = phase.createInstance(anu);
        }

        return phases[i];
    }
}
