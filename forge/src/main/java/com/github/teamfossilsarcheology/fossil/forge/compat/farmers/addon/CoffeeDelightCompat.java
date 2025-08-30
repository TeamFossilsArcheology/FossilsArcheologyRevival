package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import club.someoneice.cofe_delight.init.ItemInit;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;

public class CoffeeDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemInit.COFFEE_BERRIES.get(), 5);
        manager.addPlant(ItemInit.COFFEE_FLOUR.get(), 15);
        manager.addPlant(ItemInit.COFFEE_BEANS.get());
        manager.addPlant(ItemInit.COFFEE_BEANS_COOKED.get());
        manager.addPlant(ItemInit.COFFEE_PIE_SIDE.get());
        manager.addPlant(ItemInit.COFFEE_PIE.get()); //Not a block!
        manager.addPlant(ItemInit.QUICHE.get());
        manager.addPlant(ItemInit.WAFER.get());
        manager.addPlant(ItemInit.BERRIES_WAFER.get());
        manager.addPlant(ItemInit.COFFEE_WAFER.get());
        //10/10 added
    }
}
