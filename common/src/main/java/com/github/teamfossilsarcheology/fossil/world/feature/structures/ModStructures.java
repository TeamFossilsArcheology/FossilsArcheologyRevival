package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.world.biome.ModBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Arrays;
import java.util.Map;

public class ModStructures {
    public static final ResourceKey<Structure> ANU_CASTLE_KEY = createKey("anu_castle");
    public static final ResourceKey<Structure> HELL_BOAT_KEY = createKey("hell_boat");
    public static final ResourceKey<Structure> TREASURE_ROOM_KEY = createKey("treasure_room");

    public static final ResourceKey<Structure> AZTEC_TEMPLE_KEY = createKey("aztec_temple");
    public static final ResourceKey<Structure> AZTEC_WEAPON_SHOP_KEY = createKey("aztec_weapon_shop");
    public static final ResourceKey<Structure> EGYPTIAN_ACADEMY_KEY = createKey("egyptian_academy");
    public static final ResourceKey<Structure> FOSSIL_SITE_BADLANDS_KEY = createKey("fossil_site_badlands");
    public static final ResourceKey<Structure> FOSSIL_SITE_DESERT_KEY = createKey("fossil_site_desert");
    public static final ResourceKey<Structure> FOSSIL_SITE_NORMAL_KEY = createKey("fossil_site_normal");
    public static final ResourceKey<Structure> FOSSIL_SITE_SNOWY_KEY = createKey("fossil_site_snowy");
    public static final ResourceKey<Structure> TAR_SITE_LARGE_KEY = createKey("tar_site_large");
    public static final ResourceKey<Structure> TAR_SITE_LARGE_SNOWY_KEY = createKey("tar_site_large_snowy");
    public static final ResourceKey<Structure> TAR_SITE_SMALL_KEY = createKey("tar_site_small");
    public static final ResourceKey<Structure> TAR_SITE_SMALL_SNOWY_KEY = createKey("tar_site_small_snowy");
    public static final ResourceKey<Structure> TAR_SITE_TENT_KEY = createKey("tar_site_tent");
    public static final ResourceKey<Structure> TAR_SITE_TENT_SNOWY_KEY = createKey("tar_site_tent_snowy");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, FossilMod.location(name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templates = context.lookup(Registries.TEMPLATE_POOL);
        context.register(ANU_CASTLE_KEY, new AnuCastleStructure(
                new Structure.StructureSettings(HolderSet.direct(biomes.getOrThrow(ModBiomes.ANU_LAIR_KEY)), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));

        context.register(HELL_BOAT_KEY, new HellBoatStructure(
                new Structure.StructureSettings(biomes.getOrThrow(BiomeTags.IS_NETHER), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE),
                UniformHeight.of(VerticalAnchor.absolute(30), VerticalAnchor.absolute(30))));

        context.register(TREASURE_ROOM_KEY, new TreasureRoomStructure(
                new Structure.StructureSettings(HolderSet.direct(biomes.getOrThrow(ModBiomes.TREASURE_ROOM_KEY)), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));

        simple(AZTEC_TEMPLE_KEY, ModTemplatePools.AZTEC_TEMPLE_KEY, context, BiomeTags.IS_JUNGLE);
        simple(AZTEC_WEAPON_SHOP_KEY, ModTemplatePools.AZTEC_WEAPON_SHOP_KEY, context, BiomeTags.IS_JUNGLE);
        simple(EGYPTIAN_ACADEMY_KEY, ModTemplatePools.EGYPTIAN_ACADEMY_KEY, context, BiomeTags.HAS_DESERT_PYRAMID);

        sites(FOSSIL_SITE_BADLANDS_KEY, 2, ModTemplatePools.FOSSIL_SITE_BADLANDS_START_KEY, context, Biomes.BADLANDS);
        sites(FOSSIL_SITE_DESERT_KEY, 3, ModTemplatePools.FOSSIL_SITE_DESERT_START_KEY, context, Biomes.DESERT);
        sites(FOSSIL_SITE_NORMAL_KEY, 3, ModTemplatePools.FOSSIL_SITE_NORMAL_START_KEY, context, Biomes.PLAINS, Biomes.SAVANNA, Biomes.TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        sites(FOSSIL_SITE_SNOWY_KEY, 3, ModTemplatePools.FOSSIL_SITE_SNOWY_START_KEY, context, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA);

        sites(TAR_SITE_LARGE_KEY, 3, ModTemplatePools.TAR_SITE_LARGE_START_KEY, context, Biomes.PLAINS, Biomes.SAVANNA, Biomes.SWAMP);
        sites(TAR_SITE_LARGE_SNOWY_KEY, 3, ModTemplatePools.TAR_SITE_LARGE_SNOWY_START_KEY, context, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA);
        sites(TAR_SITE_SMALL_KEY, 3, ModTemplatePools.TAR_SITE_SMALL_START_KEY, context, Biomes.PLAINS, Biomes.SAVANNA);
        sites(TAR_SITE_SMALL_SNOWY_KEY, 3, ModTemplatePools.TAR_SITE_SMALL_SNOWY_START_KEY, context, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA);
        sites(TAR_SITE_TENT_KEY, 3, ModTemplatePools.TAR_SITE_TENT_START_KEY, context, Biomes.PLAINS, Biomes.SAVANNA, Biomes.MEADOW);
        sites(TAR_SITE_TENT_SNOWY_KEY, 3, ModTemplatePools.TAR_SITE_TENT_SNOWY_START_KEY, context, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA);
    }

    private static void sites(ResourceKey<Structure> key, int size, ResourceKey<StructureTemplatePool> pool, BootstapContext<Structure> context, ResourceKey<Biome>... biome) {
        var biomes = Arrays.stream(biome).map(biomeResourceKey -> context.lookup(Registries.BIOME).getOrThrow(biomeResourceKey)).toArray(Holder[]::new);
        context.register(key, new JigsawStructure(structure(HolderSet.direct(biomes), TerrainAdjustment.NONE),
                context.lookup(Registries.TEMPLATE_POOL).getOrThrow(pool),
                size, ConstantHeight.of(VerticalAnchor.absolute(0)),
                true, Heightmap.Types.WORLD_SURFACE_WG));
    }

    private static void simple(ResourceKey<Structure> key, ResourceKey<StructureTemplatePool> pool, BootstapContext<Structure> context, TagKey<Biome> biome) {
        context.register(key, new JigsawStructure(structure(context.lookup(Registries.BIOME).getOrThrow(biome), TerrainAdjustment.NONE),
                context.lookup(Registries.TEMPLATE_POOL).getOrThrow(pool),
                1, ConstantHeight.of(VerticalAnchor.absolute(0)),
                false, Heightmap.Types.WORLD_SURFACE_WG));
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> biomes, Map<MobCategory, StructureSpawnOverride> spawnOverrides, GenerationStep.Decoration step, TerrainAdjustment terrainAdaptation) {
        return new Structure.StructureSettings(biomes, spawnOverrides, step, terrainAdaptation);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> biomes, GenerationStep.Decoration step, TerrainAdjustment terrainAdaptation) {
        return structure(biomes, Map.of(), step, terrainAdaptation);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> biomes, TerrainAdjustment terrainAdaptation) {
        return structure(biomes, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, terrainAdaptation);
    }
}
