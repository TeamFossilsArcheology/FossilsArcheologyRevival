package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.client.SkeletonGeoModelLoader;
import com.github.teamfossilsarcheology.fossil.entity.PrehistoricSkeleton;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class SkeletonModel extends GeoModel<PrehistoricSkeleton> {
    public SkeletonModel() {
        super();
    }

    @Override
    public BakedGeoModel getBakedModel(ResourceLocation location) {
        return SkeletonGeoModelLoader.INSTANCE.getSkeletonModel(location);
    }

    @Override
    public ResourceLocation getModelResource(PrehistoricSkeleton animatable) {
        return animatable.modelLocation;
    }

    @Override
    public ResourceLocation getTextureResource(PrehistoricSkeleton animatable) {
        return animatable.textureLocation;
    }

    @Override
    public ResourceLocation getAnimationResource(PrehistoricSkeleton animatable) {
        return null;
    }
}
