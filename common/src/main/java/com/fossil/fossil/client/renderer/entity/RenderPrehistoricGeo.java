package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.model.GeoDinosaurModel;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderPrehistoricGeo<T extends Prehistoric> extends GeoEntityRenderer<T> {
    public RenderPrehistoricGeo(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }

    public RenderPrehistoricGeo(EntityRendererProvider.Context renderManager, String model, String animation) {
        super(renderManager, new GeoDinosaurModel<>(
            new ResourceLocation(Fossil.MOD_ID, "geo/entity/" + model),
            new ResourceLocation(Fossil.MOD_ID, "animations/" + animation)
        ));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.shadowRadius = entity.getBbWidth() * 0.45F;
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public boolean shouldShowName(T animatable) {
        //TODO: Find a more permanent solution
        //Calling super.shouldShowName in fabric crashes the game because the method doesn't exist in GeoEntityRenderer
        return false;
    }

    @Override
    public float getWidthScale(T animatable) {
        return animatable.getModelScale();
    }

    @Override
    public float getHeightScale(T entity) {
        return animatable.getModelScale();
    }
}
