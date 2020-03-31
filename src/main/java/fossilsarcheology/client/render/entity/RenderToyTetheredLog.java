package fossilsarcheology.client.render.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelToyTetheredLog;
import fossilsarcheology.server.entity.utility.EntityToyTetheredLog;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderToyTetheredLog extends RenderLiving<EntityToyTetheredLog> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/toy/log_swing.png");

    public RenderToyTetheredLog(RenderManager manager) {
        super(manager, new ModelToyTetheredLog(), 0.4F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityToyTetheredLog entity) {
        return TEXTURE;
    }
}
