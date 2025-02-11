package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.resources.ResourceLocation;

public class PrehistoricFishGeoModel<T extends PrehistoricFish> extends PrehistoricAnimatableModel<T> {
    private final ResourceLocation modelLocation;
    private final ResourceLocation animationLocation;
    private final ResourceLocation textureLocation;

    public PrehistoricFishGeoModel(ResourceLocation modelLocation, ResourceLocation animationLocation, ResourceLocation textureLocation) {
        this.modelLocation = modelLocation;
        this.animationLocation = animationLocation;
        this.textureLocation = textureLocation;
    }

    @Override
    public ResourceLocation getModelLocation(T object) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(T object) {
        return textureLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return animationLocation;
    }
}