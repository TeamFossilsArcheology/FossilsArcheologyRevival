package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.entity.ToyBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;

public abstract class ToyBaseRenderer<T extends ToyBase> extends EntityRenderer<T> implements RendererFabricFix {
    protected final EntityModel<T> model;

    protected ToyBaseRenderer(EntityRendererProvider.Context context, EntityModel<T> model, float f) {
        super(context);
        this.model = model;
        this.shadowRadius = f;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        model.riding = entity.isPassenger();
        float yBodyRotDiff = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
        float xRotDiff = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        setupRotations(poseStack, yBodyRotDiff);
        poseStack.scale(-1, -1, 1);
        poseStack.translate(0.0, -1.501f, 0.0);
        model.prepareMobModel(entity, 0, 0, partialTicks);
        model.setupAnim(entity, partialTicks, 0, 0, 0, xRotDiff);
        VertexConsumer vertexConsumer = buffer.getBuffer(model.renderType(_getTextureLocation(entity)));
        int m = OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(false));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, m, 1, 1, 1, 1);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    protected void setupRotations(PoseStack poseStack, float rotationYaw) {
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - rotationYaw));
    }

    @Override
    protected boolean shouldShowName(T entity) {
        return false;
    }
}
