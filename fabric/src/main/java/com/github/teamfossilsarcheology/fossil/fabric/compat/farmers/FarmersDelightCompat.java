package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.nhoryzon.mc.farmersdelight.block.PieBlock;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;

public class FarmersDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ItemsRegistry.CABBAGE.get());
        FoodMappings.addPlant(ItemsRegistry.TOMATO.get());
        FoodMappings.addPlant(ItemsRegistry.ONION.get());
        FoodMappings.addPlant(ItemsRegistry.TOMATO_SAUCE.get());
        FoodMappings.addPlant(ItemsRegistry.WHEAT_DOUGH.get());
        FoodMappings.addPlant(ItemsRegistry.RAW_PASTA.get());
        FoodMappings.addPlant(ItemsRegistry.PUMPKIN_SLICE.get());
        FoodMappings.addPlant(ItemsRegistry.CABBAGE_LEAF.get());
        FoodMappings.addPlant(ItemsRegistry.PIE_CRUST.get());
        FoodMappings.addPlant(ItemsRegistry.APPLE_PIE.get(), getPieValue((PieBlock) BlocksRegistry.APPLE_PIE.get()));
        FoodMappings.addPlant(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get(), getPieValue((PieBlock) BlocksRegistry.SWEET_BERRY_CHEESECAKE.get()));
        FoodMappings.addPlant(ItemsRegistry.CHOCOLATE_PIE.get(), getPieValue((PieBlock) BlocksRegistry.CHOCOLATE_PIE.get()));
        FoodMappings.addPlant(ItemsRegistry.APPLE_PIE_SLICE.get());
        FoodMappings.addPlant(ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get());
        FoodMappings.addPlant(ItemsRegistry.CHOCOLATE_PIE_SLICE.get());
        FoodMappings.addPlant(ItemsRegistry.SWEET_BERRY_COOKIE.get());
        FoodMappings.addPlant(ItemsRegistry.HONEY_COOKIE.get());
        FoodMappings.addPlant(ItemsRegistry.MELON_POPSICLE.get());
        FoodMappings.addPlant(ItemsRegistry.GLOW_BERRY_CUSTARD.get());
        FoodMappings.addPlant(ItemsRegistry.FRUIT_SALAD.get());
        FoodMappings.addPlant(ItemsRegistry.MIXED_SALAD.get());
        FoodMappings.addPlant(ItemsRegistry.NETHER_SALAD.get());
        FoodMappings.addPlant(ItemsRegistry.KELP_ROLL.get());
        FoodMappings.addPlant(ItemsRegistry.KELP_ROLL_SLICE.get());
        FoodMappings.addPlant(ItemsRegistry.COOKED_RICE.get());
        FoodMappings.addPlant(ItemsRegistry.VEGETABLE_SOUP.get());
        FoodMappings.addPlant(ItemsRegistry.FRIED_RICE.get());
        FoodMappings.addPlant(ItemsRegistry.PUMPKIN_SOUP.get());
        FoodMappings.addPlant(ItemsRegistry.MUSHROOM_RICE.get());
        FoodMappings.addPlant(ItemsRegistry.VEGETABLE_NOODLES.get());
        FoodMappings.addPlant(ItemsRegistry.VEGETABLE_NOODLES.get());
        FoodMappings.addPlant(ItemsRegistry.STUFFED_PUMPKIN.get());
        FoodMappings.addPlant(ItemsRegistry.ROAST_CHICKEN.get());
        FoodMappings.addPlant(ItemsRegistry.ROAST_CHICKEN.get());

        FoodMappings.addEgg(ItemsRegistry.FRIED_EGG.get());
        FoodMappings.addEgg(ItemsRegistry.EGG_SANDWICH.get());


        FoodMappings.addMeat(ItemsRegistry.MINCED_BEEF.get());
        FoodMappings.addMeat(ItemsRegistry.BEEF_PATTY.get());
        FoodMappings.addMeat(ItemsRegistry.CHICKEN_CUTS.get());
        FoodMappings.addMeat(ItemsRegistry.COOKED_CHICKEN_CUTS.get());
        FoodMappings.addMeat(ItemsRegistry.BACON.get());
        FoodMappings.addMeat(ItemsRegistry.COOKED_BACON.get());
        FoodMappings.addMeat(ItemsRegistry.HAM.get());
        FoodMappings.addMeat(ItemsRegistry.SMOKED_HAM.get());
        FoodMappings.addMeat(ItemsRegistry.BARBECUE_STICK.get());
        FoodMappings.addMeat(ItemsRegistry.CHICKEN_SANDWICH.get());
        FoodMappings.addMeat(ItemsRegistry.HAMBURGER.get());
        FoodMappings.addMeat(ItemsRegistry.BACON_SANDWICH.get());
        FoodMappings.addMeat(ItemsRegistry.MUTTON_WRAP.get());
        FoodMappings.addMeat(ItemsRegistry.DUMPLINGS.get());
        FoodMappings.addMeat(ItemsRegistry.STUFFED_POTATO.get());
        FoodMappings.addMeat(ItemsRegistry.BONE_BROTH.get());
        FoodMappings.addMeat(ItemsRegistry.BEEF_STEW.get());
        FoodMappings.addMeat(ItemsRegistry.CHICKEN_SOUP.get());
        FoodMappings.addMeat(ItemsRegistry.NOODLE_SOUP.get());
        FoodMappings.addMeat(ItemsRegistry.BACON_AND_EGGS.get());
        FoodMappings.addMeat(ItemsRegistry.PASTA_WITH_MEATBALLS.get());
        FoodMappings.addMeat(ItemsRegistry.PASTA_WITH_MUTTON_CHOP.get());
        FoodMappings.addMeat(ItemsRegistry.CABBAGE_ROLLS.get());
        FoodMappings.addMeat(ItemsRegistry.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ItemsRegistry.STEAK_AND_POTATOES.get());
        FoodMappings.addMeat(ItemsRegistry.ROAST_CHICKEN.get());
        FoodMappings.addMeat(ItemsRegistry.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ItemsRegistry.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ItemsRegistry.ROASTED_MUTTON_CHOPS.get());

        FoodMappings.addFish(ItemsRegistry.COD_SLICE.get());
        FoodMappings.addFish(ItemsRegistry.COOKED_COD_SLICE.get());
        FoodMappings.addFish(ItemsRegistry.SALMON_SLICE.get());
        FoodMappings.addFish(ItemsRegistry.COOKED_SALMON_SLICE.get());
        FoodMappings.addFish(ItemsRegistry.SALMON_ROLL.get());
        FoodMappings.addFish(ItemsRegistry.COD_ROLL.get());
        FoodMappings.addFish(ItemsRegistry.FISH_STEW.get());
        FoodMappings.addFish(ItemsRegistry.BAKED_COD_STEW.get());
        FoodMappings.addFish(ItemsRegistry.GRILLED_SALMON.get());
        FoodMappings.addFish(ItemsRegistry.COD_ROLL.get());
        FoodMappings.addFish(ItemsRegistry.COD_ROLL.get());
    }

    private static int getPieValue(PieBlock block) {
        return block.pieSlice.getFoodProperties().getNutrition() * PieBlock.MAX_BITES;
    }
}
