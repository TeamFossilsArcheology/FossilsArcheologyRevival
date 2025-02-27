package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.monster.Failuresaurus;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class FailuresaurusModel extends GeoModel<Failuresaurus> {
    private final Map<String, ResourceLocation> modelLocations = Arrays.stream(Failuresaurus.Variant.values())
            .collect(Collectors.toMap(Enum::name, variant ->
                    FossilMod.location("geo/entity/failuresaurus_" + variant.name().toLowerCase(Locale.ROOT) + ".geo.json")));
    private final Map<String, ResourceLocation> textureLocations = Arrays.stream(Failuresaurus.Variant.values())
            .collect(Collectors.toMap(Enum::name, variant ->
                    FossilMod.location("textures/entity/failuresaurus/failuresaurus_" + variant.name().toLowerCase(Locale.ROOT) + ".png")));
    private final Map<String, ResourceLocation> animationLocations = Arrays.stream(Failuresaurus.Variant.values())
            .collect(Collectors.toMap(Enum::name, variant ->
                    FossilMod.location("animations/entity/failuresaurus_" + variant.name().toLowerCase(Locale.ROOT) + ".animation.json")));

    public FailuresaurusModel() {
    }

    @Override
    public RenderType getRenderType(Failuresaurus animatable, ResourceLocation texture) {
        if (animatable.getVariant().equals(Failuresaurus.Variant.FLYING.name())) {
            return RenderType.entityCutoutNoCull(texture);
        }
        if (animatable.getVariant().equals(Failuresaurus.Variant.SAUROPOD.name())) {
            return RenderType.entityCutoutNoCull(texture);
        }
        return RenderType.entityCutout(texture);
    }

    @Override
    public ResourceLocation getModelResource(Failuresaurus failuresaurus) {
        return modelLocations.get(failuresaurus.getVariant());
    }

    @Override
    public ResourceLocation getTextureResource(Failuresaurus failuresaurus) {
        return textureLocations.get(failuresaurus.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(Failuresaurus failuresaurus) {
        return animationLocations.get(failuresaurus.getVariant());
    }
}
