package com.fossil.fossil;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.blockentity.ModBlockEntities;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.world.feature.ModPlacedFeatures;


public class Fossil {
    public static final String MOD_ID = "fossil";

    public static final RevivalConfig CONFIG_OPTIONS = new RevivalConfig();

    public static void init() {
        ModBlocks.register();
        ModItems.register();
        ModEntities.register();
        ModBlockEntities.register();
        ModPlacedFeatures.register();
    }
}
