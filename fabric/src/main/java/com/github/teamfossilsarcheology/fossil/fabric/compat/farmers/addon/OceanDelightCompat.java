package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.scouter.oceansdelight.items.ODItems;

public class OceanDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addFish(ODItems.TENTACLES);
        manager.addFish(ODItems.CUT_TENTACLES);
        manager.addFish(ODItems.SQUID_RINGS);
        manager.addFish(ODItems.TENTACLE_ON_A_STICK);
        manager.addFish(ODItems.BAKED_TENTACLE_ON_A_STICK);
        manager.addFish(ODItems.BOWL_OF_GUARDIAN_SOUP);
        manager.addFish(ODItems.GUARDIAN_TAIL);
        manager.addFish(ODItems.COOKED_GUARDIAN_TAIL);
        manager.addFish(ODItems.ELDER_GUARDIAN_SLAB);
        manager.addFish(ODItems.ELDER_GUARDIAN_SLICE);
        manager.addFish(ODItems.COOKED_ELDER_GUARDIAN_SLICE);
        manager.addFish(ODItems.ELDER_GUARDIAN_ROLL);
        manager.addFish(ODItems.CABBAGE_WRAPPED_ELDER_GUARDIAN);
        manager.addFish(ODItems.FUGU_SLICE);
        manager.addFish(ODItems.FUGU_ROLL);
        manager.addPlant(ODItems.BRAISED_SEA_PICKLE);
        //16/16 added
    }
}
