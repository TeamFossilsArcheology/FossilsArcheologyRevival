package com.github.teamfossilsarcheology.fossil.client.gui;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.inventory.WorktableMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WorktableScreen extends AbstractContainerScreen<WorktableMenu> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/workbench.png");
    public static final int PROGRESS_WIDTH = 24;
    public static final int PROGRESS_HEIGHT = 14;
    public static final int FUEL_WIDTH = 12;
    public static final int FUEL_HEIGHT = 12;

    public WorktableScreen(WorktableMenu containerMenu, Inventory inventory, Component component) {
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
        if (menu.isLit()) {
            int progress = menu.getLitProgress();
            blit(poseStack, x + 82, y + 36 + FUEL_HEIGHT - progress, 177, FUEL_HEIGHT - progress, FUEL_WIDTH, progress + 1);
        }
        int progress = menu.getBurnProgress();
        blit(poseStack, x + 76, y + 20, 176, FUEL_HEIGHT + 2, progress, PROGRESS_HEIGHT);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        font.draw(poseStack, title, titleLabelX, titleLabelY, 0x404040);
        font.draw(poseStack, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x404040);
    }
}