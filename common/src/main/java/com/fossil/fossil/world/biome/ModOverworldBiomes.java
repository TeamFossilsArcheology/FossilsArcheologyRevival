package com.fossil.fossil.world.biome;

import com.fossil.fossil.world.feature.placement.ModPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModOverworldBiomes {

    protected static int calculateSkyColor(float temperature) {
        float i = Mth.clamp(temperature / 3f, -1, 1);
        return Mth.hsvToRgb(0.62222224f - i * 0.05f, 0.5f + i * 0.1f, 1.0f);
    }

    private static Biome biome(Precipitation precipitation, BiomeCategory biomeCategory, float temperature, float downfall, int waterColor,
                               int waterFogColor, int fogColor, int skyColor, MobSpawnSettings.Builder spawnBuilder,
                               BiomeGenerationSettings.Builder biomeBuilder) {
        return new Biome.BiomeBuilder().precipitation(precipitation).biomeCategory(biomeCategory).temperature(temperature).downfall(downfall)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor).skyColor(
                        skyColor).build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build()).build();
    }

    public static Biome volcano() {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, ModPlacedFeatures.LAKE_LAVA_VOLCANO);
        biomeBuilder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ModPlacedFeatures.ashDiskVolcano());
        biomeBuilder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ModPlacedFeatures.magmaDiskVolcano());
        biomeBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, ModPlacedFeatures.FOSSIL_VOLCANO);
        biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.coneVolcano());

        //TODO: Colors
        return biome(Biome.Precipitation.NONE, Biome.BiomeCategory.DESERT, 2, 0, 0x300000, 0x300000, 0x300000, calculateSkyColor(2),
                new MobSpawnSettings.Builder(), biomeBuilder);
    }
}
