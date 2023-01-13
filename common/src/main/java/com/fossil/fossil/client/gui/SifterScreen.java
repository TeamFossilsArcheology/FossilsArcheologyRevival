package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.inventory.SifterMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SifterScreen extends AbstractContainerScreen<SifterMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/sifter.png");

    public SifterScreen(SifterMenu containerMenu, Inventory inventory, Component component) {
        super(containerMenu, inventory, component);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;
        blit(poseStack, i, j, 0, 0, imageWidth, imageHeight);
        int var7 = menu.getSiftProgress() * 26 / 200;
        blit(poseStack, i + 75, j + 33, 181, 2, 31, var7 + 1);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        font.draw(poseStack, title, (float) titleLabelX, (float) titleLabelY, 0x404040);
    }
}
