package com.github.teamfossilsarcheology.fossil.entity.prehistoric.system;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.OrderType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI;
import net.minecraft.nbt.CompoundTag;

/**
 * This system determines when a mob can and should sleep
 */
public class SleepSystem extends AISystem {
    private static final int MAX_SLEEP_DURATION = 5 * 60 * 20;
    private boolean sleepForced;
    private int ticksSlept;
    /**
     * Sleep cooldown for mobs with {@link PrehistoricEntityInfoAI.Activity#BOTH}
     */
    private int cathermalSleepCooldown = 5000 + mob.getRandom().nextInt(3000);

    public SleepSystem(Prehistoric mob) {
        super(mob);
    }

    @Override
    public void serverTick() {
        if (cathermalSleepCooldown > 0) {
            cathermalSleepCooldown--;
        }
        trySleeping();
        if (mob.isSleeping() && !sleepForced) {
            ticksSlept++;
            if (ticksSlept > 100 && mob.getRandom().nextInt(100) == 0 && !wantsToSleep()) {
                setSleeping(false);
            }
            if (ticksSlept > MAX_SLEEP_DURATION) {
                setSleeping(false);
            }
            if (mob.tickCount % 10 == 0 && !canSleep()) {
                setSleeping(false);
            }
        }
    }

    protected void trySleeping() {
        if (mob.isSleeping()) {
            return;
        }
        if (mob.aiActivityType() == PrehistoricEntityInfoAI.Activity.BOTH) {
            if (mob.getRandom().nextInt(1200) == 0 && wantsToSleep() && canSleep()) {
                setSleeping(true);
                ticksSlept = 0;
            }
        } else if (mob.aiActivityType() != PrehistoricEntityInfoAI.Activity.NO_SLEEP) {
            if (mob.getRandom().nextInt(200) == 0 && wantsToSleep() && canSleep()) {
                setSleeping(true);
                ticksSlept = 0;
            }
        }
    }

    /**
     * @return whether something is preventing the mob from sleeping
     */
    protected boolean canSleep() {
        if (isDisabled() || mob.hasTarget() || mob.getLastHurtByMob() != null || mob.getCurrentOrder() == OrderType.FOLLOW || mob.isVehicle()) {
            return false;
        }
        if (mob.isDeadlyHungry()) {
            return false;
        }
        if ((mob.aiMovingType() == PrehistoricEntityInfoAI.Moving.AQUATIC)) {
            return mob.isInWater();
        } else if (mob.aiMovingType() == PrehistoricEntityInfoAI.Moving.SEMI_AQUATIC) {
            return mob.isInWater() || mob.onGround();
        } else {
            return mob.onGround() && !mob.isInWater();
        }
    }

    /**
     * Returns whether the mob can sleep. Depends on the time of day and how long it has been asleep.
     *
     * @return whether the mob can sleep at the moment
     */
    public boolean wantsToSleep() {
        if (mob.aiActivityType() == PrehistoricEntityInfoAI.Activity.DIURNAL) {
            return !mob.level().isDay();
        } else if (mob.aiActivityType() == PrehistoricEntityInfoAI.Activity.NOCTURNAL) {
            if (!mob.level().isDay()) {
                return false;
            }
            if (mob.aiMovingType() == PrehistoricEntityInfoAI.Moving.AQUATIC || mob.aiMovingType() == PrehistoricEntityInfoAI.Moving.SEMI_AQUATIC) {
                return mob.isInWater() || !mob.level().canSeeSky(mob.blockPosition().above());
            }
            return !mob.level().canSeeSky(mob.blockPosition().above());
        }
        return mob.aiActivityType() == PrehistoricEntityInfoAI.Activity.BOTH && ticksSlept <= 4000 && cathermalSleepCooldown == 0;
    }

    /**
     * @param sleepForced if {@code true} the mob will not stop sleeping
     */
    public void setSleepForced(boolean sleepForced) {
        this.sleepForced = sleepForced;
    }

    public void setSleeping(boolean sleeping) {
        mob.getEntityData().set(Prehistoric.SLEEPING, sleeping);
        if (!sleeping) {
            cathermalSleepCooldown = 10000 + mob.getRandom().nextInt(6000);
            mob.updatePose();
        } else {
            ticksSlept = 0;
            mob.updatePose();
            mob.getNavigation().stop();
            mob.setDeltaMovement(0, mob.getDeltaMovement().y, 0);
            mob.hasImpulse = true;
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putBoolean("Sleeping", mob.isSleeping());
        tag.putInt("TicksSlept", ticksSlept);
        tag.putInt("CathermalTimer", cathermalSleepCooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        setSleeping(tag.getBoolean("Sleeping"));
        ticksSlept = tag.getInt("TicksSlept");
        cathermalSleepCooldown = tag.getInt("CathermalTimer");
    }
}
