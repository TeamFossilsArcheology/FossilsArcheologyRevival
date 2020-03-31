package fossilsarcheology.client.render.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelPigBoss;
import fossilsarcheology.client.render.entity.layer.LayerAnuGlow;
import fossilsarcheology.server.entity.monster.EntityAnu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

public class RenderAnu extends RenderLiving<EntityAnu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/anu.png");

    public RenderAnu(RenderManager manager) {
        super(manager, new ModelPigBoss(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerAnuGlow(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnu entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(EntityAnu entity, float par2) {
        if (entity.getAttackMode() == 1) {
            GlStateManager.rotate(35, 1, 0, 0);
        }
    }
}
