package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfoLoader;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;

import java.util.Map;

public interface PrehistoricAnimatable<T extends Mob & PrehistoricAnimatable<T>> extends IAnimatable {

    Map<String, Animation> getAllAnimations();

    Animation getAnimation(String name);

    Map<String, AnimationInfoLoader.ServerAnimationInfo> getServerAnimationInfos();

    AnimationLogic<T> getAnimationLogic();

    @NotNull Animation nextEatingAnimation();

    @NotNull Animation nextIdleAnimation();

    @NotNull Animation nextSittingAnimation();

    @NotNull Animation nextSleepingAnimation();

    @NotNull Animation nextMovingAnimation();

    @NotNull Animation nextSprintingAnimation();
}
