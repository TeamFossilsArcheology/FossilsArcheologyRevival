package fossilsarcheology.client.render.entity;

import com.google.common.collect.Maps;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class RenderPrehistoric extends RenderLiving<EntityPrehistoric> {

    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();

    public RenderPrehistoric(ModelBase model) {
        super(Minecraft.getMinecraft().getRenderManager(), model, 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPrehistoric entity) {
        String s = entity.getTexture();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }
        return resourcelocation;
    }

    @Override
    protected void preRenderCallback(EntityPrehistoric entity, float f) {
        float scale = entity.getGender() == 1 ? entity.getMaleSize() * entity.getAgeScale() : 1 * entity.getAgeScale();
        GlStateManager.scale(scale, scale, scale);
        this.shadowSize = entity.width * 0.45F;
    }

    @Override
    protected float getDeathMaxRotation(EntityPrehistoric entity) {
        return entity.getDeathRotation();
    }

}
