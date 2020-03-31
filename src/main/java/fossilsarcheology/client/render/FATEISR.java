package fossilsarcheology.client.render;

import fossilsarcheology.client.render.tile.TileEntityAncientChestRender;
import fossilsarcheology.client.render.tile.TileEntityAnuStatueRender;
import fossilsarcheology.client.render.tile.TileEntityAnubiteStatueRender;
import fossilsarcheology.client.render.tile.TileEntitySarcophagusRender;
import fossilsarcheology.server.block.FABlockRegistry;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class FATEISR extends TileEntityItemStackRenderer {

    public static final TileEntityAnubiteStatueRender RENDER_ANUBITE_STATUE = new TileEntityAnubiteStatueRender();
    public static final TileEntityAncientChestRender RENDER_ANCIENT_CHEST = new TileEntityAncientChestRender();
    public static final TileEntityAnuStatueRender RENDER_ANU_STATUE = new TileEntityAnuStatueRender();
    public static final TileEntitySarcophagusRender RENDER_SARCAPHOGUS = new TileEntitySarcophagusRender();

    public void renderByItem(ItemStack itemStackIn) {
        if (itemStackIn.getItem() == Item.getItemFromBlock(FABlockRegistry.ANUBITE_STATUE)) {
            GL11.glScalef(1.2F, 1.2F, 1.2F);
            RENDER_ANUBITE_STATUE.render(null, 0F, -0.2F, 0, 0, 0, 0);
        } else if (itemStackIn.getItem() == Item.getItemFromBlock(FABlockRegistry.ANCIENT_CHEST)) {
            RENDER_ANCIENT_CHEST.render(null, 0F, 0F, 0, 0, 0, 0);
        } else if (itemStackIn.getItem() == Item.getItemFromBlock(FABlockRegistry.ANU_STATUE)) {
            //GL11.glRotatef(180, 0F, 1.0F, 0F);
            RENDER_ANU_STATUE.render(null, 0F, -0.2F, 0, 0, 0, 0);
        } else if (itemStackIn.getItem() == Item.getItemFromBlock(FABlockRegistry.SARCOPHAGUS)) {
            RENDER_SARCAPHOGUS.render(null, 0, 0, 0, 0, 0, 0);
        } else {
            super.renderByItem(itemStackIn);
        }
    }
}
