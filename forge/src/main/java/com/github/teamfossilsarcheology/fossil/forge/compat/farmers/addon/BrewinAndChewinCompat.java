package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import umpaz.brewinandchewin.common.registry.BnCItems;

public class BrewinAndChewinCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(BnCItems.KIMCHI.get());
        FoodMappings.addMeat(BnCItems.JERKY.get());
        FoodMappings.addPlant(BnCItems.PICKLED_PICKLES.get());
        FoodMappings.addFish(BnCItems.KIPPERS.get());
        FoodMappings.addPlant(BnCItems.COCOA_FUDGE.get());
        FoodMappings.addPlant(BnCItems.KIMCHI.get());
        FoodMappings.addMeat(BnCItems.PIZZA_SLICE.get()); //Contains meat
        FoodMappings.addMeat(BnCItems.HAM_AND_CHEESE_SANDWICH.get());
        //8/8 added
    }
}
