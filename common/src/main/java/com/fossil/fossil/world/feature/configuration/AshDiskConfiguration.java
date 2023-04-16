package com.fossil.fossil.world.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record AshDiskConfiguration(IntProvider radius, boolean magma) implements FeatureConfiguration {

    public static final Codec<AshDiskConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(IntProvider.codec(0, 11).fieldOf("radius").forGetter(AshDiskConfiguration::radius),
                    Codec.BOOL.fieldOf("magma").forGetter(AshDiskConfiguration::magma)).apply(instance,
                    AshDiskConfiguration::new));
}
