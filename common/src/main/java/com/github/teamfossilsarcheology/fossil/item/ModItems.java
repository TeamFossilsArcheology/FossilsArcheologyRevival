package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.ToyBase;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.TimePeriod;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(FossilMod.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> LASER_POINTER = ITEMS.register("laser_pointer",
            () -> new LaserPointerItem(new Item.Properties().stacksTo(1).arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> TAR_BUCKET = ITEMS.register("tar_bucket",
            () -> new ArchitecturyBucketItem(ModFluids.TAR, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET).arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> TAR_DROP = ITEMS.register("tar_drop",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<RecordItem> MUSIC_DISC_BONES = ITEMS.register("music_disc_bones",
            () -> CustomRecordItem.get(0, ModSounds.MUSIC_BONES, new Item.Properties().stacksTo(1).arch$tab(ModTabs.FA_OTHER_ITEM_TAB).rarity(Rarity.RARE), 196));
    public static final RegistrySupplier<RecordItem> MUSIC_DISC_ANU = ITEMS.register("music_disc_anu",
            () -> CustomRecordItem.get(1, ModSounds.MUSIC_ANU, new Item.Properties().stacksTo(1).arch$tab(ModTabs.FA_OTHER_ITEM_TAB).rarity(Rarity.RARE), 221));
    public static final RegistrySupplier<RecordItem> MUSIC_DISC_SCARAB = ITEMS.register("music_disc_scarab",
            () -> CustomRecordItem.get(2, ModSounds.MUSIC_SCARAB, new Item.Properties().stacksTo(1).arch$tab(ModTabs.FA_OTHER_ITEM_TAB).rarity(Rarity.RARE), 153));
    public static final RegistrySupplier<RecordItem> MUSIC_DISC_DISCOVERY = ITEMS.register("music_disc_discovery",
            () -> CustomRecordItem.get(3, ModSounds.MUSIC_DISCOVERY, new Item.Properties().stacksTo(1).arch$tab(ModTabs.FA_OTHER_ITEM_TAB).rarity(Rarity.RARE), 250));
    public static final RegistrySupplier<Item> BIO_FOSSIL = ITEMS.register("fossil_bio", () -> new FossilItem(TimePeriod.MESOZOIC));
    public static final RegistrySupplier<Item> PlANT_FOSSIL = ITEMS.register("fossil_plant", () -> new FossilItem(null));
    public static final RegistrySupplier<Item> SHALE_FOSSIL = ITEMS.register("fossil_shale", () -> new FossilItem(TimePeriod.PALEOZOIC));
    public static final RegistrySupplier<Item> TAR_FOSSIL = ITEMS.register("fossil_tar", () -> new FossilItem(TimePeriod.CENOZOIC));

    public static final RegistrySupplier<Item> RELIC_SCRAP = ITEMS.register("relic_scrap",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> STONE_TABLET = ITEMS.register("stone_tablet",
            () -> new StoneTabletItem(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> SKULL_STICK = ITEMS.register("skull_stick",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> BROKEN_SWORD = ITEMS.register("broken_sword", () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> BROKEN_HELMET = ITEMS.register("broken_helmet", () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> ANCIENT_SWORD = ITEMS.register("ancient_sword", () -> new AncientSwordItem(Tiers.IRON, 3, -2.4f));
    public static final RegistrySupplier<Item> ANCIENT_HELMET = ITEMS.register("ancient_helmet", () -> AncientHelmetItem.get(ModArmorMaterials.ANCIENT, ArmorItem.Type.HELMET, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> FROZEN_MEAT = ITEMS.register("frozen_meat",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> TOOTH_DAGGER = ITEMS.register("tooth_dagger",
            () -> new SwordItem(ModToolTiers.TOOTH_DAGGER, 3, -2.4f, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<JavelinItem> WOODEN_JAVELIN = ITEMS.register("wooden_javelin", () -> new JavelinItem(Tiers.WOOD));
    public static final RegistrySupplier<JavelinItem> STONE_JAVELIN = ITEMS.register("stone_javelin", () -> new JavelinItem(Tiers.STONE));
    public static final RegistrySupplier<JavelinItem> GOLD_JAVELIN = ITEMS.register("gold_javelin", () -> new JavelinItem(Tiers.GOLD));
    public static final RegistrySupplier<JavelinItem> IRON_JAVELIN = ITEMS.register("iron_javelin", () -> new JavelinItem(Tiers.IRON));
    public static final RegistrySupplier<JavelinItem> DIAMOND_JAVELIN = ITEMS.register("diamond_javelin", () -> new JavelinItem(Tiers.DIAMOND));
    public static final RegistrySupplier<JavelinItem> ANCIENT_JAVELIN = ITEMS.register("ancient_javelin",
            () -> new JavelinItem(Tiers.WOOD, true));
    public static final RegistrySupplier<SwordItem> SCARAB_SWORD = ITEMS.register("scarab_sword", () -> new SwordItem(ModToolTiers.SCARAB, 3, -2.4f, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<PickaxeItem> SCARAB_PICKAXE = ITEMS.register("scarab_pickaxe", () -> new PickaxeItem(ModToolTiers.SCARAB, 1, -2.8f, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<AxeItem> SCARAB_AXE = ITEMS.register("scarab_axe", () -> new AxeItem(ModToolTiers.SCARAB, 4, -3, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<ShovelItem> SCARAB_SHOVEL = ITEMS.register("scarab_shovel", () -> new ShovelItem(ModToolTiers.SCARAB, 1.5f, -3, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<HoeItem> SCARAB_HOE = ITEMS.register("scarab_hoe", () -> new HoeItem(ModToolTiers.SCARAB, -2, -1, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));

    public static final RegistrySupplier<Item> BONE_HELMET = ITEMS.register("bone_helmet",
            () -> new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.HELMET, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> BONE_CHESTPLATE = ITEMS.register("bone_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.CHESTPLATE, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> BONE_LEGGINGS = ITEMS.register("bone_leggings",
            () -> new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.LEGGINGS, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> BONE_BOOTS = ITEMS.register("bone_boots",
            () -> new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.BOOTS, new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> WHIP = ITEMS.register("whip", WhipItem::new);
    public static final RegistrySupplier<Item> CHICKEN_ESSENCE = ITEMS.register("essence_chicken",
            () -> new DescriptiveItem(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> STUNTED_ESSENCE = ITEMS.register("essence_stunted",
            () -> new DescriptiveItem(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> POTTERY_SHARD = ITEMS.register("pottery_shard",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> BIO_GOO = ITEMS.register("bio_goo",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> SCARAB_GEM = ITEMS.register("scarab_gem",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> AQUATIC_SCARAB_GEM = ITEMS.register("scarab_gem_aquatic",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> ANCIENT_KEY = ITEMS.register("ancient_key",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> ANCIENT_CLOCK = ITEMS.register("ancient_clock",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));
    public static final RegistrySupplier<Item> DINOPEDIA = ITEMS.register("dinopedia",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB)));

    public static final RegistrySupplier<Item> ELASMOTHERIUM_FUR = ITEMS.register("fur_elasmotherium",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> MAMMOTH_FUR = ITEMS.register("fur_mammoth",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> THERIZINOSAURUS_DOWN = ITEMS.register("fur_therizinosaurus",
            () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> FAILURESAURUS_FLESH = ITEMS.register("failuresaurus_flesh",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> MAGIC_CONCH = ITEMS.register("magic_conch",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> RAW_CHICKEN_SOUP = ITEMS.register("chicken_soup_raw",
            () -> new FoodBucketItem(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB).craftRemainder(Items.BUCKET).food(new FoodProperties.Builder().nutrition(4).saturationMod(2).build())));
    public static final RegistrySupplier<Item> COOKED_CHICKEN_SOUP = ITEMS.register("chicken_soup_cooked",
            () -> new FoodBucketItem(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB).craftRemainder(Items.BUCKET).food(new FoodProperties.Builder().nutrition(8).saturationMod(2).build())));
    public static final RegistrySupplier<Item> COOKED_EGG = ITEMS.register("cooked_egg",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB).food(new FoodProperties.Builder().nutrition(4).saturationMod(2).build())));
    public static final RegistrySupplier<Item> ARTIFICIAL_HONEYCOMB = ITEMS.register("artificial_honeycomb",
            () -> new ArtificialHoneycombItem(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));

    public static final RegistrySupplier<Item> FERN_SEED_FOSSIL = ITEMS.register("fossil_seed_fern",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> FERN_SEED = ITEMS.register("fern_seed",
            () -> new ItemNameBlockItem(ModBlocks.FERNS.get(), new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> CALAMITES_FOSSIL_SAPLING = ITEMS.register("fossil_sapling_calamites",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> CORDAITES_FOSSIL_SAPLING = ITEMS.register("fossil_sapling_cordaites",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> PALM_FOSSIL_SAPLING = ITEMS.register("fossil_sapling_palm",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> SIGILLARIA_FOSSIL_SAPLING = ITEMS.register("fossil_sapling_sigillaria",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    public static final RegistrySupplier<Item> TEMPSKYA_FOSSIL_SAPLING = ITEMS.register("fossil_sapling_tempskya",
            () -> new Item(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));

    public static final RegistrySupplier<SpawnEggItem> TAR_SLIME_SPAWN_EGG = registerSpawnEgg("spawn_egg_tar_slime", ModEntities.TAR_SLIME, 0X222222, 0x0B0B0B);
    public static final RegistrySupplier<SpawnEggItem> ANU_BOSS_SPAWN_EGG = registerSpawnEgg("spawn_egg_anu_boss", ModEntities.ANU_BOSS, 0X0F0F0F, 0XF72D00);
    public static final RegistrySupplier<SpawnEggItem> SENTRY_PIGLIN_SPAWN_EGG = registerSpawnEgg("spawn_egg_sentry_piglin", ModEntities.SENTRY_PIGLIN, 15373203, 0XD0A750);
    public static final RegistrySupplier<SpawnEggItem> FAILURESAURUS_SPAWN_EGG = registerSpawnEgg("spawn_egg_failuresaurus", ModEntities.FAILURESAURUS, 0X46DA9b, 0X257B47);

    public static final Map<DyeColor, RegistrySupplier<ToyBallItem>> TOY_BALLS = Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), ModItems::registerBall));
    public static final Map<String, RegistrySupplier<ToyTetheredLogItem>> TOY_TETHERED_LOGS = WoodType.values().filter(ToyBase::isVanillaWood).collect(Collectors.toMap(WoodType::name, ModItems::registerTetheredLog));
    public static final Map<String, RegistrySupplier<ToyScratchingPostItem>> TOY_SCRATCHING_POSTS = WoodType.values().filter(ToyBase::isVanillaWood).collect(Collectors.toMap(WoodType::name, ModItems::registerScratchingPost));

    private static <T extends Mob> RegistrySupplier<SpawnEggItem> registerSpawnEgg(String id, RegistrySupplier<EntityType<T>> type, int color1, int color2) {
        return ITEMS.register(id, () -> new ArchitecturySpawnEggItem(type, color1, color2, new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB)));
    }

    private static RegistrySupplier<ToyBallItem> registerBall(DyeColor color) {
        return ITEMS.register("toy_ball_" + color.getName(), () -> new ToyBallItem(color, new Item.Properties().arch$tab(ModTabs.FA_BLOCK_TAB)));
    }

    private static RegistrySupplier<ToyTetheredLogItem> registerTetheredLog(WoodType woodType) {
        return ITEMS.register("toy_tethered_log_" + woodType.name(), () -> new ToyTetheredLogItem(woodType, new Item.Properties().arch$tab(ModTabs.FA_BLOCK_TAB)));
    }

    private static RegistrySupplier<ToyScratchingPostItem> registerScratchingPost(WoodType woodType) {
        return ITEMS.register("toy_scratching_post_" + woodType.name(), () -> new ToyScratchingPostItem(woodType, new Item.Properties().arch$tab(ModTabs.FA_BLOCK_TAB)));
    }

    public static void register() {
        VanillaEntityInfo.register();
        PrehistoricEntityInfo.register();

        ITEMS.register();
    }
}