package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.AmphoraVaseBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.KylixVaseBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.VaseBlock;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.item.ToyBallItem;
import com.github.teamfossilsarcheology.fossil.item.ToyScratchingPostItem;
import com.github.teamfossilsarcheology.fossil.item.ToyTetheredLogItem;
import com.github.teamfossilsarcheology.fossil.recipe.*;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import com.github.teamfossilsarcheology.fossil.util.TimePeriod;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.*;
import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo.*;
import static com.github.teamfossilsarcheology.fossil.item.ModItems.*;

public class ModRecipeProvider extends RecipeProvider {
    public static final BlockFamily ANCIENT_WOOD_PLANKS = new BlockFamily.Builder(ModBlocks.ANCIENT_WOOD_PLANKS.get()).slab(ANCIENT_WOOD_SLAB.get()).stairs(ANCIENT_WOOD_STAIRS.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily CALAMITES_PLANKS = new BlockFamily.Builder(ModBlocks.CALAMITES_PLANKS.get()).button(CALAMITES_BUTTON.get()).fence(CALAMITES_FENCE.get()).fenceGate(CALAMITES_FENCE_GATE.get()).pressurePlate(CALAMITES_PRESSURE_PLATE.get()).slab(CALAMITES_SLAB.get()).stairs(CALAMITES_STAIRS.get()).door(CALAMITES_DOOR.get()).trapdoor(CALAMITES_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily CORDAITES_PLANKS = new BlockFamily.Builder(ModBlocks.CORDAITES_PLANKS.get()).button(CORDAITES_BUTTON.get()).fence(CORDAITES_FENCE.get()).fenceGate(CORDAITES_FENCE_GATE.get()).pressurePlate(CORDAITES_PRESSURE_PLATE.get()).slab(CORDAITES_SLAB.get()).stairs(CORDAITES_STAIRS.get()).door(CORDAITES_DOOR.get()).trapdoor(CORDAITES_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily MUTANT_TREE_PLANKS = new BlockFamily.Builder(ModBlocks.MUTANT_TREE_PLANKS.get()).button(MUTANT_TREE_BUTTON.get()).fence(MUTANT_TREE_FENCE.get()).fenceGate(MUTANT_TREE_FENCE_GATE.get()).pressurePlate(MUTANT_TREE_PRESSURE_PLATE.get()).slab(MUTANT_TREE_SLAB.get()).stairs(MUTANT_TREE_STAIRS.get()).door(MUTANT_TREE_DOOR.get()).trapdoor(MUTANT_TREE_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily PALM_PLANKS = new BlockFamily.Builder(ModBlocks.PALM_PLANKS.get()).button(PALM_BUTTON.get()).fence(PALM_FENCE.get()).fenceGate(PALM_FENCE_GATE.get()).pressurePlate(PALM_PRESSURE_PLATE.get()).slab(PALM_SLAB.get()).stairs(PALM_STAIRS.get()).door(PALM_DOOR.get()).trapdoor(PALM_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily SIGILLARIA_PLANKS = new BlockFamily.Builder(ModBlocks.SIGILLARIA_PLANKS.get()).button(SIGILLARIA_BUTTON.get()).fence(SIGILLARIA_FENCE.get()).fenceGate(SIGILLARIA_FENCE_GATE.get()).pressurePlate(SIGILLARIA_PRESSURE_PLATE.get()).slab(SIGILLARIA_SLAB.get()).stairs(SIGILLARIA_STAIRS.get()).door(SIGILLARIA_DOOR.get()).trapdoor(SIGILLARIA_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily TEMPSKYA_PLANKS = new BlockFamily.Builder(ModBlocks.TEMPSKYA_PLANKS.get()).button(TEMPSKYA_BUTTON.get()).fence(TEMPSKYA_FENCE.get()).fenceGate(TEMPSKYA_FENCE_GATE.get()).pressurePlate(TEMPSKYA_PRESSURE_PLATE.get()).slab(TEMPSKYA_SLAB.get()).stairs(TEMPSKYA_STAIRS.get()).door(TEMPSKYA_DOOR.get()).trapdoor(TEMPSKYA_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    private static final Map<DyeColor, ItemLike> ITEM_BY_DYE = Util.make(Maps.newEnumMap(DyeColor.class), enumMap -> {
        enumMap.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        enumMap.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        enumMap.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        enumMap.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        enumMap.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        enumMap.put(DyeColor.LIME, Blocks.LIME_WOOL);
        enumMap.put(DyeColor.PINK, Blocks.PINK_WOOL);
        enumMap.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        enumMap.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        enumMap.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        enumMap.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        enumMap.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        enumMap.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        enumMap.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        enumMap.put(DyeColor.RED, Blocks.RED_WOOL);
        enumMap.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });

    public ModRecipeProvider(PackOutput arg) {
        super(arg);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        boolean cookingRecipes = true;
        boolean craftingRecipes = true;
        boolean machineRecipes = true;
        if (cookingRecipes) {
            for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
                if (info.foodItem != null && info.cookedFoodItem != null) {
                    fullCooking(RecipeCategory.FOOD, info.foodItem, info.cookedFoodItem, consumer, 1.5f);
                }
            }
            fullOre(RecipeCategory.BUILDING_BLOCKS, DENSE_SAND.get(), REINFORCED_GLASS.get(), consumer, 3);
        }
        if (craftingRecipes) {
            ToyBallItem white = TOY_BALLS.get(DyeColor.WHITE).get();
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, white).define('W', Blocks.WHITE_WOOL).define('S', Items.STRING)
                    .define('B', Items.SLIME_BALL).pattern("SWS").pattern("WBW").pattern("SWS").unlockedBy("has_wool",
                            RecipeProvider.has(ItemTags.WOOL)).save(consumer, RecipeBuilder.getDefaultRecipeId(white));
            for (Map.Entry<DyeColor, RegistrySupplier<ToyBallItem>> entry : TOY_BALLS.entrySet()) {
                DyeColor color = entry.getKey();
                ToyBallItem ball = entry.getValue().get();
                if (color == DyeColor.WHITE) continue;
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ball).requires(colorToDye(color)).requires(white).unlockedBy("has_ball",
                        RecipeProvider.has(white)).save(consumer, FossilMod.MOD_ID + ":toy_ball_white_to_" + color.getName());
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, white).requires(Items.WHITE_DYE).requires(ball).unlockedBy("has_ball",
                        RecipeProvider.has(white)).save(consumer, FossilMod.MOD_ID + ":toy_ball_" + color.getName() + "_to_white");
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ball).define('W', ITEM_BY_DYE.get(entry.getKey())).define('S', Items.STRING)
                        .define('B', Items.SLIME_BALL).pattern("SWS").pattern("WBW").pattern("SWS").unlockedBy("has_wool",
                                RecipeProvider.has(ItemTags.WOOL)).save(consumer, RecipeBuilder.getDefaultRecipeId(ball));
            }
            for (Map.Entry<String, RegistrySupplier<ToyTetheredLogItem>> entry : TOY_TETHERED_LOGS.entrySet()) {
                var block = BuiltInRegistries.BLOCK.getOptional(new ResourceLocation("minecraft:" + entry.getKey() + "_log"));
                block.ifPresent(log -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, entry.getValue().get()).define('S', Items.STRING).define('L', log)
                        .pattern("S").pattern("S").pattern("L").unlockedBy("has_log", RecipeProvider.has(log)).save(consumer));
            }
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TOY_TETHERED_LOGS.get(WoodType.CRIMSON.name()).get()).define('S', Items.STRING)
                    .define('L', Blocks.CRIMSON_STEM).pattern("S").pattern("S").pattern("L").unlockedBy("has_log",
                            RecipeProvider.has(Blocks.CRIMSON_STEM)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TOY_TETHERED_LOGS.get(WoodType.WARPED.name()).get()).define('S', Items.STRING)
                    .define('L', Blocks.WARPED_STEM).pattern("S").pattern("S").pattern("L").unlockedBy("has_log",
                            RecipeProvider.has(Blocks.WARPED_STEM)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TOY_TETHERED_LOGS.get(WoodType.BAMBOO.name()).get()).define('S', Items.STRING)
                    .define('L', Blocks.BAMBOO_BLOCK).pattern("S").pattern("S").pattern("L").unlockedBy("has_log",
                            RecipeProvider.has(Blocks.BAMBOO_BLOCK)).save(consumer);
            for (Map.Entry<String, RegistrySupplier<ToyScratchingPostItem>> entry : TOY_SCRATCHING_POSTS.entrySet()) {
                var block = BuiltInRegistries.BLOCK.getOptional(new ResourceLocation("minecraft:" + entry.getKey() + "_slab"));
                block.ifPresent(slab -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, entry.getValue().get()).define('S', Items.STICK).define('X', slab)
                        .define('W', ItemTags.WOOL).pattern("WWW").pattern("WSW").pattern(" X ").unlockedBy("has_slab",
                                RecipeProvider.has(slab)).save(consumer));
            }
            var hasScarabGem = RecipeProvider.has(SCARAB_GEM.get());
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AQUATIC_SCARAB_GEM.get()).requires(SCARAB_GEM.get()).requires(AMBER_CHUNK_DOMINICAN.get())
                    .unlockedBy("has_scarab_gem", hasScarabGem).save(consumer);
            smithing(Items.DIAMOND_AXE, SCARAB_GEM.get(), SCARAB_AXE.get(), RecipeCategory.TOOLS, consumer);
            smithing(Items.DIAMOND_HOE, SCARAB_GEM.get(), SCARAB_HOE.get(), RecipeCategory.TOOLS, consumer);
            smithing(Items.DIAMOND_PICKAXE, SCARAB_GEM.get(), SCARAB_PICKAXE.get(), RecipeCategory.TOOLS, consumer);
            smithing(Items.DIAMOND_SHOVEL, SCARAB_GEM.get(), SCARAB_SHOVEL.get(), RecipeCategory.TOOLS, consumer);
            smithing(Items.DIAMOND_SWORD, SCARAB_GEM.get(), SCARAB_SWORD.get(), RecipeCategory.COMBAT, consumer);

