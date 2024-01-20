package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.monster.Failuresaurus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FailuresaurusModel extends AnimatedGeoModel<Failuresaurus> {
    private static final ResourceLocation ANIMATION = new ResourceLocation(Fossil.MOD_ID, "animations/velociraptor.animation.json");
    private static final ResourceLocation MODEL = new ResourceLocation(Fossil.MOD_ID, "geo/entity/velociraptor.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/tar_slime.png");
    @Override
    public ResourceLocation getModelLocation(Failuresaurus object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(Failuresaurus object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Failuresaurus animatable) {
        return ANIMATION;
    }
}
