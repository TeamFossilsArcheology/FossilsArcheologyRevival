package fossilsarcheology.client.gui;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.entity.TileEntityWorktable;
import fossilsarcheology.server.container.WorktableContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class WorktableGUI extends GuiContainer {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/gui/workbench.png");
    private final TileEntityWorktable tile;

    public WorktableGUI(InventoryPlayer playerInventory, TileEntityWorktable tile) {
        super(new WorktableContainer(playerInventory, tile));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String customName = this.tile.getName();
        this.fontRenderer.drawString(I18n.format(customName), 30, 6, 0x404040);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int centerX = (this.width - this.xSize) / 2;
        int centerY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(centerX, centerY, 0, 0, this.xSize, this.ySize);
        int progress;

        if (this.tile.isBurning()) {
            progress = this.tile.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(centerX + 82, centerY + 36 + 12 - progress, 176, 12 - progress, 14, progress + 2);
        }

        progress = this.tile.getCookProgressScaled(24);
        this.drawTexturedModalRect(centerX + 79, centerY + 18, 176, 14, progress + 1, 16);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
