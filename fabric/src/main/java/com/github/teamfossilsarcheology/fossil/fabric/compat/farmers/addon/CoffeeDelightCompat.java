package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import club.someoneice.cofe_delight.init.ItemInit;
import com.github.teamfossilsarcheology.fossil.food.FoodMappings;

public class CoffeeDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ItemInit.COFFEE_BERRIES, 5);
        FoodMappings.addPlant(ItemInit.COFFEE_FLOUR, 15);
        FoodMappings.addPlant(ItemInit.COFFEE_BEANS);
        FoodMappings.addPlant(ItemInit.COFFEE_BEANS_COOKED);
        FoodMappings.addPlant(ItemInit.COFFEE_PIE_SIDE);
        FoodMappings.addPlant(ItemInit.COFFEE_PIE); //Not a block!
        FoodMappings.addPlant(ItemInit.QUICHE);
        FoodMappings.addPlant(ItemInit.WAFER);
        FoodMappings.addPlant(ItemInit.BERRIES_WAFER);
        FoodMappings.addPlant(ItemInit.COFFEE_WAFER);
        //10/10 added
    }
}
