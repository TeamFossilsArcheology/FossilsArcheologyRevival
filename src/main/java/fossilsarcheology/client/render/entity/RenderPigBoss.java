package fossilsarcheology.client.render.entity;

import com.mojang.authlib.GameProfile;
import fossilsarcheology.client.model.ModelPigBoss;
import fossilsarcheology.server.entity.mob.EntityAnu;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class RenderPigBoss extends RenderLiving {

    public RenderPigBoss(ModelPigBoss var1, float var2) {
        super(var1, var2);
        this.setRenderPassModel(var1);

    }

    protected ResourceLocation func_110919_a(EntityAnu par1Entity) {
        return new ResourceLocation("fossil:textures/model/PigBoss.png");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.func_110919_a((EntityAnu) par1Entity);
    }

    protected void preRenderScale(EntityAnu mob, float par2) {
        if (mob.getAttackMode() == 1) {
            GlStateManager.rotate(35, 1, 0, 0);
        }
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int i, float q) {
        if (((EntityAnu) entity).getAttackMode() == 2) {

            if (i != 0) {
                return -1;
            } else {
                this.bindTexture(new ResourceLocation("fossil:textures/model/PigBoss_overlay.png"));
                GlStateManager.enableBlend();
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

                if (entity.isInvisible()) {
                    GL11.glDepthMask(false);
                } else {
                    GL11.glDepthMask(true);
                }

                char c0 = 61680;
                int j = c0 % 65536;
                int k = c0 / 65536;
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                return 1;
            }
        }
        return -1;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before
     * the model is rendered. Args: entityLiving, partialTickTime
     */
    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        this.preRenderScale((EntityAnu) par1EntityLivingBase, par2);
    }

    protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        ItemStack itemstack = p_77029_1_.getHeldItem();
        ItemStack itemstack1 = p_77029_1_.func_130225_q(3);
        Item item;
        float f1;

        if (itemstack1 != null) {
            GlStateManager.pushMatrix();
            ((ModelPigBoss) this.mainModel).Head.postRender(0.0625F);
            item = itemstack1.getItem();

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack1, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack1, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if (item instanceof ItemBlock) {
                if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType())) {
                    f1 = 0.625F;
                    GlStateManager.translate(0.0F, -0.25F, 0.0F);
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.scale(f1, -f1, -f1);
                }

                this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack1, 0);
            } else if (item == Items.skull) {
                f1 = 1.0625F;
                GlStateManager.scale(f1, -f1, -f1);
                GameProfile gameprofile = null;

                if (itemstack1.hasTagCompound()) {
                    NBTTagCompound nbttagcompound = itemstack1.getTagCompound();

                    if (nbttagcompound.hasKey("SkullOwner", 10)) {
                        gameprofile = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("SkullOwner"));
                    } else if (nbttagcompound.hasKey("SkullOwner", 8) && !StringUtils.isNullOrEmpty(nbttagcompound.getString("SkullOwner"))) {
                        gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
                    }
                }

                TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack1.getItemDamage(), gameprofile);
            }

            GlStateManager.popMatrix();
        }

        if (itemstack != null && itemstack.getItem() != null) {
            item = itemstack.getItem();
            GlStateManager.pushMatrix();

            if (this.mainModel.isChild) {
                f1 = 0.5F;
                GlStateManager.translate(0.0F, 0.625F, 0.0F);
                GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                GlStateManager.scale(f1, f1, f1);
            }

            ((ModelPigBoss) this.mainModel).rightArm.postRender(0.0625F);
            GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if (item instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))) {
                f1 = 0.5F;
                GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
                f1 *= 0.75F;
                GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(-f1, -f1, f1);
            } else if (item == Items.bow) {
                f1 = 0.625F;
                GlStateManager.translate(0.0F, 0.125F, 0.3125F);
                GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(f1, -f1, f1);
                GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (item.isFull3D()) {
                f1 = 0.625F;

                if (item.shouldRotateAroundWhenRendering()) {
                    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.translate(0.0F, -0.125F, 0.0F);
                }

                this.func_82422_c();
                GlStateManager.scale(f1, -f1, f1);
                GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
                f1 = 0.375F;
                GlStateManager.translate(0.25F, 0.1875F, -0.1875F);
                GlStateManager.scale(f1, f1, f1);
                GlStateManager.rotate(60.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float f2;
            int i;
            float f5;

            if (itemstack.getItem().requiresMultipleRenderPasses()) {
                for (i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++i) {
                    int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
                    f5 = (float) (j >> 16 & 255) / 255.0F;
                    f2 = (float) (j >> 8 & 255) / 255.0F;
                    float f3 = (float) (j & 255) / 255.0F;
                    GlStateManager.color(f5, f2, f3, 1.0F);
                    this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, i);
                }
            } else {
                i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                float f4 = (float) (i >> 16 & 255) / 255.0F;
                f5 = (float) (i >> 8 & 255) / 255.0F;
                f2 = (float) (i & 255) / 255.0F;
                GlStateManager.color(f4, f5, f2, 1.0F);
                this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, 0);
            }

            GlStateManager.popMatrix();
        }
    }

    protected void func_82422_c() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_) {
        this.renderEquippedItems((EntityLiving) p_77029_1_, p_77029_2_);
    }

    @Override
    public void doRender(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
        BossStatus.setBossStatus((EntityAnu) var1, true);
        super.doRender(var1, var2, var4, var6, var8, var9);
    }
}
