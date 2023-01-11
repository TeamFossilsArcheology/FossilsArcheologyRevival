package com.fossil.fossil.client.renderer;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderPrehistoricGeo<T extends Prehistoric> extends GeoEntityRenderer<T> {
    public RenderPrehistoricGeo(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public boolean shouldShowName(T entity) {
        return super.shouldShowName(entity);
    }
}
