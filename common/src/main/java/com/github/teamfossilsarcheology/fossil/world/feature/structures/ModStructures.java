package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;

public class ModStructures {
    public static final ResourceKey<Structure> ANU_CASTLE_KEY = createKey("anu_castle");
    public static final ResourceKey<Structure> HELL_BOAT_KEY = createKey("hell_boat");
    public static final ResourceKey<Structure> TREASURE_ROOM_KEY = createKey("treasure_room");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registry.STRUCTURE_REGISTRY, FossilMod.location(name));
    }

    private static HolderSet<Biome> biomes(TagKey<Biome> key) {
        return BuiltinRegistries.BIOME.getOrCreateTag(key);
    }

    private static Holder<Structure> register(ResourceKey<Structure> key, Structure structure) {
        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, key, structure);
    }

    public static final Holder<Structure> ANU_CASTLE = register(ANU_CASTLE_KEY, new AnuCastleStructure(
            new Structure.StructureSettings(HolderSet.direct(), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));

    public static final Holder<Structure> HELL_BOAT = register(HELL_BOAT_KEY, new HellBoatStructure(
            new Structure.StructureSettings(biomes(BiomeTags.IS_NETHER), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
            UniformHeight.of(VerticalAnchor.absolute(30), VerticalAnchor.absolute(30))));

    public static final Holder<Structure> TREASURE_ROOM = register(TREASURE_ROOM_KEY, new TreasureRoomStructure(
            new Structure.StructureSettings(HolderSet.direct(), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));

    public static void register() {
    }
}
