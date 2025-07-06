package com.github.teamfossilsarcheology.fossil.world.biome;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.world.feature.placement.ModPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Stores the resource keys for the custom biomes
 */
public class ModBiomes {
    public static final ResourceKey<Biome> ANU_LAIR_KEY = resource("anu_lair");
    public static final ResourceKey<Biome> TREASURE_ROOM_KEY = resource("treasure_room");
    public static final ResourceKey<Biome> VOLCANO_KEY = resource("volcano");

    private static ResourceKey<Biome> resource(String name) {
        return ResourceKey.create(Registries.BIOME, FossilMod.location(name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(ANU_LAIR_KEY, anuLair(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
        context.register(TREASURE_ROOM_KEY, treasureRoom(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
        context.register(VOLCANO_KEY, volcano(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
    }

    public static Biome volcano(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, ModPlacedFeatures.VOLCANO_LAVA_LAKE_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.VOLCANO_ASH_DISK_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.VOLCANO_MAGMA_DISK_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.VOLCANO_VENT_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, ModPlacedFeatures.VOLCANO_FOSSIL_KEY);
        biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatures.VOLCANO_CONE_KEY);

        return biome(false, 2, 0, 0x981010, 0x4c0808, 0x504040, calculateSkyColor(2),
                new MobSpawnSettings.Builder(), biomeBuilder);
    }

    public static Biome treasureRoom(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return biome(false, 0.5f, 0.5f, 0, 0, 0, 0,
                new MobSpawnSettings.Builder(), biomeBuilder);
    }

    public static Biome anuLair(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        mobs.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 2, 1, 8));
        mobs.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.SENTRY_PIGLIN.get(), 6, 1, 8));
        return biome(false, 0.5f, 0.5f, 0x3F76E4, 0x50533, 0, 0,
                mobs, biomeBuilder);
    }

    protected static int calculateSkyColor(float temperature) {
        float i = Mth.clamp(temperature / 3f, -1, 1);
        return Mth.hsvToRgb(0.62222224f - i * 0.05f, 0.5f + i * 0.1f, 1.0f);
    }

    private static Biome biome(boolean precipitation, float temperature, float downfall, int waterColor,
                               int waterFogColor, int fogColor, int skyColor, MobSpawnSettings.Builder spawnBuilder,
                               BiomeGenerationSettings.Builder biomeBuilder) {
        return new Biome.BiomeBuilder().hasPrecipitation(precipitation).temperature(temperature).downfall(downfall)
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor).skyColor(
                        skyColor).build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build()).build();
    }
}
