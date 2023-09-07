package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.*;

import static com.fossil.fossil.entity.animation.AnimationLogic.ServerAnimationInfo;
import static com.fossil.fossil.entity.animation.AttackAnimationLogic.ServerAttackAnimationInfo;

public class Meganeura extends PrehistoricSwimming {
    public static final String ANIMATIONS = "meganeura.animation.json";
    public static final String IDLE = "animation.meganeura.idle";
    public static final String WALK = "animation.meganeura.walk";
    public static final String EAT = "animation.meganeura.eat";
    public static final String ATTACK = "animation.meganeura.attack";
    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            ServerAnimationInfo info;
            switch (animation.animationId()) {
                case IDLE, VERTICAL_IDLE -> info = new ServerAnimationInfo(animation);
                case WALK, FLY -> info = new ServerAnimationInfo(animation);
                case ATTACK -> info = new ServerAttackAnimationInfo(animation, animation.attackDelay());
                default -> info = new ServerAnimationInfo(animation);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("meganeura");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Meganeura(EntityType<Meganeura> entityType, Level level) {
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
        goalSelector.addGoal(4, new DinoFollowOwnerGoal(this, 1, 10, 2, 50, false));
        goalSelector.addGoal(5, new DinoMeleeAttackAI(this, 1, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
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
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.MEGANEURA;
    }

    @Override
    public Item getOrderItem() {
        return Items.ARROW;
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
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull ServerAnimationInfo nextChasingAnimation() {
        return getAllAnimations().get(WALK);
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
        return ModSounds.MEGANEURA_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MEGANEURA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MEGANEURA_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 0.5f;
    }
}