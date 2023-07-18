package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nautilus extends PrehistoricFish {
    public static final String ANIMATIONS = "nautilus.animation.json";
    public static final String IDLE = "animation.nautilus.idle";
    public static final String SWIM_BACKWARDS = "animation.nautilus.swim_backwards";
    public static final String SWIM_FORWARDS = "animation.nautilus.swim_forwards";
    public static final String SHELL_RETRACT = "animation.nautilus.shell_retract";
    public static final String SHELL_HOLD = "animation.nautilus.shell_hold";
    public static final String SHELL_EMERGE = "animation.nautilus.shell_emerge";
    public static final String LAND = "animation.nautilus.land";
    private static final LazyLoadedValue<Map<String, Prehistoric.ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, Prehistoric.ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            Prehistoric.ServerAnimationInfo info;
            switch (animation.animationId()) {
                case IDLE -> info = new Prehistoric.ServerAnimationInfo(animation, IDLE_PRIORITY);
                case SWIM_BACKWARDS, SWIM_FORWARDS ->
                        info = new Prehistoric.ServerAnimationInfo(animation, MOVING_PRIORITY);
                default -> info = new Prehistoric.ServerAnimationInfo(animation, DEFAULT_PRIORITY);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final EntityDataAccessor<Boolean> IS_IN_SHELL = SynchedEntityData.defineId(Nautilus.class, EntityDataSerializers.BOOLEAN);
    private float shellProgress = 0;
    private float ticksToShell = 0;

    public Nautilus(EntityType<Nautilus> entityType, Level level) {
        super(entityType, level);
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
        setInShell(compound.getBoolean("InShell"));
    }

    @Override
    public @NotNull PrehistoricEntityType type() {
        return PrehistoricEntityType.NAUTILUS;
    }

    @Override
    public void tick() {
        super.tick();
        boolean inShell = isInShell();
        if (level.isClientSide) {
            if (inShell && shellProgress < 20) {
                shellProgress += 0.5;
            } else if (!inShell && shellProgress > 0) {
                shellProgress -= 0.5;
            }
        } else {
            if (ticksToShell > 0) {
                ticksToShell--;
            }
            List<LivingEntity> nearbyMobs = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2, 2, 2), Nautilus::getsScaredBy);
            if (nearbyMobs.size() > 1 || !isInWater() && isOnGround()) {
                if (ticksToShell == 0 && !isInShell()) {
                    setInShell(true);
                }
            } else if (isInShell() && ticksToShell == 0) {
                setInShell(false);
            }
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
            setInShell(true);
        }
        return super.hurt(source, amount);
    }

    public boolean isInShell() {
        return entityData.get(IS_IN_SHELL);
    }

    public void setInShell(boolean inShell) {
        entityData.set(IS_IN_SHELL, inShell);
        ticksToShell = 60;
        if (inShell) {
            setCurrentAnimation(getAllAnimations().get(SHELL_RETRACT));
        } else {
            setCurrentAnimation(getAllAnimations().get(SHELL_EMERGE));
        }
    }

    @Override
    public Map<String, Prehistoric.ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo initialAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextIdleAnimation() {
        if (isInShell()) {
            return getAllAnimations().get(SHELL_HOLD);
        }
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextMovingAnimation() {
        if (isInShell()) {
            return getAllAnimations().get(SHELL_HOLD);
        }
        return getAllAnimations().get(SWIM_BACKWARDS);//TODO: SWIM_FAST
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextFloppingAnimation() {
        return getAllAnimations().get(LAND);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
}