package com.fossil.fossil;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.world.feature.ModPlacedFeatures;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;


public class Fossil {
    public static final String MOD_ID = "fossil";

    public static void init() {
        ModBlocks.register();
        ModItems.register();
        ModPlacedFeatures.register();
    }
}
