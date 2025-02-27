package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ModTemplatePools {
    public static final ResourceKey<StructureTemplatePool> ANU_CASTLE_NORTH_EAST = createKey("anu_castle/north_east_pool");
    public static final ResourceKey<StructureTemplatePool> ANU_CASTLE_SOUTH_EAST = createKey("anu_castle/south_east_pool");
    public static final ResourceKey<StructureTemplatePool> ANU_CASTLE_SOUTH_WEST = createKey("anu_castle/south_west_pool");
    public static final ResourceKey<StructureTemplatePool> ANU_CASTLE_START = createKey("anu_castle/starts");

    public static final ResourceKey<StructureTemplatePool> AZTEC_TEMPLE_KEY = createKey("aztec_temple");
    public static final ResourceKey<StructureTemplatePool> AZTEC_WEAPON_SHOP_KEY = createKey("aztec_weapon_shop");
    public static final ResourceKey<StructureTemplatePool> EGYPTIAN_ACADEMY_KEY = createKey("egyptian_academy");

    public static final ResourceKey<StructureTemplatePool> ARCHEO_PLAINS_BASEMENT_KEY = createKey("archeo_plains_basement_pool");
    public static final ResourceKey<StructureTemplatePool> ARCHEO_TAIGA_BASEMENT_KEY = createKey("archeo_taiga_basement_pool");
    public static final ResourceKey<StructureTemplatePool> PALEO_PLAINS_BASEMENT_KEY = createKey("paleo_plains_basement_pool");

    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_BADLANDS_START_KEY = createKey("fossil_site/start_badlands");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_DESERT_START_KEY = createKey("fossil_site/start_desert");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_NORMAL_START_KEY = createKey("fossil_site/start_normal");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_SNOWY_START_KEY = createKey("fossil_site/start_snowy");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_BADLANDS_BASEMENT_KEY = createKey("fossil_site/basement_pool_badlands");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_DESERT_BASEMENT_KEY = createKey("fossil_site/basement_pool_desert");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_NORMAL_BASEMENT_KEY = createKey("fossil_site/basement_pool_normal");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_SNOWY_BASEMENT_KEY = createKey("fossil_site/basement_pool_snowy");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_DESERT_TENT_KEY = createKey("fossil_site/tent_options_desert");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_NORMAL_TENT_KEY = createKey("fossil_site/tent_options_normal");
    public static final ResourceKey<StructureTemplatePool> FOSSIL_SITE_SNOWY_TENT_KEY = createKey("fossil_site/tent_options_snowy");

    public static final ResourceKey<StructureTemplatePool> TAR_SITE_LARGE_START_KEY = createKey("tar_site/start_large");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_LARGE_SNOWY_START_KEY = createKey("tar_site/start_large_snowy");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_SMALL_START_KEY = createKey("tar_site/start_small");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_SMALL_SNOWY_START_KEY = createKey("tar_site/start_small_snowy");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_START_KEY = createKey("tar_site/start_tent");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_SNOWY_START_KEY = createKey("tar_site/start_tent_snowy");

    public static final ResourceKey<StructureTemplatePool> TAR_SITE_LARGE_BASEMENT_KEY = createKey("tar_site/basement_pool_large");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_LARGE_SNOWY_BASEMENT_KEY = createKey("tar_site/basement_pool_large_snowy");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_SMALL_BASEMENT_KEY = createKey("tar_site/basement_pool_small");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_BASEMENT_KEY = createKey("tar_site/basement_pool_tent");

    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_KEY = createKey("tar_site/tent");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_BADLANDS_KEY = createKey("tar_site/tent_badlands");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_DESERT_KEY = createKey("tar_site/tent_desert");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_SMALL_KEY = createKey("tar_site/tent_options_small");

    public static final ResourceKey<StructureTemplatePool> ARCHEOLOGIST_KEY = createKey("villagers/archeologist");
    public static final ResourceKey<StructureTemplatePool> PALEONTOLOGIST_KEY = createKey("villagers/paleontologist");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> emptyPool = pools.getOrThrow(Pools.EMPTY);
        simple(ANU_CASTLE_START, "fossil:anu_castle_nw", context);
        simple(ANU_CASTLE_NORTH_EAST, "fossil:anu_castle_ne", context);
        simple(ANU_CASTLE_SOUTH_EAST, "fossil:anu_castle_se", context);
        simple(ANU_CASTLE_SOUTH_WEST, "fossil:anu_castle_sw", context);

        simple(ARCHEOLOGIST_KEY, "fossil:villagers/archeologist", context);
        simple(PALEONTOLOGIST_KEY, "fossil:villagers/paleontologist", context);

        simple(AZTEC_TEMPLE_KEY, "fossil:aztec/aztec_temple_ruined", context);
        simple(AZTEC_WEAPON_SHOP_KEY, "fossil:aztec/aztec_weapon_shop_ruined", context);
        simple(EGYPTIAN_ACADEMY_KEY, "fossil:egyptian_academy", context);

        simple(ARCHEO_PLAINS_BASEMENT_KEY, "fossil:houses/archeo_house_plains_base", context);
        simple(ARCHEO_TAIGA_BASEMENT_KEY, "fossil:houses/archeo_house_taiga_base", context);
        simple(PALEO_PLAINS_BASEMENT_KEY, "fossil:houses/paleo_house_plains_base", context);

        simple(FOSSIL_SITE_BADLANDS_START_KEY, "fossil:sites/fossil_site_badlands_top", context);
        simple(FOSSIL_SITE_DESERT_START_KEY, "fossil:sites/fossil_site_desert_top", context);
        simple(FOSSIL_SITE_NORMAL_START_KEY, "fossil:sites/fossil_site_normal_top", context);
        simple(FOSSIL_SITE_SNOWY_START_KEY, "fossil:sites/fossil_site_snowy_top", context);
        simple(FOSSIL_SITE_BADLANDS_BASEMENT_KEY, "fossil:sites/fossil_site_badlands_base", context);
        simple(FOSSIL_SITE_DESERT_BASEMENT_KEY, "fossil:sites/fossil_site_desert_base", context);
        simple(FOSSIL_SITE_NORMAL_BASEMENT_KEY, "fossil:sites/fossil_site_normal_base", context);
        simple(FOSSIL_SITE_SNOWY_BASEMENT_KEY, "fossil:sites/fossil_site_snowy_base", context);
        context.register(FOSSIL_SITE_DESERT_TENT_KEY, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single("fossil:sites/fossil_tent_option_desert_north"), 1),
                Pair.of(StructurePoolElement.single("fossil:sites/fossil_tent_option_desert_south"), 1)),
                StructureTemplatePool.Projection.RIGID));
        context.register(FOSSIL_SITE_NORMAL_TENT_KEY, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single("fossil:sites/fossil_tent_option_north"), 1),
                Pair.of(StructurePoolElement.single("fossil:sites/fossil_tent_option_west"), 1)),
                StructureTemplatePool.Projection.RIGID));
        context.register(FOSSIL_SITE_SNOWY_TENT_KEY, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single("fossil:sites/fossil_tent_option_snowy_north"), 1),
                Pair.of(StructurePoolElement.single("fossil:sites/fossil_tent_option_snowy_west"), 1)),
                StructureTemplatePool.Projection.RIGID));

        simple(TAR_SITE_LARGE_START_KEY, "fossil:sites/tar_site_large_top", context);
        simple(TAR_SITE_LARGE_SNOWY_START_KEY, "fossil:sites/tar_site_large_snowy_top", context);
        simple(TAR_SITE_SMALL_START_KEY, "fossil:sites/tar_site_small_top", context);
        simple(TAR_SITE_SMALL_SNOWY_START_KEY, "fossil:sites/tar_site_small_snowy_top", context);
        simple(TAR_SITE_TENT_START_KEY, "fossil:sites/tar_site_tent_top", context);
        simple(TAR_SITE_TENT_SNOWY_START_KEY, "fossil:sites/tar_site_tent_snowy_top", context);

        simple(TAR_SITE_LARGE_BASEMENT_KEY, "fossil:sites/tar_site_large_base", context);
        simple(TAR_SITE_LARGE_SNOWY_BASEMENT_KEY, "fossil:sites/tar_site_large_snowy_base", context);
        simple(TAR_SITE_SMALL_BASEMENT_KEY, "fossil:sites/tar_site_small_base", context);
        simple(TAR_SITE_TENT_BASEMENT_KEY, "fossil:sites/tar_site_tent_base", context);
        simple(TAR_SITE_TENT_KEY, "fossil:sites/fossil_site_tent_jigsaw", context);
        simple(TAR_SITE_TENT_BADLANDS_KEY, "fossil:sites/fossil_site_tent_badlands", context);
        simple(TAR_SITE_TENT_DESERT_KEY, "fossil:sites/fossil_site_tent_desert", context);
        context.register(TAR_SITE_TENT_SMALL_KEY, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single("fossil:sites/tar_tent_option_small_north"), 1),
                Pair.of(StructurePoolElement.single("fossil:sites/tar_tent_option_small_east"), 1),
                Pair.of(StructurePoolElement.single("fossil:sites/tar_tent_option_small_south"), 1),
                Pair.of(StructurePoolElement.single("fossil:sites/tar_tent_option_small_west"), 1)),
                StructureTemplatePool.Projection.RIGID));
    }

    private static void simple(ResourceKey<StructureTemplatePool> key, String location, BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> emptyPool = pools.getOrThrow(Pools.EMPTY);
        context.register(key, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single(location), 1)),
                StructureTemplatePool.Projection.RIGID));
    }

    private static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, FossilMod.location(name));
    }
}
