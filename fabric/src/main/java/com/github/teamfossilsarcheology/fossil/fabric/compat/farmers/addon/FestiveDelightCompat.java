package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import net.mcreator.festivedelight.init.FestiveDelightModBlocks;
import net.mcreator.festivedelight.init.FestiveDelightModItems;

public class FestiveDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addMeat(FestiveDelightModItems.FESTIVE_CHIKEN);
        manager.addPlant(FestiveDelightModItems.CINNAMON_POWDER, 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_DOUGH, 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_MAN_DOUGH, 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_DOUGH_CREEPER, 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_DOUGH_STAR, 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_DOUGH_BLOCK, 10);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_BLOCK);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_CREEPER);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_STAR);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_MAN);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD_MAN_BASE);
        manager.addPlant(FestiveDelightModItems.GINGERBREAD);
        //manager.addPlant(FestiveDelightModItems.SUGAR_CANE); Not suitable for dinos
        manager.addPlant(FestiveDelightModBlocks.CINNAMON_BUSH, 15);
        //14/15 added, 1 excluded
    }
}
