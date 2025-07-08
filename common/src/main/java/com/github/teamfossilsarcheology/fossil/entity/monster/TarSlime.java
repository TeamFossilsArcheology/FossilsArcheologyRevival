package com.github.teamfossilsarcheology.fossil.entity.monster;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.client.particle.ModParticles;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;

public class TarSlime extends Slime {

    public TarSlime(EntityType<? extends TarSlime> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected @NotNull ParticleOptions getParticleType() {
        return ModParticles.TAR_BUBBLE.get();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() != null && getVehicle() != null && getVehicle().is(source.getEntity()) && random.nextBoolean()) {
            stopRiding();
        }
        return super.hurt(source, amount);
    }

    @Override
    public float getVoicePitch() {
        return 0.5f + super.getVoicePitch();
    }

    @Override
    public void tick() {
        super.tick();
        if (level().getBlockState(blockPosition()).is(ModBlocks.TAR.get())) {
            setDeltaMovement(getDeltaMovement().multiply(1, 1.3, 1));
        }
        if (isOnFire()) {
            setSecondsOnFire(10);
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        int i = getSize();
        if (!level().isClientSide && i > 1 && isDeadOrDying()) {
            Component component = getCustomName();
            boolean bl = isNoAi();
            float f = i / 4.0f;
            int j = i / 2;
            int k = 2 + random.nextInt(3);
            for (int l = 0; l < k; ++l) {
                float g = ((l % 2) - 0.5f) * f;
                float h = (l / 2f - 0.5f) * f;
                TarSlime slime = ModEntities.TAR_SLIME.get().create(level());
                if (getSharedFlag(0)) {
                    slime.setSecondsOnFire(15);
                }
                if (isPersistenceRequired()) {
                    slime.setPersistenceRequired();
                }
                slime.setCustomName(component);
                slime.setNoAi(bl);
                slime.setInvulnerable(isInvulnerable());
                slime.setSize(j, true);
                slime.moveTo(getX() + g, getY() + 0.5, getZ() + h, random.nextFloat() * 360.0f, 0.0f);
                level().addFreshEntity(slime);
            }
        }
        setRemoved(reason);
        if (reason == RemovalReason.KILLED) {
            gameEvent(GameEvent.ENTITY_DIE);
        }
    }

    @Override
    protected void decreaseSquish() {
        this.targetSquish *= 0.97f;
    }

    @Override
    public void rideTick() {
        super.rideTick();
        Entity vehicle = getVehicle();
        if (vehicle != null) {
            if (vehicle instanceof LivingEntity livingEntity) {
                if (!level().isClientSide && !livingEntity.hasEffect(MobEffects.BLINDNESS)) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100), this);
                }
                if (tickCount % 20 == 0) {
                    if (!level().isClientSide) {
                        vehicle.hurt(damageSources().mobAttack(this), getSize());
                        playSound(getJumpSound(), getSoundVolume(), getVoicePitch());
                    }
                    targetSquish = 0.7f;
                } else {
                    targetSquish = Math.max(0, targetSquish * 0.9f);
                }
            }
            if (!vehicle.isAlive()) {
                stopRiding();
            }
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (!isDealsDamage() || player.isCreative() || !player.getPassengers().isEmpty() || getVehicle() != null) {
            return;
        }
        if (random.nextInt(6) == 0) {
            startRiding(player);
        }
    }

    @Override
    protected boolean doPlayJumpSound() {
        return false;
    }

    @Override
    public boolean isInWall() {
        float f = getBbWidth() * 0.8f;
        AABB aABB = AABB.ofSize(getEyePosition(), f, 1.0E-6, f);
        return BlockPos.betweenClosedStream(aABB).anyMatch(blockPos -> {
            BlockState state = level().getBlockState(blockPos);
            if (state.is(ModBlocks.TAR.get())) {
                return false;
            }
            return !state.isAir() && state.isSuffocating(level(), blockPos) && Shapes.joinIsNotEmpty(
                    state.getCollisionShape(level(), blockPos).move(blockPos.getX(), blockPos.getY(), blockPos.getZ()),
                    Shapes.create(aABB), BooleanOp.AND);
        });
    }
}
