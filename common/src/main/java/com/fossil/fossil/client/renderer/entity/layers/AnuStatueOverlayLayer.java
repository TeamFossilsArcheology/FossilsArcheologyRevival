package com.fossil.fossil.client.renderer.entity.layers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.model.AnuStatueModel;
import com.fossil.fossil.entity.AnuStatueEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AnuStatueOverlayLayer extends RenderLayer<AnuStatueEntity, AnuStatueModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/anu_statue_explosion.png");

    public AnuStatueOverlayLayer(RenderLayerParent<AnuStatueEntity, AnuStatueModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AnuStatueEntity livingEntity, float limbSwing,
                       float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
        getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, livingEntity.tickCount / 200f);
    }
}
