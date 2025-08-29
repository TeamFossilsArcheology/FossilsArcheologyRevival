package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import dev.sterner.culturaldelights.common.registry.CDObjects;

import java.util.Objects;

public class CulturalDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(CDObjects.CUCUMBER_SEEDS, 5);
        manager.addPlant(CDObjects.CORN_KERNELS, 5);
        manager.addPlant(CDObjects.EGGPLANT_SEEDS, 5);
        manager.addPlant(CDObjects.AVOCADO);
        manager.addPlant(CDObjects.CUT_AVOCADO);
        manager.addPlant(CDObjects.CUCUMBER);
        manager.addPlant(CDObjects.PICKLE);
        manager.addPlant(CDObjects.CUT_CUCUMBER);
        manager.addPlant(CDObjects.CUT_PICKLE);
        manager.addPlant(CDObjects.EGGPLANT);
        manager.addPlant(CDObjects.CUT_EGGPLANT);
        manager.addPlant(CDObjects.SMOKED_EGGPLANT);
        manager.addPlant(CDObjects.SMOKED_TOMATO);
        manager.addPlant(CDObjects.SMOKED_CORN);
        manager.addPlant(CDObjects.SMOKED_CUT_EGGPLANT);
        manager.addPlant(CDObjects.SMOKED_WHITE_EGGPLANT);
        manager.addPlant(CDObjects.WHITE_EGGPLANT);
        manager.addPlant(CDObjects.CORN_COB);
        manager.addFish(CDObjects.SQUID);
        manager.addFish(CDObjects.COOKED_SQUID);
        manager.addFish(CDObjects.RAW_CALAMARI);
        manager.addFish(CDObjects.COOKED_CALAMARI);
        manager.addPlant(CDObjects.POPCORN);
        manager.addPlant(CDObjects.CORN_DOUGH);
        manager.addPlant(CDObjects.TORTILLA);
        manager.addPlant(CDObjects.TORTILLA_CHIPS);
        manager.addPlant(CDObjects.ELOTE);
        manager.addPlant(CDObjects.HEARTY_SALAD);
        manager.addMeat(CDObjects.BEEF_BURRITO);
        manager.addMeat(CDObjects.MUTTON_SANDWICH);
        manager.addPlant(CDObjects.FRIED_EGGPLANT_PASTA);
        manager.addPlant(CDObjects.EGGPLANT_BURGER); //This has no meat in it so I consider it a plant.
        manager.addPlant(CDObjects.CREAMED_CORN);
        manager.addPlant(CDObjects.CORN_COB);
        manager.addMeat(CDObjects.CHICKEN_TACO);
        manager.addMeat(CDObjects.SPICY_CURRY);
        manager.addMeat(CDObjects.PORK_WRAP);
        manager.addFish(CDObjects.FISH_TACO);
        manager.addPlant(CDObjects.MIDORI_ROLL);
        manager.addPlant(CDObjects.MIDORI_ROLL_SLICE);
        manager.addEgg(CDObjects.EGG_ROLL);
        manager.addMeat(CDObjects.CHICKEN_ROLL);
        manager.addMeat(CDObjects.CHICKEN_ROLL_SLICE);
        manager.addFish(CDObjects.PUFFERFISH_ROLL);
        manager.addFish(CDObjects.TROPICAL_ROLL);
        manager.addFish(CDObjects.RICE_BALL); //This has fish in it so I count it as fish
        manager.addFish(CDObjects.CALAMARI_ROLL);
        manager.addPlant(CDObjects.AVOCADO_LEAVES, 20);
        manager.addPlant(CDObjects.AVOCADO_SAPLING, 15);
        manager.addPlant(CDObjects.AVOCADO_TOAST);
        manager.addPlant(CDObjects.ITEMS.keySet().stream().filter(v -> Objects.equals(v.toString(), "avocado_pit")).toList().get(0), 5);
        //51/51 added! That is a lot of food!
    }
}
