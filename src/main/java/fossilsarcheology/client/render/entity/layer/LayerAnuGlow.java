package fossilsarcheology.client.render.entity.layer;

import fossilsarcheology.Revival;
import fossilsarcheology.client.render.entity.RenderAnu;
import fossilsarcheology.server.entity.monster.EntityAnu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerAnuGlow implements LayerRenderer<EntityAnu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/anu_overlay.png");
    private final RenderAnu anurender;

    public LayerAnuGlow(RenderAnu anu) {
        this.anurender = anu;
    }

    @Override
    public void doRenderLayer(EntityAnu entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        this.anurender.bindTexture(TEXTURE);
        this.anurender.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
