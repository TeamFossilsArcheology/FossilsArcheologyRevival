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
        FoodMappings.addMeat(BCObjects.HORROR_LASAGNA); //contains meat
        FoodMappings.addMeat(BCObjects.QUICHE);
        FoodMappings.addMeat(BCObjects.QUICHE_SLICE); //can be made both without and with meat, so added twice
        FoodMappings.addPlant(BCObjects.QUICHE_SLICE);
        FoodMappings.addMeat(BCObjects.SCARLET_PIEROGIES);
        FoodMappings.addEgg(BCObjects.SCARLET_PIEROGIES); //also has eggs
        FoodMappings.addMeat(BCObjects.FIERY_FONDUE);
        FoodMappings.addPlant(BCObjects.CREAMY_ONION_SOUP);
        FoodMappings.addEgg(BCObjects.VEGETABLE_OMELET); //egg
        FoodMappings.addPlant(BCObjects.CHEESY_PASTA);
        //16/16 added
    }
}
