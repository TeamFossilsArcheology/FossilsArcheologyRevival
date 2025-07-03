package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import dev.sterner.culturaldelights.common.registry.CDObjects;

import java.util.Objects;

public class CulturalDelightCompat {
    public static void registerCulturalDelightFoodMappings() {
        FoodMappings.addPlant(CDObjects.CUCUMBER_SEEDS, 5);
        FoodMappings.addPlant(CDObjects.CORN_KERNELS, 5);
        FoodMappings.addPlant(CDObjects.EGGPLANT_SEEDS, 5);
        FoodMappings.addPlant(CDObjects.AVOCADO);
        FoodMappings.addPlant(CDObjects.CUT_AVOCADO);
        FoodMappings.addPlant(CDObjects.CUCUMBER);
        FoodMappings.addPlant(CDObjects.PICKLE);
        FoodMappings.addPlant(CDObjects.CUT_CUCUMBER);
        FoodMappings.addPlant(CDObjects.CUT_PICKLE);
        FoodMappings.addPlant(CDObjects.EGGPLANT);
        FoodMappings.addPlant(CDObjects.CUT_EGGPLANT);
        FoodMappings.addPlant(CDObjects.SMOKED_EGGPLANT);
        FoodMappings.addPlant(CDObjects.SMOKED_TOMATO);
        FoodMappings.addPlant(CDObjects.SMOKED_CORN);
        FoodMappings.addPlant(CDObjects.SMOKED_CUT_EGGPLANT);
        FoodMappings.addPlant(CDObjects.SMOKED_WHITE_EGGPLANT);
        FoodMappings.addPlant(CDObjects.WHITE_EGGPLANT);
        FoodMappings.addPlant(CDObjects.CORN_COB);
        FoodMappings.addFish(CDObjects.SQUID);
        FoodMappings.addFish(CDObjects.COOKED_SQUID);
        FoodMappings.addFish(CDObjects.RAW_CALAMARI);
        FoodMappings.addFish(CDObjects.COOKED_CALAMARI);
        FoodMappings.addPlant(CDObjects.POPCORN);
        FoodMappings.addPlant(CDObjects.CORN_DOUGH);
        FoodMappings.addPlant(CDObjects.TORTILLA);
        FoodMappings.addPlant(CDObjects.TORTILLA_CHIPS);
        FoodMappings.addPlant(CDObjects.ELOTE);
        FoodMappings.addPlant(CDObjects.HEARTY_SALAD);
        FoodMappings.addMeat(CDObjects.BEEF_BURRITO);
        FoodMappings.addMeat(CDObjects.MUTTON_SANDWICH);
        FoodMappings.addPlant(CDObjects.FRIED_EGGPLANT_PASTA);
        FoodMappings.addPlant(CDObjects.EGGPLANT_BURGER); //This has no meat in it so I consider it a plant.
        FoodMappings.addPlant(CDObjects.CREAMED_CORN);
        FoodMappings.addPlant(CDObjects.CORN_COB);
        FoodMappings.addMeat(CDObjects.CHICKEN_TACO);
        FoodMappings.addMeat(CDObjects.SPICY_CURRY);
        FoodMappings.addMeat(CDObjects.PORK_WRAP);
        FoodMappings.addFish(CDObjects.FISH_TACO);
        FoodMappings.addPlant(CDObjects.MIDORI_ROLL);
        FoodMappings.addPlant(CDObjects.MIDORI_ROLL_SLICE);
        FoodMappings.addEgg(CDObjects.EGG_ROLL);
        FoodMappings.addMeat(CDObjects.CHICKEN_ROLL);
        FoodMappings.addMeat(CDObjects.CHICKEN_ROLL_SLICE);
        FoodMappings.addFish(CDObjects.PUFFERFISH_ROLL);
        FoodMappings.addFish(CDObjects.TROPICAL_ROLL);
        FoodMappings.addFish(CDObjects.RICE_BALL); //This has fish in it so I count it as fish
        FoodMappings.addFish(CDObjects.CALAMARI_ROLL);
        FoodMappings.addPlant(CDObjects.AVOCADO_LEAVES, 20);
        FoodMappings.addPlant(CDObjects.AVOCADO_SAPLING, 15);
        FoodMappings.addPlant(CDObjects.AVOCADO_TOAST);
        FoodMappings.addPlant(CDObjects.ITEMS.keySet().stream().filter(v -> Objects.equals(v.toString(), "avocado_pit")).toList().get(0), 5);
        //51/51 added! That is a lot of food!
    }
}
