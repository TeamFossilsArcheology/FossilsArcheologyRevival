package com.fossil.fossil.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.capabilities.fabric.ModCapabilitiesImpl;
import com.fossil.fossil.config.fabric.FossilConfigImpl;
import com.fossil.fossil.fabric.capabilities.MammalComponent;
import com.fossil.fossil.fabric.world.biome.FabricFossilRegion;
import com.fossil.fossil.fabric.world.biome.FabricModBiomes;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.util.FossilFoodMappings;
import com.fossil.fossil.world.feature.placement.ModPlacedFeatures;
import com.fossil.fossil.world.surfacerules.ModSurfaceRules;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.NotNull;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

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
        MidnightConfig.init(Fossil.MOD_ID, FossilConfigImpl.class);
        FossilConfigImpl.initFabricConfig();
        Fossil.init();
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

