package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.material.ModFluids;
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
            () -> new StoneTabletItem(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> SKULL_STICK = ITEMS.register("skull_stick",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> TOOTH_DAGGER = ITEMS.register("tooth_dagger",
            () -> new SwordItem(ModToolTiers.TOOTH_DAGGER, 3, -2.4f, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<JavelinItem> WOODEN_JAVELIN = ITEMS.register("wooden_javelin", () -> new JavelinItem(Tiers.WOOD));
    public static final RegistrySupplier<JavelinItem> STONE_JAVELIN = ITEMS.register("stone_javelin", () -> new JavelinItem(Tiers.STONE));
    public static final RegistrySupplier<JavelinItem> GOLD_JAVELIN = ITEMS.register("gold_javelin", () -> new JavelinItem(Tiers.GOLD));
    public static final RegistrySupplier<JavelinItem> IRON_JAVELIN = ITEMS.register("iron_javelin", () -> new JavelinItem(Tiers.IRON));
    public static final RegistrySupplier<JavelinItem> DIAMOND_JAVELIN = ITEMS.register("diamond_javelin", () -> new JavelinItem(Tiers.DIAMOND));
    public static final RegistrySupplier<JavelinItem> ANCIENT_JAVELIN = ITEMS.register("ancient_javelin",
            () -> new JavelinItem(Tiers.WOOD, true));
    public static final RegistrySupplier<Item> BONE_HELMET = ITEMS.register("bone_helmet",
            () -> new ArmorItem(ModArmorMaterials.BONE, EquipmentSlot.HEAD, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BONE_CHESTPLATE = ITEMS.register("bone_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BONE, EquipmentSlot.CHEST, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BONE_LEGGINGS = ITEMS.register("bone_leggings",
            () -> new ArmorItem(ModArmorMaterials.BONE, EquipmentSlot.LEGS, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BONE_BOOTS = ITEMS.register("bone_boots",
            () -> new ArmorItem(ModArmorMaterials.BONE, EquipmentSlot.FEET, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> WHIP = ITEMS.register("whip", () -> new WhipItem(new Item.Properties().tab(ModTabs.FAITEMTAB)));
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


    public static final RegistrySupplier<Item> BROKEN_SWORD = ITEMS.register("broken_sword",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> BROKEN_HELMET = ITEMS.register("broken_helmet",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> ANCIENT_SWORD = ITEMS.register("ancient_sword",
            () -> new AncientSwordItem(Tiers.IRON, 3, -2.4f));
    public static final RegistrySupplier<Item> ANCIENT_HELMET = ITEMS.register("ancient_helmet",
            () -> new ArmorItem(ModArmorMaterials.ANCIENT_ARMOR, EquipmentSlot.HEAD, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    public static final RegistrySupplier<Item> FROZEN_MEAT = ITEMS.register("frozen_meat",
            () -> new SwordItem(ModToolTiers.FROZEN_MEAT, 3, -2.4f, new Item.Properties().stacksTo(64).tab(ModTabs.FAITEMTAB)));
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

    public static final RegistrySupplier<SpawnEggItem> ALLIGATOR_GAR_SPAWN_EGG = registerSpawnEgg("alligator_gar_spawn_egg", ModEntities.ALLIGATOR_GAR, 0X43462A, 0XAF4231);
    public static final RegistrySupplier<SpawnEggItem> ALLOSAURUS_SPAWN_EGG = registerSpawnEgg("allosaurus_spawn_egg", ModEntities.ALLOSAURUS, 0X907B6C, 0X5F422D);
    public static final RegistrySupplier<SpawnEggItem> ANKYLOSAURUS_SPAWN_EGG = registerSpawnEgg("ankylosaurus_spawn_egg", ModEntities.ANKYLOSAURUS, 0X8A5B49, 0X211B13);
    public static final RegistrySupplier<SpawnEggItem> ARTHROPLEURA_SPAWN_EGG = registerSpawnEgg("arthropleura_spawn_egg", ModEntities.ARTHROPLEURA, 0X2A292C, 0X97531F);
    public static final RegistrySupplier<SpawnEggItem> BRACHIOSAURUS_SPAWN_EGG = registerSpawnEgg("brachiosaurus_spawn_egg", ModEntities.BRACHIOSAURUS, 0X52523E, 0X222114);
    public static final RegistrySupplier<SpawnEggItem> CERATOSAURUS_SPAWN_EGG = registerSpawnEgg("ceratosaurus_spawn_egg", ModEntities.CERATOSAURUS, 0XB4B4A7, 0X776446);
    public static final RegistrySupplier<SpawnEggItem> CITIPATI_SPAWN_EGG = registerSpawnEgg("citipati_spawn_egg", ModEntities.CITIPATI, 0X5E4A3E, 0X667373);
    public static final RegistrySupplier<SpawnEggItem> COELACANTH_SPAWN_EGG = registerSpawnEgg("coelacanth_spawn_egg", ModEntities.COELACANTH, 0X363941, 0X9BA1A9);
    public static final RegistrySupplier<SpawnEggItem> COMPSOGNATHUS_SPAWN_EGG = registerSpawnEgg("compsognathus_spawn_egg", ModEntities.COMPSOGNATHUS, 0XCBC7C4, 0X3A312C);
    public static final RegistrySupplier<SpawnEggItem> CONFUCIUSORNIS_SPAWN_EGG = registerSpawnEgg("confuciusornis_spawn_egg", ModEntities.CONFUCIUSORNIS, 0XDAE5E9, 0X8B8B8D);
    public static final RegistrySupplier<SpawnEggItem> CRASSIGYRINUS_SPAWN_EGG = registerSpawnEgg("crassigyrinus_spawn_egg", ModEntities.CRASSIGYRINUS, 0XCA773A, 0X8F4B2D);
    public static final RegistrySupplier<SpawnEggItem> DEINONYCHUS_SPAWN_EGG = registerSpawnEgg("deinonychus_spawn_egg", ModEntities.DEINONYCHUS, 0X2B2424, 0XC8C8C8);
    public static final RegistrySupplier<SpawnEggItem> DILOPHOSAURUS_SPAWN_EGG = registerSpawnEgg("dilophosaurus_spawn_egg", ModEntities.DILOPHOSAURUS, 0X4E5931, 0XF25314);
    public static final RegistrySupplier<SpawnEggItem> DIPLOCAULUS_SPAWN_EGG = registerSpawnEgg("diplocaulus_spawn_egg", ModEntities.DIPLOCAULUS, 0XB0A380, 0X7C9694);
    public static final RegistrySupplier<SpawnEggItem> DIPLODOCUS_SPAWN_EGG = registerSpawnEgg("diplodocus_spawn_egg", ModEntities.DIPLODOCUS, 0X937373, 0XDAD8C7);
    public static final RegistrySupplier<SpawnEggItem> DODO_SPAWN_EGG = registerSpawnEgg("dodo_spawn_egg", ModEntities.DODO, 0X655751, 0XBEA47B);
    public static final RegistrySupplier<SpawnEggItem> DRYOSAURUS_SPAWN_EGG = registerSpawnEgg("dryosaurus_spawn_egg", ModEntities.DRYOSAURUS, 0X704C26, 0XC5C09A);
    public static final RegistrySupplier<SpawnEggItem> EDAPHOSAURUS_SPAWN_EGG = registerSpawnEgg("edaphosaurus_spawn_egg", ModEntities.EDAPHOSAURUS, 0X64673D, 0XB58E43);
    public static final RegistrySupplier<SpawnEggItem> ELASMOTHERIUM_SPAWN_EGG = registerSpawnEgg("elasmotherium_spawn_egg", ModEntities.ELASMOTHERIUM, 0X6B321B, 0X666666);
    public static final RegistrySupplier<SpawnEggItem> GALLIMIMUS_SPAWN_EGG = registerSpawnEgg("gallimimus_spawn_egg", ModEntities.GALLIMIMUS, 0X66412B, 0X5E2518);
    public static final RegistrySupplier<SpawnEggItem> GASTORNIS_SPAWN_EGG = registerSpawnEgg("gastornis_spawn_egg", ModEntities.GASTORNIS, 0X346C5E, 0XC8C8C8);
    public static final RegistrySupplier<SpawnEggItem> HENODUS_SPAWN_EGG = registerSpawnEgg("henodus_spawn_egg", ModEntities.HENODUS, 0X613C27, 0X9C8060);
    public static final RegistrySupplier<SpawnEggItem> ICHTYOSAURUS_SPAWN_EGG = registerSpawnEgg("ichtyosaurus_spawn_egg", ModEntities.ICHTYOSAURUS, 0X2A2632, 0XCEC296);
    public static final RegistrySupplier<SpawnEggItem> KELENKEN_SPAWN_EGG = registerSpawnEgg("kelenken_spawn_egg", ModEntities.KELENKEN, 0X392F24, 0XF2EBD5);
    public static final RegistrySupplier<SpawnEggItem> LIOPLEURODON_SPAWN_EGG = registerSpawnEgg("liopleurodon_spawn_egg", ModEntities.LIOPLEURODON, 0XBFC7C2, 0X1D211E);
    public static final RegistrySupplier<SpawnEggItem> MAMMOTH_SPAWN_EGG = registerSpawnEgg("mammoth_spawn_egg", ModEntities.MAMMOTH, 0X3D2E19, 0X24170B);
    public static final RegistrySupplier<SpawnEggItem> MEGALANIA_SPAWN_EGG = registerSpawnEgg("megalania_spawn_egg", ModEntities.MEGALANIA, 0X6D543D, 0XDCAE73);
    public static final RegistrySupplier<SpawnEggItem> MEGALOCEROS_SPAWN_EGG = registerSpawnEgg("megaloceros_spawn_egg", ModEntities.MEGALOCEROS, 0X5C2E1A, 0X8E5A3B);
    public static final RegistrySupplier<SpawnEggItem> MEGALODON_SPAWN_EGG = registerSpawnEgg("megalodon_spawn_egg", ModEntities.MEGALODON, 0X697B7E, 0XD0D5D5);
    public static final RegistrySupplier<SpawnEggItem> MEGALOGRAPTUS_SPAWN_EGG = registerSpawnEgg("megalograptus_spawn_egg", ModEntities.MEGALOGRAPTUS, 0XB26F45, 0X713719);
    public static final RegistrySupplier<SpawnEggItem> MEGANEURA_SPAWN_EGG = registerSpawnEgg("meganeura_spawn_egg", ModEntities.MEGANEURA, 0X6A4C3F, 0XE0B45A);
    public static final RegistrySupplier<SpawnEggItem> MOSASAURUS_SPAWN_EGG = registerSpawnEgg("mosasaurus_spawn_egg", ModEntities.MOSASAURUS, 0X888D90, 0X3A4C52);
    public static final RegistrySupplier<SpawnEggItem> NAUTILUS_SPAWN_EGG = registerSpawnEgg("nautilus_spawn_egg", ModEntities.NAUTILUS, 0XC55F47, 0XF5F5F5);
    public static final RegistrySupplier<SpawnEggItem> ORNITHOLESTES_SPAWN_EGG = registerSpawnEgg("ornitholestes_spawn_egg", ModEntities.ORNITHOLESTES, 0X2F221A, 0XC43824);
    public static final RegistrySupplier<SpawnEggItem> PACHYCEPHALOSAURUS_SPAWN_EGG = registerSpawnEgg("pachycephalosaurus_spawn_egg", ModEntities.PACHYCEPHALOSAURUS, 0XB6A989, 0X7D5E3A);
    public static final RegistrySupplier<SpawnEggItem> PARASAUROLOPHUS_SPAWN_EGG = registerSpawnEgg("parasaurolophus_spawn_egg", ModEntities.PARASAUROLOPHUS, 0X7E8E30, 0X4C5438);
    public static final RegistrySupplier<SpawnEggItem> PHORUSRHACOS_SPAWN_EGG = registerSpawnEgg("phorusrhacos_spawn_egg", ModEntities.PHORUSRHACOS, 0X5F4E3E, 0XD4D4D4);
    public static final RegistrySupplier<SpawnEggItem> PLATYBELODON_SPAWN_EGG = registerSpawnEgg("platybelodon_spawn_egg", ModEntities.PLATYBELODON, 0X8B6551, 0X62473A);
    public static final RegistrySupplier<SpawnEggItem> QUAGGA_SPAWN_EGG = registerSpawnEgg("quagga_spawn_egg", ModEntities.QUAGGA, 0X763C24, 0XD3B9AB);
    public static final RegistrySupplier<SpawnEggItem> SARCOSUCHUS_SPAWN_EGG = registerSpawnEgg("sarcosuchus_spawn_egg", ModEntities.SARCOSUCHUS, 0X4B4929, 0X8D8C65);
    public static final RegistrySupplier<SpawnEggItem> SMILODON_SPAWN_EGG = registerSpawnEgg("smilodon_spawn_egg", ModEntities.SMILODON, 0XB88C64, 0XECDFCE);
    public static final RegistrySupplier<SpawnEggItem> SPINOSAURUS_SPAWN_EGG = registerSpawnEgg("spinosaurus_spawn_egg", ModEntities.SPINOSAURUS, 0X84512A, 0X562F20);
    public static final RegistrySupplier<SpawnEggItem> STEGOSAURUS_SPAWN_EGG = registerSpawnEgg("stegosaurus_spawn_egg", ModEntities.STEGOSAURUS, 0X9C8138, 0X651817);
    public static final RegistrySupplier<SpawnEggItem> STURGEON_SPAWN_EGG = registerSpawnEgg("sturgeon_spawn_egg", ModEntities.STURGEON, 0X655D5B, 0XE6E3E3);
    public static final RegistrySupplier<SpawnEggItem> THERIZINOSAURUS_SPAWN_EGG = registerSpawnEgg("therizinosaurus_spawn_egg", ModEntities.THERIZINOSAURUS, 0X322212, 0XCA9C72);
    public static final RegistrySupplier<SpawnEggItem> TIKTAALIK_SPAWN_EGG = registerSpawnEgg("tiktaalik_spawn_egg", ModEntities.TIKTAALIK, 0X6A5A1A, 0XD7CF99);
    public static final RegistrySupplier<SpawnEggItem> TITANIS_SPAWN_EGG = registerSpawnEgg("titanis_spawn_egg", ModEntities.TITANIS, 0X484848, 0XEFEFEF);
    public static final RegistrySupplier<SpawnEggItem> TRICERATOPS_SPAWN_EGG = registerSpawnEgg("triceratops_spawn_egg", ModEntities.TRICERATOPS, 0X64352D, 0X251A17);
    public static final RegistrySupplier<SpawnEggItem> TROPEOGNATHUS_SPAWN_EGG = registerSpawnEgg("tropeognathus_spawn_egg", ModEntities.TROPEOGNATHUS, 0XD6D6D6, 0X3B3B3B);
    public static final RegistrySupplier<SpawnEggItem> TYRANNOSAURUS_SPAWN_EGG = registerSpawnEgg("tyrannosaurus_spawn_egg", ModEntities.TYRANNOSAURUS, 0X9D8A74, 0X4C3116);
    public static final RegistrySupplier<SpawnEggItem> VELOCIRAPTOR_SPAWN_EGG = registerSpawnEgg("velociraptor_spawn_egg", ModEntities.VELOCIRAPTOR, 0X4A0D04, 0XC9C9C9);

    public static final RegistrySupplier<SpawnEggItem> TAR_SLIME_SPAWN_EGG = registerSpawnEgg("tar_slime_spawn_egg", ModEntities.TAR_SLIME, 0X222222, 0x0B0B0B);

    public static final RegistrySupplier<SpawnEggItem> SENTRY_PIGLIN_SPAWN_EGG = registerSpawnEgg("sentry_piglin_spawn_egg", ModEntities.SENTRY_PIGLIN, 15373203, 0XD0A750);

    private static <T extends Mob> RegistrySupplier<SpawnEggItem> registerSpawnEgg(String id, RegistrySupplier<EntityType<T>> type, int color1, int color2) {
        return ITEMS.register(id, () -> new ArchitecturySpawnEggItem(type, color1, color2, new Item.Properties().tab(ModTabs.FAITEMTAB)));
    }

    public static final Map<DyeColor, RegistrySupplier<ToyBallItem>> TOY_BALLS = Arrays.stream(DyeColor.values()).collect(Collectors.toMap(Function.identity(), ModItems::registerBall));
    public static final Map<String, RegistrySupplier<ToyTetheredLogItem>> TOY_TETHERED_LOGS = WoodType.values().filter(woodType -> !woodType.name().contains(":")).collect(Collectors.toMap(WoodType::name, ModItems::registerTetheredLog));
    public static final Map<String, RegistrySupplier<ToyScratchingPostItem>> TOY_SCRATCHING_POSTS = WoodType.values().filter(woodType -> !woodType.name().contains(":")).collect(Collectors.toMap(WoodType::name, ModItems::registerScratchingPost));

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