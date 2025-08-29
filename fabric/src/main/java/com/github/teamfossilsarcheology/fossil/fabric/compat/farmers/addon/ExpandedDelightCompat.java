package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.ianm1647.expandeddelight.block.BlockList;
import com.ianm1647.expandeddelight.item.ItemList;

public class ExpandedDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemList.RAW_CINNAMON, 5);
        manager.addPlant(ItemList.GROUND_CINNAMON, 15);
        manager.addPlant(ItemList.ASPARAGUS_SEEDS, 5);
        manager.addPlant(ItemList.CHILI_PEPPER_SEEDS, 5);
        manager.addPlant(ItemList.ASPARAGUS);
        manager.addPlant(ItemList.SWEET_POTATO);
        manager.addPlant(ItemList.CHILI_PEPPER);
        manager.addPlant(ItemList.PEANUT);
        manager.addPlant(ItemList.BAKED_SWEET_POTATO);
        manager.addPlant(ItemList.PEANUT_BUTTER);
        //cheese wheel, cheese slice, cheese sandwich and grilled cheese excluded
        manager.addPlant(ItemList.PEANUT_BUTTER_SANDWICH);
        manager.addPlant(ItemList.PEANUT_BUTTER_HONEY_SANDWICH);
        manager.addPlant(ItemList.GLOW_BERRY_JELLY_SANDWICH);
        manager.addPlant(ItemList.SWEET_BERRY_JELLY_SANDWICH);
        manager.addPlant(ItemList.SWEET_ROLL);
        manager.addPlant(ItemList.BERRY_SWEET_ROLL);
        manager.addPlant(ItemList.GLOW_BERRY_SWEET_ROLL);
        manager.addPlant(ItemList.CINNAMON_RICE);
        manager.addPlant(ItemList.CINNAMON_APPLES);
        //2 cookies excluded
        manager.addPlant(ItemList.SNICKERDOODLE);
        manager.addPlant(ItemList.PEANUT_SALAD);
        manager.addPlant(ItemList.SWEET_POTATO_SALAD);
        manager.addPlant(ItemList.ASPARAGUS_SOUP);
        manager.addPlant(ItemList.ASPARAGUS_SOUP_CREAMY);
        manager.addPlant(ItemList.PEANUT_HONEY_SOUP);
        //Mac and cheese excluded
        //Cheesy asparagus and bacon excluded
        manager.addPlant(ItemList.PEPERONATA); //Pasta with onions, vegetables and tomatoes
        manager.addPlant(BlockList.CINNAMON_SAPLING, 15);
        manager.addPlant(ItemList.GLOW_BERRY_JELLY_SANDWICH);
    }

}
