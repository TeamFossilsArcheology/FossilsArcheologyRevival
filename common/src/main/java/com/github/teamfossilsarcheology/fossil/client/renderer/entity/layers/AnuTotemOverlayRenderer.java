package com.github.teamfossilsarcheology.fossil.client.renderer.entity.layers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.model.AnuTotemModel;
import com.github.teamfossilsarcheology.fossil.entity.AnuTotem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AnuTotemOverlayRenderer extends RenderLayer<AnuTotem, AnuTotemModel> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/entity/anu_totem_layer.png");

    public AnuTotemOverlayRenderer(RenderLayerParent<AnuTotem, AnuTotemModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AnuTotem livingEntity, float limbSwing,
                       float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
        getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, livingEntity.tickCount / 200f);
    }
}
