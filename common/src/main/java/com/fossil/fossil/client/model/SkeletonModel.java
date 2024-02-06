package com.fossil.fossil.client.model;

import com.fossil.fossil.entity.PrehistoricSkeleton;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SkeletonModel extends AnimatedGeoModel<PrehistoricSkeleton> {

    @Override
    public ResourceLocation getModelLocation(PrehistoricSkeleton entity) {
        return entity.modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(PrehistoricSkeleton entity) {
        return entity.textureLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PrehistoricSkeleton entity) {
        return null;
    }
}
