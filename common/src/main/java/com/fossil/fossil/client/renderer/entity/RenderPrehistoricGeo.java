package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.model.GeoDinosaurModel;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderPrehistoricGeo<T extends Prehistoric> extends GeoEntityRenderer<T> {
    public RenderPrehistoricGeo(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider, float width, float height) {
        super(renderManager, modelProvider);
        this.widthScale = width;
        this.heightScale = height;
    }

    public RenderPrehistoricGeo(EntityRendererProvider.Context renderManager, String model, String animation, float width, float height) {
        super(renderManager, new GeoDinosaurModel<>(
            new ResourceLocation(Fossil.MOD_ID, "geo/entity/" + model),
            new ResourceLocation(Fossil.MOD_ID, "animations/" + animation)
        ));
        this.widthScale = width;
        this.heightScale = height;
    }

    @Override
    public boolean shouldShowName(T animatable) {
        //TODO: Find a more permanent solution
        //Calling super.shouldShowName in fabric crashes the game because the method doesn't exist in GeoEntityRenderer
        return false;
    }
}
