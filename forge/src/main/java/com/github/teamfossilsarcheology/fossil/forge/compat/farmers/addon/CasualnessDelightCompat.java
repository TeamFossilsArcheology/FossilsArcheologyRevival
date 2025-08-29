package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.va11halla.casualness_delight.registry.ItemRegistry;

public class CasualnessDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addFish(ItemRegistry.FishAndChips.get());
        manager.addPlant(ItemRegistry.YorkshirePudding.get());
        manager.addMeat(ItemRegistry.BeefNoodles.get());
        manager.addMeat(ItemRegistry.QuicheLorraineSlice.get());
        //manager.addMeat(ItemRegistry.StargazyPie.get()); excluded because it gives you nausea
        manager.addFish(ItemRegistry.BowlOfPaperWrappedFish.get());
        manager.addMeat(ItemRegistry.BoboChicken.get());
        manager.addMeat(ItemRegistry.PhantomDumplings.get());
        //2 cheese wheels and cheese wheel slice excluded because they are made of milk, so they don't fit into any food category
        //manager.addMeat(ItemRegistry.PhantomPuff.get()); Phantom membrane is not edible, and this is just phantom membrane+cheese+milk
        //manager.addMeat(ItemRegistry.SpicyStrips.get()); Excluded because it gives rotten effect
        //manager.addMeat(ItemRegistry.GreenTongue.get()); Popsicle, so a no-go
        manager.addPlant(ItemRegistry.RawGluten.get());
        manager.addPlant(ItemRegistry.Gluten.get());
        manager.addPlant(ItemRegistry.GlutenSkewer.get());
        manager.addPlant(ItemRegistry.RoastGluten.get());
        manager.addMeat(ItemRegistry.RawDonkeyMeat.get());
        manager.addMeat(ItemRegistry.CookedDonkeyMeat.get());
        manager.addMeat(ItemRegistry.DonkeyBurger.get());
        manager.addPlant(ItemRegistry.RawPotatoBoboChicken.get(), 4 * 7); //This is just a potato+a stick
        manager.addPlant(ItemRegistry.RawCabbageBoboChicken.get(), 4 * 7); //This is just a cabbage+a stick
        manager.addMeat(ItemRegistry.RawChickenBoboChicken.get(), 4 * 7);
        manager.addPlant(ItemRegistry.PotatoBoboChicken.get());
        manager.addPlant(ItemRegistry.CabbageBoboChicken.get());
        manager.addMeat(ItemRegistry.RawChickenBoboChicken.get(), 4 * 7);
        manager.addPlant(ItemRegistry.PotatoSlice.get(), 4 * 7);
        manager.addPlant(ItemRegistry.PotatoChip.get());
        manager.addMeat(ItemRegistry.RawSpringRoll.get(), 4 * 7);
        manager.addMeat(ItemRegistry.SpringRoll.get());
        manager.addMeat(ItemRegistry.SpringRollMedley.get(), 6 * 6 * 7); //Made of 6 spring rolls
        manager.addMeat(ItemRegistry.FriedChickenChip.get());
        manager.addFish(ItemRegistry.FriedFish.get());
        manager.addMeat(ItemRegistry.Tonkatsu.get());
        manager.addMeat(ItemRegistry.RawFriedDumpling.get());
        manager.addMeat(ItemRegistry.FriedDumpling.get());
        manager.addMeat(ItemRegistry.BowlOfFriedDumpling.get());
        manager.addPlant(ItemRegistry.BowlOfSweetRice.get());
        //32/39, 7 excluded
    }
}
