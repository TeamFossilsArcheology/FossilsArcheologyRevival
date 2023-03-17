package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    float pass = Float.NaN;
    @Inject(method = "knockback(DDD)V", at = @At(value = "HEAD"))
    public void onKnockBack(double strength, double x, double z, CallbackInfo ci) {
        pass = (float) Prehistoric.beforeKnockBack((LivingEntity) ((Object)this), strength, x, z);
    }

    @ModifyVariable(
            method = "knockback(DDD)V",
            ordinal = 0,
            at = @At(value = "STORE"),
            argsOnly = true
    )
    public double modifyStrength(double original) {
        if (Float.isNaN(pass)) throw new IllegalStateException("KnockBack has not been properly handled!");
        float copy = pass;
        pass = Float.NaN;
        return copy;
    }
}
