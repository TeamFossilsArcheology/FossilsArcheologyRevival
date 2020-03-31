package fossilsarcheology.client.render.tile;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelPigBoss;
import fossilsarcheology.client.model.ModelSarcophagus;
import fossilsarcheology.server.block.SarcophagusBlock;
import fossilsarcheology.server.block.entity.TileEntitySarcophagus;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntitySarcophagusRender extends TileEntitySpecialRenderer<TileEntitySarcophagus> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/sarcophagus.png");
    private static final ResourceLocation TEXTURE_ANU = new ResourceLocation(Revival.MODID, "textures/model/anu.png");
    private static final ModelSarcophagus MODEL = new ModelSarcophagus();
    private static final ModelPigBoss MODEL_ANU = new ModelPigBoss();

    @Override
    public void render(TileEntitySarcophagus chest, double x, double y, double z, float f, int destroy, float alpha) {
        EnumFacing facing = EnumFacing.SOUTH;
        if (chest != null && chest.hasWorld() && chest.getWorld().getBlockState(chest.getPos()).getBlock() instanceof SarcophagusBlock) {
            facing = chest.getWorld().getBlockState(chest.getPos()).getValue(SarcophagusBlock.FACING);
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + 1.0F, z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, -0.5F, 0.5F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
        float f1 = 0;
        if (chest != null && chest.hasWorld()) {
            f1 = chest.chestLidCounter;
        }
        MODEL.hinge.rotateAngleY = -(float) Math.toRadians(f1);
        MODEL.renderBlock(0.0625F);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        if (chest != null && chest.hasWorld() && chest.chestState == 1) {
            this.renderItem(x, y, z, facing);
        }
        if (chest != null && chest.hasWorld() && chest.chestState != 3) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_ANU);
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
            GlStateManager.scale(0.9F, -0.9F, -0.9F);
            GlStateManager.translate(0.5F, -0.5F, 0.5F);
            GlStateManager.rotate(facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0F, -0.0625F, 0.2F);

            MODEL_ANU.renderBlock(0.0625F);
            GlStateManager.popMatrix();
        }
    }

    public void renderItem(double x, double y, double z, EnumFacing facing) {
        if (facing == EnumFacing.NORTH) {
            GlStateManager.pushMatrix();
            float scale = 1F;
            GlStateManager.translate((float) x + 0.5F, (float) y + 1.2F, (float) z);
            GlStateManager.scale(scale, scale, scale * 2F);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FAItemRegistry.SCARAB_GEM), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
        if (facing == EnumFacing.WEST) {
            GlStateManager.pushMatrix();
            float scale = 1F;
            GlStateManager.translate((float) x, (float) y + 1.2F, (float) z + 0.5F);
            GlStateManager.rotate((float) -90, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180, 0F, 1F, 0F);
            GlStateManager.scale(scale, scale, scale * 2F);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FAItemRegistry.SCARAB_GEM), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
        if (facing == EnumFacing.SOUTH) {
            GlStateManager.pushMatrix();
            float scale = 1F;
            GlStateManager.translate((float) x + 0.5F, (float) y + 1.2F, (float) z + 1);
            GlStateManager.scale(scale, scale, scale * 2F);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FAItemRegistry.SCARAB_GEM), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
        if (facing == EnumFacing.EAST) {
            GlStateManager.pushMatrix();
            float scale = 1F;
            GlStateManager.translate((float) x + 1F, (float) y + 1.2F, (float) z + 0.5F);
            GlStateManager.rotate((float) 90, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-180, 0F, 1F, 0F);
            GlStateManager.scale(scale, scale, scale * 2F);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FAItemRegistry.SCARAB_GEM), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
    }
}
