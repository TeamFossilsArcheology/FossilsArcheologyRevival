package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.item.*;
import com.github.teamfossilsarcheology.fossil.tags.ModEntityTypeTags;
import com.github.teamfossilsarcheology.fossil.util.TimePeriod;
import dev.architectury.core.item.ArchitecturyMobBucketItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.InfoFlag.BUCKETABLE;
import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.InfoFlag.bucketable;
import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType.*;
import static com.github.teamfossilsarcheology.fossil.util.TimePeriod.*;

/**
 * Stores entity info that is needed to dynamically create mob items
 */
public enum PrehistoricEntityInfo implements EntityInfo {
    //TODO: Replace mobtype with smth better
    ALLIGATOR_GAR(ModEntities.ALLIGATOR_GAR, FISH, MESOZOIC, 0X43462A, 0XAF4231, bucketable()),
    ALLOSAURUS(ModEntities.ALLOSAURUS, DINOSAUR, MESOZOIC, 0X907B6C, 0X5F422D),
    ANKYLOSAURUS(ModEntities.ANKYLOSAURUS, DINOSAUR, MESOZOIC, 0X8A5B49, 0X211B13),
    AQUILOLAMNA(ModEntities.AQUILOLAMNA, DINOSAUR_AQUATIC, MESOZOIC, 0x9C8278, 0X281F20, bucketable()),
    ARTHROPLEURA(ModEntities.ARTHROPLEURA, ARTHROPOD, PALEOZOIC, 0X2A292C, 0X97531F),
    BRACHIOSAURUS(ModEntities.BRACHIOSAURUS, DINOSAUR, MESOZOIC, 0X52523E, 0X222114),
    CERATOSAURUS(ModEntities.CERATOSAURUS, DINOSAUR, MESOZOIC, 0XB4B4A7, 0X776446),
    CITIPATI(ModEntities.CITIPATI, DINOSAUR, MESOZOIC, 0X5E4A3E, 0X667373),
    COELACANTH(ModEntities.COELACANTH, FISH, MESOZOIC, 0X363941, 0X9BA1A9, bucketable()),
    COMPSOGNATHUS(ModEntities.COMPSOGNATHUS, DINOSAUR, MESOZOIC, 0XCBC7C4, 0X3A312C),
    CONFUCIUSORNIS(ModEntities.CONFUCIUSORNIS, BIRD, MESOZOIC, 0XDAE5E9, 0X8B8B8D),
    CRASSIGYRINUS(ModEntities.CRASSIGYRINUS, DINOSAUR_FISH, PALEOZOIC, 0XCA773A, 0X8F4B2D, bucketable()),
    DEINONYCHUS(ModEntities.DEINONYCHUS, DINOSAUR, MESOZOIC, 0X2B2424, 0XC8C8C8),
    DICRANURUS(ModEntities.DICRANURUS, ARTHROPOD, PALEOZOIC, 0X998042, 0X1D1D1D, bucketable()),
    DILOPHOSAURUS(ModEntities.DILOPHOSAURUS, DINOSAUR, MESOZOIC, 0X4E5931, 0XF25314),
    DIMETRODON(ModEntities.DIMETRODON, DINOSAUR, PALEOZOIC, 0X422E23, 0X914328),
    DIMORPHODON(ModEntities.DIMORPHODON, DINOSAUR, MESOZOIC, 0XD6D6D6, 0X3B3B3B),
    DIPLOCAULUS(ModEntities.DIPLOCAULUS, DINOSAUR_FISH, PALEOZOIC, 0XB0A380, 0X7C9694, bucketable()),
    DIPLODOCUS(ModEntities.DIPLODOCUS, DINOSAUR, MESOZOIC, 0X937373, 0XDAD8C7),
    DODO(ModEntities.DODO, BIRD, CENOZOIC, 0X704C26, 0XC5C09A),
    DRYOSAURUS(ModEntities.DRYOSAURUS, DINOSAUR, MESOZOIC, 0X655751, 0XBEA47B),
    EDAPHOSAURUS(ModEntities.EDAPHOSAURUS, DINOSAUR, PALEOZOIC, 0X64673D, 0XB58E43),
    ELASMOTHERIUM(ModEntities.ELASMOTHERIUM, MAMMAL, CENOZOIC, 0X6B321B, 0X666666),
    GALLIMIMUS(ModEntities.GALLIMIMUS, DINOSAUR, MESOZOIC, 0X66412B, 0X5E2518),
    GASTORNIS(ModEntities.GASTORNIS, BIRD, CENOZOIC, 0X346C5E, 0XC8C8C8),
    HENODUS(ModEntities.HENODUS, DINOSAUR_AQUATIC, MESOZOIC, 0X613C27, 0X9C8060, bucketable()),
    ICHTHYOSAURUS(ModEntities.ICHTHYOSAURUS, DINOSAUR_AQUATIC, MESOZOIC, 0X2A2632, 0XCEC296, bucketable()),
    KELENKEN(ModEntities.KELENKEN, BIRD, CENOZOIC, 0X392F24, 0XF2EBD5),
    LIOPLEURODON(ModEntities.LIOPLEURODON, DINOSAUR_AQUATIC, MESOZOIC, 0XBFC7C2, 0X1D211E),
    LONCHODOMAS(ModEntities.LONCHODOMAS, ARTHROPOD, PALEOZOIC, 0X904136, 0XF3B2A8, bucketable()),
    MAMMOTH(ModEntities.MAMMOTH, MAMMAL, CENOZOIC, 0X3D2E19, 0X24170B),
    MEGALANIA(ModEntities.MEGALANIA, DINOSAUR, CENOZOIC, 0X6D543D, 0XDCAE73),
    MEGALOCEROS(ModEntities.MEGALOCEROS, MAMMAL, CENOZOIC, 0X5C2E1A, 0X8E5A3B),
    MEGALODON(ModEntities.MEGALODON, DINOSAUR_AQUATIC, CENOZOIC, 0X697B7E, 0XD0D5D5),
    MEGALOGRAPTUS(ModEntities.MEGALOGRAPTUS, ARTHROPOD, PALEOZOIC, 0XB26F45, 0X713719, bucketable()),
    MEGANEURA(ModEntities.MEGANEURA, ARTHROPOD, PALEOZOIC, 0X6A4C3F, 0XE0B45A),
    MOSASAURUS(ModEntities.MOSASAURUS, DINOSAUR_AQUATIC, MESOZOIC, 0X888D90, 0X3A4C52),
    NAUTILUS(ModEntities.NAUTILUS, FISH, MESOZOIC, 0XC55F47, 0XF5F5F5, bucketable()),
    ORNITHOLESTES(ModEntities.ORNITHOLESTES, DINOSAUR, MESOZOIC, 0X2F221A, 0XC43824),
    PACHYCEPHALOSAURUS(ModEntities.PACHYCEPHALOSAURUS, DINOSAUR, MESOZOIC, 0XB6A989, 0X7D5E3A),
    PACHYRHINOSAURUS(ModEntities.PACHYRHINOSAURUS, DINOSAUR, MESOZOIC, 0XB6846A, 0X87A75C),
    PARASAUROLOPHUS(ModEntities.PARASAUROLOPHUS, DINOSAUR, MESOZOIC, 0X888D90, 0X3A4C52),
    PHORUSRHACOS(ModEntities.PHORUSRHACOS, BIRD, CENOZOIC, 0X5F4E3E, 0XD4D4D4),
    PLATYBELODON(ModEntities.PLATYBELODON, MAMMAL, CENOZOIC, 0X8B6551, 0X62473A),
    PLESIOSAURUS(ModEntities.PLESIOSAURUS, DINOSAUR_AQUATIC, MESOZOIC, 0XE4A86E, 0XE17920, bucketable()),
    POSTOSUCHUS(ModEntities.POSTOSUCHUS, DINOSAUR, MESOZOIC, 0X4B4929, 0XD4D29F),
    PROTOCERATOPS(ModEntities.PROTOCERATOPS, DINOSAUR, MESOZOIC, 0XE3d7Af, 0XB4C6E2),
    PSITTACOSAURUS(ModEntities.PSITTACOSAURUS, DINOSAUR, MESOZOIC, 0X9F7065, 0X442C23),
    PTERANODON(ModEntities.PTERANODON, DINOSAUR, MESOZOIC, 0XD6D6D6, 0X3B3B3B),
    QUAGGA(ModEntities.QUAGGA, MAMMAL, CENOZOIC, 0X763C24, 0XD3B9AB),
    QUETZALCOATLUS(ModEntities.QUETZALCOATLUS, DINOSAUR, MESOZOIC, 0X1E130C, 0X72C6CA),
    SARCOSUCHUS(ModEntities.SARCOSUCHUS, DINOSAUR, MESOZOIC, 0X4B4929, 0X8D8C65),
    SCOTOHARPES(ModEntities.SCOTOHARPES, ARTHROPOD, PALEOZOIC, 0X844343, 0X2F2424, bucketable()),
    SMILODON(ModEntities.SMILODON, MAMMAL, CENOZOIC, 0XB88C64, 0XECDFCE),
    SPINOSAURUS(ModEntities.SPINOSAURUS, DINOSAUR, MESOZOIC, 0X84512A, 0X562F20),
    STEGOSAURUS(ModEntities.STEGOSAURUS, DINOSAUR, MESOZOIC, 0X9C8138, 0X651817),
    STURGEON(ModEntities.STURGEON, FISH, MESOZOIC, 0X655D5B, 0XE6E3E3, bucketable()),
    THERIZINOSAURUS(ModEntities.THERIZINOSAURUS, DINOSAUR, MESOZOIC, 0X322212, 0XCA9C72),
    TIKTAALIK(ModEntities.TIKTAALIK, DINOSAUR_FISH, PALEOZOIC, 0X6A5A1A, 0XD7CF99, bucketable()),
    TITANIS(ModEntities.TITANIS, BIRD, CENOZOIC, 0X484848, 0XEFEFEF),
    TRICERATOPS(ModEntities.TRICERATOPS, DINOSAUR, MESOZOIC, 0X64352D, 0X251A17),
    TYRANNOSAURUS(ModEntities.TYRANNOSAURUS, DINOSAUR, MESOZOIC, 0X9D8A74, 0X4C3116),
    VELOCIRAPTOR(ModEntities.VELOCIRAPTOR, DINOSAUR, MESOZOIC, 0X4A0D04, 0XC9C9C9),
    WALLISEROPS(ModEntities.WALLISEROPS, ARTHROPOD, PALEOZOIC, 0X503728, 0XBD9346, bucketable());
    private static List<PrehistoricEntityInfo> boneCache;
    public final PrehistoricMobType mobType;
    public final TimePeriod timePeriod;
    public final String resourceName;
    public final Supplier<Component> displayName;
    public final @NotNull RegistrySupplier<? extends EntityType<? extends Mob>> entitySupplier;
    public final int backgroundEggColor;
    public final int highlightEggColor;
    public final boolean bucketable;
    public Item dnaItem;
    public Item eggItem;
    public Item embryoItem;
    public Item birdEggItem;
    public Item cultivatedBirdEggItem;
    public Item armBoneItem;
    public Item footBoneItem;
    public Item legBoneItem;
    public Item ribcageBoneItem;
    public Item skullBoneItem;
    public Item tailBoneItem;
    public Item uniqueBoneItem;
    public Item vertebraeBoneItem;
    public Item foodItem;
    public Item cookedFoodItem;
    public Item spawnEggItem;
    public Item bucketItem;

