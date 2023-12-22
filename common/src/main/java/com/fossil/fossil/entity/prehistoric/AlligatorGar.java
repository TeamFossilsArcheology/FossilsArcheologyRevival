package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AlligatorGar extends PrehistoricFish {
    public static final String ANIMATIONS = "alligator_gar.animation.json";
    public static final String BEACHED = "animation.alligator_gar.land";
    public static final String IDLE = "animation.alligator_gar.idle";
    public static final String SWIM = "animation.alligator_gar.swim";
    public static final String SWIM_FAST = "animation.alligator_gar.swim_fast";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public AlligatorGar(EntityType<AlligatorGar> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull PrehistoricEntityType type() {
        return PrehistoricEntityType.ALLIGATOR_GAR;
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        if (getMoveControl().getSpeedModifier() < 1.25) {
            return getAllAnimations().get(SWIM);
        }
        return getAllAnimations().get(SWIM_FAST);
    }

    @Override
    public @NotNull Animation nextFloppingAnimation() {
        return getAllAnimations().get(BEACHED);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
