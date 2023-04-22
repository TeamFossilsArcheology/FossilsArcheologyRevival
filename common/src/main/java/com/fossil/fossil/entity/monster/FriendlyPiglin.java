package com.fossil.fossil.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FriendlyPiglin extends TamableAnimal {
    public static final TranslatableComponent KILLED = new TranslatableComponent("entity.fossil.friendly_piglin.kill");
    public static final TranslatableComponent SUMMONED = new TranslatableComponent("entity.fossil.friendly_piglin.summon");

    public FriendlyPiglin(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.ARMOR, 2)
                .add(Attributes.ATTACK_DAMAGE, 5).add(Attributes.MAX_HEALTH);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(isTame() ? 25 : 20);
        populateDefaultEquipmentSlots(difficulty);
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1, true));
        goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public void aiStep() {
        updateSwingTime();
        if (getBrightness() > 0.5F) {
            noActionTime += 2;
        }
        super.aiStep();
    }

    @Override
    public void killed(ServerLevel level, LivingEntity killedEntity) {
        super.killed(level, killedEntity);
        sendMessageToOwner(KILLED);
    }

    public void sendMessageToOwner(Component component) {
        if (getOwner() instanceof Player) {
            ((Player) getOwner()).displayClientMessage(component, false);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return false;
        }
        Entity trueSource = source.getEntity();
        if (!level.isClientSide) {
            setOrderedToSit(false);
        }
        if (trueSource != null && !(trueSource instanceof Player) && !(trueSource instanceof AbstractArrow)) {
            amount = (amount + 1.0f) / 2.0f;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hurtTarget = super.doHurtTarget(target);
        if (hurtTarget) {
            float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getItemInHand(isLeftHanded() ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND).isEmpty() && this.isOnFire() &&
                    this.random.nextFloat() < f * 0.3f) {
                target.setSecondsOnFire(2 * (int) f);
            }
            swing(isLeftHanded() ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        }
        return hurtTarget;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        playSound(SoundEvents.ZOMBIE_STEP, 0.15f, 1);
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof Creeper || target instanceof Ghast) {
            return false;
        }
        if (target instanceof Wolf wolf) {
            return !wolf.isTame() || wolf.getOwner() != owner;
        }
        if (target instanceof FriendlyPiglin piglin) {
            return !piglin.isTame() || piglin.getOwner() != owner;
        }
        if (target instanceof AbstractHorse horse) {
            return horse.isTamed();
        }
        if (target instanceof Player && owner instanceof Player && !((Player) owner).canHarmPlayer((Player) target)) {
            return false;
        }
        return !(target instanceof TamableAnimal) || !((TamableAnimal) target).isTame();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIFIED_PIGLIN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ZOMBIFIED_PIGLIN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIFIED_PIGLIN_DEATH;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return (isOwnedBy(player) || isTame()) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        if (isTame()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(Items.GOLD_NUGGET) && getHealth() < getMaxHealth()) {
                stack.shrink(1);
                heal(2);
                gameEvent(GameEvent.MOB_INTERACT, eyeBlockPosition());
                return InteractionResult.SUCCESS;
            }
            if (isOwnedBy(player)) {
                setOrderedToSit(!isOrderedToSit());
                jumping = false;
                navigation.stop();
                setTarget(null);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }
}
