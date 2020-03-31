package fossilsarcheology.client.gui;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.entity.TileEntitySifter;
import fossilsarcheology.server.container.SifterContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SifterGUI extends GuiContainer {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID + ":" + "textures/gui/sifter.png");
    private final TileEntitySifter sifterInventory;

    public SifterGUI(InventoryPlayer playerInventory, TileEntitySifter sifterInventory) {
        super(new SifterContainer(playerInventory, sifterInventory));
        this.sifterInventory = sifterInventory;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String inventoryName = I18n.format(sifterInventory.getName());
        this.fontRenderer.drawString(inventoryName, this.xSize / 8 - this.fontRenderer.getStringWidth(inventoryName) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int centerX = (this.width - this.xSize) / 2;
        int centerY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(centerX, centerY, 0, 0, this.xSize, this.ySize);
        int siftTimeRemaining = this.sifterInventory.getSiftProgressScaled(26);
        this.drawTexturedModalRect(centerX + 75, centerY + 33, 181, 2, 31, siftTimeRemaining + 1);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
