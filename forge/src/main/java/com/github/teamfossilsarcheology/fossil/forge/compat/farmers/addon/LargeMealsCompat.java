package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import net.foundwiz.largemeals.common.registry.ModBlocks;
import net.foundwiz.largemeals.common.registry.ModItems;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class LargeMealsCompat {
    public static void registerLargeMealsFoodMappings() {
        FoodMappings.addFish(ModItems.PUFFERFISH_BROTH.get());
        FoodMappings.addPlant(ModItems.RED_SOUP.get());
        FoodMappings.addPlant(ModItems.POTATO_SOUP.get());
        FoodMappings.addFish(ModItems.COD_DELUXE.get());
        FoodMappings.addFish(ModItems.COD_SURPRISE.get());
        FoodMappings.addMeat(ModItems.HEARTY_LUNCH.get()); //This contains meat so I count it as meat
        FoodMappings.addMeat(ModItems.OMURICE.get()); //Same as the hearty lunch
        FoodMappings.addMeat(ModItems.MUSHROOM_POT_PIE_SLICE.get()); //This pie has meat in it, so meat it is
        FoodMappings.addPlant(ModItems.SWEET_BERRY_CUSTARD.get());
        FoodMappings.addMeat(ModItems.MUSHROOM_POT_PIE.get(), getPieValue((PieBlock) ModBlocks.MUSHROOM_POT_PIE.get()));
        //11/11 added
    }
}
