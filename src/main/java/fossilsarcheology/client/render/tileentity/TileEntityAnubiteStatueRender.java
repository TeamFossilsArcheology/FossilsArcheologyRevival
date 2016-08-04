package fossilsarcheology.client.render.tileentity;

import fossilsarcheology.client.model.ModelAnubite;
import fossilsarcheology.server.block.entity.TileEntityAnubiteStatue;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityAnubiteStatueRender extends TileEntitySpecialRenderer {

    public static final ResourceLocation texture = new ResourceLocation("fossil:textures/model/Anubite_ancient_statue.png");

    private ModelAnubite modelBlock;

    public TileEntityAnubiteStatueRender() {
        this.modelBlock = new ModelAnubite();

    }

    public void renderAnuAt(TileEntityAnubiteStatue tileentity, double x, double y, double z, float f) {
        int i1 = 0;
        if (tileentity.hasWorldObj()) {
            i1 = tileentity.getBlockMetadata();
        }
        short short1 = 0;
        if (i1 == 2) {

            short1 = 360;
        }

        if (i1 == 3) {
            short1 = 180;
        }

        if (i1 == 4) {
            short1 = 90;
        }

        if (i1 == 5) {
            short1 = -90;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0f, 0f, 0f);
        GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GlStateManager.rotate(180, 0F, 0F, 1F);
        GlStateManager.rotate((float) short1 * -1F, 0.0F, 1.0F, 0.0F);
        GlStateManager.pushMatrix();
        this.bindTexture(texture);
        this.modelBlock.renderBlock(0.0625F);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        this.renderAnuAt((TileEntityAnubiteStatue) tileentity, pos, f);
    }
}

//
