package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.entity.PrehistoricSkeleton;
import com.github.teamfossilsarcheology.fossil.entity.animation.GeoModelManager;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class SkeletonModel extends GeoModelProvider<PrehistoricSkeleton> {

    @Override
    public GeoModel getModel(ResourceLocation location) {
        return GeoModelManager.SKELETON_MODELS.getSkeletonModel(location);
    }

    @Override
    public ResourceLocation getModelLocation(PrehistoricSkeleton entity) {
        return entity.modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(PrehistoricSkeleton entity) {
        return entity.textureLocation;
    }
}
