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

import static com.fossil.fossil.entity.prehistoric.base.PrehistoricMobType.*;
import static com.fossil.fossil.util.Diet.*;
import static com.fossil.fossil.util.TimePeriod.*;

public enum PrehistoricEntityInfo implements EntityInfo {
    ALLIGATOR_GAR(ModEntities.ALLIGATOR_GAR, FISH, MESOZOIC, NONE, 0X43462A, 0XAF4231),
    ALLOSAURUS(ModEntities.ALLOSAURUS, DINOSAUR, MESOZOIC, CARNIVORE, 0X907B6C, 0X5F422D),
    ANKYLOSAURUS(ModEntities.ANKYLOSAURUS, DINOSAUR, MESOZOIC, HERBIVORE, 0X8A5B49, 0X211B13),
    AQUILOLAMNA(ModEntities.AQUILOLAMNA, DINOSAUR_AQUATIC, MESOZOIC, PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    ARTHROPLEURA(ModEntities.ARTHROPLEURA, ARTHROPOD, PALEOZOIC, HERBIVORE, 0X2A292C, 0X97531F),
    BRACHIOSAURUS(ModEntities.BRACHIOSAURUS, DINOSAUR, MESOZOIC, HERBIVORE, 0X52523E, 0X222114),
    CERATOSAURUS(ModEntities.CERATOSAURUS, DINOSAUR, MESOZOIC, CARNIVORE, 0.6f, 0XB4B4A7, 0X776446),
    CITIPATI(ModEntities.CITIPATI, DINOSAUR, MESOZOIC, OMNIVORE, 0.6f, 0X5E4A3E, 0X667373),
    COELACANTH(ModEntities.COELACANTH, FISH, MESOZOIC, NONE, 0X363941, 0X9BA1A9),
    COMPSOGNATHUS(ModEntities.COMPSOGNATHUS, DINOSAUR, MESOZOIC, CARNIVORE, 0.2f, 0XCBC7C4, 0X3A312C),
    CONFUCIUSORNIS(ModEntities.CONFUCIUSORNIS, BIRD, MESOZOIC, HERBIVORE, 0XDAE5E9, 0X8B8B8D),
    CRASSIGYRINUS(ModEntities.CRASSIGYRINUS, DINOSAUR_AQUATIC, PALEOZOIC, PISCIVORE, 0XCA773A, 0X8F4B2D),
    DEINONYCHUS(ModEntities.DEINONYCHUS, DINOSAUR, MESOZOIC, CARNIVORE_EGG, 0.6f, 0X2B2424, 0XC8C8C8),
    DIMETRODON(ModEntities.DIMETRODON, DINOSAUR, PALEOZOIC, PISCI_CARNIVORE, 0XD6D6D6, 0X3B3B3B),
    DILOPHOSAURUS(ModEntities.DILOPHOSAURUS, DINOSAUR, MESOZOIC, CARNIVORE, 0.5f, 0X4E5931, 0XF25314),
    DIPLOCAULUS(ModEntities.DIPLOCAULUS, DINOSAUR_AQUATIC, PALEOZOIC, PISCIVORE, 0XB0A380, 0X7C9694),
    DIPLODOCUS(ModEntities.DIPLODOCUS, DINOSAUR, MESOZOIC, HERBIVORE, 0X937373, 0XDAD8C7),
    DRYOSAURUS(ModEntities.DRYOSAURUS, DINOSAUR, MESOZOIC, HERBIVORE, 0.6f, 0X655751, 0XBEA47B),
    DODO(ModEntities.DODO, BIRD, CENOZOIC, HERBIVORE, 0X704C26, 0XC5C09A),
    EDAPHOSAURUS(ModEntities.EDAPHOSAURUS, DINOSAUR, PALEOZOIC, HERBIVORE, 0X64673D, 0XB58E43),
    ELASMOTHERIUM(ModEntities.ELASMOTHERIUM, MAMMAL, CENOZOIC, HERBIVORE, 0X6B321B, 0X666666),
    GALLIMIMUS(ModEntities.GALLIMIMUS, DINOSAUR, MESOZOIC, OMNIVORE, 0.5f, 0X66412B, 0X5E2518),
    GASTORNIS(ModEntities.GASTORNIS, BIRD, CENOZOIC, HERBIVORE, 0X346C5E, 0XC8C8C8),
    HENODUS(ModEntities.HENODUS, DINOSAUR_AQUATIC, MESOZOIC, HERBIVORE, 0X613C27, 0X9C8060),
    ICHTHYOSAURUS(ModEntities.ICHTHYOSAURUS, DINOSAUR_AQUATIC, MESOZOIC, PISCIVORE, 0X2A2632, 0XCEC296),
    KELENKEN(ModEntities.KELENKEN, BIRD, CENOZOIC, CARNIVORE, 0X392F24, 0XF2EBD5),
    LIOPLEURODON(ModEntities.LIOPLEURODON, DINOSAUR_AQUATIC, MESOZOIC, PISCI_CARNIVORE, 0XBFC7C2, 0X1D211E),
    MAMMOTH(ModEntities.MAMMOTH, MAMMAL, CENOZOIC, HERBIVORE, 0X3D2E19, 0X24170B),
    MEGALANIA(ModEntities.MEGALANIA, DINOSAUR, CENOZOIC, CARNIVORE_EGG, 0X6D543D, 0XDCAE73),
    MEGALOCEROS(ModEntities.MEGALOCEROS, MAMMAL, CENOZOIC, HERBIVORE, 0X5C2E1A, 0X8E5A3B),
    MEGALODON(ModEntities.MEGALODON, DINOSAUR_AQUATIC, CENOZOIC, PISCI_CARNIVORE, 0X697B7E, 0XD0D5D5),
    MEGALOGRAPTUS(ModEntities.MEGALOGRAPTUS, ARTHROPOD, PALEOZOIC, PISCIVORE, 0XB26F45, 0X713719),
    MEGANEURA(ModEntities.MEGANEURA, ARTHROPOD, PALEOZOIC, PISCI_CARNIVORE, 0X6A4C3F, 0XE0B45A),
    MOSASAURUS(ModEntities.MOSASAURUS, DINOSAUR_AQUATIC, MESOZOIC, PISCI_CARNIVORE, 0X888D90, 0X3A4C52),
    NAUTILUS(ModEntities.NAUTILUS, FISH, MESOZOIC, NONE, 0XC55F47, 0XF5F5F5),
    ORNITHOLESTES(ModEntities.ORNITHOLESTES, DINOSAUR, MESOZOIC, CARNIVORE_EGG, 0.5f, 0X2F221A, 0XC43824),
    PACHYCEPHALOSAURUS(ModEntities.PACHYCEPHALOSAURUS, DINOSAUR, MESOZOIC, HERBIVORE, 0XB6A989, 0X7D5E3A),
    PACHYRHINOSAURUS(ModEntities.PACHYRHINOSAURUS, DINOSAUR, MESOZOIC, HERBIVORE, 0XB6A989, 0X7D5E3A),
    PARASAUROLOPHUS(ModEntities.PARASAUROLOPHUS, DINOSAUR, MESOZOIC, HERBIVORE, 0X888D90, 0X3A4C52),
    PHORUSRHACOS(ModEntities.PHORUSRHACOS, BIRD, CENOZOIC, CARNIVORE, 0X5F4E3E, 0XD4D4D4),
    PLATYBELODON(ModEntities.PLATYBELODON, MAMMAL, CENOZOIC, HERBIVORE, 0X8B6551, 0X62473A),
    PLESIOSAURUS(ModEntities.PLESIOSAURUS, DINOSAUR_AQUATIC, MESOZOIC, PISCIVORE, 0XE4A86E, 0XE17920),
    PROTOCERATOPS(ModEntities.PROTOCERATOPS, DINOSAUR, MESOZOIC, HERBIVORE, 0XD6D6D6, 0X3B3B3B),
    PSITTACOSAURUS(ModEntities.PSITTACOSAURUS, DINOSAUR, MESOZOIC, CARNIVORE, 0XD6D6D6, 0X3B3B3B),
    PTERANODON(ModEntities.PTERANODON, DINOSAUR, MESOZOIC, PISCIVORE, 0.4f, 0XD6D6D6, 0X3B3B3B),
    QUAGGA(ModEntities.QUAGGA, MAMMAL, CENOZOIC, HERBIVORE, 0X763C24, 0XD3B9AB),
    QUETZALCOATLUS(ModEntities.QUETZALCOATLUS, DINOSAUR, MESOZOIC, CARNIVORE, 0XD6D6D6, 0X3B3B3B),
    SARCOSUCHUS(ModEntities.SARCOSUCHUS, DINOSAUR_AQUATIC, MESOZOIC, PISCI_CARNIVORE, 0X4B4929, 0X8D8C65),
    SMILODON(ModEntities.SMILODON, MAMMAL, CENOZOIC, CARNIVORE, 0XB88C64, 0XECDFCE),
    SPINOSAURUS(ModEntities.SPINOSAURUS, DINOSAUR, MESOZOIC, PISCI_CARNIVORE, 0X84512A, 0X562F20),
    STEGOSAURUS(ModEntities.STEGOSAURUS, DINOSAUR, MESOZOIC, HERBIVORE, 0X9C8138, 0X651817),
    STURGEON(ModEntities.STURGEON, FISH, MESOZOIC, NONE, 0X655D5B, 0XE6E3E3),
    THERIZINOSAURUS(ModEntities.THERIZINOSAURUS, DINOSAUR, MESOZOIC, HERBIVORE, 0X322212, 0XCA9C72),
    TIKTAALIK(ModEntities.TIKTAALIK, DINOSAUR_AQUATIC, PALEOZOIC, PISCI_CARNIVORE, 0X6A5A1A, 0XD7CF99),
    TITANIS(ModEntities.TITANIS, BIRD, CENOZOIC, CARNIVORE, 0X484848, 0XEFEFEF),
    TRICERATOPS(ModEntities.TRICERATOPS, DINOSAUR, MESOZOIC, HERBIVORE, 0X64352D, 0X251A17),
    DICRANURUS(ModEntities.DICRANURUS, ARTHROPOD, PALEOZOIC, PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    LONCHODOMAS(ModEntities.LONCHODOMAS, ARTHROPOD, PALEOZOIC, PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    SCOTOHARPES(ModEntities.SCOTOHARPES, ARTHROPOD, PALEOZOIC, PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    WALLISEROPS(ModEntities.WALLISEROPS, ARTHROPOD, PALEOZOIC, PISCIVORE, 0XD6D6D6, 0X3B3B3B),
    TROPEOGNATHUS(ModEntities.TROPEOGNATHUS, DINOSAUR, MESOZOIC, CARNIVORE, 0XD6D6D6, 0X3B3B3B),
    TYRANNOSAURUS(ModEntities.TYRANNOSAURUS, DINOSAUR, MESOZOIC, CARNIVORE, 0X9D8A74, 0X4C3116),
    VELOCIRAPTOR(ModEntities.VELOCIRAPTOR, DINOSAUR, MESOZOIC, CARNIVORE_EGG, 0.5f, 0X4A0D04, 0XC9C9C9);
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
            if (info.mobType == FISH) {
                //TODO: Define value somewhere. Also should all dinos be added here?
                info.entitySupplier.listen(entityType -> FoodMappings.addFish(entityType, 100));
                registerItem("egg_item", info, properties -> new FishEggItem(info), item -> info.eggItem = item);
            } else if (info.mobType == DINOSAUR || info.mobType == DINOSAUR_AQUATIC || info.mobType == ARTHROPOD) {
                info.entitySupplier.listen(entityType -> FoodMappings.addMeat(entityType, 100));
                registerItem("egg_item", info, p -> new DinoEggItem(info), item -> info.eggItem = item);
            } else if (info.mobType == MAMMAL) {
                registerItem("syringe", info, properties -> new MammalEmbryoItem(info), item -> info.embryoItem = item);
            } else if (info.mobType == BIRD) {
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
            if (info.mobType == FISH || info == QUAGGA) {
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
                prehistoric.info().mobType == MAMMAL || mob instanceof PolarBear || mob instanceof Wolf || mob instanceof Ocelot
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
        return timePeriod != CURRENT && mobType != FISH && mobType != ARTHROPOD && this != MEGALODON;
    }

    public boolean isVivariousAquatic() {
        return mobType == DINOSAUR_AQUATIC && this != SARCOSUCHUS || mobType == ARTHROPOD;
    }
}
