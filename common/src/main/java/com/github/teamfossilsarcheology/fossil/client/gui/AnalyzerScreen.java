package com.github.teamfossilsarcheology.fossil.client.gui;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.inventory.AnalyzerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AnalyzerScreen extends AbstractContainerScreen<AnalyzerMenu> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/analyzer.png");

    public AnalyzerScreen(AnalyzerMenu containerMenu, Inventory inventory, Component component) {
        super(containerMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth / 2 - font.width(title) / 2);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        int progress = menu.getAnalyzeProgress() * 22 / AnalyzerMenu.ANALYZE_DURATION;
        guiGraphics.blit(TEXTURE, x + 80, y + 22, 177, 18, progress, 9);
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            int energyProgress = 35 * menu.getStoredEnergy() / FossilConfig.getInt(FossilConfig.MACHINE_MAX_ENERGY);
            guiGraphics.blit(TEXTURE, x + 81, y + 35, 0, 166, 20, 35);
            guiGraphics.blit(TEXTURE, x + 81, y + 35, 20, 166, 20, 35 - energyProgress);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;
            if (mouseX > x + 81 && mouseX < x + 101 && mouseY > y + 35 && mouseY < y + 70) {
                guiGraphics.renderTooltip(font, Component.literal(menu.getStoredEnergy() + " E"), mouseX, mouseY);
            }
        }
    }
}
