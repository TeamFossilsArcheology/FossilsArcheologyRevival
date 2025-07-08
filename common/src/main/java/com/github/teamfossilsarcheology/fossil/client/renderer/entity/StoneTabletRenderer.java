package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.entity.StoneTablet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class StoneTabletRenderer extends EntityRenderer<StoneTablet> implements RendererFabricFix {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/entity/stone_tablet.png");

    public StoneTabletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    private static int getLightColor(StoneTablet entity, float centerX, float centerY) {
        int x = entity.getBlockX();
        int y = Mth.floor(entity.getY() + centerY / 16);
        int z = entity.getBlockZ();
        Direction direction = entity.getDirection();
        if (direction == Direction.NORTH) {
            x = Mth.floor(entity.getX() + centerX / 16);
        }
        if (direction == Direction.WEST) {
            z = Mth.floor(entity.getZ() - centerX / 16);
        }
        if (direction == Direction.SOUTH) {
            x = Mth.floor(entity.getX() - centerX / 16);
        }
        if (direction == Direction.EAST) {
            z = Mth.floor(entity.getZ() + centerX / 16);
        }
        return LevelRenderer.getLightColor(entity.level(), new BlockPos(x, y, z));
    }

    @Override
    public void render(StoneTablet entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - entityYaw));
        StoneTablet.Variant variant = entity.variant;
        poseStack.scale(0.0625f, 0.0625f, 0.0625f);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
        renderTablet(poseStack, vertexConsumer, entity, variant.sizeX, variant.sizeY, variant.offsetX, variant.offsetY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private void renderTablet(PoseStack poseStack, VertexConsumer vertexConsumer, StoneTablet entity, int width, int height, int texU, int texV) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        float offsetX = -width / 2.0f;
        float offsetY = -height / 2.0f;
        for (int blockX = 0; blockX < width / 16; ++blockX) {
            for (int blockZ = 0; blockZ < height / 16; ++blockZ) {
                float minX = offsetX + (blockX * 16);
                float maxX = offsetX + ((blockX + 1) * 16);
                float minY = offsetY + (blockZ * 16);
                float maxY = offsetY + ((blockZ + 1) * 16);
                int light = getLightColor(entity, (maxX + minX) / 2.0f, (maxY + minY) / 2.0f);
                //texture coordinates go bottom -> top and right -> left
                //vertex coordinates map the textures onto the block with 0 in the middle and go bottom -> top and right -> left
                float minTexX = (texU + width - blockX * 16) / 256f;
                float maxTexX = (texU + width - (blockX + 1) * 16) / 256f;
                float minTexY = (texV + height - blockZ * 16) / 256f;
                float maxTexY = (texV + height - (blockZ + 1) * 16) / 256f;
                //Front
                vertex(matrix4f, matrix3f, vertexConsumer, maxX, minY, maxTexX, minTexY, 0.4f, 0, 0, -1, light);//z=-0.5f
                vertex(matrix4f, matrix3f, vertexConsumer, minX, minY, minTexX, minTexY, 0.4f, 0, 0, -1, light);
                vertex(matrix4f, matrix3f, vertexConsumer, minX, maxY, minTexX, maxTexY, 0.4f, 0, 0, -1, light);
                vertex(matrix4f, matrix3f, vertexConsumer, maxX, maxY, maxTexX, maxTexY, 0.4f, 0, 0, -1, light);

                minTexY = (texV + (height + 128) - blockZ * 16) / 256f;
                maxTexY = (texV + (height + 128) - (blockZ + 1) * 16) / 256f;
                vertex(matrix4f, matrix3f, vertexConsumer, maxX, maxY, maxTexX, maxTexY, 0.5f, 0, 0, 1, light);//z=0.5f
                vertex(matrix4f, matrix3f, vertexConsumer, minX, maxY, minTexX, maxTexY, 0.5f, 0, 0, 1, light);
                vertex(matrix4f, matrix3f, vertexConsumer, minX, minY, minTexX, minTexY, 0.5f, 0, 0, 1, light);
                vertex(matrix4f, matrix3f, vertexConsumer, maxX, minY, maxTexX, minTexY, 0.5f, 0, 0, 1, light);
            }
        }
    }

    private void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float x, float y, float u, float v, float z, int k,
                        int l, int m, int n) {
        vertexConsumer.vertex(matrix4f, x, y, z).color(255, 255, 255, 255).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(n).normal(matrix3f, k, l, m).endVertex();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(StoneTablet entity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((StoneTablet) entity);
    }
}
