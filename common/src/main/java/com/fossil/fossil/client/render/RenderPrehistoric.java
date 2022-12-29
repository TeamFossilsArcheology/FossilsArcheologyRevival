package com.fossil.fossil.client.render;

import com.fossil.fossil.entity.prehistoric.base.EntityPrehistoric;
import com.fossil.fossil.entity.prehistoric.base.EntityPrehistoricSwimming;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RenderPrehistoric extends MobRenderer<EntityPrehistoric, EntityModel<EntityPrehistoric>> {

    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();

    public RenderPrehistoric(EntityRendererProvider.Context context, EntityModel<EntityPrehistoric> model) {
        super(context, model, 0.3F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(EntityPrehistoric entity) {
        String s = entity.getTexture();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }
        return resourcelocation;


    }

    @Override
    protected void scale(EntityPrehistoric entity, PoseStack matrixStackIn, float partialTickTime) {
        float xRot = 0;
        if(entity instanceof EntityPrehistoricSwimming aquatic){
            //   xRot = aquatic.prevBreachPitch + (aquatic.getBreachPitch() - aquatic.prevBreachPitch) * f;
        //    GlStateManager.rotate(xRot, 1.0F, 0F, 0F);
        }
        float scale = entity.getGender() == 1 ? entity.getMaleSize() * entity.getAgeScale() : 1 * entity.getAgeScale();
        matrixStackIn.scale(scale, scale, scale);
        this.shadowRadius = entity.getBbWidth() * 0.45F;
    }

    @Override
    protected float getFlipDegrees(EntityPrehistoric entity) {
        return entity.getDeathRotation();
    }


}
