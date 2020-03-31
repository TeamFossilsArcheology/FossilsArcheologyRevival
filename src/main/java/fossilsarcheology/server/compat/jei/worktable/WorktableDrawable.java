package fossilsarcheology.server.compat.jei.worktable;

import fossilsarcheology.client.gui.WorktableGUI;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class WorktableDrawable implements IDrawable {

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
        minecraft.getTextureManager().bindTexture(WorktableGUI.TEXTURE);
        this.drawTexturedModalRect(xOffset, yOffset, 3, 4, 170, 79);
        int fuelHeight = 12;
        int scaledProgress = (minecraft.player.ticksExisted % 25) * fuelHeight / 25;
        this.drawTexturedModalRect(79, 32 + 12 - scaledProgress, 176, 12 - scaledProgress, 14, scaledProgress + 2);
        int progressWidth = 24;
        int scaledProgress2 = (minecraft.player.ticksExisted % 100) * progressWidth / 100;
        this.drawTexturedModalRect(76, 14, 176, 14, scaledProgress2 + 1, 16);
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x + 0, y + height, 0).tex((float) (textureX + 0) * 0.00390625F, (float) (textureY + height) * 0.00390625F).endVertex();
        bufferbuilder.pos(x + width, y + height, 0).tex((float) (textureX + width) * 0.00390625F, (float) (textureY + height) * 0.00390625F).endVertex();
        bufferbuilder.pos(x + width, y + 0, 0).tex((float) (textureX + width) * 0.00390625F, (float) (textureY + 0) * 0.00390625F).endVertex();
        bufferbuilder.pos(x + 0, y + 0, 0).tex((float) (textureX + 0) * 0.00390625F, (float) (textureY + 0) * 0.00390625F).endVertex();
        tessellator.draw();
    }
}
