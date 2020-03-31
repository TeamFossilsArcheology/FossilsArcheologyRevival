package fossilsarcheology.client.render.tile;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.AncientChestBlock;
import fossilsarcheology.server.block.entity.TileEntityAncientChest;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntityAncientChestRender extends TileEntitySpecialRenderer<TileEntityAncientChest> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/ancient_chest.png");
    private static final ModelChest CHEST_MODEL = new ModelChest();

    @Override
    public void render(TileEntityAncientChest entity, double x, double y, double z, float f, int destroy, float alpha) {
        EnumFacing facing = EnumFacing.SOUTH;
        if (entity != null && entity.hasWorld()) {
            facing = entity.getWorld().getBlockState(entity.getPos()).getValue(AncientChestBlock.FACING);
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + 1.0F, z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        float f1 = 0;
        if (entity != null && entity.hasWorld()) {
            if (entity.chestLidCounter > 90) {
                f1 = entity.chestLidCounter2 + entity.chestLidCounter;
            } else {
                f1 = entity.chestLidCounter;
            }
        }
        CHEST_MODEL.chestLid.rotateAngleX = -(float) Math.toRadians(f1);
        CHEST_MODEL.renderAll();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        if (entity != null && entity.hasWorld() && entity.chestState == 1) {
            this.renderItem(x, y, z, facing);
        }
    }

    public void renderItem(double x, double y, double z, EnumFacing facing) {
        if (facing == EnumFacing.NORTH) {
            GlStateManager.pushMatrix();
            float scale = 1F;
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.6F, (float) z - 0.1F);
            GlStateManager.rotate((float) 180, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(270, 0F, 1F, 0F);
            GlStateManager.rotate(-45, 0F, 0F, -1F);
            GlStateManager.scale(scale, scale, scale);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FAItemRegistry.ANCIENT_KEY), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
        if (facing == EnumFacing.EAST) {
            GlStateManager.pushMatrix();
            float scale = 1F;
            GlStateManager.translate((float) x + 1.1F, (float) y + 0.6F, (float) z + 0.5F);
            GlStateManager.rotate((float) 90, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-90, 0F, 1F, 0F);
            GlStateManager.rotate(45, 0F, 0F, 1F);
            GlStateManager.scale(scale, scale, scale);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FAItemRegistry.ANCIENT_KEY), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
        if (facing == EnumFacing.SOUTH) {
            GlStateManager.pushMatrix();
            float scale = 1F;
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.6F, (float) z + 1.1F);
            GlStateManager.rotate((float) 180, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(90, 0F, 1F, 0F);
            GlStateManager.rotate(-45, 0F, 0F, -1F);
            GlStateManager.scale(scale, scale, scale);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FAItemRegistry.ANCIENT_KEY), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
        if (facing == EnumFacing.WEST) {
            GlStateManager.pushMatrix();
            float scale = 1F;
            GlStateManager.translate((float) x - 0.1F, (float) y + 0.6F, (float) z + 0.5F);
            GlStateManager.rotate((float) 90, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(90, 0F, 1F, 0F);
            GlStateManager.rotate(45, 0F, 0F, 1F);
            GlStateManager.scale(scale, scale, scale);
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(FAItemRegistry.ANCIENT_KEY), ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
    }
}
