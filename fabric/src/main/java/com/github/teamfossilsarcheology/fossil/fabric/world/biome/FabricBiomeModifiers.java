package com.github.teamfossilsarcheology.fossil.fabric.world.biome;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.config.fabric.FossilConfigImpl;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.world.feature.placement.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FabricBiomeModifiers {

    public static void init() {
        if (FossilConfig.isEnabled(FossilConfig.GENERATE_MOAI)) {
            BiomeModifications.addFeature(context ->
                            context.getBiomeKey().equals(Biomes.BEACH) && context.getBiome().getBaseTemperature() > 0.2,
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    ModPlacedFeatures.MOAI_STATUE_KEY);
        }
        if (FossilConfigImpl.spawnAlligatorGar) {
            spawn(ModEntities.ALLIGATOR_GAR.get(), Biomes.SWAMP, FossilConfigImpl.alligatorGarSpawnWeight, 1, 4);
        }
        if (FossilConfigImpl.spawnCoelacanth) {
            spawn(ModEntities.COELACANTH.get(), BiomeTags.IS_OCEAN, FossilConfigImpl.coelacanthSpawnWeight, 1, 4);
        }
        if (FossilConfigImpl.spawnNautilus) {
            spawn(ModEntities.NAUTILUS.get(), BiomeTags.IS_OCEAN, FossilConfigImpl.nautilusSpawnWeight, 1, 4);
        }
        if (FossilConfigImpl.spawnSturgeon) {
            spawn(ModEntities.STURGEON.get(), BiomeTags.IS_RIVER, FossilConfigImpl.sturgeonSpawnWeight, 1, 4);
        };
    }

    private static void spawn(EntityType<? extends Entity> entityType, TagKey<Biome> biome, int weight, int minGroupSize, int maxGroupSize) {
        BiomeModifications.addSpawn(BiomeSelectors.tag(biome), entityType.getCategory(), entityType, weight, minGroupSize, maxGroupSize);
    }

    private static void spawn(EntityType<? extends Entity> entityType, ResourceKey<Biome> biome, int weight, int minGroupSize, int maxGroupSize) {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(biome), entityType.getCategory(), entityType, weight, minGroupSize, maxGroupSize);
    }
}
