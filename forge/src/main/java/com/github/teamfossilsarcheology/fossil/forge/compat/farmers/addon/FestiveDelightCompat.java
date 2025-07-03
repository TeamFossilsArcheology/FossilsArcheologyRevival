package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import net.mcreator.festivedelight.init.FestiveDelightModBlocks;
import net.mcreator.festivedelight.init.FestiveDelightModItems;

public class FestiveDelightCompat {
    public static void registerFestiveDelightFoodMappings() {
        FoodMappings.addMeat(FestiveDelightModItems.FESTIVE_CHIKEN.get());
        FoodMappings.addPlant(FestiveDelightModItems.CINNAMON_POWDER.get(), 10);
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_DOUGH.get(), 10);
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_MAN_DOUGH.get(), 10);
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_DOUGH_CREEPER.get(), 10);
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_DOUGH_STAR.get(), 10);
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_DOUGH_BLOCK.get(), 10);
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_BLOCK.get());
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_CREEPER.get());
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_COOKIE_STAR.get());
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_MAN.get());
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD_MAN_BASE.get());
        FoodMappings.addPlant(FestiveDelightModItems.GINGERBREAD.get());
        //FoodMappings.addPlant(FestiveDelightModItems.SUGAR_CANE.get()); Not suitable for dinos
        FoodMappings.addPlant(FestiveDelightModBlocks.CINNAMON_BUSH.get(), 15);
        //14/15 added, 1 excluded
    }
}
