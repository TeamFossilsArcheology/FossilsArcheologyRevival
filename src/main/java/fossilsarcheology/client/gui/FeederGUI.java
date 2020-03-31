package fossilsarcheology.client.gui;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.entity.TileEntityFeeder;
import fossilsarcheology.server.container.FeederContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class FeederGUI extends GuiContainer {
    private static final ResourceLocation loc = new ResourceLocation(Revival.MODID, "textures/gui/feeder.png");
    private final TileEntityFeeder feederEntity;

    public FeederGUI(InventoryPlayer inventoryPlayer, TileEntityFeeder entity) {
        super(new FeederContainer(inventoryPlayer, entity));
        this.feederEntity = entity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String customName = this.feederEntity.hasCustomName() ? this.feederEntity.getName() : I18n.format(this.feederEntity.getName());
        this.fontRenderer.drawString(customName, this.xSize / 6 - this.fontRenderer.getStringWidth(customName) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int left = this.guiLeft;
        int top = this.guiTop;
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) left, (float) top, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        short lightX = 240;
        short lightY = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) lightX / 1.0F, (float) lightY / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        InventoryPlayer playerInventory = this.mc.player.inventory;

        if (!playerInventory.getItemStack().isEmpty()) {
            GlStateManager.translate(0.0F, 0.0F, 32.0F);
            this.zLevel = 200.0F;
            itemRender.zLevel = 200.0F;
            itemRender.renderItemAndEffectIntoGUI(this.mc.player, playerInventory.getItemStack(), mouseX - left - 8, mouseY - top - 8);
            itemRender.renderItemOverlayIntoGUI(this.fontRenderer, playerInventory.getItemStack(), mouseX - left - 8, mouseY - top - 8, "");
            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
        }

        this.fontRenderer.drawString("" + this.feederEntity.getField(0), 23, 32, 16711680);
        this.fontRenderer.drawString("" + this.feederEntity.getField(1), 120, 32, 0X35AC47);
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(loc);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7 = this.feederEntity.getMeatBarScaled(46);
        this.drawTexturedModalRect(var5 + 66, var6 + 55 - var7, 176, 46 - var7, 3, var7);
        int var8 = this.feederEntity.getVegBarScaled(46);
        this.drawTexturedModalRect(var5 + 110, var6 + 55 - var8, 176, 46 - var8, 3, var8);
    }
}
