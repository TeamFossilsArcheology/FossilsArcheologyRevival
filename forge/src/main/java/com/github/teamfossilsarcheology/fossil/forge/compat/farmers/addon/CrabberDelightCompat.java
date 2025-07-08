package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import alabaster.crabbersdelight.common.registry.ModItems;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;

public class CrabberDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addFish(ModItems.BISQUE.get()); //Dish made of fish+plants, so I considered it a fish
        FoodMappings.addFish(ModItems.CLAM_BAKE.get()); //Dish made of fish+plants, so I considered it a fish
        FoodMappings.addMeat(ModItems.CLAM_CHOWDER.get()); //Dish made of clam meat+plants, so I considered it a meat
        FoodMappings.addMeat(ModItems.COOKED_CLAM_MEAT.get()); //Clam meat so meat
        FoodMappings.addFish(ModItems.COOKED_CLAWSTER.get());
        FoodMappings.addFish(ModItems.COOKED_CRAB.get());
        FoodMappings.addFish(ModItems.COOKED_SHRIMP.get());
        FoodMappings.addFish(ModItems.COOKED_TROPICAL_FISH.get());
        FoodMappings.addFish(ModItems.COOKED_TROPICAL_FISH_SLICE.get());
        FoodMappings.addFish(ModItems.CRAB_CAKES.get()); //Has crab in it so I will count it as a fish
        FoodMappings.addFish(ModItems.CRAB_LEGS.get());
        FoodMappings.addFish(ModItems.FISH_STICK.get());
        FoodMappings.addPlant(ModItems.KELP_SHAKE.get(), 5); //This is a drink but it is made of kelp so plant
        FoodMappings.addMeat(ModItems.RAW_CLAM_MEAT.get());
        FoodMappings.addFish(ModItems.RAW_CLAWSTER.get());
        FoodMappings.addFish(ModItems.RAW_CRAB.get());
        FoodMappings.addFish(ModItems.RAW_SHRIMP.get());
        FoodMappings.addMeat(ModItems.SEAFOOD_GUMBO.get()); //Dish made of meat, fish+plants, so I considered it a meat
        FoodMappings.addFish(ModItems.SHRIMP_SKEWER.get());
        FoodMappings.addFish(ModItems.STUFFED_NAUTILUS_SHELL.get());
        FoodMappings.addMeat(ModItems.SURF_AND_TURF.get()); //Made of fish and meat, so meat
        FoodMappings.addFish(ModItems.TROPICAL_FISH_SLICE.get());
        //22/22 added
    }
}
