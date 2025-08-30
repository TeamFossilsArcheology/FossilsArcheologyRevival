package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.ianm1647.expandeddelight.registry.BlockRegistry;
import com.ianm1647.expandeddelight.registry.ItemRegistry;

public class ExpandedDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemRegistry.RAW_CINNAMON, 5);
        manager.addPlant(ItemRegistry.GROUND_CINNAMON, 15);
        manager.addPlant(ItemRegistry.ASPARAGUS_SEEDS, 5);
        manager.addPlant(ItemRegistry.CHILI_PEPPER_SEEDS, 5);
        manager.addPlant(ItemRegistry.ASPARAGUS);
        manager.addPlant(ItemRegistry.SWEET_POTATO);
        manager.addPlant(ItemRegistry.CHILI_PEPPER);
        manager.addPlant(ItemRegistry.PEANUT);
        manager.addPlant(ItemRegistry.BAKED_SWEET_POTATO);
        manager.addPlant(ItemRegistry.PEANUT_BUTTER);
        //cheese wheel, cheese slice, cheese sandwich and grilled cheese excluded
        manager.addPlant(ItemRegistry.PEANUT_BUTTER_SANDWICH);
        manager.addPlant(ItemRegistry.PEANUT_BUTTER_HONEY_SANDWICH);
        manager.addPlant(ItemRegistry.GLOW_BERRY_JELLY_SANDWICH);
        manager.addPlant(ItemRegistry.SWEET_BERRY_JELLY_SANDWICH);
        manager.addPlant(ItemRegistry.SWEET_ROLL);
        manager.addPlant(ItemRegistry.BERRY_SWEET_ROLL);
        manager.addPlant(ItemRegistry.GLOW_BERRY_SWEET_ROLL);
        manager.addPlant(ItemRegistry.CINNAMON_RICE);
        manager.addPlant(ItemRegistry.CINNAMON_APPLES);
        //2 cookies excluded
        manager.addPlant(ItemRegistry.SNICKERDOODLE);
        manager.addPlant(ItemRegistry.PEANUT_SALAD);
        manager.addPlant(ItemRegistry.SWEET_POTATO_SALAD);
        manager.addPlant(ItemRegistry.ASPARAGUS_SOUP);
        manager.addPlant(ItemRegistry.ASPARAGUS_SOUP_CREAMY);
        manager.addPlant(ItemRegistry.PEANUT_HONEY_SOUP);
        //Mac and cheese excluded
        //Cheesy asparagus and bacon excluded
        manager.addPlant(ItemRegistry.PEPERONATA); //Pasta with onions, vegetables and tomatoes
        manager.addPlant(BlockRegistry.CINNAMON_SAPLING, 15);
        manager.addPlant(ItemRegistry.GLOW_BERRY_JELLY_SANDWICH);
    }

}
