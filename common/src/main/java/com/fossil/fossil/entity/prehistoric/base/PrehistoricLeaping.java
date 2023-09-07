package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.animation.AttackAnimationLogic;
import org.jetbrains.annotations.NotNull;

public interface PrehistoricLeaping {

    @NotNull AttackAnimationLogic.ServerAttackAnimationInfo nextLeapAnimation();
}
