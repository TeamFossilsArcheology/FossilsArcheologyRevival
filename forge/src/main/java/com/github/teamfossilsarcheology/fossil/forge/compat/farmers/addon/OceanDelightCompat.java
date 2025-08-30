package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.scouter.oceansdelight.items.ODItems;

public class OceanDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addFish(ODItems.TENTACLES.get());
        manager.addFish(ODItems.CUT_TENTACLES.get());
        manager.addFish(ODItems.SQUID_RINGS.get());
        manager.addFish(ODItems.TENTACLE_ON_A_STICK.get());
        manager.addFish(ODItems.BAKED_TENTACLE_ON_A_STICK.get());
        manager.addFish(ODItems.BOWL_OF_GUARDIAN_SOUP.get());
        manager.addFish(ODItems.GUARDIAN_TAIL.get());
        manager.addFish(ODItems.COOKED_GUARDIAN_TAIL.get());
        manager.addFish(ODItems.ELDER_GUARDIAN_SLAB.get());
        manager.addFish(ODItems.ELDER_GUARDIAN_SLICE.get());
        manager.addFish(ODItems.COOKED_ELDER_GUARDIAN_SLICE.get());
        manager.addFish(ODItems.ELDER_GUARDIAN_ROLL.get());
        manager.addFish(ODItems.CABBAGE_WRAPPED_ELDER_GUARDIAN.get());
        manager.addFish(ODItems.FUGU_SLICE.get());
        manager.addFish(ODItems.FUGU_ROLL.get());
        manager.addPlant(ODItems.BRAISED_SEA_PICKLE.get());
        //16/16 added
    }
}
