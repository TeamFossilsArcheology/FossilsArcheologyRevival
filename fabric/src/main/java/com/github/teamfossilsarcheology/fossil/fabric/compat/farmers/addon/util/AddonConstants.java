package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon.util;

import com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon.*;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import net.fabricmc.loader.api.FabricLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AddonConstants {
    private static final Map<String, Consumer<FoodMappingsManager>> SUPPORTED_ADDONS = new HashMap<>();

    static {
        register("brewinandchewin", BrewinAndChewinCompat::registerFoodMappings);
        register("casualness_delight", CasualnessDelightCompat::registerFoodMappings);
        register("culturaldelights", CulturalDelightCompat::registerFoodMappings);
        register("endersdelight", EnderDelightCompat::registerFoodMappings);
        register("ends_delight", EndDelightCompat::registerFoodMappings);
        register("expandeddelight", ExpandedDelightCompat::registerFoodMappings);
        register("farmersrespite", FarmerRespiteCompat::registerFoodMappings);
        register("festive_delight", FestiveDelightCompat::registerFoodMappings);
        register("nethersdelight", NetherDelightCompat::registerFoodMappings);
        register("oceansdelight", OceanDelightCompat::registerFoodMappings);
        register("pineapple_delight", PineappleDelightCompat::registerFoodMappings);
        register("seeddelight", SeedDelightCompat::registerFoodMappings);
    }

    public static void registerAddonFoodMappings(FoodMappingsManager manager) {
        //We cycle through each addon and check if it is loaded
        for (Map.Entry<String, Consumer<FoodMappingsManager>> entry : SUPPORTED_ADDONS.entrySet()) {
            if (!FabricLoader.getInstance().isModLoaded(entry.getKey())) {
                continue;
            }
            entry.getValue().accept(manager);
        }
    }

    private static void register(String modId, Consumer<FoodMappingsManager> function) {
        SUPPORTED_ADDONS.put(modId, function);
    }
}
