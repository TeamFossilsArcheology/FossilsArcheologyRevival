package com.github.teamfossilsarcheology.fossil.client.gui;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.inventory.WorktableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WorktableScreen extends AbstractContainerScreen<WorktableMenu> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/workbench.png");
    public static final int PROGRESS_WIDTH = 24;
    public static final int PROGRESS_HEIGHT = 14;
    public static final int FUEL_WIDTH = 14;
    public static final int FUEL_HEIGHT = 14;

    public WorktableScreen(WorktableMenu containerMenu, Inventory inventory, Component component) {
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
        if (menu.isLit()) {
            int progress = menu.getLitProgress();
            guiGraphics.blit(TEXTURE, x + 82, y + 36 + FUEL_HEIGHT - progress, 177, FUEL_HEIGHT - progress, FUEL_WIDTH, progress);
        }
        int progress = menu.getBurnProgress();
        guiGraphics.blit(TEXTURE, x + 76, y + 20, 176, FUEL_HEIGHT, progress, PROGRESS_HEIGHT);
    }
}