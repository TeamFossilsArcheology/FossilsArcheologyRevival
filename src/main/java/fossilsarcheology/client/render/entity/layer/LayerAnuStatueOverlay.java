package fossilsarcheology.client.render.entity.layer;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.utility.EntityAnuStatue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerAnuStatueOverlay implements LayerRenderer<EntityAnuStatue> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/anu_statue_explosion.png");
    private final RenderLiving render;

    public LayerAnuStatueOverlay(RenderLiving renderIn) {
        this.render = renderIn;
    }

    public void doRenderLayer(EntityAnuStatue anu, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.render.bindTexture(TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(!anu.isInvisible());
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, anu.ticksExisted / 200F);
        this.render.getMainModel().render(anu, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.render.setLightmap(anu);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}