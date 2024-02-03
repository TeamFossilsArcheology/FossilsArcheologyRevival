package com.fossil.fossil.util;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricMobType;
import com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.fossil.fossil.item.ModItems;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class FossilFoodMappings {
    public static void register() {
        FoodMappings.addPlant(Items.SUGAR_CANE, 15);
        FoodMappings.addPlant(Items.WHEAT, 13);
        FoodMappings.addPlant(Items.MELON_SLICE);
        FoodMappings.addPlant(Items.APPLE);
        FoodMappings.addPlant(Items.BEETROOT);
        FoodMappings.addPlant(Items.POTATO);
        FoodMappings.addPlant(Items.BAKED_POTATO);
        FoodMappings.addPlant(Items.CAKE, 50);
        FoodMappings.addPlant(Items.CARROT);
        FoodMappings.addPlant(Items.COOKIE);
        FoodMappings.addPlant(Items.PUMPKIN_PIE);
        FoodMappings.addPlant(Items.SUGAR, 7);
        FoodMappings.addPlant(Items.BREAD);
        FoodMappings.addPlant(Items.WHEAT_SEEDS, 5);
        FoodMappings.addPlant(Items.MELON_SEEDS, 5);
        FoodMappings.addPlant(Items.PUMPKIN_SEEDS, 5);
        FoodMappings.addPlant(Items.BEETROOT_SEEDS, 5);
        LifecycleEvent.SERVER_LEVEL_LOAD.register(level -> {
            Registry.BLOCK.getTag(BlockTags.LEAVES).get().stream().map(Holder::value).forEach(block -> {
                FoodMappings.addPlant(block, 20);
            });
            Registry.BLOCK.getTag(BlockTags.FLOWERS).get().stream().map(Holder::value).forEach(block -> {
                FoodMappings.addPlant(block, 5);
            });
            Registry.BLOCK.getTag(BlockTags.SAPLINGS).get().stream().map(Holder::value).forEach(block -> {
                FoodMappings.addPlant(block, 15);
            });
        });
        FoodMappings.addPlant(Blocks.CAKE, 35);
        FoodMappings.addPlant(Blocks.CARROTS, 20);
        FoodMappings.addPlant(Blocks.WHEAT, 10);
        FoodMappings.addPlant(Blocks.HAY_BLOCK, 90);
        FoodMappings.addPlant(Blocks.MELON, 65);
        FoodMappings.addPlant(Blocks.BROWN_MUSHROOM, 15);
        FoodMappings.addPlant(Blocks.RED_MUSHROOM, 15);
        FoodMappings.addPlant(Blocks.POTATOES, 25);
        FoodMappings.addPlant(Blocks.PUMPKIN, 30);
        FoodMappings.addPlant(Blocks.SUGAR_CANE, 15);
        FoodMappings.addPlant(Blocks.TALL_GRASS, 5);
        FoodMappings.addPlant(Blocks.LILY_PAD, 15);
        FoodMappings.addPlant(ModBlocks.FERNS.get(), 10);
        //FoodMappings.addPlant(ModBlocks.PALM_LEAVES, 40);
        FoodMappings.addPlant(Blocks.CHORUS_FLOWER, 20);
        FoodMappings.addPlant(Blocks.CHORUS_PLANT, 10);
        FoodMappings.addPlant(Items.CHORUS_FRUIT);

        FoodMappings.addFish(Items.COD);
        FoodMappings.addFish(Items.SALMON);
        FoodMappings.addFish(Items.TROPICAL_FISH);
        FoodMappings.addFish(Items.PUFFERFISH);
        FoodMappings.addFish(Items.COOKED_COD);
        FoodMappings.addFish(Items.COOKED_SALMON);

        FoodMappings.addMeat(Items.COOKED_BEEF);
        FoodMappings.addMeat(Items.BEEF);
        FoodMappings.addMeat(Items.COOKED_CHICKEN);
        FoodMappings.addMeat(Items.CHICKEN);
        FoodMappings.addMeat(Items.PORKCHOP);
        FoodMappings.addMeat(Items.COOKED_PORKCHOP);
        FoodMappings.addMeat(ModItems.FAILURESAURUS_FLESH.get(), 15);
        FoodMappings.addMeat(Items.MUTTON);
        FoodMappings.addMeat(Items.COOKED_MUTTON);
        FoodMappings.addMeat(Items.RABBIT);
        FoodMappings.addMeat(Items.COOKED_RABBIT);
        FoodMappings.addMeat(Items.RABBIT_FOOT, 7);

        FoodMappings.addEgg(Items.EGG, 7);

        for (PrehistoricEntityType info : PrehistoricEntityType.values()) {
            if (info.mobType != PrehistoricMobType.FISH) {
                if (info.foodItem != null) FoodMappings.addMeat(info.foodItem);
                if (info.cookedFoodItem != null) FoodMappings.addMeat(info.cookedFoodItem);
            } else {
                if (info.eggItem != null) FoodMappings.addFish(info.eggItem, 35);
                if (info.foodItem != null) FoodMappings.addFish(info.foodItem);
                if (info.cookedFoodItem != null) FoodMappings.addFish(info.cookedFoodItem);
            }
            if (info.mobType == PrehistoricMobType.BIRD) {
                FoodMappings.addEgg(info.cultivatedBirdEggItem, 15);
                FoodMappings.addEgg(info.birdEggItem, 10);
            }
        }
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.mobType == PrehistoricMobType.VANILLA_BIRD) {
                FoodMappings.addEgg(info.cultivatedBirdEggItem, 15);
            }
        }
        FoodMappings.addMeat(EntityType.PLAYER, 27);
        FoodMappings.addMeat(EntityType.VILLAGER, 27);
        FoodMappings.addMeat(EntityType.ZOMBIE, 23);
        FoodMappings.addMeat(EntityType.CHICKEN, 5);
        FoodMappings.addMeat(EntityType.RABBIT, 7);
        FoodMappings.addMeat(EntityType.PARROT, 1);
        FoodMappings.addMeat(EntityType.LLAMA, 35);
        FoodMappings.addMeat(EntityType.POLAR_BEAR, 60);
        FoodMappings.addMeat(EntityType.COW, 40);
        FoodMappings.addMeat(EntityType.HORSE, 55);
        FoodMappings.addMeat(EntityType.PIG, 20);
        FoodMappings.addMeat(EntityType.SHEEP, 35);
        FoodMappings.addMeat(EntityType.SQUID, 30);
        FoodMappings.addMeat(EntityType.WOLF, 15);
        FoodMappings.addMeat(EntityType.OCELOT, 10);
        FoodMappings.addMeat(EntityType.GUARDIAN, 65);
        FoodMappings.addFish(EntityType.SQUID, 40);
        FoodMappings.addInsect(EntityType.SPIDER, 30);
        FoodMappings.addInsect(EntityType.CAVE_SPIDER, 15);
    }
}
