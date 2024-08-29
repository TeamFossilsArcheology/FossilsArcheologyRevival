package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.client.gui.debug.instruction.InstructionRenderer;
import com.fossil.fossil.client.gui.debug.navigation.PathingRenderer;
import com.fossil.fossil.util.Version;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
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

    @Inject(method = "renderLevel", at = @At(value = "TAIL"))
    private <E extends Entity> void renderDebugPaths(PoseStack poseStack, float partialTicks, long finishNanoTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projectionMatrix, CallbackInfo ci) {
        if (Version.debugEnabled()) {
            MultiBufferSource.BufferSource bufferSource = renderBuffers.bufferSource();
            final Minecraft mc = Minecraft.getInstance();
            final Vec3 viewPosition = mc.gameRenderer.getMainCamera().getPosition();
            poseStack.pushPose();
            poseStack.translate(-viewPosition.x, -viewPosition.y, -viewPosition.z);
            PathingRenderer.render(poseStack, bufferSource, partialTicks, finishNanoTime);
            InstructionRenderer.render(poseStack, bufferSource, partialTicks, finishNanoTime);
            bufferSource.endBatch();
            poseStack.popPose();
        }
    }
}
