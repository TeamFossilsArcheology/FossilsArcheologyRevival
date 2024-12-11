package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public abstract class FixedGeoEntityRenderer<T extends LivingEntity & IAnimatable> extends GeoEntityRenderer<T> {

    protected FixedGeoEntityRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public boolean shouldShowName(T animatable) {
        //Calling super.shouldShowName in fabric crashes the game because the method doesn't exist in GeoEntityRenderer
        return animatable.hasCustomName() && (animatable == entityRenderDispatcher.crosshairPickEntity || animatable.shouldShowName());
    }
}
