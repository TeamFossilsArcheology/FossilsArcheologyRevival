package fossilsarcheology.client.render.tile;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelAnubite;
import fossilsarcheology.server.block.entity.TileEntityAnubiteStatue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityAnubiteStatueRender extends TileEntitySpecialRenderer<TileEntityAnubiteStatue> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/anubite_statue.png");
    private static final ModelAnubite MODEL = new ModelAnubite();

    @Override
    public void render(TileEntityAnubiteStatue entity, double x, double y, double z, float f, int destroy, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
        GlStateManager.rotate(180, 0F, 0F, 1F);
        GlStateManager.rotate(getRotation(entity), 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
        MODEL.renderBlock(0.0625F);
        GlStateManager.popMatrix();
    }

    private float getRotation(TileEntityAnubiteStatue statue) {
        if (statue != null && statue.hasWorld()) {
            switch (statue.getBlockMetadata()) {
                default:
                    return 180;
                case 1:
                    return -90;
                case 2:
                    return 0;
                case 3:
                    return 90;
            }
        }
        return 0;
    }
}
