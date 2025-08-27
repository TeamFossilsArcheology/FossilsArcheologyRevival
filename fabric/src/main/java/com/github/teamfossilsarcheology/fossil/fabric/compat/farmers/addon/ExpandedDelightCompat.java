package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import com.ianm1647.expandeddelight.block.BlockList;
import com.ianm1647.expandeddelight.item.ItemList;

public class ExpandedDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ItemList.RAW_CINNAMON, 5);
        FoodMappings.addPlant(ItemList.GROUND_CINNAMON, 15);
        FoodMappings.addPlant(ItemList.ASPARAGUS_SEEDS, 5);
        FoodMappings.addPlant(ItemList.CHILI_PEPPER_SEEDS, 5);
        FoodMappings.addPlant(ItemList.ASPARAGUS);
        FoodMappings.addPlant(ItemList.SWEET_POTATO);
        FoodMappings.addPlant(ItemList.CHILI_PEPPER);
        FoodMappings.addPlant(ItemList.PEANUT);
        FoodMappings.addPlant(ItemList.BAKED_SWEET_POTATO);
        FoodMappings.addPlant(ItemList.PEANUT_BUTTER);
        //cheese wheel, cheese slice, cheese sandwich and grilled cheese excluded
        FoodMappings.addPlant(ItemList.PEANUT_BUTTER_SANDWICH);
        FoodMappings.addPlant(ItemList.PEANUT_BUTTER_HONEY_SANDWICH);
        FoodMappings.addPlant(ItemList.GLOW_BERRY_JELLY_SANDWICH);
        FoodMappings.addPlant(ItemList.SWEET_BERRY_JELLY_SANDWICH);
        FoodMappings.addPlant(ItemList.SWEET_ROLL);
        FoodMappings.addPlant(ItemList.BERRY_SWEET_ROLL);
        FoodMappings.addPlant(ItemList.GLOW_BERRY_SWEET_ROLL);
        FoodMappings.addPlant(ItemList.CINNAMON_RICE);
        FoodMappings.addPlant(ItemList.CINNAMON_APPLES);
        //2 cookies excluded
        FoodMappings.addPlant(ItemList.SNICKERDOODLE);
        FoodMappings.addPlant(ItemList.PEANUT_SALAD);
        FoodMappings.addPlant(ItemList.SWEET_POTATO_SALAD);
        FoodMappings.addPlant(ItemList.ASPARAGUS_SOUP);
        FoodMappings.addPlant(ItemList.ASPARAGUS_SOUP_CREAMY);
        FoodMappings.addPlant(ItemList.PEANUT_HONEY_SOUP);
        //Mac and cheese excluded
        //Cheesy asparagus and bacon excluded
        FoodMappings.addPlant(ItemList.PEPERONATA); //Pasta with onions, vegetables and tomatoes
        FoodMappings.addPlant(BlockList.CINNAMON_SAPLING, 15);
        FoodMappings.addPlant(ItemList.GLOW_BERRY_JELLY_SANDWICH);
    }

}
