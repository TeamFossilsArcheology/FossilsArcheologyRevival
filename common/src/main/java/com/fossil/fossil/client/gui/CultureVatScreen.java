package com.fossil.fossil.client.gui;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.inventory.CultureVatMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CultureVatScreen extends AbstractContainerScreen<CultureVatMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/culture_vat.png");
    private static final int PROGRESS_WIDTH = 21;
    private static final int FUEL_HEIGHT = 12;

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
            int scaledProgress = menu.getFuelTime() * FUEL_HEIGHT / (menu.getTotalFuelTime() + 1);
            blit(poseStack, x + 81, y + 36 + FUEL_HEIGHT - scaledProgress, 176, FUEL_HEIGHT - scaledProgress, 14, scaledProgress + 2);
        }

        int scaledProgress = menu.getCultivationTime() * PROGRESS_WIDTH / CultureVatMenu.CULTIVATION_TIME;
        blit(poseStack, x + 78, y + 22, 176, 14, scaledProgress + 1, 16);
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            int energyProgress = 35 * menu.getStoredEnergy() / FossilConfig.getInt(FossilConfig.MACHINE_MAX_ENERGY);
            blit(poseStack, x + 114, y + 44, 0, 166, 20, 35);
            blit(poseStack, x + 114, y + 44, 20, 166, 20, 35 - energyProgress);
        }
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        font.draw(poseStack, title, titleLabelX, titleLabelY, 0x404040);
        font.draw(poseStack, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x404040);
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int x, int y) {
        super.renderTooltip(poseStack, x, y);
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            int cx = (width - imageWidth) / 2;
            int cy = (height - imageHeight) / 2;
            if (x > cx + 114 && x < cx + 134 && y > cy + 44 && y < cy + 79) {
                renderTooltip(poseStack, new TextComponent(menu.getStoredEnergy() + " E"), x, y);
            }
        }
    }
}
