package com.github.teamfossilsarcheology.fossil.client.gui;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.inventory.FeederMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FeederScreen extends AbstractContainerScreen<FeederMenu> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/feeder.png");
    private static final int BAR_HEIGHT = 47;
    private static final int BAR_WIDTH = 5;

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
        drawString(poseStack, font, String.valueOf(menu.getMeat()), x + 10, y + 20, 0xff285e);
        drawString(poseStack, font, String.valueOf(menu.getVeg()), x + 10, y + 39, 0X35AC47);
        drawString(poseStack, font, String.valueOf(menu.getFish()), x + 10, y + 58, 0X00cfff);
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
        int scaledMeat = menu.getMeat() * BAR_HEIGHT / 10000;
        blit(poseStack, x + 73, y + 55 - scaledMeat, imageWidth, BAR_HEIGHT - scaledMeat, BAR_WIDTH, scaledMeat);
        int scaledVeg = menu.getVeg() * BAR_HEIGHT / 10000;
        blit(poseStack, x + 116, y + 55 - scaledVeg, 176, BAR_HEIGHT - scaledVeg, BAR_WIDTH, scaledVeg);
        int scaledFish = menu.getFish() * BAR_HEIGHT / 10000;
        blit(poseStack, x + 158, y + 55 - scaledFish, 176, BAR_HEIGHT - scaledFish, BAR_WIDTH, scaledFish);
    }
}
