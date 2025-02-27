package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ModStructureType {
    public static final StructureType<AnuCastleStructure> ANU_CASTLE = register("anu_castle", AnuCastleStructure.CODEC);
    public static final StructureType<HellBoatStructure> HELL_BOAT = register("hell_boat", HellBoatStructure.CODEC);
    public static final StructureType<TreasureRoomStructure> TREASURE_ROOM = register("treasure_room", TreasureRoomStructure.CODEC);

    private static <S extends Structure> StructureType<S> register(String name, Codec<S> codec) {
        return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, FossilMod.location(name), () -> codec);
    }

    public static void register() {

    }
}
