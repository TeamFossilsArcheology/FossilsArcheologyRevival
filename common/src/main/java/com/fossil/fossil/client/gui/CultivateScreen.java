package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.inventory.CultivateMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CultivateScreen extends AbstractContainerScreen<CultivateMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/cultivate.png");

    public CultivateScreen(CultivateMenu containerMenu, Inventory inventory, Component component) {
        super(containerMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth / 2 - font.width(title) / 2);
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
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.getFuelTime() > 0) {
            int fuelHeight = 12;
            int scaledProgress = menu.getFuelTime() * fuelHeight / (menu.getTotalFuelTime() + 1);
            blit(poseStack, i + 82, j + 36 + fuelHeight - scaledProgress, 176, fuelHeight - scaledProgress, 14, scaledProgress + 2);
        }

        int progressWidth = 24;
        int scaledProgress = menu.getCultivationTime() * progressWidth / 6000;
        blit(poseStack, i + 79, j + 18, 176, 14, scaledProgress + 1, 16);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        this.font.draw(poseStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, 0x404040);
        this.font.draw(poseStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 0x404040);
    }
}
