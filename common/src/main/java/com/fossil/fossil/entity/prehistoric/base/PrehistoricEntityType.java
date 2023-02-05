package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.item.DNAItem;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ModTabs;
import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.TimePeriod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum PrehistoricEntityType {
    PIG(Pig.class, PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.OMNIVORE),
    COW(Cow.class, PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE),
    SHEEP(Sheep.class, PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE),
    HORSE(Horse.class, PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE),
    DONKEY(Donkey.class, PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE),
    CHICKEN(Chicken.class, PrehistoricMobType.CHICKEN, TimePeriod.CURRENT, Diet.HERBIVORE),
    PARROT(Parrot.class, PrehistoricMobType.CHICKEN, TimePeriod.CURRENT, Diet.HERBIVORE),
    POLARBEAR(PolarBear.class, PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE),
    RABBIT(Rabbit.class, PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE),
    LLAMA(Llama.class, PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE),
    NAUTILUS(PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE),
    COELACANTH(PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE),
    ALLIGATOR_GAR(PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE),
    STURGEON(PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE),
    TRICERATOPS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, true),
    VELOCIRAPTOR(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG),
    TYRANNOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE),
    PTERANODON(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.PISCIVORE),
    PLESIOSAURUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCIVORE),
    MOSASAURUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCCARNIVORE),
    STEGOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    DILOPHOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE),
    BRACHIOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    SPINOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.PISCCARNIVORE),
    COMPSOGNATHUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE),
    ANKYLOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    PACHYCEPHALOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    DEINONYCHUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG),
    GALLIMIMUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.OMNIVORE),
    LIOPLEURODON(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCCARNIVORE),
    ALLOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE),
    SARCOSUCHUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCCARNIVORE),
    CERATOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE),
    DRYOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    THERIZINOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    PARASAUROLOPHUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    CITIPATI(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.OMNIVORE),
    DIPLODOCUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    ORNITHOLESTES(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG),
    HENODUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    ICHTYOSAURUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCIVORE),
    MEGANEURA(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCCARNIVORE),
    MEGALOGRAPTUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCIVORE),
    CONFUCIUSORNIS(PrehistoricMobType.BIRD, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    DODO(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    GASTORNIS(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    KELENKEN(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE),
    PHORUSRHACOS(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE),
    TITANIS(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE),
    MAMMOTH(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    SMILODON(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.CARNIVORE),
    QUAGGA(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    ELASMOTHERIUM(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    MEGALOCEROS(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    MEGALANIA(PrehistoricMobType.DINOSAUR, TimePeriod.CENOZOIC, Diet.CARNIVORE_EGG),
    MEGALODON(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.CENOZOIC, Diet.PISCCARNIVORE),
    PLATYBELODON(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    TIKTAALIK(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCCARNIVORE),
    CRASSIGYRINUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCIVORE),
    DIPLOCAULUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCIVORE),
    EDAPHOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.PALEOZOIC, Diet.HERBIVORE),
    ARTHROPLEURA(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.HERBIVORE);
    private final Class<? extends Entity> entity;
    private final PrehistoricMobType mobType;
    private final TimePeriod timePeriod;
    private final Diet diet;
    public final String resourceName;
    private boolean hasBoneItems;
    public Item dnaItem;
    public Item legBoneItem;
    public Item armBoneItem;
    public Item footBoneItem;
    public Item skullBoneItem;
    public Item ribcageBoneItem;
    public Item vertebraeBoneItem;
    public Item uniqueBoneItem;
    public Item foodItem;
    public Item cookedFoodItem;
    public Item fishItem;

    PrehistoricEntityType(PrehistoricMobType mobType, TimePeriod timePeriod, Diet diet) {
        this.entity = null;
        this.mobType = mobType;
        this.timePeriod = timePeriod;
        this.diet = diet;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.hasBoneItems = false;
    }

    PrehistoricEntityType(PrehistoricMobType mobType, TimePeriod timePeriod, Diet diet, boolean hasBoneItems) {
        this.entity = null;
        this.mobType = mobType;
        this.timePeriod = timePeriod;
        this.diet = diet;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.hasBoneItems = hasBoneItems;
    }

    PrehistoricEntityType(Class<? extends Entity> entity, PrehistoricMobType mobType, TimePeriod timePeriod, Diet diet) {
        this.entity = entity;
        this.mobType = mobType;
        this.timePeriod = timePeriod;
        this.diet = diet;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.hasBoneItems = false;
    };

    public static void register() {
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            ModItems.ITEMS.register(type.resourceName + "_dna", () -> new DNAItem(type)).listen(item -> type.dnaItem = item);
            if (type.hasBoneItems) {
                ModItems.ITEMS.register("bone_leg_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(
                        item -> type.legBoneItem = item);
                ModItems.ITEMS.register("bone_arm_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(
                        item -> type.armBoneItem = item);
                ModItems.ITEMS.register("bone_foot_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(
                        item -> type.footBoneItem = item);
                ModItems.ITEMS.register("bone_skull_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(
                        item -> type.skullBoneItem = item);
                ModItems.ITEMS.register("bone_ribcage_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(
                        item -> type.ribcageBoneItem = item);
                ModItems.ITEMS.register("bone_vertebrae_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(
                        item -> type.vertebraeBoneItem = item);
                ModItems.ITEMS.register("bone_unique_item_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(
                        item -> type.uniqueBoneItem = item);
            }
            if (type.mobType == PrehistoricMobType.FISH) {
                ModItems.ITEMS.register("fish_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)))
                        .listen(item -> type.fishItem = item);
            }
            if (type.timePeriod != TimePeriod.CURRENT) {
                if (type.mobType != PrehistoricMobType.FISH) {
                    ModItems.ITEMS.register("meat_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)
                                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).build())))
                            .listen(item -> type.foodItem = item);
                }
                if (type != NAUTILUS) {
                    ModItems.ITEMS.register("cooked_" + type.resourceName, () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)
                                    .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8f).build())))
                            .listen(item -> type.cookedFoodItem = item);
                }
            }
        }
    }

    public static List<PrehistoricEntityType> getTimePeriodList(TimePeriod... periods) {
        List<PrehistoricEntityType> list = new ArrayList<>();
        for (PrehistoricEntityType entity : PrehistoricEntityType.values()) {
            for (TimePeriod period : periods) {
                if (entity.timePeriod == period) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    private static List<PrehistoricEntityType> boneCache;

    public static List<PrehistoricEntityType> entitiesWithBones() {
        if (boneCache == null) {
            boneCache = Arrays.stream(values()).filter(type -> type.hasBoneItems).toList();
        }
        return boneCache;
    }
}
