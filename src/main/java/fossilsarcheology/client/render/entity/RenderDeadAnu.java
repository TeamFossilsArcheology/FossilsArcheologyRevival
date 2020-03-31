package fossilsarcheology.client.render.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.utility.EntityAnuDead;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDeadAnu extends RenderLiving<EntityAnuDead> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/anu.png");

    public RenderDeadAnu(RenderManager render, ModelBase model, float shadowSize) {
        super(render, model, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnuDead entity) {
        return TEXTURE;
    }
}
