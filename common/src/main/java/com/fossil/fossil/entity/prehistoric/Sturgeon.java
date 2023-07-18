package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sturgeon extends PrehistoricFish {
    public static final String ANIMATIONS = "sturgeon.animation.json";
    public static final String IDLE = "animation.sturgeon.idle";
    public static final String SWIM = "animation.sturgeon.swim";
    public static final String SWIM_FAST = "animation.sturgeon.swim_fast";
    public static final String LAND = "animation.sturgeon.land";
    private static final LazyLoadedValue<Map<String, Prehistoric.ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, Prehistoric.ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            Prehistoric.ServerAnimationInfo info;
            switch (animation.animationId()) {
                case IDLE -> info = new Prehistoric.ServerAnimationInfo(animation, IDLE_PRIORITY);
                case SWIM, SWIM_FAST -> info = new Prehistoric.ServerAnimationInfo(animation, MOVING_PRIORITY);
                default -> info = new Prehistoric.ServerAnimationInfo(animation, DEFAULT_PRIORITY);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Sturgeon(EntityType<Sturgeon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull PrehistoricEntityType type() {
        return PrehistoricEntityType.STURGEON;
    }

    @Override
    public Map<String, Prehistoric.ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextMovingAnimation() {
        return getAllAnimations().get(SWIM);//TODO: SWIM_FAST
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextFloppingAnimation() {
        return getAllAnimations().get(LAND);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
