package com.github.teamfossilsarcheology.fossil.food;


import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public abstract class FoodMappings {

    public static int getFoodAmount(ItemLike itemLike, FoodType diet) {
        return FoodMappingsManager.INSTANCE.getItemValues(diet).getOrDefault(itemLike.asItem(), 0);
    }

    public static int getFoodAmount(ItemLike itemLike, Diet diet) {
        Set<FoodType> valid = diet.flags();
        for (FoodType type : valid) {
            if (FoodMappingsManager.INSTANCE.getItemValues(type).containsKey(itemLike.asItem())) {
                return FoodMappingsManager.INSTANCE.getItemValues(type).get(itemLike.asItem());
            }
        }
        return 0;
    }

    public static int getMobFoodPoints(LivingEntity entity, Diet diet) {
        if (entity == null) {
            return 0;
        }
        EntityType<?> entityType = entity.getType();
        if (!FossilConfig.isEnabled(FossilConfig.DINOS_EAT_MODDED_MOBS)) {
            String namespace = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getNamespace();
            if (!namespace.equals(FossilMod.MOD_ID) && !namespace.equals("minecraft")) {
                return 0;
            }
        }
        Set<FoodType> valid = diet.flags();
        if (FoodMappingsManager.INSTANCE.hasEntityEntry(entityType)) {
            for (FoodType type1 : valid) {
                if (FoodMappingsManager.INSTANCE.getEntityValues(type1).containsKey(entityType)) {
                    int mappingPoints = FoodMappingsManager.INSTANCE.getEntityValues(type1).get(entityType);
                    if (mappingPoints > 0) {
                        //Scale points based on difference between current dimensions and base dimensions
                        float scale = entity.getBbWidth() / entityType.getDimensions().width * entity.getBbHeight() / entityType.getDimensions().height;
                        return Math.round(mappingPoints * scale);
                    } else {
                        return Math.round(entity.getBbWidth() * entity.getBbHeight() * 10);
                    }
                }
            }
        } else {
            if (valid.contains(FoodType.MEAT) && entity instanceof Animal && !isAquaticMob(entity)) {
                return Math.round(entity.getBbWidth() * entity.getBbHeight() * 10);
            }
            if (valid.contains(FoodType.FISH) && isAquaticMob(entity)) {
                return Math.round(entity.getBbWidth() * entity.getBbHeight() * 10);
            }
            return 0;
        }
        return 0;
    }

    private static boolean isAquaticMob(LivingEntity entity) {
        return entity.canBreatheUnderwater() || entity instanceof WaterAnimal || entity instanceof Mob mob && mob.getNavigation() instanceof WaterBoundPathNavigation;
    }

    public static void addMeat(EntityType<?> entity, int food) {
        FoodMappingsManager.INSTANCE.addEntity(FoodType.MEAT, entity, food);
    }

    public static void addMeat(ItemLike itemLike) {
        int food = itemLike.asItem().getFoodProperties() != null ? itemLike.asItem().getFoodProperties().getNutrition() * 7 : 20;
        addMeat(itemLike, food);
    }

    public static void addMeat(ItemLike itemLike, int food) {
        FoodMappingsManager.INSTANCE.addItem(FoodType.MEAT, itemLike.asItem(), food);
    }

    public static void addFish(EntityType<?> entity, int food) {
        FoodMappingsManager.INSTANCE.addEntity(FoodType.FISH, entity, food);
    }

    public static void addFish(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 7 : 10;
        addFish(item, food);
    }

    public static void addFish(Item item, int food) {
        FoodMappingsManager.INSTANCE.addItem(FoodType.FISH, item, food);
    }

    public static void addEgg(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 7 : 15;
        addEgg(item, food);
    }

    public static void addEgg(Item item, int food) {
        FoodMappingsManager.INSTANCE.addItem(FoodType.EGG, item, food);
    }

    public static void addPlant(Block block, int food) {
        FoodMappingsManager.INSTANCE.addItem(FoodType.PLANT, block.asItem(), food);
    }

    public static void addPlant(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 5 : 17;
        addPlant(item, food);
    }

    public static void addPlant(Item item, int food) {
        FoodMappingsManager.INSTANCE.addItem(FoodType.PLANT, item, food);
    }
}
