package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import net.brdle.delightful.common.block.DelightfulBlocks;
import net.brdle.delightful.common.item.DelightfulItems;
import potionstudios.byg.common.item.BYGItems;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class BYGAndDelightfuLCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(BYGItems.BLUEBERRY_PIE.get(), getPieValue((PieBlock) DelightfulBlocks.BLUEBERRY_PIE.get()));
        manager.addPlant(DelightfulItems.BLUEBERRY_PIE_SLICE.get());
        manager.addPlant(BYGItems.CRIMSON_BERRY_PIE.get(), getPieValue((PieBlock) DelightfulBlocks.CRIMSON_BERRY_PIE.get()));
        manager.addPlant(DelightfulItems.CRIMSON_BERRY_PIE_SLICE.get());
        manager.addPlant(BYGItems.GREEN_APPLE_PIE.get(), getPieValue((PieBlock) DelightfulBlocks.GREEN_APPLE_PIE.get()));
        manager.addPlant(DelightfulItems.GREEN_APPLE_PIE_SLICE.get());
        manager.addPlant(BYGItems.NIGHTSHADE_BERRY_PIE.get(), getPieValue((PieBlock) DelightfulBlocks.NIGHTSHADE_BERRY_PIE.get()));
        manager.addPlant(DelightfulItems.NIGHTSHADE_BERRY_PIE_SLICE.get());
        //8/8 added
    }
}
