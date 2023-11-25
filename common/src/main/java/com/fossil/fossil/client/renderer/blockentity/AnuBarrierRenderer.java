package com.fossil.fossil.client.renderer.blockentity;

import com.fossil.fossil.block.custom_blocks.AnuBarrierBlock;
import com.fossil.fossil.block.entity.AnuBarrierBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AnuBarrierRenderer implements BlockEntityRenderer<AnuBarrierBlockEntity> {
    private final float textureWidth = 16;
    private final float textureHeight = 512;
    private final ResourceLocation location = new ResourceLocation("textures/block/nether_portal.png");

    public AnuBarrierRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(AnuBarrierBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        Direction direction = blockEntity.getBlockState().getValue(AnuBarrierBlock.FACING);
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5, 0, -0.5);
        //Prevent z-fighting with half blocks
        poseStack.scale(0.0625f, 0.0625f, direction.getAxis() == Direction.Axis.X ? 0.063f : 0.062f);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(location));
        renderBarrier(blockEntity, poseStack, vertexConsumer, packedLight);
        poseStack.popPose();
    }

    private void renderBarrier(AnuBarrierBlockEntity blockEntity, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight) {
        int maxWidth = (blockEntity.radius * 2 + 1) * 16;
        int maxHeight = blockEntity.height * 16;
        if (blockEntity.getState() == AnuBarrierBlockEntity.STATE_ENABLED) {
            renderRectangle(poseStack, vertexConsumer, maxWidth, 0, maxHeight, packedLight);
        } else if (blockEntity.getState() != AnuBarrierBlockEntity.STATE_DISABLED) {
            //This algorithm renders a growing/shrinking circle
            float step = Mth.lerp(blockEntity.getBarrierTimer() / AnuBarrierBlockEntity.duration, 0, maxHeight);
            int rowWidth = (int) (2 + step * 2);
            int colCount = rowWidth / 2;
            int x = colCount;
            int y = 0;
            //Start with the widest rectangle at the bottom
            int prevY;
            while (x > 0) {
                if (x * x + y * y > colCount * colCount) {
                    //If the current pixel is not in the circle remain at that height and check one to the right
                    x--;
                    continue;
                }
                prevY = y;
                boolean done = false;
                while (!done) {
                    y++;
                    //Combine multiple rectangles if they share the same width
                    if (x * x + y * y > colCount * colCount) {
                        done = true;
                        renderRectangle(poseStack, vertexConsumer, Math.min(x * 2, maxWidth), prevY, Math.min(y, maxHeight) - prevY, packedLight);
                    }
                }
                x--;
            }
        }
    }

    private void renderRectangle(PoseStack poseStack, VertexConsumer vertexConsumer, int width, float y, int height, int packedLight) {
        Matrix4f matrix4f = poseStack.last().pose();
        Matrix3f matrix3f = poseStack.last().normal();
        float minX = 8 - (width - width / 2f);
        float maxX = minX + width;
        float maxY = y + height;
        //Offset the texture horizontally by half the width
        float offset = -Mth.floor(width / 2f);
        float minTexX = (width + offset) / textureWidth;
        float maxTexX = offset / textureWidth;
        //Offset the texture vertically depending on the height and y position in the circle
        float minTexY = (textureHeight - y) / textureHeight;
        float maxTexY = minTexY - height / textureHeight;
        vertex(matrix4f, matrix3f, vertexConsumer, maxX, y, maxTexX, minTexY, packedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, minX, y, minTexX, minTexY, packedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, minX, maxY, minTexX, maxTexY, packedLight);
        vertex(matrix4f, matrix3f, vertexConsumer, maxX, maxY, maxTexX, maxTexY, packedLight);
    }

    private void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float x, float y, float u, float v, int n) {
        vertexConsumer.vertex(matrix4f, x, y, 8).color(255, 255, 255, 255).uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(n).normal(matrix3f, 0, 0, -1).endVertex();
    }
}
