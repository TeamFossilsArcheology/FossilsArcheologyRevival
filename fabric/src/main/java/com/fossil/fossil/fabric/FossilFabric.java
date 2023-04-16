package com.fossil.fossil.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.fabric.world.biome.FossilTerraBlenderRegion;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.util.FossilFoodMappings;
import com.fossil.fossil.world.surfacerules.ModSurfaceRules;
import net.fabricmc.api.ModInitializer;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class FossilFabric implements ModInitializer, TerraBlenderApi {
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
        Fossil.init();
        ModRecipes.initRecipes();
        FossilFoodMappings.register();
    }

    @Override
    public void onTerraBlenderInitialized() {
        init();
    }
}

