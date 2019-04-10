package fossilsarcheology.client.render.entity;

import fossilsarcheology.client.model.ModelQuagga;
import fossilsarcheology.server.entity.prehistoric.EntityQuagga;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderQuagga extends RenderLiving<EntityQuagga> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("fossil:textures/model/quagga_0/quagga.png");

    public RenderQuagga(RenderManager renderManager) {
        super(renderManager, new ModelQuagga(), 0.75F);
    }

    protected ResourceLocation getEntityTexture(EntityQuagga entity) {
        return TEXTURE;
    }

}
