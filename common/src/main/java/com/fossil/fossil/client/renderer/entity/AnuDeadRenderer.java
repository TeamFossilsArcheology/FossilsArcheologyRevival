package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.AnuDeadModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.AnuDead;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class AnuDeadRenderer extends EntityRenderer<AnuDead> implements RendererFabricFix {
    private final AnuDeadModel model;

    public AnuDeadRenderer(EntityRendererProvider.Context context, AnuDeadModel model) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(AnuDead entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.125, 0.6);
        poseStack.scale(-1, -1, 1);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-90));
        VertexConsumer vertexConsumer = buffer.getBuffer(model.renderType(getTextureLocation(entity)));
        int m = LivingEntityRenderer.getOverlayCoords(entity, 0);
        if (entity.tickCount > 40) {
            float alpha = (float) Math.max(0.05, 1 - (entity.tickCount + 25) / 63f * 0.01f);
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, m, 1, 1, 1, alpha);
        } else {
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, m, 1, 1, 1, 1);
        }
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(AnuDead entity) {
        return AnuDeadModel.TEXTURE;
    }

    @NotNull
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((AnuDead) entity);
    }

}