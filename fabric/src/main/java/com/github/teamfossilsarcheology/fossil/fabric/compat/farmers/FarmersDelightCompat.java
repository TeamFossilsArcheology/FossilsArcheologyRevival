package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers;

import com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon.util.AddonConstants;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FarmersDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ModItems.CABBAGE.get());
        manager.addPlant(ModItems.TOMATO.get());
        manager.addPlant(ModItems.ONION.get());
        manager.addPlant(ModItems.TOMATO_SAUCE.get());
        manager.addPlant(ModItems.WHEAT_DOUGH.get());
        manager.addPlant(ModItems.RAW_PASTA.get());
        manager.addPlant(ModItems.PUMPKIN_SLICE.get());
        manager.addPlant(ModItems.CABBAGE_LEAF.get());
        manager.addPlant(ModItems.PIE_CRUST.get());
        manager.addPlant(ModItems.APPLE_PIE.get(), getPieValue((PieBlock) ModBlocks.APPLE_PIE.get()));
        manager.addPlant(ModItems.SWEET_BERRY_CHEESECAKE.get(), getPieValue((PieBlock) ModBlocks.SWEET_BERRY_CHEESECAKE.get()));
        manager.addPlant(ModItems.CHOCOLATE_PIE.get(), getPieValue((PieBlock) ModBlocks.CHOCOLATE_PIE.get()));
        manager.addPlant(ModItems.APPLE_PIE_SLICE.get());
        manager.addPlant(ModItems.SWEET_BERRY_CHEESECAKE_SLICE.get());
        manager.addPlant(ModItems.CHOCOLATE_PIE_SLICE.get());
        manager.addPlant(ModItems.SWEET_BERRY_COOKIE.get());
        manager.addPlant(ModItems.HONEY_COOKIE.get());
        manager.addPlant(ModItems.MELON_POPSICLE.get());
        manager.addPlant(ModItems.GLOW_BERRY_CUSTARD.get());
        manager.addPlant(ModItems.FRUIT_SALAD.get());
        manager.addPlant(ModItems.MIXED_SALAD.get());
        manager.addPlant(ModItems.NETHER_SALAD.get());
        manager.addPlant(ModItems.KELP_ROLL.get());
        manager.addPlant(ModItems.KELP_ROLL_SLICE.get());
        manager.addPlant(ModItems.COOKED_RICE.get());
        manager.addPlant(ModItems.VEGETABLE_SOUP.get());
        manager.addPlant(ModItems.FRIED_RICE.get());
        manager.addPlant(ModItems.PUMPKIN_SOUP.get());
        manager.addPlant(ModItems.MUSHROOM_RICE.get());
        manager.addPlant(ModItems.VEGETABLE_NOODLES.get());
        manager.addPlant(ModItems.VEGETABLE_NOODLES.get());
        manager.addPlant(ModItems.STUFFED_PUMPKIN.get());
        manager.addPlant(ModItems.ROAST_CHICKEN.get());
        manager.addPlant(ModItems.ROAST_CHICKEN.get());
        manager.addPlant(ModItems.CABBAGE_SEEDS.get(), 5);
        manager.addPlant(ModItems.TOMATO_SEEDS.get(), 5);

        manager.addEgg(ModItems.FRIED_EGG.get());
        manager.addEgg(ModItems.EGG_SANDWICH.get());


        manager.addMeat(ModItems.MINCED_BEEF.get());
        manager.addMeat(ModItems.BEEF_PATTY.get());
        manager.addMeat(ModItems.CHICKEN_CUTS.get());
        manager.addMeat(ModItems.COOKED_CHICKEN_CUTS.get());
        manager.addMeat(ModItems.BACON.get());
        manager.addMeat(ModItems.COOKED_BACON.get());
        manager.addMeat(ModItems.HAM.get());
        manager.addMeat(ModItems.SMOKED_HAM.get());
        manager.addMeat(ModItems.BARBECUE_STICK.get());
        manager.addMeat(ModItems.CHICKEN_SANDWICH.get());
        manager.addMeat(ModItems.HAMBURGER.get());
        manager.addMeat(ModItems.BACON_SANDWICH.get());
        manager.addMeat(ModItems.MUTTON_WRAP.get());
        manager.addMeat(ModItems.DUMPLINGS.get());
        manager.addMeat(ModItems.STUFFED_POTATO.get());
        manager.addMeat(ModItems.BONE_BROTH.get());
        manager.addMeat(ModItems.BEEF_STEW.get());
        manager.addMeat(ModItems.CHICKEN_SOUP.get());
        manager.addMeat(ModItems.NOODLE_SOUP.get());
        manager.addMeat(ModItems.BACON_AND_EGGS.get());
        manager.addMeat(ModItems.PASTA_WITH_MEATBALLS.get());
        manager.addMeat(ModItems.PASTA_WITH_MUTTON_CHOP.get());
        manager.addMeat(ModItems.CABBAGE_ROLLS.get());
        manager.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        manager.addMeat(ModItems.STEAK_AND_POTATOES.get());
        manager.addMeat(ModItems.ROAST_CHICKEN.get());
        manager.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        manager.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        manager.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());

        manager.addFish(ModItems.COD_SLICE.get());
        manager.addFish(ModItems.COOKED_COD_SLICE.get());
        manager.addFish(ModItems.SALMON_SLICE.get());
        manager.addFish(ModItems.COOKED_SALMON_SLICE.get());
        manager.addFish(ModItems.SALMON_ROLL.get());
        manager.addFish(ModItems.COD_ROLL.get());
        manager.addFish(ModItems.FISH_STEW.get());
        manager.addFish(ModItems.BAKED_COD_STEW.get());
        manager.addFish(ModItems.GRILLED_SALMON.get());
        manager.addFish(ModItems.COD_ROLL.get());
        manager.addFish(ModItems.COD_ROLL.get());

        AddonConstants.registerAddonFoodMappings(manager);
    }

    public static int getPieValue(PieBlock block) {
        return block.pieSlice.get().getFoodProperties().getNutrition() * block.getMaxBites() * 5;
    }
}
