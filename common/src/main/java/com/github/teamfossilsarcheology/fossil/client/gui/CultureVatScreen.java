package com.github.teamfossilsarcheology.fossil.client.gui;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CultureVatScreen extends AbstractContainerScreen<CultureVatMenu> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/culture_vat.png");
    public static final int PROGRESS_WIDTH = 21;
    public static final int PROGRESS_HEIGHT = 9;
    public static final int FUEL_WIDTH = 14;
    public static final int FUEL_HEIGHT = 12;

    public CultureVatScreen(CultureVatMenu containerMenu, Inventory inventory, Component component) {
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

        if (menu.getFuelTime() > 0) {
            int scaledProgress = menu.getFuelTime() * (FUEL_HEIGHT + 1) / (menu.getTotalFuelTime() + 1);
            guiGraphics.blit(TEXTURE, x + 82, y + 37 + FUEL_HEIGHT - scaledProgress, 176, FUEL_HEIGHT - scaledProgress, FUEL_WIDTH, scaledProgress);
        }

        int scaledProgress = menu.getCultivationTime() * PROGRESS_WIDTH / CultureVatMenu.CULTIVATION_DURATION;
        guiGraphics.blit(TEXTURE, x + 78, y + 22, 176, FUEL_HEIGHT, scaledProgress, PROGRESS_HEIGHT);
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            int energyProgress = 35 * menu.getStoredEnergy() / FossilConfig.getInt(FossilConfig.MACHINE_MAX_ENERGY);
            guiGraphics.blit(TEXTURE, x + 114, y + 44, 0, 166, 20, 35);
            guiGraphics.blit(TEXTURE, x + 114, y + 44, 20, 166, 20, 35 - energyProgress);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            int cx = (width - imageWidth) / 2;
            int cy = (height - imageHeight) / 2;
            if (x > cx + 114 && x < cx + 134 && y > cy + 44 && y < cy + 79) {
                guiGraphics.renderTooltip(font, Component.literal(menu.getStoredEnergy() + " E"), x, y);
            }
        }
    }
}
