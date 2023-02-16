package com.fossil.fossil.util;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricMobType;
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
        FoodMappings.addPlant(Items.MELON, 10);
        FoodMappings.addPlant(Items.APPLE, 20);
        FoodMappings.addPlant(Items.BEETROOT, 20);
        FoodMappings.addPlant(Items.POTATO, 35);
        FoodMappings.addPlant(Items.BAKED_POTATO, 35);
        FoodMappings.addPlant(Items.CAKE, 50);
        FoodMappings.addPlant(Items.CARROT, 15);
        FoodMappings.addPlant(Items.COOKIE, 10);
        FoodMappings.addPlant(Items.PUMPKIN_PIE, 25);
        FoodMappings.addPlant(Items.SUGAR, 7);
        FoodMappings.addPlant(Items.BREAD, 25);
        FoodMappings.addPlant(Items.WHEAT_SEEDS, 5);
        FoodMappings.addPlant(Items.MELON_SEEDS, 5);
        FoodMappings.addPlant(Items.PUMPKIN_SEEDS, 5);
        FoodMappings.addPlant(Items.BEETROOT_SEEDS, 5);
        FoodMappings.addPlant(Blocks.CAKE, 35);
        FoodMappings.addPlant(Blocks.CARROTS, 20);
        FoodMappings.addPlant(Blocks.WHEAT, 10);
        FoodMappings.addPlant(Blocks.HAY_BLOCK, 90);
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
        FoodMappings.addPlant(Items.CHORUS_FRUIT, 15);

        FoodMappings.addFish(Items.COD, 20);
        FoodMappings.addFish(Items.SALMON, 20);
        FoodMappings.addFish(Items.TROPICAL_FISH, 15);
        FoodMappings.addFish(Items.PUFFERFISH, 15);
        FoodMappings.addFish(Items.COOKED_COD, 45);
        FoodMappings.addFish(Items.COOKED_SALMON, 45);
        FoodMappings.addFish(ModItems.COOKED_NAUTILUS.get(), 65);

        FoodMappings.addMeat(Items.COOKED_BEEF, 60);
        FoodMappings.addMeat(Items.BEEF, 40);
        FoodMappings.addMeat(Items.COOKED_CHICKEN, 15);
        FoodMappings.addMeat(Items.CHICKEN, 10);
        FoodMappings.addMeat(Items.PORKCHOP, 35);
        FoodMappings.addMeat(Items.COOKED_PORKCHOP, 55);
        FoodMappings.addMeat(ModItems.FAILURESAURUS_FLESH.get(), 15);
        FoodMappings.addMeat(Items.MUTTON, 30);
        FoodMappings.addMeat(Items.COOKED_MUTTON, 50);
        FoodMappings.addMeat(Items.RABBIT, 13);
        FoodMappings.addMeat(Items.COOKED_RABBIT, 17);
        FoodMappings.addMeat(Items.RABBIT_FOOT, 7);

        FoodMappings.addEgg(Items.EGG, 7);

        for (int i = 0; i < PrehistoricEntityType.values().length; i++) {
            PrehistoricEntityType entityType = PrehistoricEntityType.values()[i];
            if (entityType.timePeriod != TimePeriod.CURRENT) {
                if (entityType.mobType != PrehistoricMobType.FISH) {
                    FoodMappings.addMeat(entityType.foodItem, 25);
                    FoodMappings.addMeat(entityType.cookedFoodItem, 35);
                } else {
                    FoodMappings.addFish(entityType.eggItem, 35);
                    FoodMappings.addFish(entityType.fishItem, 35);
                    FoodMappings.addFish(entityType.cookedFoodItem, 75);
                }
            }
            if (entityType.mobType == PrehistoricMobType.BIRD || entityType.mobType == PrehistoricMobType.CHICKEN) {
                FoodMappings.addEgg(entityType.cultivatedBirdEggItem, 15);
                if (PrehistoricEntityType.values()[i].mobType != PrehistoricMobType.CHICKEN) {
                    FoodMappings.addEgg(entityType.birdEggItem, 10);
                }
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
        //FoodMappings.addMeat(Nautilus.class, 100);
        FoodMappings.addMeat(ModEntities.TRICERATOPS.get(), 120);
        /*FoodMappings.addMeat(Velociraptor.class, 20);
        FoodMappings.addMeat(Tyrannosaurus.class, 120);
        FoodMappings.addMeat(Pteranodon.class, 35);
        FoodMappings.addMeat(Mosasaurus.class, 50);
        FoodMappings.addMeat(Sarcosuchus.class, 50);
        FoodMappings.addMeat(Liopleurodon.class, 50);
        FoodMappings.addMeat(Stegosaurus.class, 50);
        FoodMappings.addMeat(Dilophosaurus.class, 25);
        FoodMappings.addMeat(Brachiosaurus.class, 90);
        FoodMappings.addMeat(Spinosaurus.class, 70);
        FoodMappings.addMeat(Compsognathus.class, 10);
        FoodMappings.addMeat(Ankylosaurus.class, 50);
        FoodMappings.addMeat(Pachycephalosaurus.class, 50);
        FoodMappings.addMeat(Deinonychus.class, 35);
        FoodMappings.addMeat(Gallimimus.class, 40);
        FoodMappings.addMeat(Allosaurus.class, 25);
        FoodMappings.addMeat(Dodo.class, 20);
        FoodMappings.addMeat(Quagga.class, 50);
        FoodMappings.addMeat(Titanis.class, 40);
        FoodMappings.addMeat(Ornitholestes.class, 25);
        FoodMappings.addMeat(Phorusrhacos.class, 40);
        FoodMappings.addMeat(Kelenken.class, 40);
        FoodMappings.addMeat(Titanis.class, 40);
        FoodMappings.addMeat(Mammoth.class, 100);
        FoodMappings.addMeat(Elasmotherium.class, 80);
        FoodMappings.addMeat(Confuciusornis.class, 15);
        FoodMappings.addMeat(Ceratosaurus.class, 50);
        FoodMappings.addMeat(Dryosaurus.class, 25);*/
        FoodMappings.addMeat(ModEntities.THERIZINOSAURUS.get(), 125);
        /*FoodMappings.addMeat(Parasaurolophus.class, 150);
        FoodMappings.addMeat(Platybelodon.class, 90);
        FoodMappings.addFish(Coelacanth.class, 20);
        FoodMappings.addFish(Sturgeon.class, 20);
        FoodMappings.addFish(AlligatorGar.class, 20);
        FoodMappings.addMeat(Meganeura.class, 15);
        FoodMappings.addMeat(Megaloceros.class, 60);
        FoodMappings.addMeat(Megalania.class, 70);
        FoodMappings.addMeat(Edaphosaurus.class, 50);
        FoodMappings.addMeat(Arthropleura.class, 30);
        FoodMappings.addMeat(Citipati.class, 60);
        FoodMappings.addMeat(Diplodocus.class, 160);
        FoodMappings.addMeat(Megalograptus.class, 25);
        FoodMappings.addMeat(Smilodon.class, 35);
        FoodMappings.addFish(Megalograptus.class, 25);
        FoodMappings.addFish(Nautilus.class, 30);
        FoodMappings.addFish(Coelacanth.class, 40);
        FoodMappings.addFish(AlligatorGar.class, 50);
        FoodMappings.addFish(Sturgeon.class, 50);*/
        FoodMappings.addFish(EntityType.SQUID, 40);
        /*FoodMappings.addFish(Tiktaalik.class, 40);
        FoodMappings.addFish(Crassigyrinus.class, 35);
        FoodMappings.addMeat(Diplocaulus.class, 20);
        FoodMappings.addFish(Diplocaulus.class, 20);*/
        FoodMappings.addInsect(EntityType.SPIDER, 30);
        FoodMappings.addInsect(EntityType.CAVE_SPIDER, 15);
    }
}
