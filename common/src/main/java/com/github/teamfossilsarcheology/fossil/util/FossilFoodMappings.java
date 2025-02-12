package com.github.teamfossilsarcheology.fossil.util;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import static com.github.teamfossilsarcheology.fossil.util.FoodMappings.addFishEntity;
import static com.github.teamfossilsarcheology.fossil.util.FoodMappings.addMeatEntity;

public class FossilFoodMappings {
    public static void register() {
        FoodMappings.addPlant(Items.SUGAR_CANE, 15);
        FoodMappings.addPlant(Items.WHEAT, 13);
        FoodMappings.addPlant(Items.MELON_SLICE);
        FoodMappings.addPlant(Items.APPLE);
        FoodMappings.addPlant(Items.BEETROOT);
        FoodMappings.addPlant(Items.POTATO);
        FoodMappings.addPlant(Items.BAKED_POTATO);
        FoodMappings.addPlant(Items.CAKE, 60);
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
            Registry.BLOCK.getOrCreateTag(BlockTags.LEAVES).stream().map(Holder::value).forEach(block -> {
                FoodMappings.addPlant(block, 20);
            });
            Registry.BLOCK.getOrCreateTag(BlockTags.FLOWERS).stream().map(Holder::value).forEach(block -> {
                FoodMappings.addPlant(block, 5);
            });
            Registry.BLOCK.getOrCreateTag(BlockTags.SAPLINGS).stream().map(Holder::value).forEach(block -> {
                FoodMappings.addPlant(block, 15);
            });
        });
        FoodMappings.addPlant(Blocks.CAKE, 60);
        FoodMappings.addPlant(Blocks.CARROTS, 20);
        FoodMappings.addPlant(Blocks.WHEAT, 13);
        FoodMappings.addPlant(Blocks.HAY_BLOCK, 90);
        FoodMappings.addPlant(Blocks.MELON, 65);
        FoodMappings.addPlant(Blocks.BROWN_MUSHROOM, 15);
        FoodMappings.addPlant(Blocks.RED_MUSHROOM, 15);
        FoodMappings.addPlant(Blocks.POTATOES, 25);
        FoodMappings.addPlant(Blocks.PUMPKIN, 30);
        FoodMappings.addPlant(Blocks.SUGAR_CANE, 15);
        FoodMappings.addPlant(Blocks.GRASS, 5);
        FoodMappings.addPlant(Blocks.TALL_GRASS, 10);
        FoodMappings.addPlant(Blocks.LILY_PAD, 15);
        FoodMappings.addPlant(ModBlocks.FERNS.get(), 10);
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

        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (info.mobType == PrehistoricMobType.FISH) {
                if (info.eggItem != null) FoodMappings.addFish(info.eggItem, 35);
                if (info.foodItem != null) FoodMappings.addFish(info.foodItem);
                if (info.cookedFoodItem != null) FoodMappings.addFish(info.cookedFoodItem);
            } else {
                if (info.foodItem != null) FoodMappings.addMeat(info.foodItem);
                if (info.cookedFoodItem != null) FoodMappings.addMeat(info.cookedFoodItem);
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
        FoodMappings.addMeat(EntityType.RABBIT, 4);
        FoodMappings.addMeat(EntityType.PARROT, 2);
        FoodMappings.addMeat(EntityType.LLAMA, 50);
        FoodMappings.addMeat(EntityType.POLAR_BEAR, 60);
        FoodMappings.addMeat(EntityType.COW, 40);
        FoodMappings.addMeat(EntityType.HORSE, 55);
        FoodMappings.addMeat(EntityType.PIG, 20);
        FoodMappings.addMeat(EntityType.SHEEP, 35);
        FoodMappings.addMeat(EntityType.SQUID, 30);
        FoodMappings.addMeat(EntityType.WOLF, 15);
        FoodMappings.addMeat(EntityType.OCELOT, 7);
        FoodMappings.addFish(EntityType.GUARDIAN, 65);
        FoodMappings.addFish(EntityType.SQUID, 40);
        FoodMappings.addInsect(EntityType.SPIDER, 30);
        FoodMappings.addInsect(EntityType.CAVE_SPIDER, 15);

        addMeatEntity(new ResourceLocation("rats", "rat"), 5);
        
        addMeatEntity(new ResourceLocation("bewitchment", "owl"), 7);
        addMeatEntity(new ResourceLocation("bewitchment", "raven"), 5);
        addMeatEntity(new ResourceLocation("bewitchment", "snake"), 4);
        addMeatEntity(new ResourceLocation("bewitchment", "toad"), 3);
        
        addMeatEntity(new ResourceLocation("betteranimalsplus", "deer"), 35);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "pheasant"), 10);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "turkey"), 10);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "goose"), 10);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "boar"), 30);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "moose"), 45);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "reindeer"), 35);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "squirrel"), 3);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "songbird"), 3);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "lammergeier"), 8);
        addMeatEntity(new ResourceLocation("betteranimalsplus", "gazelle"), 15);
        addFishEntity(new ResourceLocation("betteranimalsplus", "horseshoecrab"), 7);
        addFishEntity(new ResourceLocation("betteranimalsplus", "nautilus"), 10);
        addFishEntity(new ResourceLocation("betteranimalsplus", "lamprey"), 5);
        addFishEntity(new ResourceLocation("betteranimalsplus", "crab"), 5);
        addFishEntity(new ResourceLocation("betteranimalsplus", "shark"), 40);
        addFishEntity(new ResourceLocation("betteranimalsplus", "eel"), 20);
        addFishEntity(new ResourceLocation("betteranimalsplus", "whale"), 60);
        addFishEntity(new ResourceLocation("betteranimalsplus", "flying_fish"), 5);

        addMeatEntity(new ResourceLocation("totemic", "buffalo"), 55);
        addMeatEntity(new ResourceLocation("totemic", "bald_eagle"), 8);

        addMeatEntity(new ResourceLocation("quark", "crab"), 5);
        addMeatEntity(new ResourceLocation("quark", "frog"), 3);

        addMeatEntity(new ResourceLocation("exoticbirds", "cassowary"), 25);
        addMeatEntity(new ResourceLocation("exoticbirds", "duck"), 10);
        addMeatEntity(new ResourceLocation("exoticbirds", "flamingo"), 7);
        addMeatEntity(new ResourceLocation("exoticbirds", "gouldianfinch"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "hummingbird"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "kingfisher"), 5);
        addMeatEntity(new ResourceLocation("exoticbirds", "kiwi"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "lyrebird"), 5);
        addMeatEntity(new ResourceLocation("exoticbirds", "magpie"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "ostrich"), 27);
        addMeatEntity(new ResourceLocation("exoticbirds", "owl"), 7);
        addMeatEntity(new ResourceLocation("exoticbirds", "parrot"), 5);
        addMeatEntity(new ResourceLocation("exoticbirds", "peafowl"), 10);
        addMeatEntity(new ResourceLocation("exoticbirds", "pelican"), 5);
        addMeatEntity(new ResourceLocation("exoticbirds", "emperorpenguin"), 7);
        addMeatEntity(new ResourceLocation("exoticbirds", "pigeon"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "roadrunner"), 5);
        addMeatEntity(new ResourceLocation("exoticbirds", "seagull"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "swan"), 12);
        addMeatEntity(new ResourceLocation("exoticbirds", "toucan"), 7);
        addMeatEntity(new ResourceLocation("exoticbirds", "vulture"), 7);
        addMeatEntity(new ResourceLocation("exoticbirds", "woodpecker"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "heron"), 15);
        addMeatEntity(new ResourceLocation("exoticbirds", "booby"), 7);
        addMeatEntity(new ResourceLocation("exoticbirds", "cardinal"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "bluejay"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "robin"), 3);
        addMeatEntity(new ResourceLocation("exoticbirds", "crane"), 15);
        addMeatEntity(new ResourceLocation("exoticbirds", "kookaburra"), 5);
        addMeatEntity(new ResourceLocation("exoticbirds", "budgerigar"), 3);

        addMeatEntity(new ResourceLocation("twilightforest", "boar"), 20);
        addMeatEntity(new ResourceLocation("twilightforest", "bighorn_sheep"), 35);
        addMeatEntity(new ResourceLocation("twilightforest", "deer"), 35);
        addMeatEntity(new ResourceLocation("twilightforest", "penguin"), 10);
        addMeatEntity(new ResourceLocation("twilightforest", "squirrel"), 3);

        addMeatEntity(new ResourceLocation("naturalist", "boar"), 10);
        addMeatEntity(new ResourceLocation("naturalist", "bear"), 35);
        addMeatEntity(new ResourceLocation("naturalist", "deer"), 40);
        addMeatEntity(new ResourceLocation("naturalist", "snake"), 10);
        addMeatEntity(new ResourceLocation("naturalist", "coral_snake"), 10);
        addMeatEntity(new ResourceLocation("naturalist", "rattlesnake"), 10);
        addMeatEntity(new ResourceLocation("naturalist", "rhino"), 60);
        addMeatEntity(new ResourceLocation("naturalist", "zebra"), 40);
        addMeatEntity(new ResourceLocation("naturalist", "giraffe"), 50);
        addMeatEntity(new ResourceLocation("naturalist", "vulture"), 15);
        addMeatEntity(new ResourceLocation("naturalist", "ostrich"), 35);
        addFishEntity(new ResourceLocation("naturalist", "catfish"), 10);
        addFishEntity(new ResourceLocation("naturalist", "bass"), 10);
        addFishEntity(new ResourceLocation("naturalist", "duck"), 10);

		addMeatEntity(new ResourceLocation("alexsmobs", "anteater"), 10);
		addMeatEntity(new ResourceLocation("alexsmobs", "bald_eagle"), 8);
		addMeatEntity(new ResourceLocation("alexsmobs", "bison"), 20);
		addMeatEntity(new ResourceLocation("alexsmobs", "capuchin_monkey"), 5);
		addMeatEntity(new ResourceLocation("alexsmobs", "crow"), 4);
		addMeatEntity(new ResourceLocation("alexsmobs", "dropbear"), 11);
		addMeatEntity(new ResourceLocation("alexsmobs", "elephant"), 33);
		addMeatEntity(new ResourceLocation("alexsmobs", "emu"), 10);
		addMeatEntity(new ResourceLocation("alexsmobs", "gazelle"), 4);
		addMeatEntity(new ResourceLocation("alexsmobs", "gelada_monkey"), 9);
		addMeatEntity(new ResourceLocation("alexsmobs", "gorilla"), 25);
		addMeatEntity(new ResourceLocation("alexsmobs", "grizzly_bear"), 28);
		addMeatEntity(new ResourceLocation("alexsmobs", "hummingbird"), 2);
		addMeatEntity(new ResourceLocation("alexsmobs", "jerboa"), 2);
		addMeatEntity(new ResourceLocation("alexsmobs", "kangaroo"), 11);
		addMeatEntity(new ResourceLocation("alexsmobs", "komodo_dragon"), 15);
		addMeatEntity(new ResourceLocation("alexsmobs", "maned_wolf"), 8);
		addMeatEntity(new ResourceLocation("alexsmobs", "moose"), 28);
		addMeatEntity(new ResourceLocation("alexsmobs", "racoon"), 5);
		addMeatEntity(new ResourceLocation("alexsmobs", "rattlesnake"), 4);
		addMeatEntity(new ResourceLocation("alexsmobs", "roadrunner"), 4);
		addMeatEntity(new ResourceLocation("alexsmobs", "seagull"), 4);
		addMeatEntity(new ResourceLocation("alexsmobs", "shoebill"), 5);
		addMeatEntity(new ResourceLocation("alexsmobs", "snow_leopard"), 15);
		addMeatEntity(new ResourceLocation("alexsmobs", "sunbird"), 10);
		addMeatEntity(new ResourceLocation("alexsmobs", "tasmanian_devil"), 7);
		addMeatEntity(new ResourceLocation("alexsmobs", "tiger"), 25);
		addMeatEntity(new ResourceLocation("alexsmobs", "toucan"), 3);
		addMeatEntity(new ResourceLocation("alexsmobs", "tusklin"), 20);
		addMeatEntity(new ResourceLocation("alexsmobs", "platypus"), 5);
		addFishEntity(new ResourceLocation("alexsmobs", "blobfish"), 4);
		addFishEntity(new ResourceLocation("alexsmobs", "cachalot_whale"), 80);
		addFishEntity(new ResourceLocation("alexsmobs", "catfish"), 5);
		addFishEntity(new ResourceLocation("alexsmobs", "comb_jelly"), 3);
		addFishEntity(new ResourceLocation("alexsmobs", "cosmic_cod"), 2);
		addFishEntity(new ResourceLocation("alexsmobs", "devils_hole_pupfish"), 1);
		addFishEntity(new ResourceLocation("alexsmobs", "flying_fish"), 3);
		addFishEntity(new ResourceLocation("alexsmobs", "frilled_shark"), 10);
		addFishEntity(new ResourceLocation("alexsmobs", "giant_squid"), 19);
		addFishEntity(new ResourceLocation("alexsmobs", "hammerhead_shark"), 15);
		addFishEntity(new ResourceLocation("alexsmobs", "lobster"), 3);
		addFishEntity(new ResourceLocation("alexsmobs", "mantis_shrimp"), 10);
		addFishEntity(new ResourceLocation("alexsmobs", "mimic_octopus"), 8);
		addFishEntity(new ResourceLocation("alexsmobs", "orca"), 30);
		addFishEntity(new ResourceLocation("alexsmobs", "sea_bear"), 100);
		addFishEntity(new ResourceLocation("alexsmobs", "seal"), 5);
		addFishEntity(new ResourceLocation("alexsmobs", "terrapin"), 7);
		addFishEntity(new ResourceLocation("alexsmobs", "anaconda"), 5);
		addFishEntity(new ResourceLocation("alexsmobs", "crocodile"), 15);
    }
}
