package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import umpaz.brewinandchewin.common.registry.BnCItems;

public class BrewinAndChewinCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(BnCItems.KIMCHI.get());
        manager.addMeat(BnCItems.JERKY.get());
        manager.addPlant(BnCItems.PICKLED_PICKLES.get());
        manager.addFish(BnCItems.KIPPERS.get());
        manager.addPlant(BnCItems.COCOA_FUDGE.get());
        manager.addPlant(BnCItems.KIMCHI.get());
        manager.addMeat(BnCItems.PIZZA_SLICE.get()); //Contains meat
        manager.addMeat(BnCItems.HAM_AND_CHEESE_SANDWICH.get());
        //8/8 added
    }
}
