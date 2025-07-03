package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.spiteful_raccoon.honeyexpansion.init.BlockInit;
import com.spiteful_raccoon.honeyexpansion.init.ItemInit;

public class HoneyExpansionCompat {
    public static void registerHoneyExpansionFoodMappings(){
        FoodMappings.addPlant(ItemInit.HONEY_BERRIES.get());
        FoodMappings.addPlant(ItemInit.SLICE_OF_HONEY_CAKE.get());
        FoodMappings.addPlant(ItemInit.HONEY_COOKIE_SAUSAGE.get());
        FoodMappings.addPlant(ItemInit.PANCAKE.get());
        FoodMappings.addPlant(ItemInit.HONEY_PANCAKE.get());
        FoodMappings.addPlant(BlockInit.HONEY_CAKE.get(),7*7*2);//7 slices by 2 food points. the other 7 is the magic number in FoodMappings#addPlant(Item)
        //6/6 added
    }
}
