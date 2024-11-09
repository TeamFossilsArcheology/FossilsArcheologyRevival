package com.github.teamfossilsarcheology.fossil.util;


import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Optional;

public abstract class FoodMappings {
    private static final Map<ItemLike, Integer> CARNIVORE_DIET = new Object2IntOpenHashMap<>();
    private static final Map<ItemLike, Integer> CARNIVORE_EGG_DIET = new Object2IntOpenHashMap<>();
    private static final Map<ItemLike, Integer> HERBIVORE_DIET = new Object2IntOpenHashMap<>();
    private static final Map<ItemLike, Integer> OMNIVORE_DIET = new Object2IntOpenHashMap<>();
    private static final Map<ItemLike, Integer> PISCIVORE_DIET = new Object2IntOpenHashMap<>();
    private static final Map<ItemLike, Integer> PISCI_CARNIVORE_DIET = new Object2IntOpenHashMap<>();
    private static final Map<ItemLike, Integer> INSECTIVORE_DIET = new Object2IntOpenHashMap<>();
    private static final Map<EntityType<?>, Integer> CARNIVORE_ENTITY_DIET = new Object2IntOpenHashMap<>();
    private static final Map<EntityType<?>, Integer> CARNIVORE_EGG_ENTITY_DIET = new Object2IntOpenHashMap<>();
    private static final Map<EntityType<?>, Integer> HERBIVORE_ENTITY_DIET = new Object2IntOpenHashMap<>();
    private static final Map<EntityType<?>, Integer> OMNIVORE_ENTITY_DIET = new Object2IntOpenHashMap<>();
    private static final Map<EntityType<?>, Integer> PISCIVORE_ENTITY_DIET = new Object2IntOpenHashMap<>();
    private static final Map<EntityType<?>, Integer> PISCI_CARNIVORE_ENTITY_DIET = new Object2IntOpenHashMap<>();
    private static final Map<EntityType<?>, Integer> INSECTIVORE_ENTITY_DIET = new Object2IntOpenHashMap<>();


    public static void addToMappings(ItemLike itemLike, int food, Diet diet) {
        switch (diet) {
            case CARNIVORE -> CARNIVORE_DIET.put(itemLike, food);
            case CARNIVORE_EGG -> CARNIVORE_EGG_DIET.put(itemLike, food);
            case HERBIVORE -> HERBIVORE_DIET.put(itemLike, food);
            case OMNIVORE -> OMNIVORE_DIET.put(itemLike, food);
            case PISCIVORE -> PISCIVORE_DIET.put(itemLike, food);
            case PISCI_CARNIVORE -> PISCI_CARNIVORE_DIET.put(itemLike, food);
            case INSECTIVORE -> INSECTIVORE_DIET.put(itemLike, food);
        }
    }

    public static void addToMappings(EntityType<?> entity, int food, Diet diet) {
        //TODO: Calculate based on bbwidth etc at runtime instead or maybe max health?
        switch (diet) {
            case CARNIVORE -> CARNIVORE_ENTITY_DIET.put(entity, food);
            case CARNIVORE_EGG -> CARNIVORE_EGG_ENTITY_DIET.put(entity, food);
            case HERBIVORE -> HERBIVORE_ENTITY_DIET.put(entity, food);
            case OMNIVORE -> OMNIVORE_ENTITY_DIET.put(entity, food);
            case PISCIVORE -> PISCIVORE_ENTITY_DIET.put(entity, food);
            case PISCI_CARNIVORE -> PISCI_CARNIVORE_ENTITY_DIET.put(entity, food);
            case INSECTIVORE -> INSECTIVORE_ENTITY_DIET.put(entity, food);
        }
    }

    public static void addToBlockMappings(Block block, int food, Diet diet, boolean registerItem) {
        addToMappings(block, food, diet);
        if (registerItem) {
            addToMappings(block.asItem(), food, diet);
        }
    }

    public static Map<ItemLike, Integer> getFoodRenderList(Diet diet) {
        switch (diet) {
            case CARNIVORE -> {
                return CARNIVORE_DIET;
            }
            case CARNIVORE_EGG -> {
                return CARNIVORE_EGG_DIET;
            }
            case HERBIVORE -> {
                return HERBIVORE_DIET;
            }
            case OMNIVORE -> {
                return OMNIVORE_DIET;
            }
            case PISCIVORE -> {
                return PISCIVORE_DIET;
            }
            case PISCI_CARNIVORE -> {
                return PISCI_CARNIVORE_DIET;
            }
            case INSECTIVORE -> {
                return INSECTIVORE_DIET;
            }
        }
        return CARNIVORE_DIET;
    }

    public static int getFoodAmount(ItemLike itemLike, Diet diet) {
        switch (diet) {
            case CARNIVORE -> {
                return CARNIVORE_DIET.getOrDefault(itemLike, 0);
            }
            case CARNIVORE_EGG -> {
                return CARNIVORE_EGG_DIET.getOrDefault(itemLike, 0);
            }
            case HERBIVORE -> {
                return HERBIVORE_DIET.getOrDefault(itemLike, 0);
            }
            case OMNIVORE -> {
                return OMNIVORE_DIET.getOrDefault(itemLike, 0);
            }
            case PISCIVORE -> {
                return PISCIVORE_DIET.getOrDefault(itemLike, 0);
            }
            case PISCI_CARNIVORE -> {
                return PISCI_CARNIVORE_DIET.getOrDefault(itemLike, 0);
            }
            case INSECTIVORE -> {
                return INSECTIVORE_DIET.getOrDefault(itemLike, 0);
            }
        }
        return 0;
    }

