package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Unique
    private static final ResourceLocation LAYER_TEXTURE_BACK = FossilMod.location("textures/gui/parallax/layer_0.png");
    @Unique
    private static final ResourceLocation LAYER_TEXTURE_FRONT = FossilMod.location("textures/gui/parallax/layer_1.png");
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
    protected void renderCustomTitleScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (FossilConfig.isEnabled(FossilConfig.CUSTOM_MAIN_MENU)) {
            fossil$layerTick++;
            float u = fossil$initialOffsetBack + ((fossil$layerTick + partialTick) / 2f) + 1;
            guiGraphics.blit(LAYER_TEXTURE_BACK, 0, 0, u / (960f / width), 0, width, height, (int) (1024 * (height / 128f)), height);

            u = fossil$initialOffsetFront + fossil$layerTick + partialTick + 2 + 512;
            guiGraphics.blit(LAYER_TEXTURE_FRONT, 0, 0, u / (960f / width), 0, width, height, (int) (2048 * (height / 128f)), height);
        }
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PanoramaRenderer;render(FF)V"))
    protected boolean removePanorama(PanoramaRenderer instance, float deltaT, float alpha) {
        return !FossilConfig.isEnabled(FossilConfig.CUSTOM_MAIN_MENU);
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIFFIIII)V"))
    protected boolean removeOverlay(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        return !FossilConfig.isEnabled(FossilConfig.CUSTOM_MAIN_MENU);
    }
}
