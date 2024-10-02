package com.fossil.fossil.forge;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.advancements.ModTriggers;
import com.fossil.fossil.client.ClientInit;
import com.fossil.fossil.client.model.block.PlantBlockModel;
import com.fossil.fossil.config.forge.ForgeConfig;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import com.fossil.fossil.entity.prehistoric.fish.Coelacanth;
import com.fossil.fossil.forge.capabilities.mammal.IMammalCap;
import com.fossil.fossil.forge.client.ClientModEvents;
import com.fossil.fossil.forge.client.model.PlantModelLoader;
import com.fossil.fossil.forge.client.renderer.armor.ForgeAncientHelmetRenderer;
import com.fossil.fossil.forge.world.biome.ForgeFossilRegion;
import com.fossil.fossil.item.forge.AncientHelmetItemImpl;
import com.fossil.fossil.world.chunk.AnuLairChunkGenerator;
import com.fossil.fossil.world.chunk.TreasureChunkGenerator;
import com.fossil.fossil.world.feature.placement.ModPlacementTypes;
import com.fossil.fossil.world.surfacerules.ModSurfaceRules;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(Fossil.MOD_ID)
public class FossilForge {

    public FossilForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        EventBuses.registerModEventBus(Fossil.MOD_ID, modEventBus);

        Fossil.init();

        modEventBus.addListener(this::onClient);
        modEventBus.addListener(this::onCommon);
        modEventBus.addListener(this::registerCaps);
        modEventBus.addListener(this::onModelRegistryEvent);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.SPEC);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientInit::immediate);
    }

    public void onClient(FMLClientSetupEvent event) {
        ClientModEvents.registerOverlays();
        ClientInit.later();
        GeoArmorRenderer.registerArmorRenderer(AncientHelmetItemImpl.class, ForgeAncientHelmetRenderer::new);
    }

    public void onCommon(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModPlacementTypes.register();
            ModTriggers.register();
            Regions.register(new ForgeFossilRegion("overworld", RegionType.OVERWORLD, 4));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Fossil.MOD_ID, ModSurfaceRules.VOLCANIC_SURFACE_RULE);
            Registry.register(Registry.CHUNK_GENERATOR, Fossil.location("treasure_room"), TreasureChunkGenerator.CODEC);
            Registry.register(Registry.CHUNK_GENERATOR, Fossil.location("anu_lair"), AnuLairChunkGenerator.CODEC);
            SpawnPlacements.register(ModEntities.ALLIGATOR_GAR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
            SpawnPlacements.register(ModEntities.COELACANTH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Coelacanth::canCoelacanthSpawn);
            SpawnPlacements.register(ModEntities.NAUTILUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
            SpawnPlacements.register(ModEntities.STURGEON.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricFish::canSpawn);
        });
    }

    private void onModelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(PlantBlockModel.LOADER, new PlantModelLoader());
    }

    private void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IMammalCap.class);
    }
}
