package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.monster.Failuresaurus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class FailuresaurusModel extends AnimatedGeoModel<Failuresaurus> {
    private final Map<String, ResourceLocation> modelLocations = Arrays.stream(Failuresaurus.Variant.values())
            .collect(Collectors.toMap(Enum::name, variant ->
                    FossilMod.location("geo/entity/failuresaurus_" + variant.name().toLowerCase(Locale.ENGLISH) + ".geo.json")));
    private final Map<String, ResourceLocation> textureLocations = Arrays.stream(Failuresaurus.Variant.values())
            .collect(Collectors.toMap(Enum::name, variant ->
                    FossilMod.location("textures/entity/failuresaurus/failuresaurus_" + variant.name().toLowerCase(Locale.ENGLISH) + ".png")));
    private final Map<String, ResourceLocation> animationLocations = Arrays.stream(Failuresaurus.Variant.values())
            .collect(Collectors.toMap(Enum::name, variant ->
                    FossilMod.location("animations/failuresaurus_" + variant.name().toLowerCase(Locale.ENGLISH) + ".animation.json")));

    public FailuresaurusModel() {
    }

    @Override
    public ResourceLocation getModelLocation(Failuresaurus failuresaurus) {
        return modelLocations.get(failuresaurus.getVariant());
    }

    @Override
    public ResourceLocation getTextureLocation(Failuresaurus failuresaurus) {
        return textureLocations.get(failuresaurus.getVariant());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Failuresaurus failuresaurus) {
        return animationLocations.get(failuresaurus.getVariant());
    }
}
