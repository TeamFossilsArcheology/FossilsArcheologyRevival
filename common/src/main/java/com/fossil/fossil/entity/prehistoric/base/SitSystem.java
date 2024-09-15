package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.nbt.CompoundTag;

import static com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI.Moving;

public class SitSystem extends AISystem {
    protected boolean sittingDisabled;
    private int ticksSat;
    private int sitCooldown = 20 * 60 * 3;

    public SitSystem(Prehistoric mob) {
        super(mob);
    }

    @Override
    public void serverTick() {
        if (sitCooldown > 0) {
            sitCooldown--;
        }
        if (!isSitting() && canSit() && (sitCooldown == 0 && mob.getRandom().nextInt(1000) == 0 || mob.getCurrentOrder() == OrderType.STAY)) {
            setSitting(true);
        }
        if (isSitting()) {
            ticksSat++;
            if (mob.getCurrentOrder() != OrderType.STAY) {
                if (ticksSat > 300 && mob.getRandom().nextInt(100) == 0 || !canSit()) {
                    setSitting(false);
                }
            }
        }
    }

    /**
     * @return whether something is preventing the mob from sitting
     */
    protected boolean canSit() {
        if (sittingDisabled || mob.hasTarget() || mob.getLastHurtByMob() != null || mob.getCurrentOrder() == OrderType.FOLLOW) {
            return false;
        }
        if ((mob.aiMovingType() == Moving.AQUATIC)) {
            return mob.isInWater();
        } else if (mob.aiMovingType() == Moving.SEMI_AQUATIC) {
            return mob.isInWater() || mob.isOnGround();
        } else {
            return mob.isOnGround();
        }
    }

    public void setSittingDisabled(boolean sittingDisabled) {
        this.sittingDisabled = sittingDisabled;
    }

    public boolean isSitting() {
        return mob.getEntityData().get(Prehistoric.SITTING);
    }

    public void setSitting(boolean sitting) {
        mob.getEntityData().set(Prehistoric.SITTING, sitting);
        if (sitting) {
            ticksSat = 0;
            mob.getNavigation().stop();
            mob.setDeltaMovement(0, mob.getDeltaMovement().y, 0);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putBoolean("Sitting", isSitting());
    }

    @Override
    public void load(CompoundTag tag) {
        setSitting(tag.getBoolean("Sitting"));
    }
}
