package com.fossil.fossil.client.gui.debug.instruction;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.util.Color;

import java.util.List;

public class InstructionRenderUtil {
    public static void renderTextBatch(PoseStack poseStack, Minecraft minecraft, List<Pair<Vec3, Instruction>> positions) {
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        poseStack.pushPose();
        for (int i = 0; i < positions.size(); i++) {
            Vec3 pos = positions.get(i).left();
            Instruction instruction = positions.get(i).right();
            poseStack.pushPose();
            poseStack.translate(pos.x, pos.y, pos.z);
            poseStack.mulPoseMatrix(new Matrix4f(minecraft.gameRenderer.getMainCamera().rotation()));
            poseStack.scale(0.02f, -0.02f, 0.02f);
            poseStack.scale(-1, 1, 1);
            String string;
            if (instruction instanceof Instruction.Idle idle) {
                string = String.format("%s: %s (%s seconds)", i, instruction.type, idle.duration / 20);
            } else if(instruction instanceof Instruction.AttachTo attachTo) {
                string = String.format("%s: %s %s", i, instruction.type, attachTo.direction);
            } else {
                string = String.format("%s: %s", i, instruction.type);
            }
            float g = -minecraft.font.width(string) / 2f;
            minecraft.font.drawInBatch(string, g, 0, -1, false, poseStack.last().pose(), bufferSource, true, 0, 15728880);
            poseStack.popPose();
        }
        bufferSource.endBatch();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }
    private static float nanoToPulse(long finishNanoTime) {
        return 0.1f + 0.5f * Math.abs(Mth.sin(finishNanoTime / 5E8f));
    }

    public static void renderWholeBox(PoseStack poseStack, BlockPos pos, Color color, long finishNanoTime) {
        renderWholeBox(poseStack, pos, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, nanoToPulse(finishNanoTime));
    }

    public static void renderWholeBox(PoseStack poseStack, BlockPos pos, float r, float g, float b, float a) {
        //Man how does axiom make their pulsing box look so good?
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
        Matrix4f matrix4f = poseStack.last().pose();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        float maxX = 1;
        float maxY = 1;
        float maxZ = 1;
        //Different color values to add contrast
        //I have not yet found a way to render the faces in a different order
        //Left
        bufferBuilder.vertex(matrix4f, 0, 0, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, 0, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, maxY, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, maxY, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        //Front
        bufferBuilder.vertex(matrix4f, 0, maxY, maxZ).color(r * 0.8f, g * 0.8f, b * 0.8f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, 0, maxZ).color(r * 0.8f, g * 0.8f, b * 0.8f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, maxY, maxZ).color(r * 0.8f, g * 0.8f, b * 0.8f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, maxZ).color(r * 0.8f, g * 0.8f, b * 0.8f, a).endVertex();
        //Right
        bufferBuilder.vertex(matrix4f, maxX, 0, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, maxY, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, maxY, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        //Back
        bufferBuilder.vertex(matrix4f, maxX, maxY, 0).color(r * 0.8f, g * 0.8f, b * 0.8f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, 0).color(r * 0.8f, g * 0.8f, b * 0.8f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, maxY, 0).color(r * 0.8f, g * 0.8f, b * 0.8f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, 0, 0).color(r * 0.8f, g * 0.8f, b * 0.8f, a).endVertex();
        //Bottom
        bufferBuilder.vertex(matrix4f, 0, 0, 0).color(r * 0.9f, g * 0.9f, b * 0.9f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, 0).color(r * 0.9f, g * 0.9f, b * 0.9f, a).endVertex();
        //The next 3 also create a triangle for some reason
        bufferBuilder.vertex(matrix4f, 0, 0, maxZ).color(r * 0.9f, g * 0.9f, b * 0.9f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, maxX).color(r * 0.9f, g * 0.9f, b * 0.9f, a).endVertex();
        //Top
        bufferBuilder.vertex(matrix4f, 0, maxY, 0).color(r * 1, g * 1, b * 1, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, maxY, maxZ).color(r * 1, g * 1, b * 1, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, maxY, 0).color(r * 1, g * 1, b * 1, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, maxY, maxZ).color(r * 1, g * 1, b * 1, a).endVertex();
        tesselator.end();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();

        poseStack.popPose();
    }
}
