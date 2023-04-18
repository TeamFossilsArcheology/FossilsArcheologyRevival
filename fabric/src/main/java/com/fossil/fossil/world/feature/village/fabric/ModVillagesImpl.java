package com.fossil.fossil.world.feature.village.fabric;

import com.fossil.fossil.world.feature.village.ModVillages;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;

import static com.fossil.fossil.world.feature.village.ModVillages.ARCHEOLOGIST_HOUSE;
import static com.fossil.fossil.world.feature.village.ModVillages.PALEONTOLOGIST_HOUSE;

public class ModVillagesImpl {
    public static void register() {
        var pool = BuiltinRegistries.TEMPLATE_POOL.get(new ResourceLocation("minecraft:village/plains/houses"));
        ModVillages.addBuildingToPool(pool, BuiltinRegistries.PROCESSOR_LIST, ARCHEOLOGIST_HOUSE.location(), ARCHEOLOGIST_HOUSE.weight());
        ModVillages.addBuildingToPool(pool, BuiltinRegistries.PROCESSOR_LIST, PALEONTOLOGIST_HOUSE.location(), PALEONTOLOGIST_HOUSE.weight());
    }
}
