package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.item.*;
import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.FoodMappings;
import com.fossil.fossil.util.TimePeriod;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public enum PrehistoricEntityInfo implements EntityInfo {
    ALLIGATOR_GAR(ModEntities.ALLIGATOR_GAR, PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE, 0X43462A, 0XAF4231),
    ALLOSAURUS(ModEntities.ALLOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, 0X907B6C, 0X5F422D),
    ANKYLOSAURUS(ModEntities.ANKYLOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0X8A5B49, 0X211B13),
    AQUILOLAMNA(ModEntities.AQUILOLAMNA, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    ARTHROPLEURA(ModEntities.ARTHROPLEURA, PrehistoricMobType.ARTHROPOD, TimePeriod.PALEOZOIC, Diet.HERBIVORE, 0X2A292C, 0X97531F),
    BRACHIOSAURUS(ModEntities.BRACHIOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0X52523E, 0X222114),
    CERATOSAURUS(ModEntities.CERATOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, 0.6f, 0XB4B4A7, 0X776446),
    CITIPATI(ModEntities.CITIPATI, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.OMNIVORE, 0.6f, 0X5E4A3E, 0X667373),
    COELACANTH(ModEntities.COELACANTH, PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE, 0X363941, 0X9BA1A9),
    COMPSOGNATHUS(ModEntities.COMPSOGNATHUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, 0.2f, 0XCBC7C4, 0X3A312C),
    CONFUCIUSORNIS(ModEntities.CONFUCIUSORNIS, PrehistoricMobType.BIRD, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0XDAE5E9, 0X8B8B8D),
    CRASSIGYRINUS(ModEntities.CRASSIGYRINUS, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCIVORE, 0XCA773A, 0X8F4B2D),
    DEINONYCHUS(ModEntities.DEINONYCHUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG, 0.6f, 0X2B2424, 0XC8C8C8),
    DIMETRODON(ModEntities.DIMETRODON, PrehistoricMobType.DINOSAUR, TimePeriod.PALEOZOIC, Diet.PISCI_CARNIVORE, 0XD6D6D6, 0X3B3B3B),
    DILOPHOSAURUS(ModEntities.DILOPHOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, 0.5f, 0X4E5931, 0XF25314),
    DIPLOCAULUS(ModEntities.DIPLOCAULUS, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCIVORE, 0XB0A380, 0X7C9694),
    DIPLODOCUS(ModEntities.DIPLODOCUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0X937373, 0XDAD8C7),
    DRYOSAURUS(ModEntities.DRYOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0.6f, 0X655751, 0XBEA47B),
    DODO(ModEntities.DODO, PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.HERBIVORE, 0X704C26, 0XC5C09A),
    EDAPHOSAURUS(ModEntities.EDAPHOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.PALEOZOIC, Diet.HERBIVORE, 0X64673D, 0XB58E43),
    ELASMOTHERIUM(ModEntities.ELASMOTHERIUM, PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE, 0X6B321B, 0X666666),
    GALLIMIMUS(ModEntities.GALLIMIMUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.OMNIVORE, 0.5f, 0X66412B, 0X5E2518),
    GASTORNIS(ModEntities.GASTORNIS, PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.HERBIVORE, 0X346C5E, 0XC8C8C8),
    HENODUS(ModEntities.HENODUS, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0X613C27, 0X9C8060),
    ICHTHYOSAURUS(ModEntities.ICHTHYOSAURUS, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCIVORE, 0X2A2632, 0XCEC296),
    KELENKEN(ModEntities.KELENKEN, PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE, 0X392F24, 0XF2EBD5),
    LIOPLEURODON(ModEntities.LIOPLEURODON, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCI_CARNIVORE, 0XBFC7C2, 0X1D211E),
    MAMMOTH(ModEntities.MAMMOTH, PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE, 0X3D2E19, 0X24170B),
    MEGALANIA(ModEntities.MEGALANIA, PrehistoricMobType.DINOSAUR, TimePeriod.CENOZOIC, Diet.CARNIVORE_EGG, 0X6D543D, 0XDCAE73),
    MEGALOCEROS(ModEntities.MEGALOCEROS, PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE, 0X5C2E1A, 0X8E5A3B),
    MEGALODON(ModEntities.MEGALODON, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.CENOZOIC, Diet.PISCI_CARNIVORE, 0X697B7E, 0XD0D5D5),
    MEGALOGRAPTUS(ModEntities.MEGALOGRAPTUS, PrehistoricMobType.ARTHROPOD, TimePeriod.PALEOZOIC, Diet.PISCIVORE, 0XB26F45, 0X713719),
    MEGANEURA(ModEntities.MEGANEURA, PrehistoricMobType.ARTHROPOD, TimePeriod.PALEOZOIC, Diet.PISCI_CARNIVORE, 0X6A4C3F, 0XE0B45A),
    MOSASAURUS(ModEntities.MOSASAURUS, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCI_CARNIVORE, 0X888D90, 0X3A4C52),
    NAUTILUS(ModEntities.NAUTILUS, PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE, 0XC55F47, 0XF5F5F5),
    ORNITHOLESTES(ModEntities.ORNITHOLESTES, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG, 0.5f, 0X2F221A, 0XC43824),
    PACHYCEPHALOSAURUS(ModEntities.PACHYCEPHALOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0XB6A989, 0X7D5E3A),
    PACHYRHINOSAURUS(ModEntities.PACHYRHINOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0XB6A989, 0X7D5E3A),
    PARASAUROLOPHUS(ModEntities.PARASAUROLOPHUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0X888D90, 0X3A4C52),
    PHORUSRHACOS(ModEntities.PHORUSRHACOS, PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE, 0X5F4E3E, 0XD4D4D4),
    PLATYBELODON(ModEntities.PLATYBELODON, PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE, 0X8B6551, 0X62473A),
    PLESIOSAURUS(ModEntities.PLESIOSAURUS, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCIVORE, 0XE4A86E, 0XE17920),
    PROTOCERATOPS(ModEntities.PROTOCERATOPS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0XD6D6D6, 0X3B3B3B),
    PSITTACOSAURUS(ModEntities.PSITTACOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, 0XD6D6D6, 0X3B3B3B),
    PTERANODON(ModEntities.PTERANODON, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.PISCIVORE, 0.4f, 0XD6D6D6, 0X3B3B3B),
    QUAGGA(ModEntities.QUAGGA, PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE, 0X763C24, 0XD3B9AB),
    QUETZALCOATLUS(ModEntities.QUETZALCOATLUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, 0XD6D6D6, 0X3B3B3B),
    SARCOSUCHUS(ModEntities.SARCOSUCHUS, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCI_CARNIVORE, 0X4B4929, 0X8D8C65),
    SMILODON(ModEntities.SMILODON, PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.CARNIVORE, 0XB88C64, 0XECDFCE),
    SPINOSAURUS(ModEntities.SPINOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.PISCI_CARNIVORE, 0X84512A, 0X562F20),
    STEGOSAURUS(ModEntities.STEGOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0X9C8138, 0X651817),
    STURGEON(ModEntities.STURGEON, PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE, 0X655D5B, 0XE6E3E3),
    THERIZINOSAURUS(ModEntities.THERIZINOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0X322212, 0XCA9C72),
    TIKTAALIK(ModEntities.TIKTAALIK, PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCI_CARNIVORE, 0X6A5A1A, 0XD7CF99),
    TITANIS(ModEntities.TITANIS, PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE, 0X484848, 0XEFEFEF),
    TRICERATOPS(ModEntities.TRICERATOPS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, 0X64352D, 0X251A17),
    DICRANURUS(ModEntities.DICRANURUS, PrehistoricMobType.ARTHROPOD, TimePeriod.PALEOZOIC, Diet.PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    LONCHODOMAS(ModEntities.LONCHODOMAS, PrehistoricMobType.ARTHROPOD, TimePeriod.PALEOZOIC, Diet.PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    SCOTOHARPES(ModEntities.SCOTOHARPES, PrehistoricMobType.ARTHROPOD, TimePeriod.PALEOZOIC, Diet.PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    WALLISEROPS(ModEntities.WALLISEROPS, PrehistoricMobType.ARTHROPOD, TimePeriod.PALEOZOIC, Diet.PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    TROPEOGNATHUS(ModEntities.TROPEOGNATHUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, 0XD6D6D6, 0X3B3B3B),
    TYRANNOSAURUS(ModEntities.TYRANNOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, 0X9D8A74, 0X4C3116),
    VELOCIRAPTOR(ModEntities.VELOCIRAPTOR, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG, 0.5f, 0X4A0D04, 0XC9C9C9);
    private static List<PrehistoricEntityInfo> boneCache;
    private static List<PrehistoricEntityInfo> dnaCache;
    public final PrehistoricMobType mobType;
    public final TimePeriod timePeriod;
    public final Diet diet;
    public final String resourceName;
    public final Supplier<Component> displayName;
    public final float eggScale;
    private final @NotNull RegistrySupplier<? extends EntityType<? extends Mob>> entitySupplier;
    private final int backgroundEggColor;
    private final int highlightEggColor;
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

    PrehistoricEntityInfo(@NotNull RegistrySupplier<? extends EntityType<? extends Mob>> entity, PrehistoricMobType mobType, TimePeriod timePeriod, Diet diet, int backgroundEggColor, int highlightEggColor) {
        this(entity, mobType, timePeriod, diet, 1, backgroundEggColor, highlightEggColor);
    }

    PrehistoricEntityInfo(@NotNull RegistrySupplier<? extends EntityType<? extends Mob>> entity, PrehistoricMobType mobType, TimePeriod timePeriod, Diet diet, float eggScale, int backgroundEggColor, int highlightEggColor) {
        this.entitySupplier = entity;
        this.mobType = mobType;
        this.timePeriod = timePeriod;
        this.diet = diet;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.displayName = () -> new TranslatableComponent("entity.fossil." + resourceName);
        this.eggScale = eggScale;
        this.backgroundEggColor = backgroundEggColor;
        this.highlightEggColor = highlightEggColor;
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
            if (info.mobType == PrehistoricMobType.FISH) {
                //TODO: Define value somewhere. Also should all dinos be added here?
                info.entitySupplier.listen(entityType -> FoodMappings.addFish(entityType, 100));
                registerItem("egg_item", info, properties -> new FishEggItem(info), item -> info.eggItem = item);
            } else if (info.mobType == PrehistoricMobType.DINOSAUR || info.mobType == PrehistoricMobType.DINOSAUR_AQUATIC || info.mobType == PrehistoricMobType.ARTHROPOD) {
                info.entitySupplier.listen(entityType -> FoodMappings.addMeat(entityType, 100));
                registerItem("egg_item", info, p -> new DinoEggItem(info), item -> info.eggItem = item);
            } else if (info.mobType == PrehistoricMobType.MAMMAL) {
                registerItem("syringe", info, properties -> new MammalEmbryoItem(info), item -> info.embryoItem = item);
            } else if (info.mobType == PrehistoricMobType.BIRD) {
                info.entitySupplier.listen(entityType -> FoodMappings.addMeat(entityType, 100));
                registerItem("egg", info, properties -> new BirdEggItem(info, false), item -> info.birdEggItem = item);
                registerItem("egg_item", info, properties -> new BirdEggItem(info, true), item -> info.cultivatedBirdEggItem = item);
            }
            ModItems.ITEMS.register("meat_" + info.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)
                            .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).build())))
                    .listen(item -> info.foodItem = item);

            ModItems.ITEMS.register("cooked_" + info.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(info == NAUTILUS ? 2 : 0.8f).build())))
                    .listen(item -> info.cookedFoodItem = item);
            registerItem("spawn_egg", info, properties -> new ArchitecturySpawnEggItem(info.entitySupplier, info.backgroundEggColor, info.highlightEggColor, properties), item -> info.spawnEggItem = item);
        }
    }

    private static void registerItem(String name, PrehistoricEntityInfo info, Function<Item.Properties, Item> item, Consumer<Item> listener) {
        ModItems.ITEMS.register(name + "_" + info.resourceName, () -> item.apply(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(listener);
    }

    public static List<PrehistoricEntityInfo> getTimePeriodList(TimePeriod... periods) {
        List<PrehistoricEntityInfo> list = new ArrayList<>();
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            for (TimePeriod period : periods) {
                if (info.timePeriod == period) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    public static List<PrehistoricEntityInfo> entitiesWithBones() {
        if (boneCache == null) {
            boneCache = Arrays.stream(values()).filter(PrehistoricEntityInfo::hasBones).toList();
        }
        return boneCache;
    }

    public static List<PrehistoricEntityInfo> entitiesWithSkeleton(TimePeriod... periods) {
        return Arrays.stream(values()).filter(info -> {
            if (info.mobType == PrehistoricMobType.FISH || info == QUAGGA) {
                return false;
            }
            return Arrays.stream(periods).anyMatch(timePeriod -> timePeriod == info.timePeriod);
        }).toList();
    }

    public static boolean isMammal(Mob mob) {
        String className = "";
        try {
            className = mob.getClass().getSimpleName();
        } catch (Exception e) {
            System.out.println(e);
        }
        return !className.isEmpty() && (mob instanceof Cow || mob instanceof Sheep || mob instanceof Pig || mob instanceof Chicken
                || mob instanceof Rabbit || mob instanceof AbstractHorse || mob instanceof Prehistoric prehistoric &&
                prehistoric.info().mobType == PrehistoricMobType.MAMMAL || mob instanceof PolarBear || mob instanceof Wolf || mob instanceof Ocelot
                || mob instanceof Bat || className.contains("Cow") || className.contains("Sheep") || className.contains("Pig")
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

    public boolean hasBones() {
        return timePeriod != TimePeriod.CURRENT && mobType != PrehistoricMobType.FISH && mobType != PrehistoricMobType.ARTHROPOD && this != MEGALODON;
    }

    public boolean isVivariousAquatic() {
        return mobType == PrehistoricMobType.DINOSAUR_AQUATIC && this != SARCOSUCHUS || mobType == PrehistoricMobType.ARTHROPOD;
    }
}
