package com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.builder.Animation;

public class Sturgeon extends PrehistoricFish {
    public static final String ANIMATIONS = "sturgeon.animation.json";
    public static final String BEACHED = "animation.sturgeon.land";
    public static final String IDLE = "animation.sturgeon.idle";
    public static final String SWIM = "animation.sturgeon.swim";
    public static final String SWIM_FAST = "animation.sturgeon.swim_fast";

    public Sturgeon(EntityType<Sturgeon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.STURGEON;
    }

    @Override
    public @NotNull Animation nextBeachedAnimation() {
        return getAllAnimations().get(BEACHED);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(SWIM);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        return getAllAnimations().get(SWIM_FAST);
    }
}
