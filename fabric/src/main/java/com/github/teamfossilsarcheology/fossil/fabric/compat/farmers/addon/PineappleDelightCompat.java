package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import club.someoneice.pineapple_delight.BlockInit;
import club.someoneice.pineapple_delight.ItemInit;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.nhoryzon.mc.farmersdelight.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat.getPieValue;

public class PineappleDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ItemInit.PINEAPPLE_ITEM);
        FoodMappings.addPlant(ItemInit.PINEAPPLE_FRIED_RICE);
        FoodMappings.addPlant(ItemInit.PINEAPPLE_PIE_SIDE);
        FoodMappings.addPlant(ItemInit.PINEAPPLE_SIDE);
        FoodMappings.addPlant(BlockInit.PINEAPPLE_PIE, getPieValue((PieBlock) BlockInit.PINEAPPLE_PIE));
        FoodMappings.addPlant(BlockInit.PINEAPPLE_CROP, 5);
        //6/6 added
    }
}
