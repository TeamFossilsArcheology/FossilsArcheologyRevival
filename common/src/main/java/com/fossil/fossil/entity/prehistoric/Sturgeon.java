package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Sturgeon extends PrehistoricFish {
    public static final String ANIMATIONS = "sturgeon.animation.json";
    public static final String IDLE = "animation.sturgeon.idle";
    public static final String SWIM = "animation.sturgeon.swim";
    public static final String SWIM_FAST = "animation.sturgeon.swim_fast";
    public static final String LAND = "animation.sturgeon.land";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Sturgeon(EntityType<Sturgeon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull PrehistoricEntityType type() {
        return PrehistoricEntityType.STURGEON;
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(SWIM);//TODO: SWIM_FAST
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
