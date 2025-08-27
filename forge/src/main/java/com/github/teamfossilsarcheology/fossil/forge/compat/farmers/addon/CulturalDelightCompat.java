package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import com.ncpbails.culturaldelights.block.ModBlocks;
import com.ncpbails.culturaldelights.item.ModItems;

import java.util.Objects;

public class CulturalDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ModItems.CUCUMBER_SEEDS.get(), 5);
        FoodMappings.addPlant(ModItems.CORN_KERNELS.get(), 5);
        FoodMappings.addPlant(ModItems.EGGPLANT_SEEDS.get(), 5);
        FoodMappings.addPlant(ModItems.AVOCADO.get());
        FoodMappings.addPlant(ModItems.CUT_AVOCADO.get());
        FoodMappings.addPlant(ModItems.CUCUMBER.get());
        FoodMappings.addPlant(ModItems.PICKLE.get());
        FoodMappings.addPlant(ModItems.CUT_CUCUMBER.get());
        FoodMappings.addPlant(ModItems.CUT_PICKLE.get());
        FoodMappings.addPlant(ModItems.EGGPLANT.get());
        FoodMappings.addPlant(ModItems.CUT_EGGPLANT.get());
        FoodMappings.addPlant(ModItems.SMOKED_EGGPLANT.get());
        FoodMappings.addPlant(ModItems.SMOKED_TOMATO.get());
        FoodMappings.addPlant(ModItems.SMOKED_CORN.get());
        FoodMappings.addPlant(ModItems.SMOKED_CUT_EGGPLANT.get());
        FoodMappings.addPlant(ModItems.SMOKED_WHITE_EGGPLANT.get());
        FoodMappings.addPlant(ModItems.WHITE_EGGPLANT.get());
        FoodMappings.addPlant(ModItems.CORN_COB.get());
        FoodMappings.addFish(ModItems.SQUID.get());
        FoodMappings.addFish(ModItems.COOKED_SQUID.get());
        FoodMappings.addFish(ModItems.RAW_CALAMARI.get());
        FoodMappings.addFish(ModItems.COOKED_CALAMARI.get());
        FoodMappings.addPlant(ModItems.POPCORN.get());
        FoodMappings.addPlant(ModItems.CORN_DOUGH.get());
        FoodMappings.addPlant(ModItems.TORTILLA.get());
        FoodMappings.addPlant(ModItems.TORTILLA_CHIPS.get());
        FoodMappings.addPlant(ModItems.ELOTE.get());
        FoodMappings.addPlant(ModItems.HEARTY_SALAD.get());
        FoodMappings.addMeat(ModItems.BEEF_BURRITO.get());
        FoodMappings.addMeat(ModItems.MUTTON_SANDWICH.get());
        FoodMappings.addPlant(ModItems.FRIED_EGGPLANT_PASTA.get());
        FoodMappings.addPlant(ModItems.EGGPLANT_BURGER.get()); //This has no meat in it so I consider it a plant.
        FoodMappings.addPlant(ModItems.CREAMED_CORN.get());
        FoodMappings.addPlant(ModItems.CORN_COB.get());
        FoodMappings.addMeat(ModItems.CHICKEN_TACO.get());
        FoodMappings.addMeat(ModItems.SPICY_CURRY.get());
        FoodMappings.addMeat(ModItems.PORK_WRAP.get());
        FoodMappings.addFish(ModItems.FISH_TACO.get());
        FoodMappings.addPlant(ModItems.MIDORI_ROLL.get());
        FoodMappings.addPlant(ModItems.MIDORI_ROLL_SLICE.get());
        FoodMappings.addEgg(ModItems.EGG_ROLL.get());
        FoodMappings.addMeat(ModItems.CHICKEN_ROLL.get());
        FoodMappings.addMeat(ModItems.CHICKEN_ROLL_SLICE.get());
        FoodMappings.addFish(ModItems.PUFFERFISH_ROLL.get());
        FoodMappings.addFish(ModItems.TROPICAL_ROLL.get());
        FoodMappings.addFish(ModItems.RICE_BALL.get()); //This has fish in it so I count it as fish
        FoodMappings.addFish(ModItems.CALAMARI_ROLL.get());
        FoodMappings.addPlant(ModBlocks.AVOCADO_LEAVES.get(), 20);
        FoodMappings.addPlant(ModBlocks.AVOCADO_SAPLING.get(), 15);
        FoodMappings.addPlant(ModItems.AVOCADO_TOAST.get());
        FoodMappings.addPlant(ModItems.ITEMS.getEntries().stream().filter(v -> Objects.equals(v.get().toString(), "avocado_pit")).toList().get(0).get(), 5);
        //51/51 added! That is a lot of food!
    }
}
