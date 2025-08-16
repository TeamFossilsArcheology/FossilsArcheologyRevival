package com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.InstructionTab;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import software.bernie.geckolib.core.object.Color;

import java.util.List;

public class InstructionRenderUtil {
    public static void renderTextBatch(PoseStack poseStack, Minecraft minecraft, List<Pair<Vec3, Instruction>> positions) {
        RenderSystem.depthMask(true);
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        poseStack.pushPose();
        for (int i = 0; i < positions.size(); i++) {
            Vec3 pos = positions.get(i).left();
            Instruction instruction = positions.get(i).right();
            poseStack.pushPose();
            poseStack.translate(pos.x, pos.y, pos.z);
            poseStack.mulPoseMatrix(new Matrix4f().rotation(minecraft.gameRenderer.getMainCamera().rotation()));
            poseStack.scale(0.02f, -0.02f, 0.02f);
            poseStack.scale(-1, 1, 1);
            String string;
            if (instruction instanceof Instruction.Idle idle) {
                string = String.format("%s: %s (%s seconds)", i, instruction.type, idle.duration / 20);
            } else if (instruction instanceof Instruction.AttachTo attachTo) {
                string = String.format("%s: %s %s", i, instruction.type, attachTo.direction);
            } else {
                string = String.format("%s: %s", i, instruction.type);
            }
            float g = -minecraft.font.width(string) / 2f;
            int color = -1;
            if (InstructionTab.highlightInstruction == instruction) {
                color = Color.RED.hashCode();
            }
            minecraft.font.drawInBatch(string, g, 0, color, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            poseStack.popPose();
        }
        bufferSource.endBatch();
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }

    public static void renderFloatingText(PoseStack poseStack, Minecraft minecraft, String text, Vec3 pos) {
        RenderSystem.depthMask(true);
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        poseStack.pushPose();
        poseStack.translate(pos.x, pos.y, pos.z);
        poseStack.mulPoseMatrix(new Matrix4f().rotation(minecraft.gameRenderer.getMainCamera().rotation()));
        poseStack.scale(0.02f, -0.02f, 0.02f);
        poseStack.scale(-1, 1, 1);
        float g = -minecraft.font.width(text) / 2f;
        int color = -1;
        minecraft.font.drawInBatch(text, g, 0, color, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
        bufferSource.endBatch();
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
        bufferBuilder.vertex(matrix4f, 0, 0, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, 0, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, maxY, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, maxY, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, maxY, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, 0, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, 0, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, 0, maxY, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, maxY, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, maxY, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, 0).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxX, 0, maxZ).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        tesselator.end();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();

        poseStack.popPose();
    }

    public static void renderDownArrow(PoseStack poseStack, Vec3 pos, Color color, float scale, long finishNanoTime) {
        float seconds = finishNanoTime / 10E8f;
        renderArrow(poseStack, pos, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1, true, scale, (seconds * 120) % 360, Mth.sin(seconds) * 0.25f);
    }

    public static void renderArrow(PoseStack poseStack, Vec3 pos, Color color, float yRot) {
        renderArrow(poseStack, pos, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1, false, 1, yRot, 0);
    }

    public static void renderArrow(PoseStack poseStack, Vec3 pos, float r, float g, float b, float a, boolean down, float scale, float yRot, float bounce) {
        poseStack.pushPose();
        poseStack.translate(pos.x(), pos.y() + bounce, pos.z());
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.scale(scale, scale, scale);
        if (!down) {
            float i = 0.3f;
            float o = -0.2f;
            poseStack.translate(0, i, o);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.translate(0, -i, -o);
        }
        Matrix4f matrix4f = poseStack.last().pose();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        float min = -0.25f;
        float max = 0.25f;
        bufferBuilder.vertex(matrix4f, 0, min, 0).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, max, max, max).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, min, max, max).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, min, max, min).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, max, max, min).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        bufferBuilder.vertex(matrix4f, max, max, max).color(r * 0.7f, g * 0.7f, b * 0.7f, a).endVertex();
        tesselator.end();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, max, max, min).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, min, max, min).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, min, max, max).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, max, max, max).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        tesselator.end();

        float minY = max;
        float maxY = minY + 0.5f;
        min = min / 2;
        max = max / 2;
        float minU = min - 0.05f;
        float maxU = max + 0.05f;
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, min, minY, min).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, min, minY, max).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, minU, maxY, minU).color(r * 0.85f, g * 0.85f, b * 0.85f, a).endVertex();
        bufferBuilder.vertex(matrix4f, minU, maxY, maxU).color(r * 0.85f, g * 0.85f, b * 0.85f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxU, maxY, maxU).color(r * 0.85f, g * 0.85f, b * 0.85f, a).endVertex();
        bufferBuilder.vertex(matrix4f, min, minY, max).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, max, minY, max).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, min, minY, min).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, max, minY, min).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, minU, maxY, minU).color(r * 0.85f, g * 0.85f, b * 0.85f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxU, maxY, minU).color(r * 0.85f, g * 0.85f, b * 0.85f, a).endVertex();
        bufferBuilder.vertex(matrix4f, maxU, maxY, maxU).color(r * 0.85f, g * 0.85f, b * 0.85f, a).endVertex();
        bufferBuilder.vertex(matrix4f, max, minY, min).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        bufferBuilder.vertex(matrix4f, max, minY, max).color(r * 0.6f, g * 0.6f, b * 0.6f, a).endVertex();
        tesselator.end();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();

        poseStack.popPose();
    }
}
