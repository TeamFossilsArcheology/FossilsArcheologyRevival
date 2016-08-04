package fossilsarcheology.client.render.entity;

import fossilsarcheology.client.model.ModelToyBall;
import fossilsarcheology.server.entity.toy.EntityToyBall;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderToyBall extends RenderLiving {

    public RenderToyBall() {
        super(new ModelToyBall(), 0.3F);
    }

    protected void preRenderCallback(EntityLivingBase living, float f) {
        int i = BlockColored.func_150032_b(((EntityToyBall) living).getColor());
        GlStateManager.color(EntitySheep.fleeceColorTable[i][0], EntitySheep.fleeceColorTable[i][1], EntitySheep.fleeceColorTable[i][2]);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation("fossil:textures/model/toy/ball.png");
    }
}
