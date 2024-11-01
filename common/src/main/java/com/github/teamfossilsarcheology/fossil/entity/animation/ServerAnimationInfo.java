package com.github.teamfossilsarcheology.fossil.entity.animation;

import software.bernie.geckolib3.core.builder.Animation;

/**
 * A specific animation instance available to the server
 */
public class ServerAnimationInfo extends AnimationInfo {
    /**
     * x ticks after the start of the animation
     */
    public final double actionDelay;
    /**
     * move speed at which the animation looks best
     */
    public final double blocksPerSecond;
    /**
     * whether an attack box should be activated for this animation
     */
    public final boolean usesAttackBox;

    public ServerAnimationInfo(Animation animation, double actionDelay, double blocksPerSecond, boolean usesAttackBox) {
        super(animation);
        this.actionDelay = actionDelay;
        this.blocksPerSecond = blocksPerSecond;
        this.usesAttackBox = usesAttackBox;
    }
}
