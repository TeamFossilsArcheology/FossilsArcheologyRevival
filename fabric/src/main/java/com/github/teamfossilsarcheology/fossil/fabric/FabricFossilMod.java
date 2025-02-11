package com.github.teamfossilsarcheology.fossil.fabric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.block.entity.fabric.AnalyzerBlockEntityImpl;
import com.github.teamfossilsarcheology.fossil.block.entity.fabric.CultureVatBlockEntityImpl;
import com.github.teamfossilsarcheology.fossil.capabilities.fabric.ModCapabilitiesImpl;
import com.github.teamfossilsarcheology.fossil.config.fabric.FossilConfigImpl;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Coelacanth;
import com.github.teamfossilsarcheology.fossil.fabric.capabilities.FirstHatchComponent;
import com.github.teamfossilsarcheology.fossil.fabric.capabilities.MammalComponent;
import com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat;
import com.github.teamfossilsarcheology.fossil.fabric.world.biome.FabricFossilRegion;
import com.github.teamfossilsarcheology.fossil.fabric.world.biome.FabricModBiomes;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CSyncEntityInfoMessage;
import com.github.teamfossilsarcheology.fossil.world.chunk.AnuLairChunkGenerator;
import com.github.teamfossilsarcheology.fossil.world.chunk.TreasureChunkGenerator;
import com.github.teamfossilsarcheology.fossil.world.feature.placement.ModPlacedFeatures;
import com.github.teamfossilsarcheology.fossil.world.feature.placement.ModPlacementTypes;
import com.github.teamfossilsarcheology.fossil.world.surfacerules.ModSurfaceRules;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
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
        FossilConfigImpl.initFabricConfig();
        FossilMod.init();
        SpawnRestrictionAccessor.callRegister(ModEntities.ALLIGATOR_GAR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        SpawnRestrictionAccessor.callRegister(ModEntities.COELACANTH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Coelacanth::canCoelacanthSpawn);
        SpawnRestrictionAccessor.callRegister(ModEntities.NAUTILUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        SpawnRestrictionAccessor.callRegister(ModEntities.STURGEON.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        Registry.register(Registry.CHUNK_GENERATOR, FossilMod.location("treasure_room"), TreasureChunkGenerator.CODEC);
        Registry.register(Registry.CHUNK_GENERATOR, FossilMod.location("anu_lair"), AnuLairChunkGenerator.CODEC);
        ModPlacementTypes.register();
        ModTriggers.register();
        ModPlacedFeatures.register();
        FabricModBiomes.register();
        ModRegistries.register();
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            if (joined) {
                MessageHandler.SYNC_CHANNEL.sendToPlayer(player, new S2CSyncEntityInfoMessage(EntityDataLoader.INSTANCE.getEntities()));
            }
        });
        ServerLifecycleEvents.SERVER_STARTING.register(minecraftServer -> {
            if (FabricLoader.getInstance().isModLoaded("farmersdelight")) {
                FarmersDelightCompat.registerFoodMappings();
            }
        });
        ModBlockEntities.ANALYZER.listen(blockEntityType -> {
            EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> ((AnalyzerBlockEntityImpl) blockEntity).energyStorage, blockEntityType);
        });
        ModBlockEntities.CULTURE_VAT.listen(blockEntityType -> {
            EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> ((CultureVatBlockEntityImpl) blockEntity).energyStorage, blockEntityType);
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

