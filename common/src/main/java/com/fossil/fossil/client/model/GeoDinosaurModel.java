package com.fossil.fossil.client.model;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GeoDinosaurModel<T extends Prehistoric> extends AnimatedGeoModel<T> {
    private final ResourceLocation modelLocation;
    private final ResourceLocation animationLocation;

    public GeoDinosaurModel(ResourceLocation modelLocation, ResourceLocation animationLocation) {
        this.modelLocation = modelLocation;
        this.animationLocation = animationLocation;
    }

    @Override
    public ResourceLocation getModelLocation(T object) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(T object) {
        return object.textureLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return animationLocation;
    }
}
