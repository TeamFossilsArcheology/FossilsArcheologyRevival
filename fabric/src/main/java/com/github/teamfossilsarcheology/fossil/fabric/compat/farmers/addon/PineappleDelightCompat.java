package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import club.someoneice.pineapple_delight.BlockInit;
import club.someoneice.pineapple_delight.ItemInit;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat.getPieValue;

public class PineappleDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemInit.PINEAPPLE_ITEM);
        manager.addPlant(ItemInit.PINEAPPLE_FRIED_RICE);
        manager.addPlant(ItemInit.PINEAPPLE_PIE_SIDE);
        manager.addPlant(ItemInit.PINEAPPLE_SIDE);
        manager.addPlant(BlockInit.PINEAPPLE_PIE, getPieValue((PieBlock) BlockInit.PINEAPPLE_PIE));
        manager.addPlant(BlockInit.PINEAPPLE_CROP, 5);
        //6/6 added
    }
}
