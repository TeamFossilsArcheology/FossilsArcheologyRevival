package com.github.teamfossilsarcheology.fossil.entity.animation;

import software.bernie.geckolib.core.animation.Animation;

/**
 * A specific animation instance available to the server
 */
public class ServerAnimationInfo extends AnimationInfo {
    /**
     * x ticks after the start of the animation
     */
    public final double actionDelay;
    /**
     * whether an attack box should be activated for this animation
     */
    public final boolean usesAttackBox;

    public ServerAnimationInfo(Animation animation, double actionDelay, boolean usesAttackBox) {
        super(animation);
        this.actionDelay = actionDelay;
        this.usesAttackBox = usesAttackBox;
    }
}