            var bonesLeg = ModItemTags.LEG_BONES;
            var bonesFoot = ModItemTags.FOOT_BONES;
            var bonesVertebrae = ModItemTags.VERTEBRAE_BONES;
            var bonesRibcage = ModItemTags.RIBCAGE_BONES;
            var bonesSkull = ModItemTags.SKULL_BONES;
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BONE_BOOTS.get()).define('L', bonesLeg).define('F', bonesFoot).pattern("L L").pattern("F F").unlockedBy("has_bones", RecipeProvider.has(bonesFoot)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BONE_CHESTPLATE.get()).define('B', Items.BONE).define('V', bonesVertebrae).define('R', bonesRibcage).pattern("B B").pattern(" V ").pattern("BRB").unlockedBy("has_bones", RecipeProvider.has(bonesRibcage)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BONE_HELMET.get()).define('B', Items.BONE).define('S', bonesSkull).pattern("BSB").pattern("B B").unlockedBy("has_bones", RecipeProvider.has(bonesSkull)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, BONE_LEGGINGS.get()).define('B', Items.BONE).define('L', bonesLeg).pattern("BBB").pattern("L L").pattern("B B").unlockedBy("has_bones", RecipeProvider.has(bonesLeg)).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL).requires(VOLCANIC_ASH.get(), 4).unlockedBy("has_volcanic_ash", RecipeProvider.has(VOLCANIC_ASH.get())).save(consumer, FossilMod.MOD_ID + ":bone_meal_from_ash");
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL).requires(ModItemTags.ALL_BONES).unlockedBy("has_bone", RecipeProvider.has(ModItemTags.ALL_BONES)).save(consumer, FossilMod.MOD_ID + ":bone_meal_from_bone");
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CHICKEN_ESSENCE.get(), 8).define('G', Items.GLASS_BOTTLE).define('C', COOKED_CHICKEN_SOUP.get()).pattern("GGG").pattern("GCG").pattern("GGG").unlockedBy("has_cooked_chicken_soup", RecipeProvider.has(COOKED_CHICKEN_SOUP.get())).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STUNTED_ESSENCE.get()).define('P', Items.POISONOUS_POTATO).define('C', CHICKEN_ESSENCE.get()).pattern(" P ").pattern("PCP").pattern(" P ").unlockedBy("has_chicken_essence", RecipeProvider.has(CHICKEN_ESSENCE.get())).save(consumer);

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ANALYZER.get()).define('I', Items.IRON_INGOT).define('R', RELIC_SCRAP.get()).define('B', ModItemTags.FOSSILS).pattern("IRI").pattern("IBI").unlockedBy("has_fossil", RecipeProvider.has(ModItemTags.FOSSILS)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BUBBLE_BLOWER.get()).define('I', Items.GOLD_INGOT).define('N', Items.GOLD_NUGGET).define('W', Items.WATER_BUCKET).pattern("NIN").pattern("IWI").pattern("NIN").unlockedBy("has_dino_egg", RecipeProvider.has(ModItemTags.DINO_EGGS)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SIFTER.get()).define('I', Blocks.IRON_BARS).define('S', Items.STRING).define('P', ItemTags.PLANKS).pattern("SPS").pattern("PIP").pattern("PSP").unlockedBy("has_planks", RecipeProvider.has(ItemTags.PLANKS)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, WORKTABLE.get()).define('L', Items.LEATHER).define('C', Blocks.CRAFTING_TABLE).pattern("L").pattern("C").unlockedBy("has_crafting_table", RecipeProvider.has(Blocks.CRAFTING_TABLE)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DRUM.get()).define('L', Items.LEATHER).define('R', Items.REDSTONE).define('P', ItemTags.PLANKS).pattern("LLL").pattern("PRP").pattern("PPP").unlockedBy("has_crafting_table", RecipeProvider.has(Blocks.CRAFTING_TABLE)).save(consumer);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, DINOPEDIA.get()).requires(Items.BOOK).requires(ModItemTags.FOSSILS).unlockedBy("has_bio_fossil", RecipeProvider.has(BIO_FOSSIL.get())).save(consumer);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, RAW_CHICKEN_SOUP.get()).requires(Items.BUCKET).requires(Items.CHICKEN).unlockedBy("has_chicken", RecipeProvider.has(Items.CHICKEN)).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, SKULL_STICK.get()).requires(Items.STICK).requires(SKULL_BLOCK.get()).unlockedBy("has_skull_block", RecipeProvider.has(SKULL_BLOCK.get())).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, TOOTH_DAGGER.get()).requires(Items.STICK).requires(PrehistoricEntityInfo.TYRANNOSAURUS.uniqueBoneItem);
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, WHIP.get()).define('S', Items.STRING).define('T', Items.STICK).pattern("  S").pattern(" TS").pattern("T S").unlockedBy("has_dinopedia", RecipeProvider.has(DINOPEDIA.get())).save(consumer);

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AMPHORA_VASE_DAMAGED.get()).define('P', POTTERY_SHARD.get()).pattern("PP").pattern("PP").pattern("PP").unlockedBy("has_pottery_shard", RecipeProvider.has(POTTERY_SHARD.get())).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, KYLIX_VASE_DAMAGED.get()).define('P', POTTERY_SHARD.get()).pattern("PPP").pattern(" P ").unlockedBy("has_pottery_shard", RecipeProvider.has(POTTERY_SHARD.get())).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VOLUTE_VASE_DAMAGED.get()).define('P', POTTERY_SHARD.get()).pattern("P P").pattern("P P").pattern("PPP").unlockedBy("has_pottery_shard", RecipeProvider.has(POTTERY_SHARD.get())).save(consumer);
            for (Pair<DyeColor, RegistrySupplier<VaseBlock>> pair : VASES_WITH_COLOR) {
                VaseBlock vase = pair.getSecond().get();
                VaseBlock restored;
                if (vase instanceof AmphoraVaseBlock) {
                    restored = AMPHORA_VASE_RESTORED.get();
                } else if (vase instanceof KylixVaseBlock) {
                    restored = KYLIX_VASE_RESTORED.get();
                } else {
                    restored = VOLUTE_VASE_RESTORED.get();
                }
                ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, vase).requires(restored).requires(DyeItem.byColor(pair.getFirst())).unlockedBy("has_restored_vase", RecipeProvider.has(restored)).save(consumer);
            }

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, DENSE_SAND.get()).requires(Blocks.SAND).requires(Items.QUARTZ).unlockedBy("has_sand", RecipeProvider.has(Blocks.SAND)).save(consumer);
            generateRecipes(consumer, ANCIENT_WOOD_PLANKS);
            generateRecipes(consumer, CALAMITES_PLANKS);
            generateRecipes(consumer, CORDAITES_PLANKS);
            generateRecipes(consumer, MUTANT_TREE_PLANKS);
            generateRecipes(consumer, PALM_PLANKS);
            generateRecipes(consumer, SIGILLARIA_PLANKS);
            generateRecipes(consumer, TEMPSKYA_PLANKS);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.ANCIENT_WOOD_PLANKS.get(), ModItemTags.ANCIENT_WOOD_LOGS, 4);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.CALAMITES_PLANKS.get(), ModItemTags.CALAMITES_LOGS, 4);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.CORDAITES_PLANKS.get(), ModItemTags.CORDAITES_LOGS, 4);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.MUTANT_TREE_PLANKS.get(), ModItemTags.MUTANT_TREE_LOGS, 4);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.PALM_PLANKS.get(), ModItemTags.PALM_LOGS, 4);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.SIGILLARIA_PLANKS.get(), ModItemTags.SIGILLARIA_LOGS, 4);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.TEMPSKYA_PLANKS.get(), ModItemTags.TEMPSKYA_LOGS, 4);
            RecipeProvider.woodFromLogs(consumer, CALAMITES_WOOD.get(), CALAMITES_LOG.get());
            RecipeProvider.woodFromLogs(consumer, CORDAITES_WOOD.get(), CORDAITES_LOG.get());
            RecipeProvider.woodFromLogs(consumer, MUTANT_TREE_WOOD.get(), MUTANT_TREE_LOG.get());
            RecipeProvider.woodFromLogs(consumer, PALM_WOOD.get(), PALM_LOG.get());
            RecipeProvider.woodFromLogs(consumer, SIGILLARIA_WOOD.get(), SIGILLARIA_LOG.get());
            RecipeProvider.woodFromLogs(consumer, TEMPSKYA_WOOD.get(), TEMPSKYA_LOG.get());
            RecipeProvider.woodFromLogs(consumer, STRIPPED_CALAMITES_WOOD.get(), STRIPPED_CALAMITES_LOG.get());
            RecipeProvider.woodFromLogs(consumer, STRIPPED_CORDAITES_WOOD.get(), STRIPPED_CORDAITES_LOG.get());
            RecipeProvider.woodFromLogs(consumer, STRIPPED_MUTANT_TREE_WOOD.get(), STRIPPED_MUTANT_TREE_LOG.get());
            RecipeProvider.woodFromLogs(consumer, STRIPPED_PALM_WOOD.get(), STRIPPED_PALM_LOG.get());
            RecipeProvider.woodFromLogs(consumer, STRIPPED_SIGILLARIA_WOOD.get(), STRIPPED_SIGILLARIA_LOG.get());
            RecipeProvider.woodFromLogs(consumer, STRIPPED_TEMPSKYA_WOOD.get(), STRIPPED_TEMPSKYA_LOG.get());

            stonecutter(consumer, ANCIENT_STONE_SLAB.get(), ANCIENT_STONE.get(), 2);
            stonecutter(consumer, ANCIENT_STONE_STAIRS.get(), ANCIENT_STONE.get(), 1);
            stonecutter(RecipeCategory.DECORATIONS, consumer, ANCIENT_STONE_WALL.get(), ANCIENT_STONE.get(), 1);
            stonecutter(consumer, ANCIENT_STONE_BRICKS.get(), ANCIENT_STONE.get(), 1);
            stonecutter(consumer, ANCIENT_STONE_SLAB.get(), ANCIENT_STONE_BRICKS.get(), 2);
            stonecutter(consumer, ANCIENT_STONE_STAIRS.get(), ANCIENT_STONE_BRICKS.get(), 1);
            stonecutter(RecipeCategory.DECORATIONS, consumer, ANCIENT_STONE_WALL.get(), ANCIENT_STONE_BRICKS.get(), 1);
            stonecutter(consumer, VOLCANIC_BRICK_SLAB.get(), VOLCANIC_ROCK.get(), 2);
            stonecutter(consumer, VOLCANIC_BRICK_STAIRS.get(), VOLCANIC_ROCK.get(), 1);
            stonecutter(RecipeCategory.DECORATIONS, consumer, VOLCANIC_BRICK_WALL.get(), VOLCANIC_ROCK.get(), 1);
            stonecutter(consumer, VOLCANIC_BRICKS.get(), VOLCANIC_ROCK.get(), 1);
            stonecutter(consumer, VOLCANIC_BRICK_SLAB.get(), VOLCANIC_BRICKS.get(), 2);
            stonecutter(consumer, VOLCANIC_BRICK_STAIRS.get(), VOLCANIC_BRICKS.get(), 1);
            stonecutter(RecipeCategory.DECORATIONS, consumer, VOLCANIC_BRICK_WALL.get(), VOLCANIC_BRICKS.get(), 1);

            stonecutter(consumer, VOLCANIC_TILE_SLAB.get(), VOLCANIC_BRICKS.get(), 2);
            stonecutter(consumer, VOLCANIC_TILE_STAIRS.get(), VOLCANIC_BRICKS.get(), 1);
            stonecutter(RecipeCategory.DECORATIONS, consumer, VOLCANIC_TILE_WALL.get(), VOLCANIC_BRICKS.get(), 1);
            stonecutter(consumer, VOLCANIC_TILES.get(), VOLCANIC_BRICKS.get(), 1);
            stonecutter(consumer, VOLCANIC_TILE_SLAB.get(), VOLCANIC_TILES.get(), 2);
            stonecutter(consumer, VOLCANIC_TILE_STAIRS.get(), VOLCANIC_TILES.get(), 1);
            stonecutter(RecipeCategory.DECORATIONS, consumer, VOLCANIC_TILE_WALL.get(), VOLCANIC_TILES.get(), 1);
        }

        if (machineRecipes) {
            //with 19 plant seeds at 1% each
            MultiOutputAndSlotsRecipeBuilder<AnalyzerRecipeBuilder> plantFossil = analyzed(PlANT_FOSSIL.get())
                    .addOutput(Blocks.SAND, 25)
                    .addOutput(Blocks.CACTUS, 20)
                    .addOutput(Blocks.POPPY, 1.5)
                    .addOutput(Blocks.ALLIUM, 1.5)
                    .addOutput(Blocks.DANDELION, 1.5)
                    .addOutput(Blocks.OXEYE_DAISY, 1.5)
                    .addOutput(Blocks.CORNFLOWER, 1.5)
                    .addOutput(Blocks.AZURE_BLUET, 1.5)
                    .addOutput(Blocks.FERN, 1.5)
                    .addOutput(Blocks.GRASS, 1.5)
                    .addOutput(Blocks.DEAD_BUSH, 3)
                    .addOutput(Blocks.RED_MUSHROOM, 3)
                    .addOutput(Blocks.BROWN_MUSHROOM, 3)
                    .addOutput(FERN_SEED_FOSSIL.get(), 5)
                    .addOutput(CALAMITES_FOSSIL_SAPLING.get(), 2)
                    .addOutput(CORDAITES_FOSSIL_SAPLING.get(), 2)
                    .addOutput(PALM_FOSSIL_SAPLING.get(), 2)
                    .addOutput(SIGILLARIA_FOSSIL_SAPLING.get(), 2)
                    .addOutput(TEMPSKYA_FOSSIL_SAPLING.get(), 2);

            double seedWeight = (100F - plantFossil.total) / (double) PrehistoricPlantInfo.plantsWithSeeds().size();
            for (PrehistoricPlantInfo info : PrehistoricPlantInfo.plantsWithSeeds()) {
                plantFossil.addOutput(info.getFossilizedPlantSeedItem(), seedWeight);
            }
            plantFossil.save(consumer);
            MultiOutputAndSlotsRecipeBuilder<AnalyzerRecipeBuilder> bioFossil = analyzed(BIO_FOSSIL.get())
                    .addOutput(Blocks.SAND, 35)
                    .addOutput(Items.BONE_MEAL, 50);
            List<PrehistoricEntityInfo> bioFossilEntityList = PrehistoricEntityInfo.getTimePeriodList(TimePeriod.MESOZOIC);
            double bioFossilDNAChance = 15F / (double) bioFossilEntityList.size();
            for (PrehistoricEntityInfo info : bioFossilEntityList) {
                bioFossil.addOutput(info.dnaItem, bioFossilDNAChance);
            }
            bioFossil.save(consumer);
            MultiOutputAndSlotsRecipeBuilder<AnalyzerRecipeBuilder> shaleFossil = analyzed(SHALE_FOSSIL.get())
                    .addOutput(Blocks.TUFF, 35)
                    .addOutput(Items.BONE_MEAL, 50);
            List<PrehistoricEntityInfo> shaleFossilEntityList = PrehistoricEntityInfo.getTimePeriodList(TimePeriod.PALEOZOIC);
            double shaleFossilDNAChance = 15F / (double) shaleFossilEntityList.size();
            for (PrehistoricEntityInfo info : shaleFossilEntityList) {
                shaleFossil.addOutput(info.dnaItem, shaleFossilDNAChance);
            }
            shaleFossil.save(consumer);
            /*for (PrehistoricEntityType type : PrehistoricEntityType.entitiesWithBones()) {
                analyzed(type.legBoneItem)
                        .addOutput(Items.BONE_MEAL, 30)
                        .addOutput(Items.BONE, 35)
                        .addOutput(type.dnaItem, 35).save(consumer);
                analyzed(type.armBoneItem)
                        .addOutput(Items.BONE_MEAL, 30)
                        .addOutput(Items.BONE, 35)
                        .addOutput(type.dnaItem, 35).save(consumer);
                analyzed(type.footBoneItem)
                        .addOutput(Items.BONE_MEAL, 30)
                        .addOutput(Items.BONE, 35)
                        .addOutput(type.dnaItem, 35).save(consumer);
                analyzed(type.skullBoneItem)
                        .addOutput(Items.BONE_MEAL, 30)
                        .addOutput(Items.BONE, 35)
                        .addOutput(type.dnaItem, 35).save(consumer);
                analyzed(type.ribcageBoneItem)
                        .addOutput(Items.BONE_MEAL, 30)
                        .addOutput(Items.BONE, 35)
                        .addOutput(type.dnaItem, 35).save(consumer);
                analyzed(type.vertebraeBoneItem)
                        .addOutput(Items.BONE_MEAL, 30)
                        .addOutput(Items.BONE, 35)
                        .addOutput(type.dnaItem, 35).save(consumer);
                analyzed(type.uniqueBoneItem)
                        .addOutput(Items.BONE_MEAL, 30)
                        .addOutput(Items.BONE, 35)
                        .addOutput(type.dnaItem, 35).save(consumer);
            }*/
            analyzed(TAR_DROP.get())
                    .addOutput(Items.COAL, 20)
                    .addOutput(Items.CHARCOAL, 20)
                    .addOutput(TAR_FOSSIL.get(), 45)
                    .addOutput(VOLCANIC_ROCK.get(), 15).save(consumer);
            MultiOutputAndSlotsRecipeBuilder<AnalyzerRecipeBuilder> tarFossil = analyzed(TAR_FOSSIL.get())
                    .addOutput(Items.BONE_MEAL, 15)
                    .addOutput(VOLCANIC_ROCK.get(), 30);
            List<EntityInfo> tarFossilEntityList = new ArrayList<>(PrehistoricEntityInfo.getTimePeriodList(TimePeriod.CENOZOIC));
            tarFossilEntityList.add(AXOLOTL);
            tarFossilEntityList.add(BAT);
            tarFossilEntityList.add(CAT);
            tarFossilEntityList.add(DOLPHIN);
            tarFossilEntityList.add(FOX);
            tarFossilEntityList.add(OCELOT);
            tarFossilEntityList.add(PANDA);
            tarFossilEntityList.add(WOLF);
            double tarFossilDNAChance = 25 / (double) tarFossilEntityList.size();
            for (EntityInfo info : tarFossilEntityList) {
                tarFossil.addOutput(info.dnaItem(), tarFossilDNAChance);
            }
            tarFossil.save(consumer);

            MultiOutputAndSlotsRecipeBuilder<AnalyzerRecipeBuilder> failuresaurusFlesh = analyzed(FAILURESAURUS_FLESH.get())
                    .addOutput(Items.ROTTEN_FLESH, 33);
            double failuresaurusDNAChance = 67F / (PrehistoricEntityInfo.values().length + VanillaEntityInfo.values().length);
            for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
                failuresaurusFlesh.addOutput(info.dnaItem, failuresaurusDNAChance);
                /*if (type.foodItem != null) {
                    analyzed(type.foodItem).addOutput(type.dnaItem, 100).save(consumer);
                }
                if (type.eggItem != null) {
                    analyzed(type.eggItem).addOutput(type.dnaItem, 100).save(consumer);
                }
                if (type.birdEggItem != null) {
                    analyzed(type.birdEggItem).addOutput(type.dnaItem, 100).save(consumer);
                }
                if (type.cultivatedBirdEggItem != null) {
                    analyzed(type.cultivatedBirdEggItem).addOutput(type.dnaItem, 100).save(consumer);
                }
                if (type.embryoItem != null) {
                    analyzed(type.embryoItem).addOutput(type.dnaItem, 100).save(consumer);
                }*/
            }
            for (VanillaEntityInfo info : values()) {
                failuresaurusFlesh.addOutput(info.dnaItem, failuresaurusDNAChance);
            }
            failuresaurusFlesh.save(consumer);
            MultiOutputAndSlotsRecipeBuilder<AnalyzerRecipeBuilder> frozenMeat = analyzed(FROZEN_MEAT.get())
                    .addOutput(Items.CHICKEN, 15)
                    .addOutput(Items.MUTTON, 15)
                    .addOutput(Items.BEEF, 15)
                    .addOutput(Items.PORKCHOP, 15)
                    .addOutput(Items.CHICKEN, 15)
                    .addOutput(TAR_FOSSIL.get(), 20);
            for (EntityInfo info : tarFossilEntityList) {
                frozenMeat.addOutput(info.dnaItem(), tarFossilDNAChance);
            }
            frozenMeat.save(consumer);
            analyzed(AMBER_CHUNK_DOMINICAN.get()).addOutput(Items.SPIDER_EYE, 9).addOutput(Items.STRING, 10).addOutput(Blocks.DIRT, 25).addOutput(Blocks.GRAVEL, 25)
                    .addOutput(Items.WHEAT_SEEDS, 1).addOutput(Items.BEETROOT_SEEDS, 1).addOutput(Items.PUMPKIN_SEEDS, 1).addOutput(Items.MELON_SEEDS, 1)
                    .addOutput(CALAMITES_FOSSIL_SAPLING.get(), 1).addOutput(CORDAITES_FOSSIL_SAPLING.get(), 1).addOutput(PALM_FOSSIL_SAPLING.get(), 1).addOutput(SIGILLARIA_FOSSIL_SAPLING.get(), 1).addOutput(TEMPSKYA_FOSSIL_SAPLING.get(), 1).save(consumer);
            analyzed(Items.HONEYCOMB).addOutput(BEE.dnaItem, 100).save(consumer);
            analyzed(Items.CHICKEN).addOutput(CHICKEN.dnaItem, 100).save(consumer);
            analyzed(Items.EGG).addOutput(CHICKEN.dnaItem, 100).save(consumer);
            analyzed(Items.FEATHER).addOutput(CHICKEN.dnaItem, 95).addOutput(PARROT.dnaItem, 5).save(consumer);
            analyzed(Items.COD).addOutput(COD.dnaItem, 90).addOutput(POLAR_BEAR.dnaItem, 10).save(consumer);
            analyzed(Items.BEEF).addOutput(COW.dnaItem, 99).addOutput(MOOSHROOM.dnaItem, 1).save(consumer);
            analyzed(Items.LEATHER).addOutput(COW.dnaItem, 57).addOutput(DONKEY.dnaItem, 10).addOutput(HORSE.dnaItem, 27).addOutput(HOGLIN.dnaItem, 5).addOutput(MOOSHROOM.dnaItem, 1).save(consumer);
            analyzed(Items.GLOW_INK_SAC).addOutput(GLOW_SQUID.dnaItem, 100).save(consumer);
            //TODO: 1.19 adds goat horns analyzed(Items.GOAT_HORN).addOutput(GOAT.dnaItem, 100).save(consumer);
            analyzed(Items.PORKCHOP).addOutput(PIG.dnaItem, 95).addOutput(HOGLIN.dnaItem, 5).save(consumer);
            analyzed(Items.PUFFERFISH).addOutput(PUFFERFISH.dnaItem, 90).addOutput(POLAR_BEAR.dnaItem, 10).save(consumer);
            analyzed(Items.RABBIT).addOutput(RABBIT.dnaItem, 100).save(consumer);
            analyzed(Items.RABBIT_FOOT).addOutput(RABBIT.dnaItem, 100).save(consumer);
            analyzed(Items.RABBIT_HIDE).addOutput(RABBIT.dnaItem, 100).save(consumer);
            analyzed(Items.SALMON).addOutput(SALMON.dnaItem, 90).addOutput(POLAR_BEAR.dnaItem, 10).save(consumer);
            analyzed(Items.MUTTON).addOutput(SHEEP.dnaItem, 100).save(consumer);
            analyzed(ItemTags.WOOL).addOutput(Items.STRING, 3, 50).addOutput(SHEEP.dnaItem, 27).addOutput(LLAMA.dnaItem, 13).addOutput(GOAT.dnaItem, 10).save(consumer);
            analyzed(Items.INK_SAC).addOutput(SQUID.dnaItem, 100).save(consumer);
            analyzed(Items.STRING).addOutput(STRIDER.dnaItem, 100).save(consumer);
            analyzed(Items.TROPICAL_FISH).addOutput(TROPICAL_FISH.dnaItem, 90).addOutput(POLAR_BEAR.dnaItem, 10).save(consumer);
            analyzed(Items.SCUTE).addOutput(TURTLE.dnaItem, 100).save(consumer);
            analyzed(Blocks.TURTLE_EGG).addOutput(TURTLE.dnaItem, 100).save(consumer);
            analyzed(Items.FROG_SPAWN_EGG).addOutput(FROG.dnaItem, 100).save(consumer);

            analyzed(RELIC_SCRAP.get()).addOutput(Blocks.GRAVEL, 30).addOutput(Items.FLINT, 18).addOutput(POTTERY_SHARD.get(), 4).addOutput(BROKEN_HELMET.get(), 4).addOutput(BROKEN_SWORD.get(), 4).addOutput(STONE_TABLET.get(), 30)
                    .addOutput(ANU_FIGURINE_DESTROYED.get(), 4).addOutput(ENDERMAN_FIGURINE_DESTROYED.get(), 4).addOutput(PIGLIN_FIGURINE_DESTROYED.get(), 4).addOutput(SKELETON_FIGURINE_DESTROYED.get(), 4).addOutput(STEVE_FIGURINE_DESTROYED.get(), 4).addOutput(ZOMBIE_FIGURINE_DESTROYED.get(), 4).save(consumer);

            worktable(ModBlocks.AMPHORA_VASE_DAMAGED, ModBlocks.AMPHORA_VASE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.VOLUTE_VASE_DAMAGED, ModBlocks.VOLUTE_VASE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.KYLIX_VASE_DAMAGED, ModBlocks.KYLIX_VASE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.ANU_FIGURINE_DESTROYED, ModBlocks.ANU_FIGURINE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.ENDERMAN_FIGURINE_DESTROYED, ModBlocks.ENDERMAN_FIGURINE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.PIGLIN_FIGURINE_DESTROYED, ModBlocks.PIGLIN_FIGURINE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.SKELETON_FIGURINE_DESTROYED, ModBlocks.SKELETON_FIGURINE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.STEVE_FIGURINE_DESTROYED, ModBlocks.STEVE_FIGURINE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.ZOMBIE_FIGURINE_DESTROYED, ModBlocks.ZOMBIE_FIGURINE_RESTORED, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.ANU_FIGURINE_RESTORED, ModBlocks.ANU_FIGURINE_PRISTINE, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.ENDERMAN_FIGURINE_RESTORED, ModBlocks.ENDERMAN_FIGURINE_PRISTINE, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.PIGLIN_FIGURINE_RESTORED, ModBlocks.PIGLIN_FIGURINE_PRISTINE, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.SKELETON_FIGURINE_RESTORED, ModBlocks.SKELETON_FIGURINE_PRISTINE, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.STEVE_FIGURINE_RESTORED, ModBlocks.STEVE_FIGURINE_PRISTINE, ModItems.POTTERY_SHARD, consumer);
            worktable(ModBlocks.ZOMBIE_FIGURINE_RESTORED, ModBlocks.ZOMBIE_FIGURINE_PRISTINE, ModItems.POTTERY_SHARD, consumer);
            worktable(ModItems.BROKEN_SWORD, ModItems.ANCIENT_SWORD, ModItems.RELIC_SCRAP, 3000, consumer);
            worktable(ModItems.BROKEN_HELMET, ModItems.ANCIENT_HELMET, ModItems.RELIC_SCRAP, 3000, consumer);
            worktable(ModItems.ANCIENT_SWORD, ModItems.ANCIENT_SWORD, ModItems.RELIC_SCRAP, 3000, consumer);
            worktable(ModItems.ANCIENT_HELMET, ModItems.ANCIENT_HELMET, ModItems.RELIC_SCRAP, 3000, consumer);
            worktable(ModItems.SCARAB_SWORD, ModItems.SCARAB_SWORD, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.SCARAB_PICKAXE, ModItems.SCARAB_PICKAXE, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.SCARAB_AXE, ModItems.SCARAB_AXE, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.SCARAB_SHOVEL, ModItems.SCARAB_SHOVEL, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.SCARAB_HOE, ModItems.SCARAB_HOE, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.WOODEN_JAVELIN, ModItems.WOODEN_JAVELIN, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.STONE_JAVELIN, ModItems.STONE_JAVELIN, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.IRON_JAVELIN, ModItems.IRON_JAVELIN, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.GOLD_JAVELIN, ModItems.GOLD_JAVELIN, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.DIAMOND_JAVELIN, ModItems.DIAMOND_JAVELIN, ModItems.RELIC_SCRAP, consumer);
            worktable(ModItems.ANCIENT_JAVELIN, ModItems.ANCIENT_JAVELIN, ModItems.RELIC_SCRAP, consumer);

            sifter(ModItemTags.SIFTER_INPUTS).addOutput(Blocks.SAND, 5d).addOutput(Items.POTATO, 15d).
                    addOutput(Items.CARROT, 15d).addOutput(Items.BEETROOT_SEEDS, 5d).addOutput(Items.PUMPKIN_SEEDS, 4d).
                    addOutput(Items.MELON_SEEDS, 4d).addOutput(Items.BONE_MEAL, 20d).
                    addOutput(Items.IRON_NUGGET, 3d).addOutput(Items.GOLD_NUGGET, 3d).
                    addOutput(ModBlocks.AMBER_CHUNK_DOMINICAN.get(), 1d).addOutput(ModItems.FERN_SEED_FOSSIL.get(), 1d).
                    addOutput(Blocks.COBBLESTONE, 5d).
                    addOutput(ModItems.PlANT_FOSSIL.get(), 2d).addOutput(ModItems.BIO_FOSSIL.get(), 2d).
                    addOutput(ModItems.POTTERY_SHARD.get(), 5d).addOutput(Items.CLAY_BALL, 10d).save(consumer);

            sifter(Blocks.GRAVEL).addOutput(Blocks.SAND, 5d).addOutput(Items.POTATO, 15d).
                    addOutput(Items.CARROT, 10d).addOutput(Items.BEETROOT_SEEDS, 3d).addOutput(Items.PUMPKIN_SEEDS, 2d).
                    addOutput(Items.MELON_SEEDS, 2d).addOutput(Items.FLINT, 5d).addOutput(Items.BONE_MEAL, 20d).
                    addOutput(Items.IRON_NUGGET, 4d).addOutput(Items.GOLD_NUGGET, 4d).
                    addOutput(ModBlocks.AMBER_CHUNK_DOMINICAN.get(), 1d).
                    addOutput(Blocks.COBBLESTONE, 5d).
                    addOutput(ModItems.PlANT_FOSSIL.get(), 2d).addOutput(ModItems.BIO_FOSSIL.get(), 2d).
                    addOutput(ModItems.POTTERY_SHARD.get(), 5d).addOutput(Items.CLAY_BALL, 15d).save(consumer);

            for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
                if (info.dnaItem != null && info.getDNAResult() != null) {
                    cultureVat(info.dnaItem, info.getDNAResult(), consumer);
                }
            }
            for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
                if (info.dnaItem != null && info.getDNAResult() != null) {
                    cultureVat(info.dnaItem, info.getDNAResult(), consumer);
                }
                if (info.cultivatedBirdEggItem != null) {
                    analyzed(info.cultivatedBirdEggItem).addOutput(info.dnaItem, 100).save(consumer);
                }
            }
            analyzed(Items.NAUTILUS_SHELL).addOutput(PrehistoricEntityInfo.NAUTILUS.dnaItem, 100).save(consumer);
            cultureVat(BEE.dnaItem, ARTIFICIAL_HONEYCOMB.get(), consumer);
            cultureVat(TURTLE.dnaItem, Items.TURTLE_EGG, consumer);
            cultureVat(FROG.dnaItem, Items.FROG_SPAWN_EGG, consumer);
            cultureVat(ModItems.FERN_SEED_FOSSIL, ModItems.FERN_SEED, consumer);
            cultureVat(ModItems.CALAMITES_FOSSIL_SAPLING, ModBlocks.CALAMITES_SAPLING, consumer);
            cultureVat(ModItems.CORDAITES_FOSSIL_SAPLING, ModBlocks.CORDAITES_SAPLING, consumer);
            cultureVat(ModItems.PALM_FOSSIL_SAPLING, ModBlocks.PALM_SAPLING, consumer);
            cultureVat(ModItems.SIGILLARIA_FOSSIL_SAPLING, ModBlocks.SIGILLARIA_SAPLING, consumer);
            cultureVat(ModItems.TEMPSKYA_FOSSIL_SAPLING, ModBlocks.TEMPSKYA_SAPLING, consumer);
            for (PrehistoricPlantInfo info : PrehistoricPlantInfo.plantsWithSeeds()) {
                cultureVat(info.getFossilizedPlantSeedItem(), info.getPlantSeedItem(), consumer);
            }
        }
        if (ModList.get().isLoaded(ModConstants.CREATE)) {
            FossilCreateRecipeProvider.buildCraftingRecipes(consumer);
        }
    }

    public AnalyzerRecipeBuilder analyzed(ItemLike itemLike) {
        return new AnalyzerRecipeBuilder(FossilMod.MOD_ID, itemLike);
    }

    public AnalyzerRecipeBuilder analyzed(TagKey<Item> tagKey) {
        return new AnalyzerRecipeBuilder(FossilMod.MOD_ID, tagKey);
    }

    public SifterRecipeBuilder sifter(ItemLike itemLike) {
        return new SifterRecipeBuilder(FossilMod.MOD_ID, itemLike);
    }

    public SifterRecipeBuilder sifter(TagKey<Item> tagKey) {
        return new SifterRecipeBuilder(FossilMod.MOD_ID, tagKey);
    }

    public void worktable(RegistrySupplier<? extends ItemLike> itemInput, RegistrySupplier<? extends ItemLike> itemOutput, RegistrySupplier<? extends ItemLike> itemFuel, int duration, Consumer<FinishedRecipe> consumer) {
        new WorktableRecipeBuilder(FossilMod.MOD_ID, itemInput.get(), itemFuel.get(), itemOutput.get(), duration).save(consumer);
    }

    public void worktable(RegistrySupplier<? extends ItemLike> itemInput, RegistrySupplier<? extends ItemLike> itemOutput, RegistrySupplier<? extends ItemLike> itemFuel, Consumer<FinishedRecipe> consumer) {
        new WorktableRecipeBuilder(FossilMod.MOD_ID, itemInput.get(), itemFuel.get(), itemOutput.get()).save(consumer);
    }

    public void cultureVat(ItemLike itemInput, ItemLike itemOutput, Consumer<FinishedRecipe> consumer) {
        new CultureVatRecipeBuilder(FossilMod.MOD_ID, itemInput, itemOutput).save(consumer);
    }

    public void cultureVat(RegistrySupplier<? extends ItemLike> itemInput, RegistrySupplier<? extends ItemLike> itemOutput, Consumer<FinishedRecipe> consumer) {
        new CultureVatRecipeBuilder(FossilMod.MOD_ID, itemInput.get(), itemOutput.get()).save(consumer);
    }

    private Item colorToDye(DyeColor dyeColor) {
        return switch (dyeColor) {
            case WHITE -> Items.WHITE_DYE;
            case ORANGE -> Items.ORANGE_DYE;
            case MAGENTA -> Items.MAGENTA_DYE;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
            case YELLOW -> Items.YELLOW_DYE;
            case LIME -> Items.LIME_DYE;
            case PINK -> Items.PINK_DYE;
            case GRAY -> Items.GRAY_DYE;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
            case CYAN -> Items.CYAN_DYE;
            case PURPLE -> Items.PURPLE_DYE;
            case BLUE -> Items.BLUE_DYE;
            case BROWN -> Items.BROWN_DYE;
            case GREEN -> Items.GREEN_DYE;
            case RED -> Items.RED_DYE;
            case BLACK -> Items.BLACK_DYE;
        };

    }

    private static void smithing(ItemLike base, ItemLike addition, ItemLike result, RecipeCategory recipeCategory, Consumer<FinishedRecipe> consumer) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(base), Ingredient.of(addition), recipeCategory, result.asItem())
                .unlocks("has_item", inventoryTrigger(ItemPredicate.Builder.item().of(addition).build()))
                .save(consumer, BuiltInRegistries.ITEM.getKey(result.asItem()));
    }

    private static void fullCooking(RecipeCategory category, Ingredient ingredient, ItemPredicate predicate, ItemLike result, String ingredientName, String resultName, Consumer<FinishedRecipe> consumer, float exp) {
        ResourceLocation resultLocation = FossilMod.location(resultName);
        var furnace = SimpleCookingRecipeBuilder.smelting(ingredient, category, result, exp, 200)
                .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
        var campfire = SimpleCookingRecipeBuilder.campfireCooking(ingredient, category, result, exp, 600)
                .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
        var smoker = SimpleCookingRecipeBuilder.smoking(ingredient, category, result, exp, 100)
                .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
        furnace.save(consumer, resultLocation);
        campfire.save(consumer, resultLocation + "_from_campfire_cooking");
        smoker.save(consumer, resultLocation + "_from_smoking");
    }

    private static void fullCooking(RecipeCategory category, TagKey<Item> ingredient, ItemLike result, String resultName, Consumer<FinishedRecipe> consumer, float exp) {
        fullCooking(category, Ingredient.of(ingredient), ItemPredicate.Builder.item().of(ingredient).build(), result, ingredient.location().getPath(), resultName, consumer, exp);
    }

    private static void fullCooking(RecipeCategory category, ItemLike ingredient, ItemLike result, String resultName, Consumer<FinishedRecipe> consumer, float exp) {
        fullCooking(category, Ingredient.of(ingredient), ItemPredicate.Builder.item().of(ingredient).build(), result, RecipeBuilder.getDefaultRecipeId(ingredient).getPath(), resultName, consumer, exp);
    }

    private static void fullCooking(RecipeCategory category, ItemLike ingredient, ItemLike result, Consumer<FinishedRecipe> consumer, float exp) {
        fullCooking(category, ingredient, result, RecipeBuilder.getDefaultRecipeId(result).getPath(), consumer, exp);
    }

    private void fullOre(RecipeCategory category, ItemLike ingredient, ItemLike result, Consumer<FinishedRecipe> consumer, float exp) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), category, result, exp, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_smelting_" + getItemName(ingredient));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), category, result, exp, 100)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_blasting_" + getItemName(ingredient));
    }

    protected void stonecutter(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike material, int resultCount) {
        stonecutter(RecipeCategory.BUILDING_BLOCKS, consumer, result, material, resultCount);
    }

    protected void stonecutter(RecipeCategory category, Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder builder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount).unlockedBy(getHasName(material), has(material));
        String id = getConversionRecipeName(result, material);
        builder.save(finishedRecipeConsumer, FossilMod.MOD_ID + ":" + id + "_stonecutting");
    }
}
