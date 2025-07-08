package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Nautilus;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.animal.AbstractFish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin {

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/AbstractFish;onGround()Z"))
    private boolean preventNautilusBounce(boolean original) {
        //Prevent bouncing
        return !(((AbstractFish) (Object) (this)) instanceof Nautilus) && original;
    }
}
