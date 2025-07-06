package com.github.teamfossilsarcheology.fossil.world.feature.village.fabric;

import fzzyhmstrs.structurized_reborn.impl.FabricStructurePoolRegistry;
import net.minecraft.resources.ResourceLocation;

import static com.github.teamfossilsarcheology.fossil.world.feature.village.ModVillages.*;

/**
 * @see com.github.teamfossilsarcheology.fossil.world.feature.village.ModVillages
 */
public class ModVillagesImpl {
    public static void register() {
        ResourceLocation desert = new ResourceLocation("minecraft:village/desert/houses");
        FabricStructurePoolRegistry.registerSimple(desert, ARCHEOLOGIST_HOUSE_DESERT.resourceLocation(), ARCHEOLOGIST_HOUSE_DESERT.weight());
        FabricStructurePoolRegistry.registerSimple(desert, PALEONTOLOGIST_HOUSE_DESERT.resourceLocation(), PALEONTOLOGIST_HOUSE_DESERT.weight());
        ResourceLocation plains = new ResourceLocation("minecraft:village/plains/houses");
        FabricStructurePoolRegistry.registerSimple(plains, ARCHEOLOGIST_HOUSE_PLAINS.resourceLocation(), ARCHEOLOGIST_HOUSE_PLAINS.weight());
        FabricStructurePoolRegistry.registerSimple(plains, PALEONTOLOGIST_HOUSE_PLAINS.resourceLocation(), PALEONTOLOGIST_HOUSE_PLAINS.weight());
        ResourceLocation savanna = new ResourceLocation("minecraft:village/savanna/houses");
        FabricStructurePoolRegistry.registerSimple(savanna, ARCHEOLOGIST_HOUSE_SAVANNA.resourceLocation(), ARCHEOLOGIST_HOUSE_SAVANNA.weight());
        FabricStructurePoolRegistry.registerSimple(savanna, PALEONTOLOGIST_HOUSE_SAVANNA.resourceLocation(), PALEONTOLOGIST_HOUSE_SAVANNA.weight());
        ResourceLocation snowy = new ResourceLocation("minecraft:village/snowy/houses");
        FabricStructurePoolRegistry.registerSimple(snowy, ARCHEOLOGIST_HOUSE_SNOWY.resourceLocation(), ARCHEOLOGIST_HOUSE_SNOWY.weight());
        FabricStructurePoolRegistry.registerSimple(snowy, PALEONTOLOGIST_HOUSE_SNOWY.resourceLocation(), PALEONTOLOGIST_HOUSE_SNOWY.weight());
        ResourceLocation taiga = new ResourceLocation("minecraft:village/taiga/houses");
        FabricStructurePoolRegistry.registerSimple(taiga, ARCHEOLOGIST_HOUSE_TAIGA.resourceLocation(), ARCHEOLOGIST_HOUSE_TAIGA.weight());
        FabricStructurePoolRegistry.registerSimple(taiga, PALEONTOLOGIST_HOUSE_TAIGA.resourceLocation(), PALEONTOLOGIST_HOUSE_TAIGA.weight());
    }
}
