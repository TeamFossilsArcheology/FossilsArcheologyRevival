package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.client.model.FailuresaurusModel;
import com.github.teamfossilsarcheology.fossil.entity.monster.Failuresaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FailuresaurusRenderer extends FixedGeoEntityRenderer<Failuresaurus> {
    public FailuresaurusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FailuresaurusModel());
    }

    @Override
    public RenderType getRenderType(Failuresaurus animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        if (animatable.getVariant().equals(Failuresaurus.Variant.FLYING.name())) {
            return RenderType.entityCutoutNoCull(texture);
        }
        if (animatable.getVariant().equals(Failuresaurus.Variant.SAUROPOD.name())) {
            return RenderType.entityCutoutNoCull(texture);
        }
        return RenderType.entityCutout(texture);
    }
}
