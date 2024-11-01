package com.github.teamfossilsarcheology.fossil.entity.animation;

import java.util.Map;

public record BakedAnimationInfo<T extends AnimationInfo>(Map<String, T> animations) {

}
