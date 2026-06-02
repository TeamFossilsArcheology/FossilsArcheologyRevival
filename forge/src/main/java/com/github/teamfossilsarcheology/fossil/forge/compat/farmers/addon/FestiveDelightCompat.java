package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import net.toopa.festivedelight.init.FestiveDelightModBlocks;
import net.toopa.festivedelight.init.FestiveDelightModItems;

public class FestiveDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addMeat(FestiveDelightModItems.FESTIVE_CHIKEN.get());
        manager.addPlant(FestiveDelightModItems.CINNAMON_POWDER.get(), 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_DOUGH.get(), 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_MAN_DOUGH.get(), 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_DOUGH_CREEPER.get(), 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_CREEPER.get());
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_MAN.get());
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_MAN_BASE.get());
        //manager.addPlant(FestiveDelightModItems.SUGAR_CANE.get()); Not suitable for dinos
        manager.addPlant(FestiveDelightModBlocks.CINNAMON_BUSH.get(), 15);
        //14/15 added, 1 excluded
    }
}
