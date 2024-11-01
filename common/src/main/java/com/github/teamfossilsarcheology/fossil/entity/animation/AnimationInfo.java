package com.github.teamfossilsarcheology.fossil.entity.animation;

import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.RawAnimation;

public class AnimationInfo {
    public final Animation animation;
    public final RawAnimation rawAnimation;

    public AnimationInfo(Animation animation) {
        this.animation = animation;
        this.rawAnimation = new RawAnimation(animation.animationName, animation.loop);
    }
}
