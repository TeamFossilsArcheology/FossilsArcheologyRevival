package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.network.syncher.SynchedEntityData;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;

import java.util.Map;

public interface PrehistoricAnimatable extends IAnimatable {
    int IDLE_PRIORITY = 0;
    int MOVING_PRIORITY = 1;
    int DEFAULT_PRIORITY = 2;
    int ATTACKING_PRIORITY = 3;

    /**
     *
     * @return
     */
    Map<String, Prehistoric.ServerAnimationInfo> getAllAnimations();

    Prehistoric.ServerAnimationInfo getCurrentAnimation();

    void setCurrentAnimation(@NotNull Prehistoric.ServerAnimationInfo newAnimation);


    /**
     * Returns the idle animation by default. Should be overriden if the implementation of {@link #nextIdleAnimation} requires
     * the entities {@link SynchedEntityData} to be defined
     */
    default @NotNull Prehistoric.ServerAnimationInfo initialAnimation() {
        return nextIdleAnimation();
    }

    @NotNull Prehistoric.ServerAnimationInfo nextIdleAnimation();

    @NotNull Prehistoric.ServerAnimationInfo nextMovingAnimation();
}
