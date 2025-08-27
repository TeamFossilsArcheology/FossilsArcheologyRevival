package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import club.someoneice.pineapple.init.BlockList;
import club.someoneice.pineapple.init.ItemList;
import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class PineappleDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ItemList.PINEAPPLE.get());
        FoodMappings.addPlant(ItemList.PINEAPPLE_FRIED_RICE.get());
        FoodMappings.addPlant(ItemList.PINEAPPLE_PIE_SIDE.get());
        FoodMappings.addPlant(ItemList.PINEAPPLE_SIDE.get());
        FoodMappings.addPlant(BlockList.PINEAPPLE_PIE_ITEM.get(), getPieValue((PieBlock) BlockList.PINEAPPLE_PIE.get()));
        FoodMappings.addPlant(BlockList.PINEAPPLE_CROP_ITEM.get(), 5);
        //6/6 added
    }
}
