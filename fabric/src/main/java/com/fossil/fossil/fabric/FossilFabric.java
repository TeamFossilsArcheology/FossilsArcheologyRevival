package com.fossil.fossil.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.capabilities.fabric.ModCapabilitiesImpl;
import com.fossil.fossil.config.fabric.FossilConfigImpl;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.Coelacanth;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import com.fossil.fossil.fabric.capabilities.MammalComponent;
import com.fossil.fossil.fabric.world.biome.FabricFossilRegion;
import com.fossil.fossil.fabric.world.biome.FabricModBiomes;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.SyncEntityInfoMessage;
import com.fossil.fossil.world.chunk.AnuLairChunkGenerator;
import com.fossil.fossil.world.chunk.TreasureChunkGenerator;
import com.fossil.fossil.world.feature.placement.ModPlacedFeatures;
import com.fossil.fossil.world.surfacerules.ModSurfaceRules;
import dev.architectury.platform.Platform;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class FossilFabric implements ModInitializer, TerraBlenderApi, EntityComponentInitializer {
    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        MidnightConfig.init(Fossil.MOD_ID, FossilConfigImpl.class);
        FossilConfigImpl.initFabricConfig();
        Fossil.init();
        SpawnRestrictionAccessor.callRegister(ModEntities.ALLIGATOR_GAR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        SpawnRestrictionAccessor.callRegister(ModEntities.COELACANTH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Coelacanth::canCoelacanthSpawn);
        SpawnRestrictionAccessor.callRegister(ModEntities.NAUTILUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        SpawnRestrictionAccessor.callRegister(ModEntities.STURGEON.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Fossil.MOD_ID, "treasure_room"), TreasureChunkGenerator.CODEC);
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Fossil.MOD_ID, "anu_lair"), AnuLairChunkGenerator.CODEC);
        ModPlacedFeatures.register();
        FabricModBiomes.register();
        ModRegistries.register();
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            if (joined && Platform.getEnv() == EnvType.SERVER) {
                MessageHandler.SYNC_CHANNEL.sendToPlayer(player, new SyncEntityInfoMessage(EntityDataManager.ENTITY_DATA.getEntities()));
            }
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
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Fossil.MOD_ID, ModSurfaceRules.VOLCANIC_SURFACE_RULE);
    }

    @Override
    public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
        registry.registerFor(Animal.class, ModCapabilitiesImpl.MAMMAL, MammalComponent::new);
    }
}

