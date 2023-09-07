package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fossil.fossil.entity.animation.AnimationLogic.ServerAnimationInfo;
import static com.fossil.fossil.entity.animation.AttackAnimationLogic.ServerAttackAnimationInfo;

public class Megalodon extends PrehistoricSwimming {
    public static final String ANIMATIONS = "megalodon.animation.json";
    public static final String IDLE = "animation.megalodon.swim_idle";
    public static final String SWIM = "animation.megalodon.swim";
    public static final String SWIM_FAST = "animation.megalodon.swim_fast";
    public static final String EAT = "animation.megalodon.eat";
    public static final String ATTACK = "animation.megalodon.attack";
    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            ServerAnimationInfo info;
            switch (animation.animationId()) {
                case IDLE -> info = new ServerAnimationInfo(animation);
                case SWIM, SWIM_FAST -> info = new ServerAnimationInfo(animation);
                case ATTACK -> info = new ServerAttackAnimationInfo(animation, animation.attackDelay());
                default -> info = new ServerAnimationInfo(animation);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("megalodon");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Megalodon(EntityType<Megalodon> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new EnterWaterWithoutTargetGoal(this, 1));
        goalSelector.addGoal(0, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(1, new DinoMeleeAttackAI(this, 1, false));
        goalSelector.addGoal(3, new EatFromFeederGoal(this));
        goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 10));
        goalSelector.addGoal(4, new EatItemEntityGoal(this));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    public boolean isAmphibious() {
        return false;
    }

    @Override
    public double swimSpeed() {
        return 1;
    }

    @Override
    public boolean doesBreachAttack() {
        return true;
    }

    @Override
    public boolean canBreatheOnLand() {
        return false;
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        if (isAlive() && !isInWaterOrBubble()) {
            setAirSupply(airSupply - 1);
            if (getAirSupply() == -20) {
                setAirSupply(0);
                hurt(DamageSource.DROWN, 2.0f);
            }
        } else {
            setAirSupply(1000);
        }
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.MEGALODON;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    protected float getGenderedScale() {
        return getGender() == Gender.MALE ? 0.8f : super.getGenderedScale();
    }

    @Override
    public float getTargetScale() {
        return 2;
    }

    @Override
    public boolean canHuntMobsOnLand() {
        return false;
    }

    @Override
    public EntityDataManager.Data data() {
        return data;
    }

    @Override
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }

    @Override
    public @NotNull ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull ServerAnimationInfo nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextMovingAnimation() {
        return getAllAnimations().get(SWIM);
    }

    @Override
    public @NotNull ServerAnimationInfo nextChasingAnimation() {
        return getAllAnimations().get(SWIM);
    }

    @Override
    public @NotNull ServerAttackAnimationInfo nextAttackAnimation() {
        return (ServerAttackAnimationInfo) getAllAnimations().get(ATTACK);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.MEGALODON_AMBIENT.get() : ModSounds.MOSASAURUS_AMBIENT_OUTSIDE.get();

    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return isInWater() ? ModSounds.MEGALODON_HURT.get() : ModSounds.MEGALODON_HURT_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGALODON_DEATH.get();
    }
}