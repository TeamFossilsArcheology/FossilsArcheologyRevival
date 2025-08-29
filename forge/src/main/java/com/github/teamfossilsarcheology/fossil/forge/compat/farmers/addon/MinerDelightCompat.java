package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.sammy.minersdelight.setup.MDItems;

public class MinerDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(MDItems.BAKED_CAVE_CARROT.get());
        manager.addFish(MDItems.BAKED_SQUID.get());
        manager.addFish(MDItems.BAKED_TENTACLES.get());
        manager.addMeat(MDItems.BAT_WING.get());
        manager.addPlant(MDItems.BEETROOT_SOUP_CUP.get());
        manager.addFish(MDItems.BOWL_OF_STUFFED_SQUID.get()); //Has squid so fish
        manager.addPlant(MDItems.CAVE_CARROT.get());
        manager.addPlant(MDItems.CAVE_SOUP.get());
        manager.addFish(MDItems.GLOW_SQUID.get());
        //manager.addPlant(MDItems.HOT_COCOA_CUP.get()); Excluded because the normal cocoa cup is excluded too
        manager.addMeat(MDItems.IMPROVISED_BARBECUE_STICK.get()); //Has bat wings so meat
        manager.addPlant(MDItems.MUSHROOM_STEW_CUP.get());
        manager.addPlant(MDItems.PASTA_WITH_VEGGIEBALLS.get());
        manager.addMeat(MDItems.RABBIT_STEW_CUP.get());
        manager.addEgg(MDItems.SILVERFISH_EGGS.get());
        manager.addMeat(MDItems.SMOKED_BAT_WING.get());
        manager.addFish(MDItems.SQUID.get());
        manager.addFish(MDItems.TENTACLES.get());
        manager.addFish(MDItems.WEIRD_CAVIAR.get());
        manager.addFish(MDItems.BAKED_COD_STEW_CUP.get());
        manager.addMeat(MDItems.NOODLE_SOUP_CUP.get()); //This has meat in it so it is a meat
        manager.addMeat(MDItems.BEEF_STEW_CUP.get());
        manager.addMeat(MDItems.CHICKEN_SOUP_CUP.get());
        manager.addFish(MDItems.FISH_STEW_CUP.get());
        manager.addMeat(MDItems.PUMPKIN_SOUP_CUP.get()); //This has meat in it so it is a meat
        manager.addPlant(MDItems.VEGETABLE_SOUP_CUP.get());
        //25/26, 1 excluded
    }
}
