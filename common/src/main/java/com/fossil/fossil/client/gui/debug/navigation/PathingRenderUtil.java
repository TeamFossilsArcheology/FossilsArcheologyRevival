package com.fossil.fossil.client.gui.debug.navigation;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.phys.AABB;

public class PathingRenderUtil {

    public static void renderLineBox(PoseStack poseStack, MultiBufferSource buffer, BlockPos blockPos) {
        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), new AABB(blockPos), 1, 1, 1, 0.75f);
    }

    public static void renderLine(PoseStack poseStack, Node start, Node end) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        Vector4f vector4f = new Vector4f(start.x, start.y, start.z, 1.0f);
        vector4f.add(-PathingDebug.pos1.getX() + 0.5f, -PathingDebug.pos1.getY(), -PathingDebug.pos1.getZ() + 0.5f, 0);
        vector4f.transform(poseStack.last().pose());
        bufferBuilder.vertex(vector4f.x(), vector4f.y(), vector4f.z()).color(255, 255, 255, 255).endVertex();

        vector4f = new Vector4f(end.x, end.y, end.z, 1.0f);
        vector4f.add(-PathingDebug.pos1.getX() + 0.5f, -PathingDebug.pos1.getY(), -PathingDebug.pos1.getZ() + 0.5f, 0);
        vector4f.transform(poseStack.last().pose());
        bufferBuilder.vertex(vector4f.x(), vector4f.y(), vector4f.z()).color(255, 255, 255, 255).endVertex();
        tesselator.end();
    }

    public static void renderTextBatch(PoseStack poseStack, Minecraft minecraft, Node[] nodes) {
        RenderSystem.enableTexture();
        boolean transparent = true;
        if (transparent) {
            RenderSystem.disableDepthTest();
        } else {
            RenderSystem.enableDepthTest();
        }
        RenderSystem.depthMask(true);
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        poseStack.pushPose();
        for (int i = 0; i < nodes.length; i++) {
            poseStack.pushPose();
            poseStack.translate(-PathingDebug.pos1.getX(), -PathingDebug.pos1.getY(), -PathingDebug.pos1.getZ());
            poseStack.translate(nodes[i].x + 0.5, nodes[i].y + 0.5, nodes[i].z + 0.5);
            poseStack.mulPoseMatrix(new Matrix4f(minecraft.gameRenderer.getMainCamera().rotation()));
            poseStack.scale(0.02f, -0.02f, 0.02f);
            poseStack.scale(-1, 1, 1);
            float g = -minecraft.font.width(String.valueOf(i)) / 2f;
            minecraft.font.drawInBatch(String.valueOf(i), g, 0, -1, false, poseStack.last().pose(), bufferSource, transparent, 0, 15728880);
            poseStack.popPose();
        }
        bufferSource.endBatch();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }
}
