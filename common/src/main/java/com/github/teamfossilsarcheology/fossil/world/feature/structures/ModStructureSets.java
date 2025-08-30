package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.world.biome.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;

public class ModStructureSets {
    public static final ResourceKey<StructureSet> ANU_CASTLE_KEY = createKey("anu_castle");
    public static final ResourceKey<StructureSet> TREASURE_ROOM_KEY = createKey("treasure_room");
    public static final ResourceKey<StructureSet> AZTEC_TEMPLE_KEY = createKey("aztec_temple");
    public static final ResourceKey<StructureSet> AZTEC_WEAPON_SHOP_KEY = createKey("aztec_weapon_shop");
    public static final ResourceKey<StructureSet> EGYPTIAN_ACADEMY_KEY = createKey("egyptian_academy");
    public static final ResourceKey<StructureSet> HELL_BOAT_KEY = createKey("hell_boat");
    public static final ResourceKey<StructureSet> FOSSIL_SITE_KEY = createKey("fossil_site");
    public static final ResourceKey<StructureSet> TAR_SITE_KEY = createKey("tar_site");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, FossilMod.location(name));
    }

    public static void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        context.register(ANU_CASTLE_KEY, new StructureSet(structures.getOrThrow(ModStructures.ANU_CASTLE_KEY),
                new ConcentricRingsStructurePlacement(1, 1, 1, HolderSet.direct(biomes.getOrThrow(ModBiomes.ANU_LAIR_KEY)))));
        context.register(TREASURE_ROOM_KEY, new StructureSet(structures.getOrThrow(ModStructures.TREASURE_ROOM_KEY),
                new ConcentricRingsStructurePlacement(1, 1, 1, HolderSet.direct(biomes.getOrThrow(ModBiomes.TREASURE_ROOM_KEY)))));

        context.register(AZTEC_TEMPLE_KEY, new StructureSet(structures.getOrThrow(ModStructures.AZTEC_TEMPLE_KEY),
                new RandomSpreadStructurePlacement(16, 8, RandomSpreadType.LINEAR, 1770839626)));

        context.register(AZTEC_WEAPON_SHOP_KEY, new StructureSet(structures.getOrThrow(ModStructures.AZTEC_WEAPON_SHOP_KEY),
                new RandomSpreadStructurePlacement(16, 8, RandomSpreadType.LINEAR, 2011799054)));

        context.register(EGYPTIAN_ACADEMY_KEY, new StructureSet(structures.getOrThrow(ModStructures.EGYPTIAN_ACADEMY_KEY),
                new RandomSpreadStructurePlacement(48, 16, RandomSpreadType.LINEAR, 473717084)));

        context.register(HELL_BOAT_KEY, new StructureSet(structures.getOrThrow(ModStructures.HELL_BOAT_KEY),
                new RandomSpreadStructurePlacement(24, 5, RandomSpreadType.LINEAR, 92182587)));

        context.register(FOSSIL_SITE_KEY, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(ModStructures.FOSSIL_SITE_BADLANDS_KEY)),
                StructureSet.entry(structures.getOrThrow(ModStructures.FOSSIL_SITE_DESERT_KEY)),
                StructureSet.entry(structures.getOrThrow(ModStructures.FOSSIL_SITE_NORMAL_KEY)),
                StructureSet.entry(structures.getOrThrow(ModStructures.FOSSIL_SITE_SNOWY_KEY))),
                new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 1637066178)));

        context.register(TAR_SITE_KEY, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(ModStructures.TAR_SITE_TENT_KEY), 3),
                StructureSet.entry(structures.getOrThrow(ModStructures.TAR_SITE_TENT_SNOWY_KEY), 3),
                StructureSet.entry(structures.getOrThrow(ModStructures.TAR_SITE_SMALL_KEY), 3),
                StructureSet.entry(structures.getOrThrow(ModStructures.TAR_SITE_SMALL_SNOWY_KEY), 3),
                StructureSet.entry(structures.getOrThrow(ModStructures.TAR_SITE_LARGE_KEY)),
                StructureSet.entry(structures.getOrThrow(ModStructures.TAR_SITE_LARGE_SNOWY_KEY))),
                new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 1151905941)));
    }

}
