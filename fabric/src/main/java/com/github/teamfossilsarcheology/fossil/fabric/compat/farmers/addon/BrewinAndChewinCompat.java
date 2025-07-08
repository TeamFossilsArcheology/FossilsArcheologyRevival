package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import dev.sterner.brewinandchewin.common.registry.BCObjects;

public class BrewinAndChewinCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(BCObjects.KIMCHI);
        FoodMappings.addMeat(BCObjects.JERKY);
        FoodMappings.addPlant(BCObjects.PICKLED_PICKLES);
        FoodMappings.addFish(BCObjects.KIPPERS);
        FoodMappings.addPlant(BCObjects.COCOA_FUDGE);
        FoodMappings.addPlant(BCObjects.KIMCHI);
        FoodMappings.addMeat(BCObjects.PIZZA_SLICE); //Contains meat
        FoodMappings.addMeat(BCObjects.HAM_AND_CHEESE_SANDWICH);
        //8/8 added
    }
}
