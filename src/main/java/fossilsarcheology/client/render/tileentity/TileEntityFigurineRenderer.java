package fossilsarcheology.client.render.tileentity;

import fossilsarcheology.client.model.ModelFigurine;
import fossilsarcheology.client.model.ModelFigurineBroken;
import fossilsarcheology.server.block.entity.TileEntityFigurine;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

public class TileEntityFigurineRenderer extends TileEntitySpecialRenderer {
    // the model texture of our block
    private static final ResourceLocation pristine_steve = new ResourceLocation("fossil:textures/blocks/figurines/figurine_steve_pristine.png");
    private static final ResourceLocation pristine_skeleton = new ResourceLocation("fossil:textures/blocks/figurines/figurine_skeleton_pristine.png");
    private static final ResourceLocation pristine_zombie = new ResourceLocation("fossil:textures/blocks/figurines/figurine_zombie_pristine.png");
    private static final ResourceLocation pristine_enderman = new ResourceLocation("fossil:textures/blocks/figurines/figurine_enderman_pristine.png");
    private static final ResourceLocation pristine_zombiepig = new ResourceLocation("fossil:textures/blocks/figurines/figurine_pigzombie_pristine.png");
    private static final ResourceLocation damaged_steve = new ResourceLocation("fossil:textures/blocks/figurines/figurine_steve_damaged.png");
    private static final ResourceLocation damaged_skeleton = new ResourceLocation("fossil:textures/blocks/figurines/figurine_skeleton_damaged.png");
    private static final ResourceLocation damaged_zombie = new ResourceLocation("fossil:textures/blocks/figurines/figurine_zombie_damaged.png");
    private static final ResourceLocation damaged_enderman = new ResourceLocation("fossil:textures/blocks/figurines/figurine_enderman_damaged.png");
    private static final ResourceLocation damaged_zombiepig = new ResourceLocation("fossil:textures/blocks/figurines/figurine_pigzombie_damaged.png");
    private static final ResourceLocation broken_steve = new ResourceLocation("fossil:textures/blocks/figurines/figurine_steve_broken.png");
    private static final ResourceLocation broken_skeleton = new ResourceLocation("fossil:textures/blocks/figurines/figurine_skeleton_broken.png");
    private static final ResourceLocation broken_zombie = new ResourceLocation("fossil:textures/blocks/figurines/figurine_zombie_broken.png");
    private static final ResourceLocation broken_enderman = new ResourceLocation("fossil:textures/blocks/figurines/figurine_enderman_broken.png");
    private static final ResourceLocation broken_zombiepig = new ResourceLocation("fossil:textures/blocks/figurines/figurine_pigzombie_broken.png");
    private static final ResourceLocation mysterious = new ResourceLocation("fossil:textures/blocks/figurines/figurine_mysterious.png");
    // the model of our block
    public final ModelFigurine model;
    public final ModelFigurineBroken modelbroken;

    // also gets model of our block
    public TileEntityFigurineRenderer() {
        this.model = new ModelFigurine();
        this.modelbroken = new ModelFigurineBroken();
    }

    // renders entity in world
    public void renderTileEntityFigurineAt(TileEntityFigurine te, double x, double y, double z, float scale) {
        // push matrix tells the renderer to start doing something
        GlStateManager.pushMatrix();
        // this sets the initial location
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
        // this rotates your block otherwise will render upside down
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180F, 1.0F, 0.0F, 1.0F);
        rotateBlock(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.blockType, te.getFigurineType());
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    // rotates block
    private void rotateBlock(World world, BlockPos pos, Block block, int par6) {
        if (world != null) {
            int dir = world.getBlockMetadata(pos);
            GlStateManager.pushMatrix();
            // this line rotates renderer
            GlStateManager.rotate(dir * (90), 0F, 1F, 0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);

            // gets the texture for model
            switch (par6) {
                case 0:
                default:
                    this.bindTexture(pristine_steve);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 1:
                    this.bindTexture(pristine_skeleton);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 2:
                    this.bindTexture(pristine_zombie);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 3:
                    this.bindTexture(pristine_enderman);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 4:
                    this.bindTexture(pristine_zombiepig);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 5:
                    this.bindTexture(damaged_steve);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 6:
                    this.bindTexture(damaged_skeleton);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 7:
                    this.bindTexture(damaged_zombie);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 8:
                    this.bindTexture(damaged_enderman);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 9:
                    this.bindTexture(damaged_zombiepig);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 10:
                    this.bindTexture(broken_steve);
                    this.modelbroken.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 11:
                    this.bindTexture(broken_skeleton);
                    this.modelbroken.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 12:
                    this.bindTexture(broken_zombie);
                    this.modelbroken.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 13:
                    this.bindTexture(broken_enderman);
                    this.modelbroken.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 14:
                    this.bindTexture(broken_zombiepig);
                    this.modelbroken.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
                    break;

                case 15:
                    this.bindTexture(mysterious);
                    this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
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
            this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
        this.renderTileEntityFigurineAt((TileEntityFigurine) tileentity, d0, d1, d2, f);
    }
}
