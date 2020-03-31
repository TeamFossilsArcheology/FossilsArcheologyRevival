package fossilsarcheology.client.render.entity;

import fossilsarcheology.server.entity.monster.EntityFriendlyPigZombie;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

public class RenderFriendlyPigZombie extends RenderLiving<EntityFriendlyPigZombie> {

    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");

    public RenderFriendlyPigZombie(ModelBase model, RenderManager renderer) {
        super(renderer, model, 0.5F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this) {
            @Override
            protected void initArmor() {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        });
    }

    @Override
    protected void preRenderCallback(EntityFriendlyPigZombie fpz, float par2) {
        if (fpz.isSitting()) {
            GlStateManager.translate(0F, 0.4F, 0F);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFriendlyPigZombie entity) {
        return ZOMBIE_PIGMAN_TEXTURE;
    }


}
