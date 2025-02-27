package com.github.teamfossilsarcheology.fossil.entity.animation;

import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;

public class AnimationInfo {
    public final Animation animation;
    public final RawAnimation rawAnimation;

    public AnimationInfo(Animation animation) {
        this.animation = animation;
        this.rawAnimation = RawAnimation.begin().thenPlay(animation.name());
    }
}
