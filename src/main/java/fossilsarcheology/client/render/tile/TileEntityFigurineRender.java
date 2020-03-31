package fossilsarcheology.client.render.tile;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelFigurine;
import fossilsarcheology.server.block.entity.TileEntityFigurine;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class TileEntityFigurineRender extends TileEntitySpecialRenderer<TileEntityFigurine> {

    public static final ModelFigurine MODEL = new ModelFigurine();
    private static final ResourceLocation pristine_steve = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_steve_pristine.png");
    private static final ResourceLocation pristine_skeleton = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_skeleton_pristine.png");
    private static final ResourceLocation pristine_zombie = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_zombie_pristine.png");
    private static final ResourceLocation pristine_enderman = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_enderman_pristine.png");
    private static final ResourceLocation pristine_zombiepig = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_pigzombie_pristine.png");
    private static final ResourceLocation damaged_steve = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_steve_damaged.png");
    private static final ResourceLocation damaged_skeleton = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_skeleton_damaged.png");
    private static final ResourceLocation damaged_zombie = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_zombie_damaged.png");
    private static final ResourceLocation damaged_enderman = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_enderman_damaged.png");
    private static final ResourceLocation damaged_zombiepig = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_pigzombie_damaged.png");
    private static final ResourceLocation broken_steve = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_steve_broken.png");
    private static final ResourceLocation broken_skeleton = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_skeleton_broken.png");
    private static final ResourceLocation broken_zombie = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_zombie_broken.png");
    private static final ResourceLocation broken_enderman = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_enderman_broken.png");
    private static final ResourceLocation broken_zombiepig = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_pigzombie_broken.png");
    private static final ResourceLocation mysterious = new ResourceLocation(Revival.MODID, "textures/blocks/figurines/figurine_mysterious.png");

    public TileEntityFigurineRender() {
    }

    @Override
    public void render(TileEntityFigurine te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180F, 1.0F, 0.0F, 1.0F);
        this.rotateBlock(te.getWorld(), te, te.getFigurineType());
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    private void rotateBlock(World world, TileEntityFigurine te, int figurineType) {
        if (world != null) {
            int dir = te.getFigurineRotation();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(dir * 90, 0F, 1F, 0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);

            switch (figurineType) {
                case 0:
                default:
                    this.bindTexture(pristine_steve);
                    MODEL.render(0.0625F, false);
                    break;

                case 1:
                    this.bindTexture(pristine_skeleton);
                    MODEL.render(0.0625F, false);
                    break;

                case 2:
                    this.bindTexture(pristine_zombie);
                    MODEL.render(0.0625F, false);
                    break;

                case 3:
                    this.bindTexture(pristine_enderman);
                    MODEL.render(0.0625F, false);
                    break;

                case 4:
                    this.bindTexture(pristine_zombiepig);
                    MODEL.render(0.0625F, false);
                    break;

                case 5:
                    this.bindTexture(damaged_steve);
                    MODEL.render(0.0625F, false);
                    break;

                case 6:
                    this.bindTexture(damaged_skeleton);
                    MODEL.render(0.0625F, false);
                    break;

                case 7:
                    this.bindTexture(damaged_zombie);
                    MODEL.render(0.0625F, false);
                    break;

                case 8:
                    this.bindTexture(damaged_enderman);
                    MODEL.render(0.0625F, false);
                    break;

                case 9:
                    this.bindTexture(damaged_zombiepig);
                    MODEL.render(0.0625F, false);
                    break;

                case 10:
                    this.bindTexture(broken_steve);
                    MODEL.render(0.0625F, true);
                    break;

                case 11:
                    this.bindTexture(broken_skeleton);
                    MODEL.render(0.0625F, true);
                    break;

                case 12:
                    this.bindTexture(broken_zombie);
                    MODEL.render(0.0625F, true);
                    break;

                case 13:
                    this.bindTexture(broken_enderman);
                    MODEL.render(0.0625F, true);
                    break;

                case 14:
                    this.bindTexture(broken_zombiepig);
                    MODEL.render(0.0625F, true);
                    break;

                case 15:
                    this.bindTexture(mysterious);
                    MODEL.render(0.0625F, false);
                    break;
            }

            // renders the model
            // this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F,
            // 0.0625F);
            GlStateManager.popMatrix();
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(0F, 0F, 1F, 0F);
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(pristine_steve);
            MODEL.render(0.0625F, false);
            GlStateManager.popMatrix();
        }
    }
}
