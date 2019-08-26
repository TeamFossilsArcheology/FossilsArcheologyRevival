package fossilsarcheology.client.render.tile;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelEmbryoGeneric;
import fossilsarcheology.client.model.ModelEmbryoPlant;
import fossilsarcheology.server.block.entity.TileEntityCultivate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityCultivateRender extends TileEntitySpecialRenderer<TileEntityCultivate> {
    private static final ResourceLocation TEXTURE_EMBRYO_BASIC = new ResourceLocation(Revival.MODID, "textures/blocks/cultivate/embryo_generic.png");
    private static final ResourceLocation TEXTURE_EMBRYO_LIMBLESS = new ResourceLocation(Revival.MODID, "textures/blocks/cultivate/embryo_legless.png");
    private static final ResourceLocation TEXTURE_EMBRYO_PLANT = new ResourceLocation(Revival.MODID, "textures/blocks/cultivate/embryo_plant.png");
    private static final ResourceLocation TEXTURE_EMBRYO_SPORE = new ResourceLocation(Revival.MODID, "textures/blocks/cultivate/embryo_spore.png");

    private final ModelEmbryoGeneric model;
    private final ModelEmbryoPlant modelPlant;

    public TileEntityCultivateRender() {
        this.model = new ModelEmbryoGeneric();
        this.modelPlant = new ModelEmbryoPlant();
    }

    public void renderCultureVatAt(TileEntityCultivate entity, double x, double y, double z, float partialTicks) {
        float rot = Minecraft.getMinecraft().player.ticksExisted;
        float bob = (float) (Math.sin(Minecraft.getMinecraft().player.ticksExisted * 0.1F) * 1 * 0.05F - 1 * 0.05F);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
        GlStateManager.rotate(180, 0F, 0F, 1F);
        GlStateManager.pushMatrix();
        if (entity.isActive) {
            if (entity.getDNAType() == 1) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0, 0.5F + bob, 0);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                GlStateManager.rotate(rot, 0, 9, 0);
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_EMBRYO_LIMBLESS);
                this.model.render(0.0625F);
                GlStateManager.popMatrix();
            } else if (entity.getDNAType() == 2) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0, 0.5F + bob, 0);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                GlStateManager.rotate(rot, 0, 9, 0);
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_EMBRYO_PLANT);
                this.modelPlant.render(0.0625F);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0, 0.5F + bob, 0);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                GlStateManager.rotate(rot, 0, 9, 0);
                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_EMBRYO_BASIC);
                this.model.render(0.0625F);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    @Override
    public void render(TileEntityCultivate entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        this.renderCultureVatAt(entity, x, y, z, partialTicks);
    }
}

