package fossilsarcheology.client.gui;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.entity.TileEntityCultivate;
import fossilsarcheology.server.container.CultivateContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.text.NumberFormat;

public class CultivateGUI extends GuiContainer {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/gui/cultivate.png");
    private final TileEntityCultivate entity;
    private final CultivateContainer container;

    public CultivateGUI(TileEntityCultivate entity, CultivateContainer container) {
        super(container);
        this.entity = entity;
        this.container = container;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String customName = this.entity.getName();
        this.fontRenderer.drawString(I18n.format(customName), this.xSize / 2 - this.fontRenderer.getStringWidth(I18n.format(customName)) / 2, 6, 4210752);

        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        int drawX = (this.width - this.xSize) / 2;
        int drawY = (this.height - this.ySize) / 2;
        if (Revival.CONFIG_OPTIONS.machinesRequireEnergy) {
            if (mouseX > drawX + 114 && mouseX < drawX + 134) {
                if (mouseY > drawY + 44 && mouseY < drawY + 79) {
                    this.drawHoveringText(NumberFormat.getNumberInstance().format(container.energy) + " RF", mouseX - drawX, mouseY - drawY);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int drawX = (this.width - this.xSize) / 2;
        int drawY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(drawX, drawY, 0, 0, this.xSize, this.ySize);

        if (this.container.fuelTime > 0) {
            int fuelHeight = 12;
            int scaledProgress = this.container.fuelTime * fuelHeight / (this.container.totalFuelTime + 1);
            this.drawTexturedModalRect(drawX + 82, drawY + 36 + fuelHeight - scaledProgress, 176, fuelHeight - scaledProgress, 14, scaledProgress + 2);
        }

        int progressWidth = 24;
        int scaledProgress = this.container.cultivationTime * progressWidth / 6000;
        this.drawTexturedModalRect(drawX + 79, drawY + 18, 176, 14, scaledProgress + 1, 16);
        if (Revival.CONFIG_OPTIONS.machinesRequireEnergy) {
            int energyProgress = (int) ((container.energy / (float) entity.energyStorage.getMaxEnergyStored()) * 35);
            this.drawTexturedModalRect(drawX + 114, drawY + 44, 0, 166, 20, 35);
            this.drawTexturedModalRect(drawX + 114, drawY + 44, 20, 166, 20, 35 - energyProgress);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
