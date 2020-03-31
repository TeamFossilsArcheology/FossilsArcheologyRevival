package fossilsarcheology.client.gui.dinopedia;

import fossilsarcheology.Revival;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonDinopediaPage extends GuiButton {
    private static final ResourceLocation BOOK_TEXTURE = new ResourceLocation(Revival.MODID, "textures/gui/dinopedia.png");
    private final boolean nextPage;
    private final int page;

    public ButtonDinopediaPage(int id, int xPos, int yPos, boolean nextPage, int bookpage) {
        super(id, xPos, yPos, 34, 24, "");
        this.nextPage = nextPage;
        page = bookpage;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.enabled) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.renderEngine.bindTexture(BOOK_TEXTURE);
            int width = 0;
            int height = 223;

            // if ((hovering) || (this.nextPage && page == lastpage) ||
            // (!this.nextPage && page == 0))
            int lastpage = 1;
            if ((this.nextPage && page == lastpage) || (!this.nextPage && page == 0)) {
                height -= 23;
            }

            if (!this.nextPage) {
                width += 34;
            }

            this.drawTexturedModalRect(this.x, this.y, width, height, 34, 30);
        }
    }
}
