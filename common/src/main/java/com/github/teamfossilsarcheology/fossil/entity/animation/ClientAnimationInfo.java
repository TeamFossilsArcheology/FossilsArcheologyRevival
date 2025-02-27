package com.github.teamfossilsarcheology.fossil.entity.animation;

import software.bernie.geckolib.core.animation.Animation;

public class ClientAnimationInfo extends AnimationInfo {
    /**
     * move speed at which the animation looks best
     */
    public final double blocksPerSecond;
    public ClientAnimationInfo(Animation animation, double blocksPerSecond) {
        super(animation);
        this.blocksPerSecond = blocksPerSecond;
    }
}
