package com.github.teamfossilsarcheology.fossil.forge.world.biome;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.world.feature.placement.ModPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import static net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;

public class ForgeFossilBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_MOAI_STATUE = createKey("add_moai_statue");
    public static final ResourceKey<BiomeModifier> ADD_ALLIGATOR_GAR = createKey("add_alligator_gar");
    public static final ResourceKey<BiomeModifier> ADD_COELACANTH = createKey("add_coelacanth");
    public static final ResourceKey<BiomeModifier> ADD_NAUTILUS = createKey("add_nautilus");
    public static final ResourceKey<BiomeModifier> ADD_STURGEON = createKey("add_sturgon");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        feature(ADD_MOAI_STATUE, ModPlacedFeatures.MOAI_STATUE_KEY, Biomes.BEACH, GenerationStep.Decoration.SURFACE_STRUCTURES, context);

        spawn(ADD_ALLIGATOR_GAR, ModEntities.ALLIGATOR_GAR.get(), Biomes.SWAMP, 4, 1, 4, context);
        spawn(ADD_COELACANTH, ModEntities.COELACANTH.get(), Biomes.OCEAN, 3, 1, 4, context);
        spawn(ADD_NAUTILUS, ModEntities.NAUTILUS.get(), Biomes.OCEAN, 2, 1, 4, context);
        spawn(ADD_STURGEON, ModEntities.STURGEON.get(), Biomes.RIVER, 5, 1, 4, context);
    }

    private static void feature(ResourceKey<BiomeModifier> key, ResourceKey<PlacedFeature> feature, ResourceKey<Biome> biome, GenerationStep.Decoration generationStep, BootstapContext<BiomeModifier> context) {
        context.register(key, new AddFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)),
                HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(feature)), generationStep));
    }

    private static void spawn(ResourceKey<BiomeModifier> key, EntityType<? extends Entity> entityType, ResourceKey<Biome> biome, int weight, int minGroupSize, int maxGroupSize, BootstapContext<BiomeModifier> context) {
        context.register(key, new AddSpawnsBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)),
                List.of(new MobSpawnSettings.SpawnerData(entityType, weight, minGroupSize, maxGroupSize))));
    }

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, FossilMod.location(name));
    }
}
