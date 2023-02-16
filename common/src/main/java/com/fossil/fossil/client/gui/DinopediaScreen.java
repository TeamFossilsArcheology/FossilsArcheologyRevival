package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class DinopediaScreen extends Screen {
    private static final ResourceLocation DINOPEDIA_BACKGROUND = new ResourceLocation(Fossil.MOD_ID, "textures/gui/dinopedia.png");
    private final Prehistoric entity;
    private final int xSize = 512;
    private final int ySize = 430;

    public DinopediaScreen(Prehistoric entity) {
        super(new TextComponent(""));
        this.entity = entity;
    }

    @Override
    protected void init() {
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        renderBackgroundLayer(poseStack);
        renderForegroundLayer(poseStack, mouseX, mouseY, partialTick);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }
    private void renderBackgroundLayer(PoseStack poseStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DINOPEDIA_BACKGROUND);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        blit(poseStack, k, l, 0, 0, xSize, ySize, 512, 512);
    }

    private void renderForegroundLayer(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        int col = (157 << 16) | (126 << 8) | 103;
        int x = (width - xSize/2) / 2;
        int y = (height - ySize/2) / 2;
        poseStack.pushPose();
        poseStack.scale(1.5f, 1.5f, 1.5f);
        drawString(poseStack, font, entity.getType().getDescription(), x, y, (66 << 16) | (48 << 8) | 36);
        poseStack.popPose();
        drawString(poseStack, font, new TextComponent("Age " + entity.getAgeInDays()),  x, y, col);
        drawString(poseStack, font, new TextComponent("Health " + entity.getHealth() + "/" + entity.getMaxHealth()),  x, y+10, col);
        drawString(poseStack, font, new TextComponent("Hunger " + entity.getHunger() + "/" + entity.getMaxHunger()),  x, y+20, col);
    }

}
