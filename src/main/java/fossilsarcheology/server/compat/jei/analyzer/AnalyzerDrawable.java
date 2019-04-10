package fossilsarcheology.server.compat.jei.analyzer;

import fossilsarcheology.client.gui.AnalyzerGUI;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;

public class AnalyzerDrawable implements IDrawable {

    private int chance;

    public AnalyzerDrawable(int chance) {
        this.chance = chance;
    }

    @Override
    public int getWidth() {
        return 170;
    }

    @Override
    public int getHeight() {
        return 79;
    }

    @Override
    public void draw(Minecraft minecraft, int xOffset, int yOffset) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(AnalyzerGUI.TEXTURE);
        this.drawTexturedModalRect(xOffset, yOffset, 3, 4, 170, 79);
        int progress = minecraft.player.ticksExisted % 22;
        this.drawTexturedModalRect(xOffset + 77, yOffset + 18, 177, 18, progress, 9);
        String chance_less = chance > 1 ? "" + chance : "< 1";
        String chance_string = I18n.format("analyzer.chance") + " " + chance_less + "%";
        minecraft.fontRenderer.drawString(chance_string, 90 - minecraft.fontRenderer.getStringWidth(chance_string) / 2, 70, 4210752);
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + 0), (double)(y + height), (double)0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), (double)0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + 0), (double)0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + 0), (double)(y + 0), (double)0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }
}
