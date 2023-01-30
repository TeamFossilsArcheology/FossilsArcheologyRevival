package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.material.ModFluids;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Fossil.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> TAR_BUCKET = ITEMS.register("tar_bucket",
            () -> new ArchitecturyBucketItem(ModFluids.TAR, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> TAR_DROP = ITEMS.register("tar_drop",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> AMBER = ITEMS.register("amber",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> DOMINICAN_AMBER = ITEMS.register("amber_dominican",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BIO_FOSSIL = ITEMS.register("fossil_bio",
            () -> new BioFossilItem(false));

    public static final RegistrySupplier<Item> TAR_FOSSIL = ITEMS.register("fossil_tar",
            () -> new BioFossilItem(true));
    public static final RegistrySupplier<Item> PlANT_FOSSIL = ITEMS.register("fossil_plant",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));

    public static final RegistrySupplier<Item> RELIC_SCRAP = ITEMS.register("relic_scrap",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> STONE_TABLET = ITEMS.register("stone_tablet",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> SKULL_STICK = ITEMS.register("skull_stick",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));

    public static final RegistrySupplier<Item> CHICKEN_ESSENCE = ITEMS.register("chicken_essence",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> COOKED_NAUTILUS = ITEMS.register("nautilus_cooked",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB).food(new FoodProperties.Builder().nutrition(8).saturationMod(2).build())));

    public static final RegistrySupplier<Item> POTTERY_SHARD = ITEMS.register("pottery_shard",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BIO_GOO = ITEMS.register("bio_goo",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> SCARAB_GEM = ITEMS.register("scarab_gem",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> AQUATIC_SCARAB_GEM = ITEMS.register("scarab_gem_aquatic",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> ANCIENT_KEY = ITEMS.register("ancient_key",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> ANCIENT_CLOCK = ITEMS.register("ancient_clock",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> DINOPEDIA = ITEMS.register("dinopedia",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> FAILURESAURUS_FLESH = ITEMS.register("failuresaurus_flesh",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> RAW_CHICKEN_SOUP = ITEMS.register("chicken_soup_raw",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB).food(new FoodProperties.Builder().nutrition(4).saturationMod(2).build())));
    public static final RegistrySupplier<Item> COOKED_CHICKEN_SOUP = ITEMS.register("chicken_soup_cooked",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB).food(new FoodProperties.Builder().nutrition(8).saturationMod(2).build())));
    public static final RegistrySupplier<Item> COOKED_EGG = ITEMS.register("cooked_egg",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB).food(new FoodProperties.Builder().nutrition(4).saturationMod(2).build())));


    public static final RegistrySupplier<Item> FERN_SEED_FOSSIL = ITEMS.register("fossil_seed_fern",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> PALAE_SAPLING_FOSSIL = ITEMS.register("fossil_sapling_palae",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> CALAMITES_SAPLING_FOSSIL = ITEMS.register("fossil_sapling_calamites",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> SIGILLARIA_SAPLING_FOSSIL = ITEMS.register("fossil_sapling_sigillaria",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> CORDAITES_SAPLING_FOSSIL = ITEMS.register("fossil_sapling_cordaites",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));

    public static final RegistrySupplier<SpawnEggItem> TRICERATOPS_SPAWN_EGG = ITEMS.register("triceratops_spawn_egg",
            () -> new ArchitecturySpawnEggItem(ModEntities.TRICERATOPS, 0x64352D, 0x251A17, new Item.Properties().tab(ModTabs.FAITEMTAB))
    );

    public static final RegistrySupplier<SpawnEggItem> THERIZINOSAURUS_SPAWN_EGG = ITEMS.register("therizinosaurus_spawn_egg",
            () -> new ArchitecturySpawnEggItem(ModEntities.THERIZINOSAURUS, 0x64352D, 0x251A17, new Item.Properties().tab(ModTabs.FAITEMTAB))
    );
    public static final RegistrySupplier<SpawnEggItem> TAR_SLIME_SPAWN_EGG = ITEMS.register("tar_slime_spawn_egg",
            () -> new ArchitecturySpawnEggItem(ModEntities.TAR_SLIME, 0X222222, 0x0B0B0B, new Item.Properties().tab(ModTabs.FAITEMTAB))
    );

    public static void register() {
        ITEMS.register();
        PrehistoricEntityType.register();
    }
}