package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.food.FoodType;
import com.github.teamfossilsarcheology.fossil.food.FoodValueProvider;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;


public class ModFoodValueProvider extends FoodValueProvider {

    public ModFoodValueProvider(PackOutput output) {
        super(output);
    }

    protected void buildFoodValues() {
        var egg = type(FoodType.EGG);
        var fish = type(FoodType.FISH);
        var meat = type(FoodType.MEAT);
        var plant = type(FoodType.PLANT);

        egg.item(Items.EGG, 7);

        plant.itemTag(TagKey.create(Registries.ITEM, new ResourceLocation("c:foods/berry")));
        plant.itemTag(TagKey.create(Registries.ITEM, new ResourceLocation("c:foods/bread")));
        plant.itemTag(TagKey.create(Registries.ITEM, new ResourceLocation("c:foods/fruit")));
        plant.itemTag(TagKey.create(Registries.ITEM, new ResourceLocation("c:foods/vegetable")));
        //crop: nutrition * max drop
        plant.block(Blocks.BEETROOTS, Foods.BEETROOT.getNutrition() + 2);
        plant.block(Blocks.BROWN_MUSHROOM, 3);
        plant.block(Blocks.CAKE, 14);
        plant.block(Blocks.CARROTS, Foods.CARROT.getNutrition() * 5);
        plant.block(Blocks.CHORUS_FLOWER, Foods.CHORUS_FRUIT.getNutrition() * 2);
        plant.block(Blocks.CHORUS_PLANT, Foods.CHORUS_FRUIT.getNutrition());
        plant.block(Blocks.GRASS, 1);
        plant.block(Blocks.HAY_BLOCK, 15);
        plant.block(Blocks.KELP, Foods.DRIED_KELP.getNutrition());
        plant.block(Blocks.KELP_PLANT, Foods.DRIED_KELP.getNutrition());
        plant.block(Blocks.LILY_PAD, 2);
        plant.block(Blocks.MELON, Foods.MELON_SLICE.getNutrition() * 5);
        plant.block(Blocks.POTATOES, Foods.POTATO.getNutrition() * 5);
        plant.block(Blocks.PUMPKIN, Foods.MELON_SLICE.getNutrition() * 4);
        plant.block(Blocks.RED_MUSHROOM, 3);
        plant.block(Blocks.SUGAR_CANE, 3);
        plant.block(Blocks.SWEET_BERRY_BUSH, Foods.SWEET_BERRIES.getNutrition() * 3);
        plant.block(Blocks.TALL_GRASS, 2);
        plant.block(Blocks.WHEAT, 3);

        plant.block(PrehistoricPlantInfo.BENNETTITALES_LARGE.getPlantBlock(), 6);
        plant.block(PrehistoricPlantInfo.BENNETTITALES_SMALL.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.CEPHALOTAXUS.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.CRATAEGUS.getPlantBlock(), Foods.SWEET_BERRIES.getNutrition() * 3 + 1);
        plant.block(PrehistoricPlantInfo.CYATHEA.getPlantBlock(), 12);
        plant.block(PrehistoricPlantInfo.DICTYOPHYLLUM.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.DILLHOFFIA.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.DIPTERIS.getPlantBlock(), 6);
        plant.block(PrehistoricPlantInfo.DUISBERGIA.getPlantBlock(), 6);
        plant.block(PrehistoricPlantInfo.EPHEDRA.getPlantBlock(), Foods.SWEET_BERRIES.getNutrition() * 2 + 1);
        plant.block(PrehistoricPlantInfo.FLORISSANTIA.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.FOOZIA.getPlantBlock(), 6);
        plant.block(PrehistoricPlantInfo.HORSETAIL_LARGE.getPlantBlock(), 6);
        plant.block(PrehistoricPlantInfo.HORSETAIL_SMALL.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.LICOPODIOPHYTA.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.OSMUNDA.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.SAGENOPTERIS.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.SARRACENIA.getPlantBlock(), 6);
        plant.block(PrehistoricPlantInfo.VACCINIUM.getPlantBlock(), Foods.SWEET_BERRIES.getNutrition() * 3 + 1);
        plant.block(PrehistoricPlantInfo.WELWITSCHIA.getPlantBlock(), 3);
        plant.block(PrehistoricPlantInfo.ZAMITES.getPlantBlock(), 6);
        plant.block(ModBlocks.FERNS.get(), 3);

        plant.item(Items.APPLE);
        plant.item(Items.BAKED_POTATO);
        plant.item(Items.BEETROOT);
        plant.item(Items.BEETROOT_SEEDS, 1);
        plant.item(Items.BREAD);
        plant.item(Items.CAKE, 14 * 5);
        plant.item(Items.CARROT);
        plant.item(Items.CHORUS_FRUIT);
        plant.item(Items.COOKIE);
        plant.item(Items.DRIED_KELP);
        plant.item(Items.GLOW_BERRIES);
        plant.item(Items.MELON_SEEDS, 5);
        plant.item(Items.MELON_SLICE);
        plant.item(Items.POTATO);
        plant.item(Items.PUMPKIN_PIE);
        plant.item(Items.PUMPKIN_SEEDS, 5);
        plant.item(Items.SUGAR, 7);
        plant.item(Items.SUGAR_CANE, 15);
        plant.item(Items.SWEET_BERRIES);
        plant.item(Items.WHEAT, 13);
        plant.item(Items.WHEAT_SEEDS, 5);

        fish.itemTag(TagKey.create(Registries.ITEM, new ResourceLocation("c:foods/raw_fish")));
        fish.itemTag(TagKey.create(Registries.ITEM, new ResourceLocation("c:foods/cooked_fish")));
        fish.item(Items.COD);
        fish.item(Items.PUFFERFISH);
        fish.item(Items.SALMON);
        fish.item(Items.TROPICAL_FISH);
        fish.item(Items.COOKED_COD);
        fish.item(Items.COOKED_SALMON);

        meat.itemTag(TagKey.create(Registries.ITEM, new ResourceLocation("c:foods/raw_meat")));
        meat.itemTag(TagKey.create(Registries.ITEM, new ResourceLocation("c:foods/cooked_meat")));
        meat.item(Items.PORKCHOP);
        meat.item(Items.COOKED_PORKCHOP);
        meat.item(Items.BEEF);
        meat.item(Items.COOKED_BEEF);
        meat.item(Items.COOKED_CHICKEN);
        meat.item(Items.CHICKEN);
        meat.item(ModItems.FAILURESAURUS_FLESH.get(), 15);
        meat.item(Items.MUTTON);
        meat.item(Items.COOKED_MUTTON);
        meat.item(Items.RABBIT);
        meat.item(Items.COOKED_RABBIT);
        meat.item(Items.RABBIT_FOOT, 7);

        meat.entity(EntityType.AXOLOTL, 5);
        meat.entity(EntityType.BAT, 5);
        meat.entity(EntityType.CAT, 10);
        meat.entity(EntityType.CHICKEN, 7);//Drops not included
        fish.entity(EntityType.COD, 5);
        meat.entity(EntityType.COW, 40);
        fish.entity(EntityType.DOLPHIN, 17);
        meat.entity(EntityType.DONKEY, 45);
        meat.entity(EntityType.FOX, 15);
        meat.entity(EntityType.GOAT, 30);
        fish.entity(EntityType.GLOW_SQUID, 20);
        meat.entity(EntityType.HOGLIN, 55);
        meat.entity(EntityType.HORSE, 55);
        meat.entity(EntityType.LLAMA, 50);
        meat.entity(EntityType.MOOSHROOM, 40);
        meat.entity(EntityType.MULE, 50);
        meat.entity(EntityType.OCELOT, 4);
        meat.entity(EntityType.PANDA, 27);
        meat.entity(EntityType.PARROT, 2);
        meat.entity(EntityType.PIG, 20);
        meat.entity(EntityType.POLAR_BEAR, 60);
        fish.entity(EntityType.PUFFERFISH, 5);
        meat.entity(EntityType.RABBIT, 5);
        fish.entity(EntityType.SALMON, 5);
        meat.entity(EntityType.SHEEP, 35);
        fish.entity(EntityType.SQUID, 20);
        fish.entity(EntityType.TROPICAL_FISH, 5);
        fish.entity(EntityType.TURTLE, 5);
        meat.entity(EntityType.WOLF, 15);

        meat.entity(EntityType.PLAYER, 27);
        meat.entity(EntityType.VILLAGER, 27);
        fish.entity(EntityType.GUARDIAN, 65);
        meat.entity(EntityType.SPIDER, 30);
        meat.entity(EntityType.CAVE_SPIDER, 15);


        meat.entity(new ResourceLocation("rats", "rat"), 5);

        meat.entity(new ResourceLocation("bewitchment", "owl"), 7);
        meat.entity(new ResourceLocation("bewitchment", "raven"), 5);
        meat.entity(new ResourceLocation("bewitchment", "snake"), 4);
        meat.entity(new ResourceLocation("bewitchment", "toad"), 3);

        //TODO: Farmers delight tags?
        String betterAnimalsPlus = "betteranimalsplus";
        meat.entity(new ResourceLocation(betterAnimalsPlus, "deer"), 35);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "pheasant"), 10);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "turkey"), 10);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "goose"), 10);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "boar"), 30);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "moose"), 45);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "reindeer"), 35);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "squirrel"), 3);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "songbird"), 3);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "lammergeier"), 8);
        meat.entity(new ResourceLocation(betterAnimalsPlus, "gazelle"), 15);
        fish.entity(new ResourceLocation(betterAnimalsPlus, "horseshoecrab"), 7);
        fish.entity(new ResourceLocation(betterAnimalsPlus, "nautilus"), 10);
        fish.entity(new ResourceLocation(betterAnimalsPlus, "lamprey"), 5);
        fish.entity(new ResourceLocation(betterAnimalsPlus, "crab"), 5);
        fish.entity(new ResourceLocation(betterAnimalsPlus, "shark"), 40);
        fish.entity(new ResourceLocation(betterAnimalsPlus, "eel"), 20);
        fish.entity(new ResourceLocation(betterAnimalsPlus, "whale"), 60);
        fish.entity(new ResourceLocation(betterAnimalsPlus, "flying_fish"), 5);

        meat.entity(new ResourceLocation("totemic", "buffalo"), 55);
        meat.entity(new ResourceLocation("totemic", "bald_eagle"), 8);

        meat.entity(new ResourceLocation("quark", "crab"), 5);
        meat.entity(new ResourceLocation("quark", "frog"), 3);

        String exoticBirds = "exoticbirds";
        meat.entity(new ResourceLocation(exoticBirds, "cassowary"), 25);
        meat.entity(new ResourceLocation(exoticBirds, "duck"), 10);
        meat.entity(new ResourceLocation(exoticBirds, "flamingo"), 7);
        meat.entity(new ResourceLocation(exoticBirds, "gouldianfinch"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "hummingbird"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "kingfisher"), 5);
        meat.entity(new ResourceLocation(exoticBirds, "kiwi"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "lyrebird"), 5);
        meat.entity(new ResourceLocation(exoticBirds, "magpie"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "ostrich"), 27);
        meat.entity(new ResourceLocation(exoticBirds, "owl"), 7);
        meat.entity(new ResourceLocation(exoticBirds, "parrot"), 5);
        meat.entity(new ResourceLocation(exoticBirds, "peafowl"), 10);
        meat.entity(new ResourceLocation(exoticBirds, "pelican"), 5);
        meat.entity(new ResourceLocation(exoticBirds, "emperorpenguin"), 7);
        meat.entity(new ResourceLocation(exoticBirds, "pigeon"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "roadrunner"), 5);
        meat.entity(new ResourceLocation(exoticBirds, "seagull"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "swan"), 12);
        meat.entity(new ResourceLocation(exoticBirds, "toucan"), 7);
        meat.entity(new ResourceLocation(exoticBirds, "vulture"), 7);
        meat.entity(new ResourceLocation(exoticBirds, "woodpecker"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "heron"), 15);
        meat.entity(new ResourceLocation(exoticBirds, "booby"), 7);
        meat.entity(new ResourceLocation(exoticBirds, "cardinal"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "bluejay"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "robin"), 3);
        meat.entity(new ResourceLocation(exoticBirds, "crane"), 15);
        meat.entity(new ResourceLocation(exoticBirds, "kookaburra"), 5);
        meat.entity(new ResourceLocation(exoticBirds, "budgerigar"), 3);

        meat.entity(new ResourceLocation("twilightforest", "boar"), 20);
        meat.entity(new ResourceLocation("twilightforest", "bighorn_sheep"), 35);
        meat.entity(new ResourceLocation("twilightforest", "deer"), 35);
        meat.entity(new ResourceLocation("twilightforest", "penguin"), 10);
        meat.entity(new ResourceLocation("twilightforest", "squirrel"), 3);

        meat.entity(new ResourceLocation("naturalist", "boar"), 10);
        meat.entity(new ResourceLocation("naturalist", "bear"), 35);
        meat.entity(new ResourceLocation("naturalist", "deer"), 40);
        meat.entity(new ResourceLocation("naturalist", "snake"), 10);
        meat.entity(new ResourceLocation("naturalist", "coral_snake"), 10);
        meat.entity(new ResourceLocation("naturalist", "rattlesnake"), 10);
        meat.entity(new ResourceLocation("naturalist", "rhino"), 60);
        meat.entity(new ResourceLocation("naturalist", "zebra"), 40);
        meat.entity(new ResourceLocation("naturalist", "giraffe"), 50);
        meat.entity(new ResourceLocation("naturalist", "vulture"), 15);
        meat.entity(new ResourceLocation("naturalist", "ostrich"), 35);
        fish.entity(new ResourceLocation("naturalist", "catfish"), 10);
        fish.entity(new ResourceLocation("naturalist", "bass"), 10);
        fish.entity(new ResourceLocation("naturalist", "duck"), 10);
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Food Values";
    }
}
