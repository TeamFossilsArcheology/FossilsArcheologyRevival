package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import net.brnbrd.delightful.common.item.DelightfulItems;

public class BYGAndDelightfuLCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(DelightfulItems.SALMONBERRY_PIE_SLICE.get());
        manager.addPlant(DelightfulItems.PUMPKIN_PIE_SLICE.get());
        manager.addPlant(DelightfulItems.GLOOMGOURD_PIE_SLICE.get());
        manager.addPlant(DelightfulItems.BLUEBERRY_PIE_SLICE.get());
        manager.addPlant(DelightfulItems.GREEN_APPLE_PIE_SLICE.get());
        manager.addPlant(DelightfulItems.MULBERRY_PIE_SLICE.get());
        manager.addPlant(DelightfulItems.PASSION_FRUIT_TART_SLICE.get());
        manager.addPlant(DelightfulItems.CHORUS_PIE_SLICE.get());
        manager.addPlant(DelightfulItems.SOURCE_BERRY_PIE_SLICE.get());
        //8/8 added
    }
}
