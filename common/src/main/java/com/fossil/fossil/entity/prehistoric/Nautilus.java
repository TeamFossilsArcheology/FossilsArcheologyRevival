package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class Nautilus extends PrehistoricFish {
    public static final String ANIMATIONS = "nautilus.animation.json";
    public static final String IDLE = "animation.nautilus.idle";
    public static final String SWIM_BACKWARDS = "animation.nautilus.swim_backwards";
    public static final String SWIM_FORWARDS = "animation.nautilus.swim_forwards";
    public static final String SHELL_RETRACT = "animation.nautilus.shell_retract";
    public static final String SHELL_HOLD = "animation.nautilus.shell_hold";
    public static final String SHELL_EMERGE = "animation.nautilus.shell_emerge";
    public static final String LAND = "animation.nautilus.land";
    private static final EntityDataAccessor<Boolean> IS_IN_SHELL = SynchedEntityData.defineId(Nautilus.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private float ticksToShell = 0;

    //TODO: Cant swim in 1 block water
    //TODO: Pose for inshell
    public Nautilus(EntityType<Nautilus> entityType, Level level) {
        super(entityType, level);
    }

    private static boolean getsScaredBy(Entity entity) {
        if (entity instanceof Player player && !player.isCreative()) {
            return true;
        }
        if (entity instanceof Prehistoric prehistoric) {
            return prehistoric.type().diet.getFearIndex() >= 2;
        }
        if (entity instanceof Nautilus) {
            return false;
        }
        return entity.getBbWidth() >= 1.2;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_IN_SHELL, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("InShell", isInShell());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        hideInShell(compound.getBoolean("InShell"));
    }

    @Override
    public @NotNull PrehistoricEntityType type() {
        return PrehistoricEntityType.NAUTILUS;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isInShell()) {
            super.travel(Vec3.ZERO);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isInWater()) {
            setDeltaMovement(Vec3.ZERO);
            travel(Vec3.ZERO);//Apply gravity
            hasImpulse = false;
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (ticksToShell > 0) {
            ticksToShell--;
        }
        List<LivingEntity> nearbyMobs = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2, 2, 2), Nautilus::getsScaredBy);
        if (nearbyMobs.size() > 1 || (!isInWater() && isOnGround())) {
            if (ticksToShell == 0 && !isInShell()) {
                hideInShell(true);
            }
        } else if (isInShell() && ticksToShell == 0) {
            hideInShell(false);
        }
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.FLINT)) {
            if (!level.isClientSide) {
                hideInShell(false);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        if (!isInShell()) {
            super.handleAirSupply(airSupply);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (amount > 0 && isInShell() && source.getEntity() != null) {
            playSound(SoundEvents.ITEM_BREAK, 1, random.nextFloat() + 0.8f);
            if (getVehicle() != null) {
                return super.hurt(source, amount);
            }
            return false;
        }
        if (!isInShell()) {
            hideInShell(true);
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean isPushedByFluid() {
        return isInShell();
    }

    public boolean isInShell() {
        return entityData.get(IS_IN_SHELL);
    }

    public void hideInShell(boolean inShell) {
        entityData.set(IS_IN_SHELL, inShell);
        ticksToShell = 60;
    }

    @Override
    public void registerControllers(AnimationData data) {
        super.registerControllers(data);
        data.addAnimationController(new AnimationController<>(this, "Shell", 4, this::shellPredicate));
    }

    private PlayState shellPredicate(AnimationEvent<Nautilus> event) {
        var ctrl = event.getController();
        var anim = ctrl.getCurrentAnimation();
        if (event.getAnimatable().isInShell()) {
            if (anim == null || anim.animationName.equals(SHELL_EMERGE) && ctrl.getAnimationState() == AnimationState.Stopped) {
                ctrl.setAnimation(new AnimationBuilder().addAnimation(SHELL_RETRACT).addAnimation(SHELL_HOLD));
            }
        } else {
            if (anim != null && anim.animationName.equals(SHELL_HOLD)) {
                ctrl.setAnimation(new AnimationBuilder().addAnimation(SHELL_EMERGE));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(SWIM_BACKWARDS);
    }

    @Override
    public @NotNull Animation nextFloppingAnimation() {
        return getAllAnimations().get(LAND);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}