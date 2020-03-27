package fossilsarcheology.client.render.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelDebugTest;
import fossilsarcheology.server.entity.EntityFishBase;
import fossilsarcheology.server.entity.utility.EntityToyTetheredLog;
import fossilsarcheology.server.entity.utility.EntityWorseDebugTest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWorseDebugTest extends RenderLiving<EntityWorseDebugTest> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Revival.MODID, "textures/model/debug/debugtest_2.png");

    public RenderWorseDebugTest(RenderManager manager) {
        super(manager, new ModelDebugTest(), 0.7F);
    }

    @Override
    protected void preRenderCallback(EntityWorseDebugTest entity, float f) {
        GlStateManager.scale(1.5F, 1.5F, 1.5F);
    }


    @Override
    protected ResourceLocation getEntityTexture(EntityWorseDebugTest entity) {
        return TEXTURE;
    }
}
