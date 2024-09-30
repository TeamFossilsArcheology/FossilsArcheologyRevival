package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.inventory.AnalyzerMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AnalyzerScreen extends AbstractContainerScreen<AnalyzerMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/analyzer.png");

    public AnalyzerScreen(AnalyzerMenu containerMenu, Inventory inventory, Component component) {
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
        int progress = menu.getAnalyzeProgress() * 22 / 200;
        blit(poseStack, x + 80, y + 22, 177, 18, progress, 9);
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            int energyProgress = 35 * menu.getStoredEnergy() / FossilConfig.getInt(FossilConfig.MACHINE_MAX_ENERGY);
            blit(poseStack, x + 81, y + 35, 0, 166, 20, 35);
            blit(poseStack, x + 81, y + 35, 20, 166, 20, 35 - energyProgress);
        }
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        font.draw(poseStack, title, titleLabelX, titleLabelY, 0x404040);
        font.draw(poseStack, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x404040);
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY) {
        super.renderTooltip(poseStack, mouseX, mouseY);
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;
            if (mouseX > x + 81 && mouseX < x + 101 && mouseY > y + 35 && mouseY < y + 70) {
                renderTooltip(poseStack, new TextComponent(menu.getStoredEnergy() + " E"), mouseX, mouseY);
            }
        }
    }
}
