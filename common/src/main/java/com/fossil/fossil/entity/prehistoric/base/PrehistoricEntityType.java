package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.item.*;
import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.FoodMappings;
import com.fossil.fossil.util.TimePeriod;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
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
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public enum PrehistoricEntityType {
    CHICKEN(vanilla(EntityType.CHICKEN), PrehistoricMobType.CHICKEN, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    COW(vanilla(EntityType.COW), PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    DONKEY(vanilla(EntityType.DONKEY), PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    HORSE(vanilla(EntityType.HORSE), PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    LLAMA(vanilla(EntityType.LLAMA), PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    PARROT(vanilla(EntityType.PARROT), PrehistoricMobType.CHICKEN, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    PIG(vanilla(EntityType.PIG), PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.OMNIVORE, Map.of()),
    POLARBEAR(vanilla(EntityType.POLAR_BEAR), PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    RABBIT(vanilla(EntityType.RABBIT), PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    SHEEP(vanilla(EntityType.SHEEP), PrehistoricMobType.VANILLA, TimePeriod.CURRENT, Diet.HERBIVORE, Map.of()),
    ALLIGATOR_GAR(PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE),
    ALLOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE),
    ANKYLOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    ARTHROPLEURA(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.HERBIVORE),
    BRACHIOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    CERATOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, Map.of("eggScale", 0.6f)),
    CITIPATI(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.OMNIVORE, Map.of("eggScale", 0.6f)),
    COELACANTH(PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE),
    COMPSOGNATHUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, Map.of("eggScale", 0.2f)),
    CONFUCIUSORNIS(PrehistoricMobType.BIRD, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    CRASSIGYRINUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCIVORE),
    DEINONYCHUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG, Map.of("eggScale", 0.6f)),
    DILOPHOSAURUS(ModEntities.DILOPHOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE, Map.of("eggScale", 0.5f)),
    DIPLOCAULUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCIVORE),
    DIPLODOCUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    DRYOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, Map.of("eggScale", 0.6f)),
    DODO(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    EDAPHOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.PALEOZOIC, Diet.HERBIVORE),
    ELASMOTHERIUM(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    GALLIMIMUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.OMNIVORE, Map.of("eggScale", 0.5f)),
    GASTORNIS(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    HENODUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    ICHTYOSAURUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCIVORE),
    KELENKEN(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE),
    LIOPLEURODON(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCI_CARNIVORE),
    MAMMOTH(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    MEGALANIA(PrehistoricMobType.DINOSAUR, TimePeriod.CENOZOIC, Diet.CARNIVORE_EGG),
    MEGALOCEROS(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    MEGALODON(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.CENOZOIC, Diet.PISCI_CARNIVORE),
    MEGALOGRAPTUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCIVORE),
    MEGANEURA(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCI_CARNIVORE),
    MOSASAURUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCI_CARNIVORE),
    NAUTILUS(PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE),
    ORNITHOLESTES(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG, Map.of("eggScale", 0.5f)),
    PACHYCEPHALOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    PARASAUROLOPHUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    PHORUSRHACOS(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE),
    PLATYBELODON(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    PLESIOSAURUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCIVORE),
    PTERANODON(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.PISCIVORE),
    PTEROSAUR(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.PISCIVORE, Map.of("eggScale", 0.54f)),
    QUAGGA(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.HERBIVORE),
    SARCOSUCHUS(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.MESOZOIC, Diet.PISCI_CARNIVORE),
    SMILODON(PrehistoricMobType.MAMMAL, TimePeriod.CENOZOIC, Diet.CARNIVORE),
    SPINOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.PISCI_CARNIVORE),
    STEGOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE),
    STURGEON(PrehistoricMobType.FISH, TimePeriod.MESOZOIC, Diet.NONE),
    THERIZINOSAURUS(ModEntities.THERIZINOSAURUS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, Map.of()),
    TIKTAALIK(PrehistoricMobType.DINOSAUR_AQUATIC, TimePeriod.PALEOZOIC, Diet.PISCI_CARNIVORE),
    TITANIS(PrehistoricMobType.BIRD, TimePeriod.CENOZOIC, Diet.CARNIVORE),
    TRICERATOPS(ModEntities.TRICERATOPS, PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.HERBIVORE, Map.of("hasBoneItems", true)),
    TYRANNOSAURUS(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE),
    VELOCIRAPTOR(PrehistoricMobType.DINOSAUR, TimePeriod.MESOZOIC, Diet.CARNIVORE_EGG, Map.of("eggScale", 0.5f));
    private final RegistrySupplier<? extends EntityType<? extends Entity>> entitySupplier;
    public final PrehistoricMobType mobType;
    public final TimePeriod timePeriod;
    public final Diet diet;
    public final String resourceName;
    public final Supplier<Component> displayName;
    private final boolean hasBoneItems;
    public final float eggScale;
    public Item dnaItem;
    public Item eggItem;
    public Item embryoItem;
    public Item birdEggItem;
    public Item cultivatedBirdEggItem;
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
        this.entitySupplier = null;//TODO: Should not be null in final version
        this.mobType = mobType;
        this.timePeriod = timePeriod;
        this.diet = diet;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.displayName = () -> new TranslatableComponent("entity.fossil." + resourceName);
        this.hasBoneItems = false;
        this.eggScale = 1;
    }

    PrehistoricEntityType(PrehistoricMobType mobType, TimePeriod timePeriod, Diet diet, Map<String, Object> attributes) {
        this.entitySupplier = null;
        this.mobType = mobType;
        this.timePeriod = timePeriod;
        this.diet = diet;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.displayName = () -> new TranslatableComponent("entity.fossil." + resourceName);
        this.hasBoneItems = (boolean) attributes.getOrDefault("hasBoneItems", false);
        this.eggScale = (float) attributes.getOrDefault("eggScale", (float) 1.0f);
    }

    PrehistoricEntityType(RegistrySupplier<? extends EntityType<? extends Entity>> entity, PrehistoricMobType mobType, TimePeriod timePeriod, Diet diet, Map<String, Object> attributes) {
        this.entitySupplier = entity;
        this.mobType = mobType;
        this.timePeriod = timePeriod;
        this.diet = diet;
        this.resourceName = this.name().toLowerCase(Locale.ENGLISH);
        this.displayName = () -> entitySupplier.get().getDescription();
        this.hasBoneItems = (boolean) attributes.getOrDefault("hasBoneItems", false);
        this.eggScale = (float) attributes.getOrDefault("eggScale", (float) 1.0f);
    }

    public static void register() {
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            ModItems.ITEMS.register(type.resourceName + "_dna", () -> new DNAItem(type)).listen(item -> type.dnaItem = item);
            if (type.hasBoneItems) {
                registerItem("bone_leg", type, Item::new, item -> type.legBoneItem = item);
                registerItem("bone_arm", type, Item::new, item -> type.armBoneItem = item);
                registerItem("bone_foot", type, Item::new, item -> type.footBoneItem = item);
                registerItem("bone_skull", type, Item::new, item -> type.skullBoneItem = item);
                registerItem("bone_ribcage", type, Item::new, item -> type.ribcageBoneItem = item);
                registerItem("bone_vertebrae", type, Item::new, item -> type.vertebraeBoneItem = item);
                registerItem("bone_unique_item", type, Item::new, item -> type.uniqueBoneItem = item);
            }
            if (type.mobType == PrehistoricMobType.FISH) {
                if (type.entitySupplier != null) type.entitySupplier.listen(entityType -> FoodMappings.addFish(entityType, 100));//TODO: Define value somewhere. Also should all dinos be added here?
                registerItem("fish", type, Item::new, item -> type.fishItem = item);
                registerItem("egg_item", type, Item::new, item -> type.eggItem = item);
            } else if (type.mobType == PrehistoricMobType.DINOSAUR) {
                if (type.entitySupplier != null) type.entitySupplier.listen(entityType -> FoodMappings.addMeat(entityType, 100));
                registerItem("egg_item", type, p -> new DinoEggItem(type), item -> type.eggItem = item);
            } else if (type.mobType == PrehistoricMobType.MAMMAL || type.mobType == PrehistoricMobType.VANILLA) {
                registerItem("syringe", type, properties -> new MammalEmbryoItem(type), item -> type.embryoItem = item);
            } else if (type.mobType == PrehistoricMobType.BIRD || type.mobType == PrehistoricMobType.CHICKEN) {
                if (type.entitySupplier != null) type.entitySupplier.listen(entityType -> FoodMappings.addMeat(entityType, 100));
                if (type.mobType == PrehistoricMobType.BIRD) {
                    registerItem("egg", type, Item::new, item -> type.birdEggItem = item);
                }
                registerItem("egg_item", type, Item::new, item -> type.cultivatedBirdEggItem = item);
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

    private static void registerItem(String name, PrehistoricEntityType type, Function<Item.Properties, Item> item, Consumer<Item> listener) {
        ModItems.ITEMS.register(name + "_" + type.resourceName, () -> item.apply(new Item.Properties().tab(ModTabs.FAITEMTAB))).listen(listener);
    }

    public EntityType<? extends Entity> entityType() {
        return entitySupplier.get();
    }

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

    public boolean isVivariousAquatic() {
        return this.mobType == PrehistoricMobType.DINOSAUR_AQUATIC && this != SARCOSUCHUS;
    }

    private static List<PrehistoricEntityType> boneCache;
    private static List<PrehistoricEntityType> dnaCache;

    public static List<PrehistoricEntityType> entitiesWithBones() {
        if (boneCache == null) {
            boneCache = Arrays.stream(values()).filter(type -> type.hasBoneItems).toList();
        }
        return boneCache;
    }

    public static List<PrehistoricEntityType> entitiesWithDNAResult() {
        if (dnaCache == null) {
            dnaCache = Arrays.stream(values()).filter(
                    type -> type.eggItem != null || type.embryoItem != null || type.cultivatedBirdEggItem != null).toList();
        }
        return dnaCache;
    }

    public static boolean isMammal(Mob mob) {
        //TODO: Maybe could be done with tags?
        String className = "";
        try {
            className = mob.getClass().getSimpleName();
        } catch (Exception e) {
            System.out.println(e);
        }
        return !className.isEmpty() && (mob instanceof Cow || mob instanceof Sheep || mob instanceof Pig || mob instanceof Chicken
                || mob instanceof Rabbit || mob instanceof AbstractHorse || mob instanceof Prehistoric prehistoric &&
                prehistoric.type().mobType == PrehistoricMobType.MAMMAL || mob instanceof PolarBear || mob instanceof Wolf || mob instanceof Ocelot
                || mob instanceof Bat || className.contains("Cow") || className.contains("Sheep") || className.contains("Pig")
                || className.contains("Rabbit") || className.contains("Goat") || className.contains("Ferret") || className.contains("Hedgehog")
                || className.contains("Sow") || className.contains("Hog"));
    }

    private static RegistrySupplier<EntityType<?>> vanilla(EntityType<? extends Entity> entity) {
        return Registries.get(Fossil.MOD_ID).get(Registry.ENTITY_TYPE_REGISTRY).delegate(entity.arch$registryName());
    }
}
