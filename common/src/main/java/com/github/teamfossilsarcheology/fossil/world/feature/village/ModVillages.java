package com.github.teamfossilsarcheology.fossil.world.feature.village;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;

public class ModVillages {
    public static final Tuple ARCHEOLOGIST_HOUSE_DESERT = createStructure("houses/archeo_house_desert", 1);
    public static final Tuple PALEONTOLOGIST_HOUSE_DESERT = createStructure("houses/paleo_house_desert", 1);
    public static final Tuple ARCHEOLOGIST_HOUSE_PLAINS = createStructure("houses/archeo_house_plains_top", 1);
    public static final Tuple PALEONTOLOGIST_HOUSE_PLAINS = createStructure("houses/paleo_house_plains_top", 1);
    public static final Tuple ARCHEOLOGIST_HOUSE_SAVANNA = createStructure("houses/archeo_house_savanna", 1);
    public static final Tuple PALEONTOLOGIST_HOUSE_SAVANNA = createStructure("houses/paleo_house_savanna", 1);
    public static final Tuple ARCHEOLOGIST_HOUSE_SNOWY = createStructure("houses/archeo_house_snowy", 1);
    public static final Tuple PALEONTOLOGIST_HOUSE_SNOWY = createStructure("houses/paleo_house_snowy", 1);
    public static final Tuple ARCHEOLOGIST_HOUSE_TAIGA = createStructure("houses/archeo_house_taiga_top", 1);
    public static final Tuple PALEONTOLOGIST_HOUSE_TAIGA = createStructure("houses/paleo_house_taiga", 1);
    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registries.PROCESSOR_LIST, new ResourceLocation("minecraft", "empty"));

    private static Tuple createStructure(String name, int weight) {
        return new Tuple(FossilMod.MOD_ID + ":" + name, weight);
    }

    public static void addBuildingToPool(StructureTemplatePool pool, Registry<StructureProcessorList> registry, Tuple building) {
        if (pool == null) return;
        Holder<StructureProcessorList> emptyProcessorList = registry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        SinglePoolElement piece = SinglePoolElement.legacy(building.location, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

        for (int i = 0; i < building.weight; i++) {
            pool.templates.add(piece);
        }
        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, building.weight));
        pool.rawTemplates = listOfPieceEntries;
    }

    @ExpectPlatform
    public static void register() {
    }

    public record Tuple(String location, int weight) {
        public ResourceLocation resourceLocation() {
            return new ResourceLocation(location);
        }
    }
}
