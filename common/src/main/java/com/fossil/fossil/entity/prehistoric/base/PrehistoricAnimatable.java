package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.animation.AnimationLogic;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;

import java.util.Map;

public interface PrehistoricAnimatable<T extends Mob & PrehistoricAnimatable<T>> extends IAnimatable {

    Map<String, Animation> getAllAnimations();

    Map<String, AnimationInfoManager.ServerAnimationInfo> getServerAnimationInfos();

    AnimationLogic<T> getAnimationLogic();

    @NotNull Animation nextEatingAnimation();

    @NotNull Animation nextIdleAnimation();

    default @NotNull Animation nextSittingAnimation() {
        return nextIdleAnimation();
    }

    @NotNull Animation nextSleepingAnimation();

    @NotNull Animation nextMovingAnimation();

    @NotNull Animation nextSprintingAnimation();

    boolean shouldStartEatAnimation();

    void setStartEatAnimation(boolean start);
}
