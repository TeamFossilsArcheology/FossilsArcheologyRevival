package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.ianm1647.expandeddelight.registry.BlockRegistry;
import com.ianm1647.expandeddelight.registry.ItemRegistry;

public class ExpandedDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ItemRegistry.RAW_CINNAMON, 5);
        FoodMappings.addPlant(ItemRegistry.GROUND_CINNAMON, 15);
        FoodMappings.addPlant(ItemRegistry.ASPARAGUS_SEEDS, 5);
        FoodMappings.addPlant(ItemRegistry.CHILI_PEPPER_SEEDS, 5);
        FoodMappings.addPlant(ItemRegistry.ASPARAGUS);
        FoodMappings.addPlant(ItemRegistry.SWEET_POTATO);
        FoodMappings.addPlant(ItemRegistry.CHILI_PEPPER);
        FoodMappings.addPlant(ItemRegistry.PEANUT);
        FoodMappings.addPlant(ItemRegistry.BAKED_SWEET_POTATO);
        FoodMappings.addPlant(ItemRegistry.PEANUT_BUTTER);
        //cheese wheel, cheese slice, cheese sandwich and grilled cheese excluded
        FoodMappings.addPlant(ItemRegistry.PEANUT_BUTTER_SANDWICH);
        FoodMappings.addPlant(ItemRegistry.PEANUT_BUTTER_HONEY_SANDWICH);
        FoodMappings.addPlant(ItemRegistry.GLOW_BERRY_JELLY_SANDWICH);
        FoodMappings.addPlant(ItemRegistry.SWEET_BERRY_JELLY_SANDWICH);
        FoodMappings.addPlant(ItemRegistry.SWEET_ROLL);
        FoodMappings.addPlant(ItemRegistry.BERRY_SWEET_ROLL);
        FoodMappings.addPlant(ItemRegistry.GLOW_BERRY_SWEET_ROLL);
        FoodMappings.addPlant(ItemRegistry.CINNAMON_RICE);
        FoodMappings.addPlant(ItemRegistry.CINNAMON_APPLES);
        //2 cookies excluded
        FoodMappings.addPlant(ItemRegistry.SNICKERDOODLE);
        FoodMappings.addPlant(ItemRegistry.PEANUT_SALAD);
        FoodMappings.addPlant(ItemRegistry.SWEET_POTATO_SALAD);
        FoodMappings.addPlant(ItemRegistry.ASPARAGUS_SOUP);
        FoodMappings.addPlant(ItemRegistry.ASPARAGUS_SOUP_CREAMY);
        FoodMappings.addPlant(ItemRegistry.PEANUT_HONEY_SOUP);
        //Mac and cheese excluded
        //Cheesy asparagus and bacon excluded
        FoodMappings.addPlant(ItemRegistry.PEPERONATA); //Pasta with onions, vegetables and tomatoes
        FoodMappings.addPlant(BlockRegistry.CINNAMON_SAPLING, 15);
        FoodMappings.addPlant(ItemRegistry.GLOW_BERRY_JELLY_SANDWICH);
    }

}
