package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static com.github.teamfossilsarcheology.fossil.client.gui.CustomTitleScreen.LAYER_TEXTURE_BACK;
import static com.github.teamfossilsarcheology.fossil.client.gui.CustomTitleScreen.LAYER_TEXTURE_FRONT;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Unique
    private final int fossil$initialOffsetFront = new Random().nextInt(1027);
    @Unique
    private final int fossil$initialOffsetBack = new Random().nextInt(2047);
    @Unique
    private int fossil$layerTick;

    private TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PanoramaRenderer;render(FF)V"))
    protected void r(PoseStack poseStack, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        fossil$layerTick++;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableTexture();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, LAYER_TEXTURE_BACK);
        float u = fossil$initialOffsetBack + ((fossil$layerTick + partialTick) / 2f) + 1;
        blit(poseStack, 0, 0, u / (960f / width), 0, width, height, (int) (1024 * (height / 128f)), height);

        RenderSystem.setShaderTexture(0, LAYER_TEXTURE_FRONT);
        u = fossil$initialOffsetFront + fossil$layerTick + partialTick + 2 + 512;
        blit(poseStack, 0, 0, u / (960f / width), 0, width, height, (int) (2048 * (height / 128f)), height);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PanoramaRenderer;render(FF)V"))
    protected void removePanorama(PanoramaRenderer instance, float deltaT, float alpha) {
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIFFIIII)V"))
    protected void removeOverlay(PoseStack poseStack, int a, int b, int c, int d, float e, float f, int g, int h, int i, int j) {
    }
}
