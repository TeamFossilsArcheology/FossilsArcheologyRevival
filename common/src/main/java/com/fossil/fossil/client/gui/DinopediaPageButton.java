package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class DinopediaPageButton extends Button {
    private static final ResourceLocation DINOPEDIA_TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/dinopedia.png");
    private final boolean isForward;

    public DinopediaPageButton(int x, int y, int width, int height, boolean isForward, OnPress onPress) {
        super(x, y, width, height, new TextComponent(""), onPress);
        this.isForward = isForward;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DINOPEDIA_TEXTURE);
        blit(poseStack, x, y, isForward ? 0 : 34, 223, 34, 30);
    }

    @Override
    public void playDownSound(SoundManager handler) {
        super.playDownSound(handler);
    }
}
