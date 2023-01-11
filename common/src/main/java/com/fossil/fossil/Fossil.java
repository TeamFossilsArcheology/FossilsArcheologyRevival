package com.fossil.fossil;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.inventory.ModMenus;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.world.feature.ModPlacedFeatures;


public class Fossil {
    public static final String MOD_ID = "fossil";

    public static final RevivalConfig CONFIG_OPTIONS = new RevivalConfig();

    public static void init() {
        ModBlocks.register();
        ModItems.register();
        ModEntities.register();
        ModMenus.register();
        ModBlockEntities.register();
        ModPlacedFeatures.register();
        ModRecipes.register();

    }
}
