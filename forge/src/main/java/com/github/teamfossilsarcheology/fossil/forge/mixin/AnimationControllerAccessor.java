package com.github.teamfossilsarcheology.fossil.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib3.core.controller.AnimationController;

@Mixin(AnimationController.class)
public interface AnimationControllerAccessor {

    @Accessor("tickOffset")
    double getTickOffset();

    @Accessor("tickOffset")
    void setTickOffset(double tickOffset);
}
