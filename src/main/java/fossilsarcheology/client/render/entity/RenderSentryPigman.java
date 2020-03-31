package fossilsarcheology.client.render.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.monster.EntitySentryPigman;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSentryPigman extends RenderBiped<EntitySentryPigman> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/sentry_pigman.png");

    public RenderSentryPigman(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelZombie(), 0.5F);
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
    protected ResourceLocation getEntityTexture(EntitySentryPigman mob) {
        return TEXTURE;
    }
}
