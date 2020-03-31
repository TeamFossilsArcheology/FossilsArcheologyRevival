package fossilsarcheology.client.render.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelToyBall;
import fossilsarcheology.server.entity.utility.EntityToyBall;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderToyBall extends RenderLiving<EntityToyBall> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/toy/ball.png");

    private static final float[][] COLORS = new float[][]{
            {1.0F, 1.0F, 1.0F},
            {0.85F, 0.5F, 0.2F},
            {0.7F, 0.3F, 0.85F},
            {0.4F, 0.6F, 0.85F},
            {0.9F, 0.9F, 0.2F},
            {0.5F, 0.8F, 0.1F},
            {0.95F, 0.5F, 0.65F},
            {0.3F, 0.3F, 0.3F},
            {0.6F, 0.6F, 0.6F},
            {0.3F, 0.5F, 0.6F},
            {0.5F, 0.25F, 0.7F},
            {0.2F, 0.3F, 0.7F},
            {0.4F, 0.3F, 0.2F},
            {0.4F, 0.5F, 0.2F},
            {0.6F, 0.2F, 0.2F},
            {0.1F, 0.1F, 0.1F}
    };

    public RenderToyBall(RenderManager manager) {
        super(manager, new ModelToyBall(), 0.3F);
    }

    @Override
    protected void preRenderCallback(EntityToyBall ball, float f) {
        int color = ~ball.getColor() & 15;
        GlStateManager.color(COLORS[color][0], COLORS[color][1], COLORS[color][2]);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityToyBall entity) {
        return TEXTURE;
    }
}
