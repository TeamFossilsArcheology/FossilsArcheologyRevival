package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.world.effect.ModEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "getVisibilityPercent", at = @At("RETURN"), cancellable = true)
    public void getVisibilityPercent(Entity lookingEntity, CallbackInfoReturnable<Double> cir) {
        if (((LivingEntity) (Object) this).hasEffect(ModEffects.COMFY_BED.get())) {
            cir.setReturnValue(cir.getReturnValue() * 0.5);
        }
    }
}
