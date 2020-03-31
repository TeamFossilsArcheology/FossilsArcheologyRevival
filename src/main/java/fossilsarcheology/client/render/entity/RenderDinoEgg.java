package fossilsarcheology.client.render.entity;

import fossilsarcheology.client.model.ModelDinoEgg;
import fossilsarcheology.server.entity.EntityDinosaurEgg;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDinoEgg extends RenderLiving<EntityDinosaurEgg> {

    public RenderDinoEgg(RenderManager manager) {
        super(manager, new ModelDinoEgg(), 0.25F);
    }

    @Override
    protected void preRenderCallback(EntityDinosaurEgg entity, float f) {
        GlStateManager.scale(entity.selfType.eggScale, entity.selfType.eggScale, entity.selfType.eggScale);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDinosaurEgg entity) {
        return new ResourceLocation(entity.getTexture());
    }
}