    public static int getFoodAmount(EntityType<? extends Entity> entity, Diet diet) {
        switch (diet) {
            case CARNIVORE -> {
                return CARNIVORE_ENTITY_DIET.getOrDefault(entity, 0);
            }
            case CARNIVORE_EGG -> {
                return CARNIVORE_EGG_ENTITY_DIET.getOrDefault(entity, 0);
            }
            case HERBIVORE -> {
                return HERBIVORE_ENTITY_DIET.getOrDefault(entity, 0);
            }
            case OMNIVORE -> {
                return OMNIVORE_ENTITY_DIET.getOrDefault(entity, 0);
            }
            case PISCIVORE -> {
                return PISCIVORE_ENTITY_DIET.getOrDefault(entity, 0);
            }
            case PISCI_CARNIVORE -> {
                return PISCI_CARNIVORE_ENTITY_DIET.getOrDefault(entity, 0);
            }
            case INSECTIVORE -> {
                return INSECTIVORE_ENTITY_DIET.getOrDefault(entity, 0);
            }
        }
        return 0;
    }

    public static int getMobFoodPoints(LivingEntity entity, Diet diet) {
        if (entity != null) {
            int mappingsPoints = getFoodAmount(entity.getType(), diet);
            if (mappingsPoints == 0 && FossilConfig.isEnabled(FossilConfig.DINOS_EAT_MODDED_MOBS)) {
                if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                    return 0;
                }
                int widthPoints = Math.round(entity.getBbWidth() * entity.getBbHeight() * 10);
                if (entity instanceof Animal && !isAquaticMob(entity)) {
                    if (diet == Diet.OMNIVORE || diet == Diet.CARNIVORE || diet == Diet.PISCI_CARNIVORE) {
                        return widthPoints;
                    }
                }
                if (diet == Diet.PISCIVORE || diet == Diet.PISCI_CARNIVORE) {
                    return isAquaticMob(entity) ? widthPoints : 0;
                }
            }
            return mappingsPoints;
        }
        return 0;
    }

    private static boolean isAquaticMob(LivingEntity entity) {
        return entity.canBreatheUnderwater() || entity instanceof WaterAnimal || entity instanceof Mob mob && mob.getNavigation() instanceof WaterBoundPathNavigation;
    }

    public static void addMeatEntity(ResourceLocation location, int food) {
        Optional<EntityType<?>> entity = Registry.ENTITY_TYPE.getOptional(location);
        entity.ifPresent(entityType -> addMeat(entityType, food));
    }

    public static void addFishEntity(ResourceLocation location, int food) {
        Optional<EntityType<?>> entity = Registry.ENTITY_TYPE.getOptional(location);
        entity.ifPresent(entityType -> addFish(entityType, food));
    }

    public static void addMeat(EntityType<?> entity, int food) {
        addToMappings(entity, food, Diet.CARNIVORE);
        addToMappings(entity, food, Diet.CARNIVORE_EGG);
        addToMappings(entity, food, Diet.OMNIVORE);
        addToMappings(entity, food, Diet.PISCI_CARNIVORE);
    }

    public static void addMeat(ItemLike itemLike) {
        int food = itemLike.asItem().getFoodProperties() != null ? itemLike.asItem().getFoodProperties().getNutrition() * 7 : 20;
        addToMappings(itemLike, food, Diet.CARNIVORE);
        addToMappings(itemLike, food, Diet.CARNIVORE_EGG);
        addToMappings(itemLike, food, Diet.OMNIVORE);
        addToMappings(itemLike, food, Diet.PISCI_CARNIVORE);
    }

    public static void addMeat(ItemLike itemLike, int food) {
        addToMappings(itemLike, food, Diet.CARNIVORE);
        addToMappings(itemLike, food, Diet.CARNIVORE_EGG);
        addToMappings(itemLike, food, Diet.OMNIVORE);
        addToMappings(itemLike, food, Diet.PISCI_CARNIVORE);
    }

    public static void addFish(EntityType<?> entity, int food) {
        addToMappings(entity, food, Diet.PISCIVORE);
        addToMappings(entity, food, Diet.PISCI_CARNIVORE);
    }

    public static void addFish(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 7 : 10;
        addToMappings(item, food, Diet.PISCIVORE);
        addToMappings(item, food, Diet.PISCI_CARNIVORE);
    }

    public static void addFish(Item item, int food) {
        addToMappings(item, food, Diet.PISCIVORE);
        addToMappings(item, food, Diet.PISCI_CARNIVORE);
    }

    public static void addEgg(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 7 : 15;
        addToMappings(item, food, Diet.CARNIVORE_EGG);
        addToMappings(item, food, Diet.OMNIVORE);
    }

    public static void addEgg(Item item, int food) {
        addToMappings(item, food, Diet.CARNIVORE_EGG);
        addToMappings(item, food, Diet.OMNIVORE);
    }

    public static void addPlant(Block block, int food) {
        addToBlockMappings(block, food, Diet.HERBIVORE, true);
        addToBlockMappings(block, food, Diet.OMNIVORE, true);
    }

    public static void addPlant(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 7 : 20;
        addToMappings(item, food, Diet.HERBIVORE);
        addToMappings(item, food, Diet.OMNIVORE);
    }

    public static void addPlant(Item item, int food) {
        addToMappings(item, food, Diet.HERBIVORE);
        addToMappings(item, food, Diet.OMNIVORE);
    }

    public static void addInsect(EntityType<? extends LivingEntity> entity, int food) {
        addToMappings(entity, food, Diet.INSECTIVORE);
    }
}
