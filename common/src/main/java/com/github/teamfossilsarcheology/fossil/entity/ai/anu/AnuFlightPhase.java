package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;

public class AnuFlightPhase extends AbstractAnuPhaseInstance {
    private Vec3 targetLocation;
    private int seeTime;
    private int attackTime;
    private int fireballReturnedCount;
    private static final int MAX_ATTACK_TIME = 40;
    private static final int MIN_ATTACK_TIME = 20;

    public AnuFlightPhase(AnuBoss anuBoss) {
        super(anuBoss);
    }

    @Override
    public void doClientTick() {
        for (int i = 0; i < 2; ++i) {
            anu.level().addParticle(ParticleTypes.SMOKE, anu.getRandomX(0.5), anu.getRandomY(), anu.getRandomZ(0.5), 0, 0, 0);
        }
    }

    @Override
    public void doServerTick() {
        if (fireballReturnedCount == 3) {
            anu.phaseSystem.setPhase(AnuPhase.LANDING);
            return;
        }
        if (anu.tickCount % 20 == 0) {
            anu.heal(2);
        }
        if (!anu.onGround() && anu.getDeltaMovement().y < 0) {
            anu.setDeltaMovement(anu.getDeltaMovement().multiply(1, 0.6, 1));
        }
        //Flight
        if (targetLocation == null || !anu.getMoveControl().hasWanted()) {
            if (findRandomPosAwayFromPlayer()) {
                anu.getNavigation().stop();
                anu.getMoveControl().setWantedPosition(targetLocation.x, targetLocation.y, targetLocation.z, 1);
            }
        } else {
            Player player = getPlayer();
            if (player != null) {
                if (targetLocation.subtract(player.position()).horizontalDistance() < 5 && findRandomPosAwayFromPlayer()) {
                    anu.getMoveControl().setWantedPosition(targetLocation.x, targetLocation.y, targetLocation.z, 1);
                }
                throwFireballs(player);
            }
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

    private void throwFireballs(Player targetPlayer) {
        double dist = anu.distanceToSqr(targetPlayer);
        boolean hasLineOfSight = anu.getSensing().hasLineOfSight(targetPlayer);
        if (hasLineOfSight) {
            seeTime++;
        } else {
            seeTime = 0;
        }
        attackTime--;
        if (attackTime == 0) {
            if (!hasLineOfSight) {
                return;
            }
            anu.performRangedAttack(targetPlayer, 0);
            float distanceFactor = (float) (Math.sqrt(dist) / AnuBoss.ARENA_RADIUS);
            attackTime = (int) Mth.lerp(distanceFactor, MIN_ATTACK_TIME, MAX_ATTACK_TIME);
        } else if (attackTime < 0) {
            float distanceFactor = (float) (Math.sqrt(dist) / AnuBoss.ARENA_RADIUS);
            attackTime = (int) Mth.lerp(distanceFactor, MIN_ATTACK_TIME, MAX_ATTACK_TIME);
        }
    }

    private Player getPlayer() {
        if (Version.debugEnabled() && anu.level().getNearestPlayer(anu, 200) != null) {
            return anu.level().getNearestPlayer(anu, 200);
        }
        if (anu.getTarget() instanceof Player player) {
            return player;
        }
        return null;
    }

    private boolean findRandomPosAwayFromPlayer() {
        Player player = getPlayer();
        for (int i = 0; i < 3; i++) {
            float r = (float) (AnuBoss.ARENA_RADIUS * Math.sqrt(anu.getRandom().nextFloat()));
            float t = anu.getRandom().nextFloat() * 2 * Mth.PI;
            float targetY = Mth.randomBetween(anu.getRandom(), -0, 4);
            targetLocation = anu.getSpawnPos().add(r * Mth.cos(t), targetY, r * Mth.sin(t));
            if (anu.level().isEmptyBlock(BlockPos.containing(targetLocation))) {
                if (player != null) {
                    return targetLocation.subtract(player.position()).horizontalDistance() > 5;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public AnuPhase getPhase() {
        return AnuPhase.FLIGHT;
    }

    @Override
    public void onHurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof LargeFireball && source.getEntity() instanceof Player) {
            fireballReturnedCount++;
        }
    }

    @Override
    public void end() {
        fireballReturnedCount = 0;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.ANU_COUGH.get();
    }

    @Override
    public boolean isFlying() {
        return true;
    }
}
