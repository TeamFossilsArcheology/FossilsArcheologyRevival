package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.inventory.FeederMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FeederScreen extends AbstractContainerScreen<FeederMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/feeder.png");

    public FeederScreen(FeederMenu containerMenu, Inventory inventory, Component component) {
        super(containerMenu, inventory, component);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        drawString(poseStack, font, String.valueOf(menu.getMeat()), x + 25, y + 32, 16711680);
        drawString(poseStack, font, String.valueOf(menu.getVeg()), x + 121, y + 32, 0X35AC47);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
        int scaledMeat = menu.getMeat() * 46 / 10000;
        blit(poseStack, x + 66, y + 55 - scaledMeat, imageWidth, 46 - scaledMeat, 3, scaledMeat);
        int scaledVeg = menu.getVeg() * 46 / 10000;
        blit(poseStack, x + 110, y + 55 - scaledVeg, 176, 46 - scaledVeg, 3, scaledVeg);
    }
}
