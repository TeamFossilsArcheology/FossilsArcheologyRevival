package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.scouter.oceansdelight.items.ODItems;

public class OceanDelightCompat {
    public static void registerOceanDelightFoodMappings() {
        FoodMappings.addFish(ODItems.TENTACLES.get());
        FoodMappings.addFish(ODItems.CUT_TENTACLES.get());
        FoodMappings.addFish(ODItems.SQUID_RINGS.get());
        FoodMappings.addFish(ODItems.TENTACLE_ON_A_STICK.get());
        FoodMappings.addFish(ODItems.BAKED_TENTACLE_ON_A_STICK.get());
        FoodMappings.addFish(ODItems.BOWL_OF_GUARDIAN_SOUP.get());
        FoodMappings.addFish(ODItems.GUARDIAN_TAIL.get());
        FoodMappings.addFish(ODItems.COOKED_GUARDIAN_TAIL.get());
        FoodMappings.addFish(ODItems.ELDER_GUARDIAN_SLAB.get());
        FoodMappings.addFish(ODItems.ELDER_GUARDIAN_SLICE.get());
        FoodMappings.addFish(ODItems.COOKED_ELDER_GUARDIAN_SLICE.get());
        FoodMappings.addFish(ODItems.ELDER_GUARDIAN_ROLL.get());
        FoodMappings.addFish(ODItems.CABBAGE_WRAPPED_ELDER_GUARDIAN.get());
        FoodMappings.addFish(ODItems.FUGU_SLICE.get());
        FoodMappings.addFish(ODItems.FUGU_ROLL.get());
        FoodMappings.addPlant(ODItems.BRAISED_SEA_PICKLE.get());
        //16/16 added
    }
}
