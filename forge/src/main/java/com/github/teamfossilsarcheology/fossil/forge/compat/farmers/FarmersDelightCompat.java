package com.github.teamfossilsarcheology.fossil.forge.compat.farmers;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FarmersDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ModItems.CABBAGE.get());
        FoodMappings.addPlant(ModItems.TOMATO.get());
        FoodMappings.addPlant(ModItems.ONION.get());
        FoodMappings.addPlant(ModItems.TOMATO_SAUCE.get());
        FoodMappings.addPlant(ModItems.WHEAT_DOUGH.get());
        FoodMappings.addPlant(ModItems.RAW_PASTA.get());
        FoodMappings.addPlant(ModItems.PUMPKIN_SLICE.get());
        FoodMappings.addPlant(ModItems.CABBAGE_LEAF.get());
        FoodMappings.addPlant(ModItems.PIE_CRUST.get());
        FoodMappings.addPlant(ModItems.APPLE_PIE.get(), getPieValue((PieBlock) ModBlocks.APPLE_PIE.get()));
        FoodMappings.addPlant(ModItems.SWEET_BERRY_CHEESECAKE.get(), getPieValue((PieBlock) ModBlocks.SWEET_BERRY_CHEESECAKE.get()));
        FoodMappings.addPlant(ModItems.CHOCOLATE_PIE.get(), getPieValue((PieBlock) ModBlocks.CHOCOLATE_PIE.get()));
        FoodMappings.addPlant(ModItems.APPLE_PIE_SLICE.get());
        FoodMappings.addPlant(ModItems.SWEET_BERRY_CHEESECAKE_SLICE.get());
        FoodMappings.addPlant(ModItems.CHOCOLATE_PIE_SLICE.get());
        FoodMappings.addPlant(ModItems.SWEET_BERRY_COOKIE.get());
        FoodMappings.addPlant(ModItems.HONEY_COOKIE.get());
        FoodMappings.addPlant(ModItems.MELON_POPSICLE.get());
        FoodMappings.addPlant(ModItems.GLOW_BERRY_CUSTARD.get());
        FoodMappings.addPlant(ModItems.FRUIT_SALAD.get());
        FoodMappings.addPlant(ModItems.MIXED_SALAD.get());
        FoodMappings.addPlant(ModItems.NETHER_SALAD.get());
        FoodMappings.addPlant(ModItems.KELP_ROLL.get());
        FoodMappings.addPlant(ModItems.KELP_ROLL_SLICE.get());
        FoodMappings.addPlant(ModItems.COOKED_RICE.get());
        FoodMappings.addPlant(ModItems.VEGETABLE_SOUP.get());
        FoodMappings.addPlant(ModItems.FRIED_RICE.get());
        FoodMappings.addPlant(ModItems.PUMPKIN_SOUP.get());
        FoodMappings.addPlant(ModItems.MUSHROOM_RICE.get());
        FoodMappings.addPlant(ModItems.VEGETABLE_NOODLES.get());
        FoodMappings.addPlant(ModItems.VEGETABLE_NOODLES.get());
        FoodMappings.addPlant(ModItems.STUFFED_PUMPKIN.get());
        FoodMappings.addPlant(ModItems.ROAST_CHICKEN.get());
        FoodMappings.addPlant(ModItems.ROAST_CHICKEN.get());

        FoodMappings.addEgg(ModItems.FRIED_EGG.get());
        FoodMappings.addEgg(ModItems.EGG_SANDWICH.get());


        FoodMappings.addMeat(ModItems.MINCED_BEEF.get());
        FoodMappings.addMeat(ModItems.BEEF_PATTY.get());
        FoodMappings.addMeat(ModItems.CHICKEN_CUTS.get());
        FoodMappings.addMeat(ModItems.COOKED_CHICKEN_CUTS.get());
        FoodMappings.addMeat(ModItems.BACON.get());
        FoodMappings.addMeat(ModItems.COOKED_BACON.get());
        FoodMappings.addMeat(ModItems.HAM.get());
        FoodMappings.addMeat(ModItems.SMOKED_HAM.get());
        FoodMappings.addMeat(ModItems.BARBECUE_STICK.get());
        FoodMappings.addMeat(ModItems.CHICKEN_SANDWICH.get());
        FoodMappings.addMeat(ModItems.HAMBURGER.get());
        FoodMappings.addMeat(ModItems.BACON_SANDWICH.get());
        FoodMappings.addMeat(ModItems.MUTTON_WRAP.get());
        FoodMappings.addMeat(ModItems.DUMPLINGS.get());
        FoodMappings.addMeat(ModItems.STUFFED_POTATO.get());
        FoodMappings.addMeat(ModItems.BONE_BROTH.get());
        FoodMappings.addMeat(ModItems.BEEF_STEW.get());
        FoodMappings.addMeat(ModItems.CHICKEN_SOUP.get());
        FoodMappings.addMeat(ModItems.NOODLE_SOUP.get());
        FoodMappings.addMeat(ModItems.BACON_AND_EGGS.get());
        FoodMappings.addMeat(ModItems.PASTA_WITH_MEATBALLS.get());
        FoodMappings.addMeat(ModItems.PASTA_WITH_MUTTON_CHOP.get());
        FoodMappings.addMeat(ModItems.CABBAGE_ROLLS.get());
        FoodMappings.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ModItems.STEAK_AND_POTATOES.get());
        FoodMappings.addMeat(ModItems.ROAST_CHICKEN.get());
        FoodMappings.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());

        FoodMappings.addFish(ModItems.COD_SLICE.get());
        FoodMappings.addFish(ModItems.COOKED_COD_SLICE.get());
        FoodMappings.addFish(ModItems.SALMON_SLICE.get());
        FoodMappings.addFish(ModItems.COOKED_SALMON_SLICE.get());
        FoodMappings.addFish(ModItems.SALMON_ROLL.get());
        FoodMappings.addFish(ModItems.COD_ROLL.get());
        FoodMappings.addFish(ModItems.FISH_STEW.get());
        FoodMappings.addFish(ModItems.BAKED_COD_STEW.get());
        FoodMappings.addFish(ModItems.GRILLED_SALMON.get());
        FoodMappings.addFish(ModItems.COD_ROLL.get());
        FoodMappings.addFish(ModItems.COD_ROLL.get());
    }

    private static int getPieValue(PieBlock block) {
        return block.getPieSliceItem().getItem().getFoodProperties().getNutrition() * block.getMaxBites();
    }
}
