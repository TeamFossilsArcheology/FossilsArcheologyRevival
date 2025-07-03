package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import club.someoneice.cofe_delight.init.ItemInit;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;

public class CoffeeDelightCompat {
    public static void registerCoffeeDelightFoodMappings() {
        FoodMappings.addPlant(ItemInit.COFFEE_BERRIES.get(), 5);
        FoodMappings.addPlant(ItemInit.COFFEE_FLOUR.get(), 15);
        FoodMappings.addPlant(ItemInit.COFFEE_BEANS.get());
        FoodMappings.addPlant(ItemInit.COFFEE_BEANS_COOKED.get());
        FoodMappings.addPlant(ItemInit.COFFEE_PIE_SIDE.get());
        FoodMappings.addPlant(ItemInit.COFFEE_PIE.get()); //Not a block!
        FoodMappings.addPlant(ItemInit.QUICHE.get());
        FoodMappings.addPlant(ItemInit.WAFER.get());
        FoodMappings.addPlant(ItemInit.BERRIES_WAFER.get());
        FoodMappings.addPlant(ItemInit.COFFEE_WAFER.get());
        //10/10 added
    }
}
