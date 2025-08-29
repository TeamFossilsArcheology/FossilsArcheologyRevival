package com.github.teamfossilsarcheology.fossil.forge;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.client.ClientInit;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel;
import com.github.teamfossilsarcheology.fossil.config.forge.ForgeConfig;
import com.github.teamfossilsarcheology.fossil.config.forge.ForgeConfigFix;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Coelacanth;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.mammal.IMammalCap;
import com.github.teamfossilsarcheology.fossil.forge.client.model.PlantModelLoader;
import com.github.teamfossilsarcheology.fossil.forge.compat.alexsmobs.AlexsMobsCompat;
import com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat;
import com.github.teamfossilsarcheology.fossil.forge.world.biome.ForgeFossilRegion;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import com.github.teamfossilsarcheology.fossil.world.chunk.AnuLairChunkGenerator;
import com.github.teamfossilsarcheology.fossil.world.chunk.TreasureChunkGenerator;
import com.github.teamfossilsarcheology.fossil.world.feature.placement.ModPlacedFeatures;
import com.github.teamfossilsarcheology.fossil.world.feature.structures.ModStructureType;
import com.github.teamfossilsarcheology.fossil.world.surfacerules.ModSurfaceRules;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(FossilMod.MOD_ID)
public class ForgeFossilMod {

    public ForgeFossilMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        EventBuses.registerModEventBus(FossilMod.MOD_ID, modEventBus);

        FossilMod.init();

        modEventBus.addListener(this::onClient);
        modEventBus.addListener(this::onCommon);
        modEventBus.addListener(this::registerCaps);
        modEventBus.addListener(this::onModelRegistryEvent);
        modEventBus.addListener(ForgeConfigFix::fixConfig);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ForgeConfig.CLIENT_SPEC);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientInit::immediate);
    }

    public void onClient(FMLClientSetupEvent event) {
        ClientInit.later();
    }

    public void onCommon(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (ModList.get().isLoaded(ModConstants.FARMERS)) {
                FoodMappingsManager.INSTANCE.listen(FarmersDelightCompat::registerFoodMappings);
            }
            if (ModList.get().isLoaded(ModConstants.ALEXS_MOBS)) {
                FoodMappingsManager.INSTANCE.listen(AlexsMobsCompat::register);
            }
            ModPlacedFeatures.register();
            ModStructureType.register();
            ModTriggers.register();
            Regions.register(new ForgeFossilRegion("overworld", RegionType.OVERWORLD, 4));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, FossilMod.MOD_ID, ModSurfaceRules.VOLCANIC_SURFACE_RULE);
            Registry.register(BuiltInRegistries.CHUNK_GENERATOR, FossilMod.location("treasure_room"), TreasureChunkGenerator.CODEC);
            Registry.register(BuiltInRegistries.CHUNK_GENERATOR, FossilMod.location("anu_lair"), AnuLairChunkGenerator.CODEC);
            SpawnPlacements.register(ModEntities.ALLIGATOR_GAR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
            SpawnPlacements.register(ModEntities.COELACANTH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Coelacanth::canCoelacanthSpawn);
            SpawnPlacements.register(ModEntities.NAUTILUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
            SpawnPlacements.register(ModEntities.STURGEON.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        });
    }

    private void onModelRegistryEvent(ModelEvent.RegisterGeometryLoaders event) {
        event.register(PlantBlockModel.LOADER.getPath(), new PlantModelLoader());
    }

    private void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IMammalCap.class);
    }
}
