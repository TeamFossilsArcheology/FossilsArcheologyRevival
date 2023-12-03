package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.inventory.CultureVatMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CultureVatScreen extends AbstractContainerScreen<CultureVatMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/culture_vat.png");

    public CultureVatScreen(CultureVatMenu containerMenu, Inventory inventory, Component component) {
        super(containerMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth / 2 - font.width(title) / 2);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
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

        if (menu.getFuelTime() > 0) {
            int fuelHeight = 12;
            int scaledProgress = menu.getFuelTime() * fuelHeight / (menu.getTotalFuelTime() + 1);
            blit(poseStack, x + 82, y + 36 + fuelHeight - scaledProgress, 176, fuelHeight - scaledProgress, 14, scaledProgress + 2);
        }

        int progressWidth = 24;
        int scaledProgress = menu.getCultivationTime() * progressWidth / CultureVatMenu.CULTIVATION_TIME;
        blit(poseStack, x + 79, y + 18, 176, 14, scaledProgress + 1, 16);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        font.draw(poseStack, title, (float) titleLabelX, (float) titleLabelY, 0x404040);
        font.draw(poseStack, playerInventoryTitle, (float) inventoryLabelX, (float) inventoryLabelY, 0x404040);
    }
}
