package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.food.Diet;
import com.github.teamfossilsarcheology.fossil.item.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public enum VanillaEntityInfo implements EntityInfo {
    AXOLOTL(EntityType.AXOLOTL, PrehistoricMobType.FISH, Diet.INSECTIVORE),
    BAT(EntityType.BAT, PrehistoricMobType.MAMMAL, Diet.INSECTIVORE),
    BEE(EntityType.BAT, PrehistoricMobType.OTHER, Diet.HERBIVORE),
    CAT(EntityType.CAT, PrehistoricMobType.MAMMAL, Diet.CARNIVORE),
    CHICKEN(EntityType.CHICKEN, PrehistoricMobType.VANILLA_BIRD, Diet.HERBIVORE),
    CAMEL(EntityType.CAMEL, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    COD(EntityType.COD, PrehistoricMobType.FISH, Diet.PISCI_CARNIVORE),
    COW(EntityType.COW, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    DOLPHIN(EntityType.DOLPHIN, PrehistoricMobType.MAMMAL, Diet.PISCI_CARNIVORE),
    DONKEY(EntityType.DONKEY, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    FOX(EntityType.FOX, PrehistoricMobType.MAMMAL, Diet.OMNIVORE),
    FROG(EntityType.FROG, PrehistoricMobType.OTHER, Diet.HERBIVORE),
    GOAT(EntityType.GOAT, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    GLOW_SQUID(EntityType.GLOW_SQUID, PrehistoricMobType.FISH, Diet.PISCI_CARNIVORE),
    HOGLIN(EntityType.HOGLIN, PrehistoricMobType.MAMMAL, Diet.OMNIVORE),
    HORSE(EntityType.HORSE, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    LLAMA(EntityType.LLAMA, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    MOOSHROOM(EntityType.MOOSHROOM, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    OCELOT(EntityType.OCELOT, PrehistoricMobType.MAMMAL, Diet.CARNIVORE),
    PANDA(EntityType.PANDA, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    PARROT(EntityType.PARROT, PrehistoricMobType.VANILLA_BIRD, Diet.HERBIVORE),
    PIG(EntityType.PIG, PrehistoricMobType.MAMMAL, Diet.OMNIVORE),
    POLAR_BEAR(EntityType.POLAR_BEAR, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    PUFFERFISH(EntityType.PUFFERFISH, PrehistoricMobType.FISH, Diet.OMNIVORE),
    RABBIT(EntityType.RABBIT, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    SALMON(EntityType.SALMON, PrehistoricMobType.FISH, Diet.PISCI_CARNIVORE),
    SHEEP(EntityType.SHEEP, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    SNIFFER(EntityType.SNIFFER, PrehistoricMobType.OTHER, Diet.HERBIVORE),
    SQUID(EntityType.SQUID, PrehistoricMobType.FISH, Diet.PISCI_CARNIVORE),
    STRIDER(EntityType.STRIDER, PrehistoricMobType.MAMMAL, Diet.HERBIVORE),
    TROPICAL_FISH(EntityType.TROPICAL_FISH, PrehistoricMobType.FISH, Diet.PISCI_CARNIVORE),
    TURTLE(EntityType.TURTLE, PrehistoricMobType.OTHER, Diet.HERBIVORE),
    WOLF(EntityType.WOLF, PrehistoricMobType.MAMMAL, Diet.CARNIVORE);
    private final EntityType<? extends Entity> entityType;
    public final PrehistoricMobType mobType;
    public final Diet diet;
    public final String resourceName;
    public final Supplier<Component> displayName;
    public Item dnaItem;
    public Item eggItem;
    public Item embryoItem;
    public Item cultivatedBirdEggItem;

    VanillaEntityInfo(EntityType<? extends Mob> entityType, PrehistoricMobType mobType, Diet diet) {
        this.entityType = entityType;
        this.mobType = mobType;
        this.diet = diet;
        this.resourceName = this.name().toLowerCase(Locale.ROOT);
        this.displayName = () -> Component.translatable("entity.minecraft." + resourceName);
    }

    public static void register() {
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            ModItems.ITEMS.register(info.resourceName + "_dna", () -> new DNAItem(info)).listen(item -> info.dnaItem = item);
            if (info.mobType == PrehistoricMobType.FISH) {
                registerItem("egg_item", info, properties -> new FishEggItem(info), item -> info.eggItem = item);
            } else if (info.mobType == PrehistoricMobType.MAMMAL) {
                registerItem("syringe", info, properties -> new MammalEmbryoItem(info), item -> info.embryoItem = item);
            } else if (info.mobType == PrehistoricMobType.VANILLA_BIRD) {
                registerItem("egg_item", info, properties -> new BirdEggItem(info, true), item -> info.cultivatedBirdEggItem = item);
            }
        }
    }

    private static void registerItem(String name, VanillaEntityInfo info, Function<Item.Properties, Item> item, Consumer<Item> listener) {
        ModItems.ITEMS.register(name + "_" + info.resourceName, () -> item.apply(new Item.Properties().arch$tab(ModTabs.FA_MOB_ITEM_TAB))).listen(listener);
    }

    @Override
    public EntityType<? extends Entity> entityType() {
        return entityType;
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
}
