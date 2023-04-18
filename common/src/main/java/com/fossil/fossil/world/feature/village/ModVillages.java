package com.fossil.fossil.world.feature.village;

import com.fossil.fossil.Fossil;
import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;

public class ModVillages {
    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registry.PROCESSOR_LIST_REGISTRY, new ResourceLocation("minecraft", "empty"));
    public static final Tuple ARCHEOLOGIST_HOUSE = createStructure("archeologist_villager_house", 25);
    public static final Tuple PALEONTOLOGIST_HOUSE = createStructure("paleontologist_villager_house", 25);

    private static Tuple createStructure(String name, int weight) {
        return new Tuple(Fossil.MOD_ID + ":" + name, weight);
    }

    public static void addBuildingToPool(StructureTemplatePool pool, Registry<StructureProcessorList> registry, String structureLocation, int weight) {
        if (pool == null) return;
        Holder<StructureProcessorList> emptyProcessorList = registry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        SinglePoolElement piece = SinglePoolElement.legacy(structureLocation, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

        for (int i = 0; i < weight; i++) {
            pool.templates.add(piece);
        }
        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries;
    }

    @ExpectPlatform
    public static void register() {
    }

    public record Tuple(String location, int weight) {

    }
}
