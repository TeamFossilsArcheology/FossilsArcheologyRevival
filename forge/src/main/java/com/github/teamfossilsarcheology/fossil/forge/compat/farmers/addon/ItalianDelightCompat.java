package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import net.mcreator.italiandelight.init.ItalianDelightModItems;

public class ItalianDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItalianDelightModItems.PIZZA_MARGHERITA_SLICE.get()); //Normally I wouldn't count pizzas as they contain cheese, but this mod is basically mostly pizzas, so I will make an exception
        manager.addPlant(ItalianDelightModItems.PIZZA_DIAVOLA_SLICE.get());
        manager.addPlant(ItalianDelightModItems.GRAPE_SEEDS.get(), 5);
        manager.addPlant(ItalianDelightModItems.GRAPE_BUNCH.get());
        manager.addMeat(ItalianDelightModItems.SALAMI.get());
        manager.addMeat(ItalianDelightModItems.SALAMI_PIECES.get());
        manager.addMeat(ItalianDelightModItems.WINE_SALAMI.get());
        manager.addPlant(ItalianDelightModItems.MUSHROOM_PIZZA_SLICE.get());
        manager.addMeat(ItalianDelightModItems.PIZZA_PROSCIUTTO_SLICE.get()); //Ham Pizza Slice
        manager.addPlant(ItalianDelightModItems.MUSHROOM_RISOTTO.get());
        manager.addPlant(ItalianDelightModItems.RISOTTO_AL_SUGO.get());
        manager.addPlant(ItalianDelightModItems.DANTES_SPECIAL_SLICE.get());
        manager.addPlant(ItalianDelightModItems.BERRIEDDELIGHT.get());
        manager.addPlant(ItalianDelightModItems.PANDORO_DOUGH.get(), 8 * 7); //8 slices of one food point
        manager.addPlant(ItalianDelightModItems.PANDORO.get(), 3 * 8 * 7);
        manager.addPlant(ItalianDelightModItems.PANDORO_SLICE.get());
        manager.addPlant(ItalianDelightModItems.PASTA_AL_PESTO.get());
        manager.addPlant(ItalianDelightModItems.TOMATO_PASTA.get()); //This mod is making me especially hungry
        manager.addPlant(ItalianDelightModItems.PANETTONE.get(), 3 * 8 * 7);
        manager.addPlant(ItalianDelightModItems.PANETTONE_SLICE.get());
        manager.addPlant(ItalianDelightModItems.TIRAMISU.get());
        manager.addPlant(ItalianDelightModItems.MOZZARELLA.get());
        manager.addPlant(ItalianDelightModItems.MOZZARELLA_SALAD.get());
        manager.addPlant(ItalianDelightModItems.MOZZARELLA_PANINI.get());
        //24/24 added, wines/full pizza blocks excluded
    }
}
