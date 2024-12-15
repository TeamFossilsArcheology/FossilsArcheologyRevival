package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Mixin(GeoEntityRenderer.class)
public class GeoEntityRendererMixin<T extends LivingEntity & IAnimatable> {

    @Shadow
    protected T animatable;

    @ModifyExpressionValue(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z"))
    public boolean disableSitting(boolean original) {
        if (animatable instanceof PrehistoricLeaping leaping && leaping.getLeapSystem().isAttackRiding()) {
            return false;
        }
        return original;
    }
}
