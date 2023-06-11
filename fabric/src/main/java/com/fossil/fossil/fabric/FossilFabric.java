package com.fossil.fossil.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.capabilities.fabric.ModCapabilitiesImpl;
import com.fossil.fossil.config.fabric.FossilConfigImpl;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.fabric.capabilities.MammalComponent;
import com.fossil.fossil.fabric.world.biome.FabricFossilRegion;
import com.fossil.fossil.fabric.world.biome.FabricModBiomes;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.util.FossilFoodMappings;
import com.fossil.fossil.world.chunk.AnuLairChunkGenerator;
import com.fossil.fossil.world.chunk.TreasureChunkGenerator;
import com.fossil.fossil.world.feature.placement.ModPlacedFeatures;
import com.fossil.fossil.world.surfacerules.ModSurfaceRules;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.NotNull;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FossilFabric implements ModInitializer, TerraBlenderApi, EntityComponentInitializer {
    private static boolean initialized = false;

    @Override
    public void onInitialize() {
        init();
    }

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier barrier, @NotNull ResourceManager manager, @NotNull ProfilerFiller preparationsProfiler, @NotNull ProfilerFiller reloadProfiler, @NotNull Executor backgroundExecutor, @NotNull Executor gameExecutor) {
                return AnimationManager.ANIMATIONS.reload(barrier, manager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
            }

            @Override
            public ResourceLocation getFabricId() {
                return new ResourceLocation(Fossil.MOD_ID, "animations");
            }
        });
        MidnightConfig.init(Fossil.MOD_ID, FossilConfigImpl.class);
        FossilConfigImpl.initFabricConfig();
        Fossil.init();
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Fossil.MOD_ID, "treasure_room"), TreasureChunkGenerator.CODEC);
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(Fossil.MOD_ID, "anu_lair"), AnuLairChunkGenerator.CODEC);
        ModPlacedFeatures.register();
        FabricModBiomes.register();
        ModRecipes.initRecipes();
        ModRegistries.register();
        FossilFoodMappings.register();
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

