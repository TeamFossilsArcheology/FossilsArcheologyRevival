package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.util;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.*;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AddonConstants {
    private static final Map<String, Consumer<FoodMappingsManager>> SUPPORTED_ADDONS = new HashMap<>();

    static {
        register("alexsdelight", AlexDelightCompat::registerFoodMappings);
        register("argentinas_delight", ArgentinaDelightCompat::registerFoodMappings);
        register("brewinandchewin", BrewinAndChewinCompat::registerFoodMappings);
        register("butchersdelightfoods", ButcherDelightFoodCompat::registerFoodMappings);
        register("casualness_delight", CasualnessDelightCompat::registerFoodMappings);
        register("coffee_delight", CoffeeDelightCompat::registerFoodMappings);
        register("corn_delight", CornDelightCompat::registerFoodMappings);
        register("culturaldelights", CulturalDelightCompat::registerFoodMappings);
        register("crabbersdelight", CrabberDelightCompat::registerFoodMappings);
        register("delightful", DelightfulCompat::registerFoodMappings);
        register("endersdelight", EnderDelightCompat::registerFoodMappings);
        register("ends_delight", EndDelightCompat::registerFoodMappings);
        register("farmersrespite", FarmerRespiteCompat::registerFoodMappings);
        register("festive_delight", FestiveDelightCompat::registerFoodMappings);
        register("miners_delight", MinerDelightCompat::registerFoodMappings);
        register("nethersdelight", NetherDelightCompat::registerFoodMappings);
        register("oceansdelight", OceanDelightCompat::registerFoodMappings);
        register("pineapple_delight", PineappleDelightCompat::registerFoodMappings);
        register("seeddelight", SeedDelightCompat::registerFoodMappings);
    }

    public static void registerAddonFoodMappings(FoodMappingsManager manager) {
        //We cycle through each addon and check if it is loaded
        for (Map.Entry<String, Consumer<FoodMappingsManager>> entry : AddonConstants.SUPPORTED_ADDONS.entrySet()) {
            if (!ModList.get().isLoaded(entry.getKey())) {
                continue;
            }
            entry.getValue().accept(manager);
        }
    }

    private static void register(String modId, Consumer<FoodMappingsManager> function) {
        SUPPORTED_ADDONS.put(modId, function);
    }
}
