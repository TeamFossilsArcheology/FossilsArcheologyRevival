package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.fossil.fossil.entity.prehistoric.base.Pterosaurs;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Confuciusornis extends Pterosaurs implements PrehistoricLeaping {
    public static final String ANIMATIONS = "confuciusornis.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            ServerAnimationInfo info;
            switch (animation.animationId()) {
                case ATTACK1 -> info = new ServerAttackAnimationInfo(animation, ATTACKING_PRIORITY, 12);
                case IDLE -> info = new ServerAnimationInfo(animation, IDLE_PRIORITY);
                default -> info = new ServerAnimationInfo(animation, DEFAULT_PRIORITY);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("confuciusornis");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Confuciusornis(EntityType<Confuciusornis> entityType, Level level) {
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
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(5, new FindAirTargetGoal(this));
        goalSelector.addGoal(6, new DinoLeapAtTargetGoal<>(this));
        goalSelector.addGoal(7, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(4, new HuntGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.CONFUCIUSORNIS;
    }

    @Override
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }
    
    @Override
    public EntityDataManager.Data data() {
        return data;
    }

    @Override
    public @NotNull ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextMovingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextChasingAnimation() {
        return getAllAnimations().get(IDLE);
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

    @Override
    public ServerAnimationInfo getTakeOffAnimation() {
        return null;
    }

    @Override
    public boolean isFlying() {
        return false;
    }
}
