package fossilsarcheology.client.render;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import fossilsarcheology.server.entity.prehistoric.base.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.base.EntityPrehistoricSwimming;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class RenderPrehistoric extends MobRenderer<EntityPrehistoric, EntityModel<EntityPrehistoric>> {

    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();

    public RenderPrehistoric(EntityModel model) {
        super(Minecraft.getInstance().getRenderManager(), model, 0.3F);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityPrehistoric entity) {
        String s = entity.getTexture();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }
        return resourcelocation;
    }

    @Override
    protected void preRenderCallback(EntityPrehistoric entity, MatrixStack matrixStackIn, float partialTickTime) {
        float xRot = 0;
        if(entity instanceof EntityPrehistoricSwimming){
            EntityPrehistoricSwimming aquatic = (EntityPrehistoricSwimming)entity;
         //   xRot = aquatic.prevBreachPitch + (aquatic.getBreachPitch() - aquatic.prevBreachPitch) * f;
        //    GlStateManager.rotate(xRot, 1.0F, 0F, 0F);
        }
        float scale = entity.getGender() == 1 ? entity.getMaleSize() * entity.getAgeScale() : 1 * entity.getAgeScale();
        matrixStackIn.scale(scale, scale, scale);
        this.shadowSize = entity.getWidth() * 0.45F;
    }

    @Override
    protected float getDeathMaxRotation(EntityPrehistoric entity) {
        return entity.getDeathRotation();
    }


}
