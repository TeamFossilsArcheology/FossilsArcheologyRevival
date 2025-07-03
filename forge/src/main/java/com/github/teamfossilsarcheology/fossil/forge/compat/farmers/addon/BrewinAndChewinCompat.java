package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.brewinandchewin.core.registry.BCItems;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;

public class BrewinAndChewinCompat {
    public static void registerBrewinAndChewinFoodMappings() {
        FoodMappings.addPlant(BCItems.KIMCHI.get());
        FoodMappings.addMeat(BCItems.JERKY.get());
        FoodMappings.addPlant(BCItems.PICKLED_PICKLES.get());
        FoodMappings.addFish(BCItems.KIPPERS.get());
        FoodMappings.addPlant(BCItems.COCOA_FUDGE.get());
        FoodMappings.addPlant(BCItems.KIMCHI.get());
        FoodMappings.addMeat(BCItems.PIZZA_SLICE.get()); //Contains meat
        FoodMappings.addMeat(BCItems.HAM_AND_CHEESE_SANDWICH.get());
        //8/8 added
    }
}
