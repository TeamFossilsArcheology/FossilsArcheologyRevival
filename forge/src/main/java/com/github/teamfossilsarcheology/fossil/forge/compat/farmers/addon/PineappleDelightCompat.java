package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import club.someoneice.pineapple.init.BlockList;
import club.someoneice.pineapple.init.ItemList;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class PineappleDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemList.PINEAPPLE.get());
        manager.addPlant(ItemList.PINEAPPLE_FRIED_RICE.get());
        manager.addPlant(ItemList.PINEAPPLE_PIE_SIDE.get());
        manager.addPlant(ItemList.PINEAPPLE_SIDE.get());
        manager.addPlant(BlockList.PINEAPPLE_PIE_ITEM.get(), getPieValue((PieBlock) BlockList.PINEAPPLE_PIE.get()));
        manager.addPlant(BlockList.PINEAPPLE_CROP_ITEM.get(), 5);
        //6/6 added
    }
}
