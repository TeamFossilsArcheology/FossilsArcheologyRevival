package com.github.teamfossilsarcheology.fossil.entity.monster;

import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Anubite extends PathfinderMob {
    private int targetChangedTick;

    public Anubite(EntityType<Anubite> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.34).add(Attributes.ATTACK_DAMAGE, 4);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new TeleportMeleeAttackGoal(this, 1, true));
        goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32));
        goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, LivingEntity.class, 8));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 1, true, false, this::shouldAttackPlayer));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true));
    }

    private boolean shouldAttackPlayer(LivingEntity player) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
        return !stack.is(ModItems.ANCIENT_HELMET.get());
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target == null) {
            targetChangedTick = 0;
        } else {
            targetChangedTick = tickCount;
        }
    }

    @Override
    protected void customServerAiStep() {
        if (tickCount >= targetChangedTick + 600) {
            float f = getLightLevelDependentMagicValue();
            if (f > 0.5 && random.nextFloat() * 30 < (f - 0.4) * 2) {
                teleportRandomly();
            }
        }
        super.customServerAiStep();
    }

    private boolean teleportRandomly() {
        if (level().isClientSide() || !isAlive()) {
            return false;
        }
        double x = getX() + (random.nextDouble() - 0.5) * 64.0;
        double y = getY() + (random.nextInt(64) - 32);
        double z = getZ() + (random.nextDouble() - 0.5) * 64.0;
        return teleport(x, y, z);
    }

    private boolean teleportTowards(Entity target) {
        Vec3 vecToTarget = new Vec3(getX() - target.getX(), getY(0.5) - target.getEyeY(), getZ() - target.getZ());
        vecToTarget = vecToTarget.normalize();
        double x = getX() + (random.nextDouble() - 0.5) * 8 - vecToTarget.x * 16;
        double y = getY() + (random.nextInt(16) - 8) - vecToTarget.y * 16;
        double z = getZ() + (random.nextDouble() - 0.5) * 8 - vecToTarget.z * 16;
        return teleport(x, y, z);
    }

    private boolean teleport(double x, double y, double z) {
        boolean canTeleport = randomTeleport(x, y, z, true);
        if (canTeleport && !isSilent()) {
            level().playSound(null, xo, yo, zo, SoundEvents.ENDERMAN_TELEPORT, getSoundSource(), 1, 1);
            playSound(SoundEvents.ENDERMAN_TELEPORT, 1, 1);
        }
        return canTeleport;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (random.nextInt(5) == 0) {
            teleportRandomly();
        }
        return super.doHurtTarget(target);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isInvulnerableTo(source) && !source.is(DamageTypeTags.IS_EXPLOSION)) {
            return false;
        } else if (source.isIndirect()) {
            for (int i = 0; i < 64; i++) {
                if (teleportRandomly()) {
                    return true;
                }
            }
            return false;
        } else {
            boolean flag = super.hurt(source, amount);
            if (source.is(DamageTypeTags.BYPASSES_SHIELD) && random.nextInt(10) != 0) {
                teleportRandomly();
            }
            return flag;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ITEM_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    static class TeleportMeleeAttackGoal extends MeleeAttackGoal {
        private final Anubite anubite;
        private int teleportTime;

        public TeleportMeleeAttackGoal(Anubite anubite, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(anubite, speedModifier, followingTargetEvenIfNotSeen);
            this.anubite = anubite;
        }

        @Override
        public void start() {
            super.start();
            teleportTime = 0;
        }

        @Override
        public void tick() {
            LivingEntity target = anubite.getTarget();
            if (target != null) {
                if (anubite.shouldAttackPlayer(target)) {
                    if (target.distanceToSqr(anubite) > 45 && anubite.random.nextInt(55) == 0) {
                        anubite.teleportTowards(target);
                    }
                    teleportTime = 0;
                } else if (target.distanceToSqr(anubite) > 256 && teleportTime++ >= 30 && anubite.teleportTowards(target)) {
                    teleportTime = 0;
                }
            }
            super.tick();
        }
    }
}
