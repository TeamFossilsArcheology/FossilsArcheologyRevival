package com.fossil.fossil.world.feature.placement;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.world.feature.ModOreFeatures;
import com.fossil.fossil.world.feature.configuration.ModConfiguredFeatures;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> LAKE_LAVA_VOLCANO = PlacementUtils.register("volcano_lake_lava", MiscOverworldFeatures.LAKE_LAVA,
            RarityFilter.onAverageOnceEvery(6), CountPlacement.of(6), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome());
    public static final Holder<PlacedFeature> FOSSIL_VOLCANO = PlacementUtils.register("volcano_fossil", CaveFeatures.FOSSIL_COAL,
            RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
            RandomOffsetPlacement.vertical(ClampedNormalInt.of(-9, 4, -16, -5)), BiomeFilter.biome());
    private static Holder<PlacedFeature> ashDiskVolcano;
    private static Holder<PlacedFeature> magmaDiskVolcano;
    private static Holder<PlacedFeature> coneVolcano;

    static {
        StructureSets.register(ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(Fossil.MOD_ID, "hell_boat")),
                ModConfiguredFeatures.HELL_BOAT, new LazyRandomSpreadPlacement(FossilConfig.HELL_SHIP_SPACING, FossilConfig.HELL_SHIP_SEPERATION,
                        RandomSpreadType.LINEAR, 92182587));
    }

    public static void register() {
        //Features that depend on ModConfiguredFeatures can't be called before the block registries have been initialized
        ashDiskVolcano = PlacementUtils.register("volcano_ash_disk", ModConfiguredFeatures.ASH_DISK,
                CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        magmaDiskVolcano = PlacementUtils.register("volcano_magma_disk", ModConfiguredFeatures.MAGMA_DISK,
                RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        coneVolcano = PlacementUtils.register("volcano_cone", ModConfiguredFeatures.VOLCANO_CONE,
                RarityFilter.onAverageOnceEvery(30), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        var tarPitPlaced = PlacementUtils.register("tar_pit_placed", ModConfiguredFeatures.TAR_PIT,
                new LazyRarityFilter(FossilConfig.TAR_PIT_RARITY),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        var moaiStatuePlaced = PlacementUtils.register("moai_statue", ModConfiguredFeatures.MOAI_STATUE, BiomeFilter.biome(),
                new LazyRarityFilter(FossilConfig.MOAI_RARITY), InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

        var fossilBlockPlaced = PlacementUtils.register("fossil_block_placed", ModOreFeatures.FOSSIL_BLOCK,
                commonOrePlacement(FossilConfig.FOSSIL_ORE_RARITY, // VeinsPerChunk
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-0), VerticalAnchor.aboveBottom(256))));

        var volcanicRockPlaced = PlacementUtils.register("volcanic_rock_placed", ModOreFeatures.VOLCANIC_ROCK,
                commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));

        var permafrostBlockPlaced = PlacementUtils.register("permafrost_block_placed", ModOreFeatures.PERMAFROST_BLOCK,
                commonOrePlacement(FossilConfig.PERMAFROST_RARITY, // VeinsPerChunk
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(100), VerticalAnchor.aboveBottom(256))));

        BiomeModifications.addProperties((context, mutable) -> {
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_FOSSILS)) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, fossilBlockPlaced);
            }
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_VOLCANIC_ROCK)) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, volcanicRockPlaced);
            }
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_PERMAFROST)) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, permafrostBlockPlaced);
            }
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_TAR_PITS) && mutable.getCategory() == Biome.BiomeCategory.SWAMP) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.LAKES, tarPitPlaced);
            }
            if (FossilConfig.isEnabled(FossilConfig.GENERATE_MOAI) && mutable.getCategory() == Biome.BiomeCategory.BEACH && mutable.getClimateProperties().getTemperature() > 0.2) {
                mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, moaiStatuePlaced);
            }
            if (FossilConfig.isEnabled(FossilConfig.SPAWN_ALLIGATOR_GAR) && mutable.getCategory() == Biome.BiomeCategory.SWAMP) {
                mutable.getSpawnProperties().addSpawn(ModEntities.ALLIGATOR_GAR.get().getCategory(), new MobSpawnSettings.SpawnerData(ModEntities.ALLIGATOR_GAR.get(), FossilConfig.getInt(FossilConfig.ALLIGATOR_GAR_SPAWN_WEIGHT), 1, 4));
            }
            if (FossilConfig.isEnabled(FossilConfig.SPAWN_COELACANTH) && mutable.getCategory() == Biome.BiomeCategory.OCEAN) {
                mutable.getSpawnProperties().addSpawn(ModEntities.COELACANTH.get().getCategory(), new MobSpawnSettings.SpawnerData(ModEntities.COELACANTH.get(), FossilConfig.getInt(FossilConfig.COELACANTH_SPAWN_WEIGHT), 1, 4));
            }
            if (FossilConfig.isEnabled(FossilConfig.SPAWN_NAUTILUS) && mutable.getCategory() == Biome.BiomeCategory.OCEAN) {
                mutable.getSpawnProperties().addSpawn(ModEntities.NAUTILUS.get().getCategory(), new MobSpawnSettings.SpawnerData(ModEntities.NAUTILUS.get(), FossilConfig.getInt(FossilConfig.NAUTILUS_SPAWN_WEIGHT), 1, 4));
            }
            if (FossilConfig.isEnabled(FossilConfig.SPAWN_STURGEON) && mutable.getCategory() == Biome.BiomeCategory.RIVER) {
                mutable.getSpawnProperties().addSpawn(ModEntities.STURGEON.get().getCategory(), new MobSpawnSettings.SpawnerData(ModEntities.STURGEON.get(), FossilConfig.getInt(FossilConfig.STURGEON_SPAWN_WEIGHT), 1, 4));
            }
        });
    }

    public static Holder<PlacedFeature> ashDiskVolcano() {
        return ashDiskVolcano;
    }

    public static Holder<PlacedFeature> magmaDiskVolcano() {
        return magmaDiskVolcano;
    }

    public static Holder<PlacedFeature> coneVolcano() {
        return coneVolcano;
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(String configEntry, PlacementModifier placementModifier) {
        return orePlacement(new LazyCountPlacement(configEntry), placementModifier);
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(count), placementModifier);
    }

    private static List<PlacementModifier> rareOrePlacement(int rarity, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), placementModifier);
    }
}
