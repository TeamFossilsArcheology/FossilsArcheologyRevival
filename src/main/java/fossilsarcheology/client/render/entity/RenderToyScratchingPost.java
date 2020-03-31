package fossilsarcheology.client.render.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelToyScratchingPost;
import fossilsarcheology.server.entity.utility.EntityToyScratchingPost;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderToyScratchingPost extends RenderLiving<EntityToyScratchingPost> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/toy/scratching_post.png");

    public RenderToyScratchingPost(RenderManager manager) {
        super(manager, new ModelToyScratchingPost(), 0.4F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityToyScratchingPost entity) {
        return TEXTURE;
    }
}
