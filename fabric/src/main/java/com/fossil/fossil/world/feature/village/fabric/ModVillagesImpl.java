package com.fossil.fossil.world.feature.village.fabric;

import com.fossil.fossil.world.feature.village.ModVillages;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import static com.fossil.fossil.world.feature.village.ModVillages.*;

public class ModVillagesImpl {
    public static void register() {
        var desert = BuiltinRegistries.TEMPLATE_POOL.get(new ResourceLocation("minecraft:village/desert/houses"));
        addBuilding(desert, ARCHEOLOGIST_HOUSE_DESERT);
        addBuilding(desert, PALEONTOLOGIST_HOUSE_DESERT);
        var plains = BuiltinRegistries.TEMPLATE_POOL.get(new ResourceLocation("minecraft:village/plains/houses"));
        addBuilding(plains, ARCHEOLOGIST_HOUSE_PLAINS);
        addBuilding(plains, PALEONTOLOGIST_HOUSE_PLAINS);
        var savanna = BuiltinRegistries.TEMPLATE_POOL.get(new ResourceLocation("minecraft:village/savanna/houses"));
        addBuilding(savanna, ARCHEOLOGIST_HOUSE_SAVANNA);
        addBuilding(savanna, PALEONTOLOGIST_HOUSE_SAVANNA);
        var snowy = BuiltinRegistries.TEMPLATE_POOL.get(new ResourceLocation("minecraft:village/snowy/houses"));
        addBuilding(snowy, ARCHEOLOGIST_HOUSE_SNOWY);
        addBuilding(snowy, PALEONTOLOGIST_HOUSE_SNOWY);
        var taiga = BuiltinRegistries.TEMPLATE_POOL.get(new ResourceLocation("minecraft:village/taiga/houses"));
        addBuilding(taiga, ARCHEOLOGIST_HOUSE_TAIGA);
        addBuilding(taiga, PALEONTOLOGIST_HOUSE_TAIGA);
    }

    private static void addBuilding(StructureTemplatePool pool, ModVillages.Tuple building) {
        addBuildingToPool(pool, BuiltinRegistries.PROCESSOR_LIST, building);
    }
}
