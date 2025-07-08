package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractAnuPhaseInstance implements AnuPhaseInstance {
    protected static final int TIMEOUT = 5 * 20;
    protected final AnuBoss anu;
    protected final AABB arenaBounds;
    protected long endTime = -1;

    protected AbstractAnuPhaseInstance(AnuBoss anuBoss) {
        this.anu = anuBoss;
        Vec3 spawn = anuBoss.getSpawnPos();
        arenaBounds = new AABB(spawn.x - AnuBoss.ARENA_RADIUS, spawn.y - 10, spawn.z - AnuBoss.ARENA_RADIUS,
                spawn.x + AnuBoss.ARENA_RADIUS, spawn.y + 10, spawn.z + AnuBoss.ARENA_RADIUS);
    }

    protected void switchPhaseByHealth() {
        if (anu.level().getGameTime() > endTime) {
            AnuPhase newPhase = AnuPhase.byFraction(anu.getHealth() / anu.getMaxHealth());
            anu.phaseSystem.setPhase(newPhase, 5 * 20);
        }
    }

    @Override
    public void doClientTick() {

    }

    @Override
    public void doServerTick() {

    }

    @Override
    public void onHurt(DamageSource source, float amount) {

    }

    @Override
    public void begin(int duration) {
        endTime = anu.level().getGameTime() + duration;
    }

    @Override
    public void end() {

    }
}
