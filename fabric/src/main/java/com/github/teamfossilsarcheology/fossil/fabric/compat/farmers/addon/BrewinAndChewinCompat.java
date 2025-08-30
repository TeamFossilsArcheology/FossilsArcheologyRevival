package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import dev.sterner.brewinandchewin.common.registry.BCObjects;

public class BrewinAndChewinCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(BCObjects.KIMCHI);
        manager.addMeat(BCObjects.JERKY);
        manager.addPlant(BCObjects.PICKLED_PICKLES);
        manager.addFish(BCObjects.KIPPERS);
        manager.addPlant(BCObjects.COCOA_FUDGE);
        manager.addPlant(BCObjects.KIMCHI);
        manager.addMeat(BCObjects.PIZZA_SLICE); //Contains meat
        manager.addMeat(BCObjects.HAM_AND_CHEESE_SANDWICH);
        //8/8 added
    }
}
