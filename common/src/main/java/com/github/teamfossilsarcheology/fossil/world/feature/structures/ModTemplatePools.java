package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
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

    public static final ResourceKey<StructureTemplatePool> TAR_SITE_LARGE_BASEMENT_KEY = createKey("tar_site/basement_pool_large");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_SMALL_BASEMENT_KEY = createKey("tar_site/basement_pool_small");

    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_KEY = createKey("tar_site/tent");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_BADLANDS_KEY = createKey("tar_site/tent_badlands");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_DESERT_KEY = createKey("tar_site/tent_desert");
    public static final ResourceKey<StructureTemplatePool> TAR_SITE_TENT_SMALL_KEY = createKey("tar_site/tent_options_small");

    public static final ResourceKey<StructureTemplatePool> ARCHEOLOGIST_KEY = createKey("villagers/archeologist");
    public static final ResourceKey<StructureTemplatePool> PALEONTOLOGIST_KEY = createKey("villagers/paleontologist");

    private static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registry.TEMPLATE_POOL_REGISTRY, FossilMod.location(name));
    }
}
