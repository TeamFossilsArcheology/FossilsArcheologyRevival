package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import net.foundwiz.largemeals.common.registry.ModBlocks;
import net.foundwiz.largemeals.common.registry.ModItems;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class LargeMealsCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addFish(ModItems.PUFFERFISH_BROTH.get());
        manager.addPlant(ModItems.RED_SOUP.get());
        manager.addPlant(ModItems.POTATO_SOUP.get());
        manager.addFish(ModItems.COD_DELUXE.get());
        manager.addFish(ModItems.COD_SURPRISE.get());
        manager.addMeat(ModItems.HEARTY_LUNCH.get()); //This contains meat so I count it as meat
        manager.addMeat(ModItems.OMURICE.get()); //Same as the hearty lunch
        manager.addMeat(ModItems.MUSHROOM_POT_PIE_SLICE.get()); //This pie has meat in it, so meat it is
        manager.addPlant(ModItems.SWEET_BERRY_CUSTARD.get());
        manager.addMeat(ModItems.MUSHROOM_POT_PIE.get(), getPieValue((PieBlock) ModBlocks.MUSHROOM_POT_PIE.get()));
        //11/11 added
    }
}
