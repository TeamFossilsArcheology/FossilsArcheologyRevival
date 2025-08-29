package com.github.teamfossilsarcheology.fossil.fabric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.capabilities.fabric.ModCapabilitiesImpl;
import com.github.teamfossilsarcheology.fossil.config.fabric.FossilConfigImpl;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Coelacanth;
import com.github.teamfossilsarcheology.fossil.fabric.capabilities.FirstHatchComponent;
import com.github.teamfossilsarcheology.fossil.fabric.capabilities.MammalComponent;
import com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat;
import com.github.teamfossilsarcheology.fossil.fabric.world.biome.FabricBiomeModifiers;
import com.github.teamfossilsarcheology.fossil.fabric.world.biome.FabricFossilRegion;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import com.github.teamfossilsarcheology.fossil.world.chunk.AnuLairChunkGenerator;
import com.github.teamfossilsarcheology.fossil.world.chunk.TreasureChunkGenerator;
import com.github.teamfossilsarcheology.fossil.world.feature.placement.ModPlacedFeatures;
import com.github.teamfossilsarcheology.fossil.world.feature.structures.ModStructureType;
import com.github.teamfossilsarcheology.fossil.world.surfacerules.ModSurfaceRules;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;
import team.reborn.energy.api.EnergyStorage;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class FabricFossilMod implements ModInitializer, TerraBlenderApi, EntityComponentInitializer {
    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        MidnightConfig.init(FossilMod.MOD_ID, FossilConfigImpl.class);
        FossilMod.init();
        ModStructureType.register();
        FabricBiomeModifiers.init();
        SpawnPlacements.register(ModEntities.ALLIGATOR_GAR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        SpawnPlacements.register(ModEntities.COELACANTH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Coelacanth::canCoelacanthSpawn);
        SpawnPlacements.register(ModEntities.NAUTILUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        SpawnPlacements.register(ModEntities.STURGEON.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, FossilMod.location("treasure_room"), TreasureChunkGenerator.CODEC);
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, FossilMod.location("anu_lair"), AnuLairChunkGenerator.CODEC);
        ModTriggers.register();
        ModPlacedFeatures.register();
        ModRegistries.register();
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> FossilMod.syncData(player));
        ServerLifecycleEvents.SERVER_STARTING.register(minecraftServer -> {
            if (FabricLoader.getInstance().isModLoaded(ModConstants.FARMERS)) {
                FoodMappingsManager.INSTANCE.listen(FarmersDelightCompat::registerFoodMappings);
            }
        });
        ModBlockEntities.ANALYZER.listen(blockEntityType -> {
            EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> (EnergyStorage) blockEntity.getEnergyStorage(), blockEntityType);
        });
        ModBlockEntities.CULTURE_VAT.listen(blockEntityType -> {
            EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> (EnergyStorage) blockEntity.getEnergyStorage(), blockEntityType);
        });
    }

    @Override
    public void onInitialize() {
        init();
    }

    @Override
    public void onTerraBlenderInitialized() {
        init();
        Regions.register(new FabricFossilRegion("overworld", RegionType.OVERWORLD, 4));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, FossilMod.MOD_ID, ModSurfaceRules.VOLCANIC_SURFACE_RULE);
    }

    @Override
    public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
        registry.registerFor(Animal.class, ModCapabilitiesImpl.MAMMAL, MammalComponent::new);
        registry.registerFor(Player.class, ModCapabilitiesImpl.PLAYER, FirstHatchComponent::new);
    }
}

