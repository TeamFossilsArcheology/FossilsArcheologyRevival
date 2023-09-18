package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.animation.AnimationLogic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;

import java.util.Map;

public interface PrehistoricAnimatable extends IAnimatable {

    Map<String, Animation> getAllAnimations();

    Map<String, AnimationInfoManager.ServerAnimationInfo> getServerAnimationInfos();

    @Nullable AnimationLogic.ActiveAnimationInfo getActiveAnimation(String controller);

    void addActiveAnimation(String controller, Animation animation);

    @NotNull Animation nextIdleAnimation();

    @NotNull Animation nextEatingAnimation();

    @NotNull Animation nextMovingAnimation();

    boolean shouldStartEatAnimation();

    void setStartEatAnimation(boolean start);
}
