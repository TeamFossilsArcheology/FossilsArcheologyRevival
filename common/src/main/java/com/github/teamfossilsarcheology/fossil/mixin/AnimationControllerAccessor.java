package com.github.teamfossilsarcheology.fossil.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.EasingType;

import java.util.function.Function;

@Mixin(AnimationController.class)
public interface AnimationControllerAccessor<T extends GeoAnimatable> {

    @Accessor
    void setIsJustStarting(boolean isJustStarting);

    @Accessor("overrideEasingTypeFunction")
    Function<T, EasingType> getOverrideEasingTypeFunction();
}
