package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.animation.AnimationLogic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;

import java.util.Map;

public interface PrehistoricAnimatable extends IAnimatable {

    Map<String, AnimationLogic.ServerAnimationInfo> getAllAnimations();

    @Nullable AnimationLogic.ActiveAnimationInfo getActiveAnimation(String controller);

    void addActiveAnimation(String controller, AnimationLogic.ServerAnimationInfo animation);

    @NotNull AnimationLogic.ServerAnimationInfo nextIdleAnimation();

    @NotNull AnimationLogic.ServerAnimationInfo nextEatingAnimation();

    @NotNull AnimationLogic.ServerAnimationInfo nextMovingAnimation();

    boolean shouldStartEatAnimation();

    void setStartEatAnimation(boolean start);
}
