package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Mixin(GeoEntityRenderer.class)
public class GeoEntityRendererMixin {

    @Shadow
    protected Entity animatable;

    @ModifyExpressionValue(method = "actuallyRender(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/Entity;Lsoftware/bernie/geckolib/cache/object/BakedGeoModel;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZFIIFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isPassenger()Z"))
    public boolean disableSitting(boolean original) {
        if (animatable instanceof PrehistoricLeaping leaping && leaping.getLeapSystem().isAttackRiding()) {
            return false;
        }
        return original;
    }
}
