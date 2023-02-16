package com.fossil.fossil.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.util.FossilFoodMappings;
import net.fabricmc.api.ModInitializer;

public class FossilFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Fossil.init();
        ModRecipes.initRecipes();
        FossilFoodMappings.register();
    }
}

