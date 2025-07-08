package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import net.mcreator.italiandelight.init.ItalianDelightModItems;

public class ItalianDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ItalianDelightModItems.PIZZA_MARGHERITA_SLICE.get()); //Normally I wouldn't count pizzas as they contain cheese, but this mod is basically mostly pizzas, so I will make an exception
        FoodMappings.addPlant(ItalianDelightModItems.PIZZA_DIAVOLA_SLICE.get());
        FoodMappings.addPlant(ItalianDelightModItems.GRAPE_SEEDS.get(), 5);
        FoodMappings.addPlant(ItalianDelightModItems.GRAPE_BUNCH.get());
        FoodMappings.addMeat(ItalianDelightModItems.SALAMI.get());
        FoodMappings.addMeat(ItalianDelightModItems.SALAMI_PIECES.get());
        FoodMappings.addMeat(ItalianDelightModItems.WINE_SALAMI.get());
        FoodMappings.addPlant(ItalianDelightModItems.MUSHROOM_PIZZA_SLICE.get());
        FoodMappings.addMeat(ItalianDelightModItems.PIZZA_PROSCIUTTO_SLICE.get()); //Ham Pizza Slice
        FoodMappings.addPlant(ItalianDelightModItems.MUSHROOM_RISOTTO.get());
        FoodMappings.addPlant(ItalianDelightModItems.RISOTTO_AL_SUGO.get());
        FoodMappings.addPlant(ItalianDelightModItems.DANTES_SPECIAL_SLICE.get());
        FoodMappings.addPlant(ItalianDelightModItems.BERRIEDDELIGHT.get());
        FoodMappings.addPlant(ItalianDelightModItems.PANDORO_DOUGH.get(), 8 * 7); //8 slices of one food point
        FoodMappings.addPlant(ItalianDelightModItems.PANDORO.get(), 3 * 8 * 7);
        FoodMappings.addPlant(ItalianDelightModItems.PANDORO_SLICE.get());
        FoodMappings.addPlant(ItalianDelightModItems.PASTA_AL_PESTO.get());
        FoodMappings.addPlant(ItalianDelightModItems.TOMATO_PASTA.get()); //This mod is making me especially hungry
        FoodMappings.addPlant(ItalianDelightModItems.PANETTONE.get(), 3 * 8 * 7);
        FoodMappings.addPlant(ItalianDelightModItems.PANETTONE_SLICE.get());
        FoodMappings.addPlant(ItalianDelightModItems.TIRAMISU.get());
        FoodMappings.addPlant(ItalianDelightModItems.MOZZARELLA.get());
        FoodMappings.addPlant(ItalianDelightModItems.MOZZARELLA_SALAD.get());
        FoodMappings.addPlant(ItalianDelightModItems.MOZZARELLA_PANINI.get());
        //24/24 added, wines/full pizza blocks excluded
    }
}
