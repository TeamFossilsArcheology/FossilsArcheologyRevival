package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.*;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Velociraptor extends Prehistoric implements PrehistoricLeaping, PrehistoricScary {
    public static final String ANIMATIONS = "velociraptor.animation.json";
    public static final String IDLE = "animation.velociraptor.idle";
    public static final String WALK = "animation.velociraptor.walk";
    public static final String RUN = "animation.velociraptor.run";
    public static final String SPEAK = "animation.velociraptor.speak";
    public static final String CALL = "animation.velociraptor.call";
    public static final String ATTACK1 = "animation.velociraptor.attack1";
    public static final String DISPLAY = "animation.velociraptor.display";
    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            ServerAnimationInfo info;
            switch (animation.animationId()) {
                case IDLE -> info = new ServerAnimationInfo(animation, IDLE_PRIORITY);
                case WALK, RUN -> info = new ServerAnimationInfo(animation, MOVING_PRIORITY);
                case ATTACK1 -> info = new ServerAttackAnimationInfo(animation, ATTACKING_PRIORITY, 12);
                default -> info = new ServerAnimationInfo(animation, DEFAULT_PRIORITY);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("velociraptor");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Velociraptor(EntityType<Velociraptor> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackAI(this, 1, false));
        goalSelector.addGoal(0, new DinoLeapAtTargetGoal<>(this));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(4, new RestrictSunGoal(this));
        goalSelector.addGoal(4, new FleeSunGoal(this, 1));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        targetSelector.addGoal(4, new HuntGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.VELOCIRAPTOR;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return this.isBaby() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERRITORIAL;
    }

    @Override
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }
    
    @Override
    public EntityDataManager.Data data() {
        return data;
    }

    @Override
    public @NotNull ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(DISPLAY);
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
    public @NotNull Prehistoric.ServerAnimationInfo nextChasingAnimation() {
        return getAllAnimations().get(RUN);
    }

    @Override
    public @NotNull Prehistoric.ServerAttackAnimationInfo nextAttackAnimation() {
        return (ServerAttackAnimationInfo) getAllAnimations().get(ATTACK1);
    }

    @Override
    public @NotNull Prehistoric.ServerAttackAnimationInfo nextLeapAnimation() {
        return (ServerAttackAnimationInfo) getAllAnimations().get(ATTACK1);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
