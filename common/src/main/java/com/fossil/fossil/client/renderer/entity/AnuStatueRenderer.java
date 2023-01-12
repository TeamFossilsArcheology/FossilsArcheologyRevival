package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.AnuStatueModel;
import com.fossil.fossil.client.renderer.entity.layers.AnuStatueOverlayLayer;
import com.fossil.fossil.entity.AnuStatueEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AnuStatueRenderer extends MobRenderer<AnuStatueEntity, AnuStatueModel> {
    public AnuStatueRenderer(EntityRendererProvider.Context context, AnuStatueModel entityModel) {
        super(context, entityModel, 0.5f);
        addLayer(new AnuStatueOverlayLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(AnuStatueEntity entity) {
        return AnuStatueModel.TEXTURE;
    }

    @Override
    protected void setupRotations(AnuStatueEntity entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(ageInTicks * ageInTicks * 0.15f));
    }

    @Override
    public void render(AnuStatueEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                       int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        float i = (entity.tickCount + partialTicks) / 200f;
        float j = Math.min(i > 0.8f ? (i - 0.8f) / 0.2f : 0, 1);
        Random random = new Random(432L);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.lightning());
        poseStack.pushPose();
        int k = 0;
        poseStack.translate(0, 1.5, 0);
        while ((float) k < (i + i * i) / 2f * 60f) {
            poseStack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360 + i * 90));
            float l = random.nextFloat() * 20 + 5 + j * 10;
            float m = random.nextFloat() * 2 + 1 + j * 2;
            Matrix4f pose = poseStack.last().pose();
            int n = (int) (255 * (1 - j));
            vertex01(vertexConsumer, pose, n);
            vertex2(vertexConsumer, pose, l, m);
            vertex3(vertexConsumer, pose, l, m);
            vertex01(vertexConsumer, pose, n);
            vertex3(vertexConsumer, pose, l, m);
            vertex4(vertexConsumer, pose, l, m);
            vertex01(vertexConsumer, pose, n);
            vertex4(vertexConsumer, pose, l, m);
            vertex2(vertexConsumer, pose, l, m);
            k++;
        }
        poseStack.popPose();
    }

    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0) / 2.0);

    private static void vertex01(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i) {
        vertexConsumer.vertex(matrix4f, 0.0f, 0.0f, 0.0f).color(66, 0, 176, i).endVertex();
    }

    private static void vertex2(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g) {
        vertexConsumer.vertex(matrix4f, -HALF_SQRT_3 * g, f, -0.5f * g).color(255, 0, 0, 0).endVertex();
    }

    private static void vertex3(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g) {
        vertexConsumer.vertex(matrix4f, HALF_SQRT_3 * g, f, -0.5f * g).color(255, 0, 0, 0).endVertex();
    }

    private static void vertex4(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g) {
        vertexConsumer.vertex(matrix4f, 0.0f, f, g).color(255, 0, 0, 0).endVertex();
    }

    @NotNull
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((AnuStatueEntity) entity);
    }

}
