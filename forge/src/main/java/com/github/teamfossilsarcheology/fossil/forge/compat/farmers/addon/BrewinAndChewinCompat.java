package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import umpaz.brewinandchewin.common.registry.BCItems;

public class BrewinAndChewinCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(BCItems.KIMCHI.get());
        manager.addMeat(BCItems.JERKY.get());
        manager.addPlant(BCItems.PICKLED_PICKLES.get());
        manager.addFish(BCItems.KIPPERS.get());
        manager.addPlant(BCItems.COCOA_FUDGE.get());
        manager.addPlant(BCItems.KIMCHI.get());
        manager.addMeat(BCItems.PIZZA_SLICE.get()); //Contains meat
        manager.addMeat(BCItems.HAM_AND_CHEESE_SANDWICH.get());
        //8/8 added
    }
}
