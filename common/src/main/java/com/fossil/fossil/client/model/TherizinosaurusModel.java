package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.Therizinosaurus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TherizinosaurusModel extends AnimatedGeoModel<Therizinosaurus> {
    private static final ResourceLocation modelLocation = new ResourceLocation(Fossil.MOD_ID, "geo/entity/therizinosaurus.geo.json");
    private static final ResourceLocation animationLocation = new ResourceLocation(Fossil.MOD_ID, "animations/fa.therizinosaurus.animations.json");
    @Override
    public ResourceLocation getModelLocation(Therizinosaurus object) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(Therizinosaurus object) {
        return object.textureLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Therizinosaurus animatable) {
        return animationLocation;
    }
}
