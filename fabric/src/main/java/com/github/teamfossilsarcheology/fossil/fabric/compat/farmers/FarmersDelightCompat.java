package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers;

import com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon.util.AddonConstants;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.nhoryzon.mc.farmersdelight.block.PieBlock;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;

public class FarmersDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemsRegistry.CABBAGE.get());
        manager.addPlant(ItemsRegistry.TOMATO.get());
        manager.addPlant(ItemsRegistry.ONION.get());
        manager.addPlant(ItemsRegistry.TOMATO_SAUCE.get());
        manager.addPlant(ItemsRegistry.WHEAT_DOUGH.get());
        manager.addPlant(ItemsRegistry.RAW_PASTA.get());
        manager.addPlant(ItemsRegistry.PUMPKIN_SLICE.get());
        manager.addPlant(ItemsRegistry.CABBAGE_LEAF.get());
        manager.addPlant(ItemsRegistry.PIE_CRUST.get());
        manager.addPlant(ItemsRegistry.APPLE_PIE.get(), getPieValue((PieBlock) BlocksRegistry.APPLE_PIE.get()));
        manager.addPlant(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get(), getPieValue((PieBlock) BlocksRegistry.SWEET_BERRY_CHEESECAKE.get()));
        manager.addPlant(ItemsRegistry.CHOCOLATE_PIE.get(), getPieValue((PieBlock) BlocksRegistry.CHOCOLATE_PIE.get()));
        manager.addPlant(ItemsRegistry.APPLE_PIE_SLICE.get());
        manager.addPlant(ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get());
        manager.addPlant(ItemsRegistry.CHOCOLATE_PIE_SLICE.get());
        manager.addPlant(ItemsRegistry.SWEET_BERRY_COOKIE.get());
        manager.addPlant(ItemsRegistry.HONEY_COOKIE.get());
        manager.addPlant(ItemsRegistry.MELON_POPSICLE.get());
        manager.addPlant(ItemsRegistry.GLOW_BERRY_CUSTARD.get());
        manager.addPlant(ItemsRegistry.FRUIT_SALAD.get());
        manager.addPlant(ItemsRegistry.MIXED_SALAD.get());
        manager.addPlant(ItemsRegistry.NETHER_SALAD.get());
        manager.addPlant(ItemsRegistry.KELP_ROLL.get());
        manager.addPlant(ItemsRegistry.KELP_ROLL_SLICE.get());
        manager.addPlant(ItemsRegistry.COOKED_RICE.get());
        manager.addPlant(ItemsRegistry.VEGETABLE_SOUP.get());
        manager.addPlant(ItemsRegistry.FRIED_RICE.get());
        manager.addPlant(ItemsRegistry.PUMPKIN_SOUP.get());
        manager.addPlant(ItemsRegistry.MUSHROOM_RICE.get());
        manager.addPlant(ItemsRegistry.VEGETABLE_NOODLES.get());
        manager.addPlant(ItemsRegistry.VEGETABLE_NOODLES.get());
        manager.addPlant(ItemsRegistry.STUFFED_PUMPKIN.get());
        manager.addPlant(ItemsRegistry.ROAST_CHICKEN.get());
        manager.addPlant(ItemsRegistry.ROAST_CHICKEN.get());
        manager.addPlant(ItemsRegistry.CABBAGE_SEEDS.get(), 5);
        manager.addPlant(ItemsRegistry.TOMATO_SEEDS.get(), 5);

        manager.addEgg(ItemsRegistry.FRIED_EGG.get());
        manager.addEgg(ItemsRegistry.EGG_SANDWICH.get());


        manager.addMeat(ItemsRegistry.MINCED_BEEF.get());
        manager.addMeat(ItemsRegistry.BEEF_PATTY.get());
        manager.addMeat(ItemsRegistry.CHICKEN_CUTS.get());
        manager.addMeat(ItemsRegistry.COOKED_CHICKEN_CUTS.get());
        manager.addMeat(ItemsRegistry.BACON.get());
        manager.addMeat(ItemsRegistry.COOKED_BACON.get());
        manager.addMeat(ItemsRegistry.HAM.get());
        manager.addMeat(ItemsRegistry.SMOKED_HAM.get());
        manager.addMeat(ItemsRegistry.BARBECUE_STICK.get());
        manager.addMeat(ItemsRegistry.CHICKEN_SANDWICH.get());
        manager.addMeat(ItemsRegistry.HAMBURGER.get());
        manager.addMeat(ItemsRegistry.BACON_SANDWICH.get());
        manager.addMeat(ItemsRegistry.MUTTON_WRAP.get());
        manager.addMeat(ItemsRegistry.DUMPLINGS.get());
        manager.addMeat(ItemsRegistry.STUFFED_POTATO.get());
        manager.addMeat(ItemsRegistry.BONE_BROTH.get());
        manager.addMeat(ItemsRegistry.BEEF_STEW.get());
        manager.addMeat(ItemsRegistry.CHICKEN_SOUP.get());
        manager.addMeat(ItemsRegistry.NOODLE_SOUP.get());
        manager.addMeat(ItemsRegistry.BACON_AND_EGGS.get());
        manager.addMeat(ItemsRegistry.PASTA_WITH_MEATBALLS.get());
        manager.addMeat(ItemsRegistry.PASTA_WITH_MUTTON_CHOP.get());
        manager.addMeat(ItemsRegistry.CABBAGE_ROLLS.get());
        manager.addMeat(ItemsRegistry.ROASTED_MUTTON_CHOPS.get());
        manager.addMeat(ItemsRegistry.STEAK_AND_POTATOES.get());
        manager.addMeat(ItemsRegistry.ROAST_CHICKEN.get());
        manager.addMeat(ItemsRegistry.ROASTED_MUTTON_CHOPS.get());
        manager.addMeat(ItemsRegistry.ROASTED_MUTTON_CHOPS.get());
        manager.addMeat(ItemsRegistry.ROASTED_MUTTON_CHOPS.get());

        manager.addFish(ItemsRegistry.COD_SLICE.get());
        manager.addFish(ItemsRegistry.COOKED_COD_SLICE.get());
        manager.addFish(ItemsRegistry.SALMON_SLICE.get());
        manager.addFish(ItemsRegistry.COOKED_SALMON_SLICE.get());
        manager.addFish(ItemsRegistry.SALMON_ROLL.get());
        manager.addFish(ItemsRegistry.COD_ROLL.get());
        manager.addFish(ItemsRegistry.FISH_STEW.get());
        manager.addFish(ItemsRegistry.BAKED_COD_STEW.get());
        manager.addFish(ItemsRegistry.GRILLED_SALMON.get());
        manager.addFish(ItemsRegistry.COD_ROLL.get());
        manager.addFish(ItemsRegistry.COD_ROLL.get());

        AddonConstants.registerAddonFoodMappings();
    }

    public static int getPieValue(PieBlock block) {
        return block.pieSlice.getFoodProperties().getNutrition() * PieBlock.MAX_BITES * 5;
    }
}