    PrehistoricEntityInfo(@NotNull RegistrySupplier<? extends EntityType<? extends Mob>> entity, PrehistoricMobType mobType, TimePeriod timePeriod, int backgroundEggColor, int highlightEggColor) {
        this(entity, mobType, timePeriod, backgroundEggColor, highlightEggColor, EnumSet.noneOf(InfoFlag.class));
    }

    PrehistoricEntityInfo(@NotNull RegistrySupplier<? extends EntityType<? extends Mob>> entity, PrehistoricMobType mobType, TimePeriod timePeriod, int backgroundEggColor, int highlightEggColor, EnumSet<InfoFlag> flags) {
        this.entitySupplier = entity;
        this.mobType = mobType;
        this.timePeriod = timePeriod;
        this.resourceName = this.name().toLowerCase(Locale.ROOT);
        this.displayName = () -> Component.translatable("entity.fossil." + resourceName);
        this.backgroundEggColor = backgroundEggColor;
        this.highlightEggColor = highlightEggColor;
        this.bucketable = flags.contains(BUCKETABLE);
    }

    public static void register() {
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            ModItems.ITEMS.register(info.resourceName + "_dna", () -> new DNAItem(info)).listen(item -> info.dnaItem = item);
            if (info.hasBones()) {
                DinoBoneItem.registerItem("bone_arm", info, item -> info.armBoneItem = item);
                DinoBoneItem.registerItem("bone_foot", info, item -> info.footBoneItem = item);
                DinoBoneItem.registerItem("bone_leg", info, item -> info.legBoneItem = item);
                DinoBoneItem.registerItem("bone_ribcage", info, item -> info.ribcageBoneItem = item);
                DinoBoneItem.registerItem("bone_skull", info, item -> info.skullBoneItem = item);
                DinoBoneItem.registerItem("bone_tail", info, item -> info.tailBoneItem = item);
                DinoBoneItem.registerItem("bone_unique", info, item -> info.uniqueBoneItem = item);
                DinoBoneItem.registerItem("bone_vertebrae", info, item -> info.vertebraeBoneItem = item);
            } else if (info == MEGALODON) {
                DinoBoneItem.registerItem("bone_unique", info, item -> info.uniqueBoneItem = item);
            }
            if (info.mobType == FISH || info.mobType == DINOSAUR_FISH) {
                registerItem("egg_item", info, properties -> new FishEggItem(info), item -> info.eggItem = item);
            } else if (info.mobType == DINOSAUR || info.mobType == DINOSAUR_AQUATIC || info.mobType == ARTHROPOD) {
                registerItem("egg_item", info, p -> new DinoEggItem(info), item -> info.eggItem = item);
            } else if (info.mobType == MAMMAL) {
                registerItem("syringe", info, properties -> new MammalEmbryoItem(info), item -> info.embryoItem = item);
            } else if (info.mobType == BIRD) {
                registerItem("egg", info, properties -> new BirdEggItem(info, false), item -> info.birdEggItem = item);
                registerItem("egg_item", info, properties -> new BirdEggItem(info, true), item -> info.cultivatedBirdEggItem = item);
            }
            if (info == NAUTILUS) {
                registerItem("meat", info, properties -> new NautilusMeatItem(info), item -> info.foodItem = item);
                registerItem("cooked", info, properties -> new NautilusMeatItem(info, 2), item -> info.cookedFoodItem = item);
            } else {
                registerItem("meat", info, properties -> new MeatItem(info, false), item -> info.foodItem = item);
                registerItem("cooked", info, properties -> new MeatItem(info, true), item -> info.cookedFoodItem = item);
            }
            registerItem("spawn_egg", info, properties -> new CustomSpawnEggItem(info), item -> info.spawnEggItem = item);

            if (info.bucketable) {
                registerItem("bucket_item", info, properties -> new ArchitecturyMobBucketItem(info.entitySupplier, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).arch$tab(ModTabs.FA_MOB_ITEM_TAB)),
                        item -> info.bucketItem = item);
            }
        }
    }

    private static void registerItem(String name, PrehistoricEntityInfo info, Function<Item.Properties, Item> item, Consumer<Item> listener) {
        ModItems.ITEMS.register(name + "_" + info.resourceName, () -> item.apply(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB))).listen(listener);
    }

    public static List<PrehistoricEntityInfo> getTimePeriodList(TimePeriod... periods) {
        return Arrays.stream(values()).filter(info -> Arrays.stream(periods).anyMatch(timePeriod -> info.timePeriod == timePeriod)).toList();
    }

    public static List<PrehistoricEntityInfo> entitiesWithBones() {
        if (boneCache == null) {
            boneCache = Arrays.stream(values()).filter(PrehistoricEntityInfo::hasBones).toList();
        }
        return boneCache;
    }

    public static List<PrehistoricEntityInfo> entitiesWithSkeleton(TimePeriod... periods) {
        return Arrays.stream(values()).filter(info -> {
            if (info.mobType == FISH || info == QUAGGA) {
                return false;
            }
            return Arrays.stream(periods).anyMatch(timePeriod -> timePeriod == info.timePeriod);
        }).toList();
    }

    public static boolean isMammal(Mob mob) {
        if (mob.getType().is(ModEntityTypeTags.MAMMAL)) {
            return true;
        }
        String className = "";
        try {
            className = mob.getClass().getSimpleName();
        } catch (Exception e) {
            FossilMod.LOGGER.warn(e);
        }
        return !className.isEmpty() && (mob instanceof AbstractHorse || className.contains("Cow") || className.contains("Sheep") || className.contains("Pig")
                || className.contains("Rabbit") || className.contains("Goat") || className.contains("Ferret") || className.contains("Hedgehog")
                || className.contains("Sow") || className.contains("Hog"));
    }

    @Override
    public EntityType<? extends Entity> entityType() {
        return entitySupplier.get();
    }

    @Override
    public PrehistoricMobType mobType() {
        return mobType;
    }

    @Override
    public @Nullable Item getDNAResult() {
        if (eggItem != null) {
            return eggItem;
        } else if (embryoItem != null) {
            return embryoItem;
        } else if (cultivatedBirdEggItem != null) {
            return cultivatedBirdEggItem;
        }
        return null;
    }

    @Override
    public Supplier<Component> displayName() {
        return displayName;
    }

    @Override
    public Item dnaItem() {
        return dnaItem;
    }

    public EntityDataLoader.Data data() {
        return EntityDataLoader.INSTANCE.getData(resourceName);
    }

    public boolean hasBones() {
        return timePeriod != CURRENT && mobType != FISH && mobType != ARTHROPOD && this != MEGALODON && this != AQUILOLAMNA;
    }

    public boolean isViviparousAquatic() {
        return mobType == DINOSAUR_AQUATIC || mobType == ARTHROPOD;
    }
}
