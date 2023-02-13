package com.fossil.fossil;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.enchantment.ModEnchantments;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.inventory.ModMenus;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.material.ModFluids;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.DisposableTask;
import com.fossil.fossil.world.feature.ModPlacedFeatures;
import net.minecraft.world.level.timers.TimerCallbacks;


public class Fossil {
    public static final String MOD_ID = "fossil";

    public static final RevivalConfig CONFIG_OPTIONS = new RevivalConfig();

    public static void init() {
        ModFluids.register(); //Before ModBlocks
        ModBlocks.register();
        ModItems.register();
        ModEntities.register();
        ModMenus.register();
        ModBlockEntities.register();
        ModPlacedFeatures.register();
        ModRecipes.register();
        ModSounds.register();
        ModEnchantments.register();

        TimerCallbacks.SERVER_CALLBACKS.register(new DisposableTask.Serializer());
    }
}
