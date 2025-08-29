package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import club.someoneice.cofe_delight.init.ItemInit;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;

public class CoffeeDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemInit.COFFEE_BERRIES, 5);
        manager.addPlant(ItemInit.COFFEE_FLOUR, 15);
        manager.addPlant(ItemInit.COFFEE_BEANS);
        manager.addPlant(ItemInit.COFFEE_BEANS_COOKED);
        manager.addPlant(ItemInit.COFFEE_PIE_SIDE);
        manager.addPlant(ItemInit.COFFEE_PIE); //Not a block!
        manager.addPlant(ItemInit.QUICHE);
        manager.addPlant(ItemInit.WAFER);
        manager.addPlant(ItemInit.BERRIES_WAFER);
        manager.addPlant(ItemInit.COFFEE_WAFER);
        //10/10 added
    }
}
