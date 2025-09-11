package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import alabaster.crabbersdelight.common.registry.CDModItems;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;

public class CrabberDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addFish(CDModItems.BISQUE.get()); //Dish made of fish+plants, so I considered it a fish
        manager.addFish(CDModItems.CLAM_BAKE.get()); //Dish made of fish+plants, so I considered it a fish
        manager.addMeat(CDModItems.CLAM_CHOWDER.get()); //Dish made of clam meat+plants, so I considered it a meat
        manager.addMeat(CDModItems.COOKED_CLAM_MEAT.get()); //Clam meat so meat
        manager.addFish(CDModItems.COOKED_CLAWSTER.get());
        manager.addFish(CDModItems.COOKED_CRAB.get());
        manager.addFish(CDModItems.COOKED_SHRIMP.get());
        manager.addFish(CDModItems.COOKED_TROPICAL_FISH.get());
        manager.addFish(CDModItems.COOKED_TROPICAL_FISH_SLICE.get());
        manager.addFish(CDModItems.CRAB_CAKES.get()); //Has crab in it so I will count it as a fish
        manager.addFish(CDModItems.CRAB_LEGS.get());
        manager.addFish(CDModItems.FISH_STICK.get());
        manager.addPlant(CDModItems.KELP_SHAKE.get(), 5); //This is a drink but it is made of kelp so plant
        manager.addMeat(CDModItems.RAW_CLAM_MEAT.get());
        manager.addFish(CDModItems.RAW_CLAWSTER.get());
        manager.addFish(CDModItems.RAW_CRAB.get());
        manager.addFish(CDModItems.RAW_SHRIMP.get());
        manager.addMeat(CDModItems.SEAFOOD_GUMBO.get()); //Dish made of meat, fish+plants, so I considered it a meat
        manager.addFish(CDModItems.SHRIMP_SKEWER.get());
        manager.addFish(CDModItems.STUFFED_NAUTILUS_SHELL.get());
        manager.addMeat(CDModItems.SURF_AND_TURF.get()); //Made of fish and meat, so meat
        manager.addFish(CDModItems.TROPICAL_FISH_SLICE.get());
        //22/22 added
    }
}
