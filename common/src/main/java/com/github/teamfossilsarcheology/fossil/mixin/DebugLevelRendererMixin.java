package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.InstructionRenderer;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingRenderer;
import com.github.teamfossilsarcheology.fossil.util.Version;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class DebugLevelRendererMixin {
    @Shadow
    @Final
    private RenderBuffers renderBuffers;

    @Shadow
    private Frustum cullingFrustum;

    @Inject(method = "renderLevel", at = @At(value = "TAIL"))
    private void renderDebugPaths(PoseStack poseStack, float partialTick, long finishNanoTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projectionMatrix, CallbackInfo ci) {
        if (Version.debugEnabled()) {
            MultiBufferSource.BufferSource bufferSource = renderBuffers.bufferSource();
            final Minecraft mc = Minecraft.getInstance();
            final Vec3 viewPosition = mc.gameRenderer.getMainCamera().getPosition();
            poseStack.pushPose();
            poseStack.translate(-viewPosition.x, -viewPosition.y, -viewPosition.z);
            PathingRenderer.render(poseStack, bufferSource, partialTick, finishNanoTime);
            InstructionRenderer.render(poseStack, bufferSource, partialTick, finishNanoTime, cullingFrustum);
            bufferSource.endBatch();
            poseStack.popPose();
        }
    }
}
