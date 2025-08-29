package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.ncpbails.alexsdelight.item.ModItems;

public class AlexDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addMeat(ModItems.RAW_BISON.get());
        manager.addMeat(ModItems.COOKED_BISON.get());
        manager.addMeat(ModItems.RAW_BUNFUNGUS.get());
        manager.addMeat(ModItems.COOKED_BUNFUNGUS.get());
        manager.addMeat(ModItems.COOKED_CENTIPEDE_LEG.get());
        manager.addMeat(ModItems.KANGAROO_SHANK.get());
        manager.addMeat(ModItems.COOKED_KANGAROO_SHANK.get());
        manager.addMeat(ModItems.LOOSE_MOOSE_RIB.get());
        manager.addMeat(ModItems.COOKED_LOOSE_MOOSE_RIB.get());
        manager.addMeat(ModItems.BISON_MINCE.get());
        manager.addMeat(ModItems.BISON_PATTY.get());
        manager.addMeat(ModItems.RAW_BUNFUNGUS_DRUMSTICK.get());
        manager.addMeat(ModItems.COOKED_BUNFUNGUS_DRUMSTICK.get());
        manager.addFish(ModItems.RAW_CATFISH_SLICE.get());
        manager.addFish(ModItems.COOKED_CATFISH_SLICE.get());
        manager.addPlant(ModItems.GONGYLIDIA_BRUSCHETTA.get());
        manager.addMeat(ModItems.MAGGOT_SALAD.get());
        manager.addMeat(ModItems.KANGAROO_STEW.get());
        manager.addPlant(ModItems.ACACIA_BLOSSOM_SOUP.get());
        manager.addFish(ModItems.LOBSTER_PASTA.get());
        manager.addMeat(ModItems.BISON_BURGER.get());
        manager.addMeat(ModItems.BUNFUNGUS_SANDWICH.get());
        manager.addMeat(ModItems.KANGAROO_PASTA.get());
    }
}
