package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.material.ModFluids;
import com.fossil.fossil.sounds.ModSounds;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Fossil.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> TAR_BUCKET = ITEMS.register("tar_bucket",
            () -> new ArchitecturyBucketItem(ModFluids.TAR, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> TAR_DROP = ITEMS.register("tar_drop",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<RecordItem> RECORD_BONES = ITEMS.register("record_bones",
            () -> CustomRecordItem.get(0, ModSounds.MUSIC_BONES, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<RecordItem> RECORD_ANU = ITEMS.register("record_anu",
            () -> CustomRecordItem.get(1, ModSounds.MUSIC_ANU, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<RecordItem> RECORD_SCARAB = ITEMS.register("record_scarab",
            () -> CustomRecordItem.get(2, ModSounds.MUSIC_SCARAB, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<RecordItem> RECORD_DISCOVERY = ITEMS.register("record_discovery",
            () -> CustomRecordItem.get(3, ModSounds.MUSIC_DISCOVERY, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BIO_FOSSIL = ITEMS.register("fossil_bio",
            () -> new BioFossilItem(false));

    public static final RegistrySupplier<Item> TAR_FOSSIL = ITEMS.register("fossil_tar",
            () -> new BioFossilItem(true));
    public static final RegistrySupplier<Item> PlANT_FOSSIL = ITEMS.register("fossil_plant",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));

    public static final RegistrySupplier<Item> RELIC_SCRAP = ITEMS.register("relic_scrap",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> STONE_TABLET = ITEMS.register("stone_tablet",
            () -> new StoneTabletItem(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> SKULL_STICK = ITEMS.register("skull_stick",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BROKEN_SWORD = ITEMS.register("broken_sword", () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BROKEN_HELMET = ITEMS.register("broken_helmet", () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> ANCIENT_SWORD = ITEMS.register("ancient_sword", () -> new AncientSwordItem(Tiers.IRON, 3, -2.4f));
    public static final RegistrySupplier<Item> ANCIENT_HELMET = ITEMS.register("ancient_helmet", () -> new ArmorItem(ModArmorMaterials.ANCIENT, EquipmentSlot.HEAD, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> FROZEN_MEAT = ITEMS.register("frozen_meat",
            () -> new SwordItem(ModToolTiers.FROZEN_MEAT, 3, -2.4f, new Item.Properties().stacksTo(64).defaultDurability(4).tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> TOOTH_DAGGER = ITEMS.register("tooth_dagger",
            () -> new SwordItem(ModToolTiers.TOOTH_DAGGER, 3, -2.4f, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<JavelinItem> WOODEN_JAVELIN = ITEMS.register("wooden_javelin", () -> new JavelinItem(Tiers.WOOD));
    public static final RegistrySupplier<JavelinItem> STONE_JAVELIN = ITEMS.register("stone_javelin", () -> new JavelinItem(Tiers.STONE));
    public static final RegistrySupplier<JavelinItem> GOLD_JAVELIN = ITEMS.register("gold_javelin", () -> new JavelinItem(Tiers.GOLD));
    public static final RegistrySupplier<JavelinItem> IRON_JAVELIN = ITEMS.register("iron_javelin", () -> new JavelinItem(Tiers.IRON));
    public static final RegistrySupplier<JavelinItem> DIAMOND_JAVELIN = ITEMS.register("diamond_javelin", () -> new JavelinItem(Tiers.DIAMOND));
    public static final RegistrySupplier<JavelinItem> ANCIENT_JAVELIN = ITEMS.register("ancient_javelin",
            () -> new JavelinItem(Tiers.WOOD, true));
    public static final RegistrySupplier<SwordItem> SCARAB_SWORD = ITEMS.register("scarab_sword", () -> new SwordItem(ModToolTiers.SCARAB, 3, -2.4f, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<PickaxeItem> SCARAB_PICKAXE = ITEMS.register("scarab_pickaxe", () -> new PickaxeItem(ModToolTiers.SCARAB, 1, -2.8f, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<AxeItem> SCARAB_AXE = ITEMS.register("scarab_axe", () -> new AxeItem(ModToolTiers.SCARAB, 4, -3, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<ShovelItem> SCARAB_SHOVEL = ITEMS.register("scarab_shovel", () -> new ShovelItem(ModToolTiers.SCARAB, 1.5f, -3, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<HoeItem> SCARAB_HOE = ITEMS.register("scarab_hoe", () -> new HoeItem(ModToolTiers.SCARAB, -2, -1, new Item.Properties().tab(ModTabs.FAITEMTAB)));

    public static final RegistrySupplier<Item> BONE_HELMET = ITEMS.register("bone_helmet",
            () -> new ArmorItem(ModArmorMaterials.BONE, EquipmentSlot.HEAD, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BONE_CHESTPLATE = ITEMS.register("bone_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BONE, EquipmentSlot.CHEST, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BONE_LEGGINGS = ITEMS.register("bone_leggings",
            () -> new ArmorItem(ModArmorMaterials.BONE, EquipmentSlot.LEGS, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BONE_BOOTS = ITEMS.register("bone_boots",
            () -> new ArmorItem(ModArmorMaterials.BONE, EquipmentSlot.FEET, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> WHIP = ITEMS.register("whip", () -> new WhipItem(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> CHICKEN_ESSENCE = ITEMS.register("essence_chicken",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> STUNTED_ESSENCE = ITEMS.register("essence_stunted",
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
            () -> new FoodBucketItem(new Item.Properties().tab(ModTabs.FAITEMTAB).food(new FoodProperties.Builder().nutrition(4).saturationMod(2).build())));
    public static final RegistrySupplier<Item> COOKED_CHICKEN_SOUP = ITEMS.register("chicken_soup_cooked",
            () -> new FoodBucketItem(new Item.Properties().tab(ModTabs.FAITEMTAB).food(new FoodProperties.Builder().nutrition(8).saturationMod(2).build())));
    public static final RegistrySupplier<Item> COOKED_EGG = ITEMS.register("cooked_egg",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB).food(new FoodProperties.Builder().nutrition(4).saturationMod(2).build())));


    public static final RegistrySupplier<Item> FERN_SEED_FOSSIL = ITEMS.register("fossil_seed_fern",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> FERN_SEED = ITEMS.register("fern_seed",
            () -> new ItemNameBlockItem(ModBlocks.FERNS.get(), new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> PALAE_SAPLING_FOSSIL = ITEMS.register("fossil_sapling_palae",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> CALAMITES_SAPLING_FOSSIL = ITEMS.register("fossil_sapling_calamites",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> SIGILLARIA_SAPLING_FOSSIL = ITEMS.register("fossil_sapling_sigillaria",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> CORDAITES_SAPLING_FOSSIL = ITEMS.register("fossil_sapling_cordaites",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));

    public static final RegistrySupplier<SpawnEggItem> TAR_SLIME_SPAWN_EGG = registerSpawnEgg("tar_slime_spawn_egg", ModEntities.TAR_SLIME, 0X222222, 0x0B0B0B);

    public static final RegistrySupplier<SpawnEggItem> SENTRY_PIGLIN_SPAWN_EGG = registerSpawnEgg("sentry_piglin_spawn_egg", ModEntities.SENTRY_PIGLIN, 15373203, 0XD0A750);
    public static final Map<DyeColor, RegistrySupplier<ToyBallItem>> TOY_BALLS = Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), ModItems::registerBall));
    public static final Map<String, RegistrySupplier<ToyTetheredLogItem>> TOY_TETHERED_LOGS = WoodType.values().filter(woodType -> !woodType.name().contains(":")).collect(Collectors.toMap(WoodType::name, ModItems::registerTetheredLog));
    public static final Map<String, RegistrySupplier<ToyScratchingPostItem>> TOY_SCRATCHING_POSTS = WoodType.values().filter(woodType -> !woodType.name().contains(":")).collect(Collectors.toMap(WoodType::name, ModItems::registerScratchingPost));

    private static <T extends Mob> RegistrySupplier<SpawnEggItem> registerSpawnEgg(String id, RegistrySupplier<EntityType<T>> type, int color1, int color2) {
        return ITEMS.register(id, () -> new ArchitecturySpawnEggItem(type, color1, color2, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    }

    private static RegistrySupplier<ToyBallItem> registerBall(DyeColor color) {
        return ITEMS.register("toy_ball_" + color.getName(), () -> new ToyBallItem(color, new Item.Properties().tab(ModTabs.FAPARKTAB)));
    }

    private static RegistrySupplier<ToyTetheredLogItem> registerTetheredLog(WoodType woodType) {
        return ITEMS.register("toy_tethered_log_" + woodType.name(), () -> new ToyTetheredLogItem(woodType, new Item.Properties().tab(ModTabs.FAPARKTAB)));
    }

    private static RegistrySupplier<ToyScratchingPostItem> registerScratchingPost(WoodType woodType) {
        return ITEMS.register("toy_scratching_post_" + woodType.name(), () -> new ToyScratchingPostItem(woodType, new Item.Properties().tab(ModTabs.FAPARKTAB)));
    }

    public static void register() {
        PrehistoricEntityType.register();

        ITEMS.register();
    }
}