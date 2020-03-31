package fossilsarcheology.client.render.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelTarSlime;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderTarSlime extends RenderSlime {

    public RenderTarSlime(RenderManager rendermanager) {
        super(rendermanager);
        this.mainModel = new ModelTarSlime(16);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySlime entity) {
        return new ResourceLocation(Revival.MODID, "textures/model/tar_slime.png");
    }
}
