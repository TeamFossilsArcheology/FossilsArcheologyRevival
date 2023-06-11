package com.fossil.fossil.forge;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.ClientInit;
import com.fossil.fossil.config.forge.ForgeConfig;
import com.fossil.fossil.forge.capabilities.mammal.IMammalCap;
import com.fossil.fossil.forge.client.ClientModEvents;
import com.fossil.fossil.forge.world.biome.ForgeFossilRegion;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.util.FossilFoodMappings;
import com.fossil.fossil.world.chunk.AnuLairChunkGenerator;
import com.fossil.fossil.world.chunk.TreasureChunkGenerator;
import com.fossil.fossil.world.surfacerules.ModSurfaceRules;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
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
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.SPEC);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientInit::immediate);
    }

    public void onClient(FMLClientSetupEvent event) {
        ClientModEvents.registerOverlays();
        ClientInit.later();
    }

    public void onCommon(FMLCommonSetupEvent event) {
        ModRecipes.initRecipes();
        FossilFoodMappings.register();
        event.enqueueWork(() -> {
            Regions.register(new ForgeFossilRegion("overworld", RegionType.OVERWORLD, 4));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Fossil.MOD_ID, ModSurfaceRules.VOLCANIC_SURFACE_RULE);
            Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Fossil.MOD_ID, "treasure_room"), TreasureChunkGenerator.CODEC);
            Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Fossil.MOD_ID, "anu_lair"), AnuLairChunkGenerator.CODEC);
        });
    }

    public void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IMammalCap.class);
    }
}
