package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.animation.AnimationLogic;
import net.minecraft.nbt.CompoundTag;
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

    void addActiveAnimation(String controller, CompoundTag animationTag);

    @NotNull Animation nextEatingAnimation();

    @NotNull Animation nextIdleAnimation();

    default @NotNull Animation nextSleepingAnimation() {
        return nextIdleAnimation();
    }

    @NotNull Animation nextMovingAnimation();

    boolean shouldStartEatAnimation();

    void setStartEatAnimation(boolean start);
}
