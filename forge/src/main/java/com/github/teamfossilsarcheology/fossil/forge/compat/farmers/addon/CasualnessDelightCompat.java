package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.va11halla.casualness_delight.registry.ItemRegistry;

public class CasualnessDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addFish(ItemRegistry.FishAndChips.get());
        FoodMappings.addPlant(ItemRegistry.YorkshirePudding.get());
        FoodMappings.addMeat(ItemRegistry.BeefNoodles.get());
        FoodMappings.addMeat(ItemRegistry.QuicheLorraineSlice.get());
        //FoodMappings.addMeat(ItemRegistry.StargazyPie.get()); excluded because it gives you nausea
        FoodMappings.addFish(ItemRegistry.BowlOfPaperWrappedFish.get());
        FoodMappings.addMeat(ItemRegistry.BoboChicken.get());
        FoodMappings.addMeat(ItemRegistry.PhantomDumplings.get());
        //2 cheese wheels and cheese wheel slice excluded because they are made of milk, so they don't fit into any food category
        //FoodMappings.addMeat(ItemRegistry.PhantomPuff.get()); Phantom membrane is not edible, and this is just phantom membrane+cheese+milk
        //FoodMappings.addMeat(ItemRegistry.SpicyStrips.get()); Excluded because it gives rotten effect
        //FoodMappings.addMeat(ItemRegistry.GreenTongue.get()); Popsicle, so a no-go
        FoodMappings.addPlant(ItemRegistry.RawGluten.get());
        FoodMappings.addPlant(ItemRegistry.Gluten.get());
        FoodMappings.addPlant(ItemRegistry.GlutenSkewer.get());
        FoodMappings.addPlant(ItemRegistry.RoastGluten.get());
        FoodMappings.addMeat(ItemRegistry.RawDonkeyMeat.get());
        FoodMappings.addMeat(ItemRegistry.CookedDonkeyMeat.get());
        FoodMappings.addMeat(ItemRegistry.DonkeyBurger.get());
        FoodMappings.addPlant(ItemRegistry.RawPotatoBoboChicken.get(), 4 * 7); //This is just a potato+a stick
        FoodMappings.addPlant(ItemRegistry.RawCabbageBoboChicken.get(), 4 * 7); //This is just a cabbage+a stick
        FoodMappings.addMeat(ItemRegistry.RawChickenBoboChicken.get(), 4 * 7);
        FoodMappings.addPlant(ItemRegistry.PotatoBoboChicken.get());
        FoodMappings.addPlant(ItemRegistry.CabbageBoboChicken.get());
        FoodMappings.addMeat(ItemRegistry.RawChickenBoboChicken.get(), 4 * 7);
        FoodMappings.addPlant(ItemRegistry.PotatoSlice.get(), 4 * 7);
        FoodMappings.addPlant(ItemRegistry.PotatoChip.get());
        FoodMappings.addMeat(ItemRegistry.RawSpringRoll.get(), 4 * 7);
        FoodMappings.addMeat(ItemRegistry.SpringRoll.get());
        FoodMappings.addMeat(ItemRegistry.SpringRollMedley.get(), 6 * 6 * 7); //Made of 6 spring rolls
        FoodMappings.addMeat(ItemRegistry.FriedChickenChip.get());
        FoodMappings.addFish(ItemRegistry.FriedFish.get());
        FoodMappings.addMeat(ItemRegistry.Tonkatsu.get());
        FoodMappings.addMeat(ItemRegistry.RawFriedDumpling.get());
        FoodMappings.addMeat(ItemRegistry.FriedDumpling.get());
        FoodMappings.addMeat(ItemRegistry.BowlOfFriedDumpling.get());
        FoodMappings.addPlant(ItemRegistry.BowlOfSweetRice.get());
        //32/39, 7 excluded
    }
}
