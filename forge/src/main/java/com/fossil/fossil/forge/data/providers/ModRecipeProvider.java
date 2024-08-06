package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.PrehistoricPlantInfo;
import com.fossil.fossil.block.custom_blocks.AmphoraVaseBlock;
import com.fossil.fossil.block.custom_blocks.KylixVaseBlock;
import com.fossil.fossil.block.custom_blocks.VaseBlock;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.fossil.fossil.forge.data.recipe.AnalyzerRecipeBuilder;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ToyBallItem;
import com.fossil.fossil.item.ToyScratchingPostItem;
import com.fossil.fossil.item.ToyTetheredLogItem;
import com.fossil.fossil.tags.ModItemTags;
import com.fossil.fossil.util.TimePeriod;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static com.fossil.fossil.block.ModBlocks.*;
import static com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo.*;

public class ModRecipeProvider extends RecipeProvider {
    public static final BlockFamily ANCIENT_WOOD_PLANKS = new BlockFamily.Builder(ModBlocks.ANCIENT_WOOD_PLANKS.get()).slab(ANCIENT_WOOD_SLAB.get()).stairs(ANCIENT_WOOD_STAIRS.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily CALAMITES_PLANKS = new BlockFamily.Builder(ModBlocks.CALAMITES_PLANKS.get()).button(CALAMITES_BUTTON.get()).fence(CALAMITES_FENCE.get()).fenceGate(CALAMITES_FENCE_GATE.get()).pressurePlate(CALAMITES_PRESSURE_PLATE.get()).slab(CALAMITES_SLAB.get()).stairs(CALAMITES_STAIRS.get()).door(CALAMITES_DOOR.get()).trapdoor(CALAMITES_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily CORDAITES_PLANKS = new BlockFamily.Builder(ModBlocks.CORDAITES_PLANKS.get()).button(CORDAITES_BUTTON.get()).fence(CORDAITES_FENCE.get()).fenceGate(CORDAITES_FENCE_GATE.get()).pressurePlate(CORDAITES_PRESSURE_PLATE.get()).slab(CORDAITES_SLAB.get()).stairs(CORDAITES_STAIRS.get()).door(CORDAITES_DOOR.get()).trapdoor(CORDAITES_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily MUTANT_TREE_PLANKS = new BlockFamily.Builder(ModBlocks.MUTANT_TREE_PLANKS.get()).button(MUTANT_TREE_BUTTON.get()).fence(MUTANT_TREE_FENCE.get()).fenceGate(MUTANT_TREE_FENCE_GATE.get()).pressurePlate(MUTANT_TREE_PRESSURE_PLATE.get()).slab(MUTANT_TREE_SLAB.get()).stairs(MUTANT_TREE_STAIRS.get()).door(MUTANT_TREE_DOOR.get()).trapdoor(MUTANT_TREE_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily PALM_PLANKS = new BlockFamily.Builder(ModBlocks.PALM_PLANKS.get()).button(PALM_BUTTON.get()).fence(PALM_FENCE.get()).fenceGate(PALM_FENCE_GATE.get()).pressurePlate(PALM_PRESSURE_PLATE.get()).slab(PALM_SLAB.get()).stairs(PALM_STAIRS.get()).door(PALM_DOOR.get()).trapdoor(PALM_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily SIGILLARIA_PLANKS = new BlockFamily.Builder(ModBlocks.SIGILLARIA_PLANKS.get()).button(SIGILLARIA_BUTTON.get()).fence(SIGILLARIA_FENCE.get()).fenceGate(SIGILLARIA_FENCE_GATE.get()).pressurePlate(SIGILLARIA_PRESSURE_PLATE.get()).slab(SIGILLARIA_SLAB.get()).stairs(SIGILLARIA_STAIRS.get()).door(SIGILLARIA_DOOR.get()).trapdoor(SIGILLARIA_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily TEMPSKYA_PLANKS = new BlockFamily.Builder(ModBlocks.TEMPSKYA_PLANKS.get()).button(TEMPSKYA_BUTTON.get()).fence(TEMPSKYA_FENCE.get()).fenceGate(TEMPSKYA_FENCE_GATE.get()).pressurePlate(TEMPSKYA_PRESSURE_PLATE.get()).slab(TEMPSKYA_SLAB.get()).stairs(TEMPSKYA_STAIRS.get()).door(TEMPSKYA_DOOR.get()).trapdoor(TEMPSKYA_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();


    public ModRecipeProvider(DataGenerator arg) {
        super(arg);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        boolean cookingRecipes = true;
        boolean craftingRecipes = true;
        boolean analyzerRecipes = true;
        if (cookingRecipes) {
            for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
                if (info.foodItem != null && info.cookedFoodItem != null) {
                    fullCooking(info.foodItem, info.cookedFoodItem, info.resourceName, consumer, "_meat", 1.5f);
                }
            }
            fullOre(DENSE_SAND.get(), REINFORCED_GLASS.get(), consumer, 3);
            fullCooking(ModItemTags.COOKABLE_EGGS, ModItems.COOKED_EGG.get(), "dino_egg", consumer, "", 1);
        }
        if (craftingRecipes) {
            ToyBallItem white = ModItems.TOY_BALLS.get(DyeColor.WHITE).get();
            ShapedRecipeBuilder.shaped(white).define('W', ItemTags.WOOL).define('S', Items.STRING)
                    .define('B', Items.SLIME_BALL).pattern("SWS").pattern("WBW").pattern("SWS").unlockedBy("has_wool",
                            RecipeProvider.has(ItemTags.WOOL)).save(consumer, RecipeBuilder.getDefaultRecipeId(white));
            for (Map.Entry<DyeColor, RegistrySupplier<ToyBallItem>> entry : ModItems.TOY_BALLS.entrySet()) {
                DyeColor color = entry.getKey();
                ToyBallItem ball = entry.getValue().get();
                if (color == DyeColor.WHITE) continue;
                ShapelessRecipeBuilder.shapeless(ball).requires(colorToDye(color)).requires(white).unlockedBy("has_ball",
                        RecipeProvider.has(white)).save(consumer, Fossil.MOD_ID + ":toy_ball_white_to_" + color.getName());
                ShapelessRecipeBuilder.shapeless(white).requires(Items.WHITE_DYE).requires(ball).unlockedBy("has_ball",
                        RecipeProvider.has(white)).save(consumer, Fossil.MOD_ID + ":toy_ball_" + color.getName() + "_to_white");
            }
            for (Map.Entry<String, RegistrySupplier<ToyTetheredLogItem>> entry : ModItems.TOY_TETHERED_LOGS.entrySet()) {
                var block = Registry.BLOCK.getOptional(new ResourceLocation("minecraft:" + entry.getKey() + "_log"));
                block.ifPresent(log -> ShapedRecipeBuilder.shaped(entry.getValue().get()).define('S', Items.STRING).define('L', log)
                        .pattern("S").pattern("S").pattern("L").unlockedBy("has_log", RecipeProvider.has(log)).save(consumer));
            }
            ShapedRecipeBuilder.shaped(ModItems.TOY_TETHERED_LOGS.get(WoodType.CRIMSON.name()).get()).define('S', Items.STRING)
                    .define('L', Blocks.CRIMSON_STEM).pattern("S").pattern("S").pattern("L").unlockedBy("has_log",
                            RecipeProvider.has(Blocks.CRIMSON_STEM)).save(consumer);
            ShapedRecipeBuilder.shaped(ModItems.TOY_TETHERED_LOGS.get(WoodType.WARPED.name()).get()).define('S', Items.STRING)
                    .define('L', Blocks.WARPED_STEM).pattern("S").pattern("S").pattern("L").unlockedBy("has_log",
                            RecipeProvider.has(Blocks.WARPED_STEM)).save(consumer);
            for (Map.Entry<String, RegistrySupplier<ToyScratchingPostItem>> entry : ModItems.TOY_SCRATCHING_POSTS.entrySet()) {
                var block = Registry.BLOCK.getOptional(new ResourceLocation("minecraft:" + entry.getKey() + "_slab"));
                block.ifPresent(slab -> ShapedRecipeBuilder.shaped(entry.getValue().get()).define('S', Items.STICK).define('X', slab)
                        .define('W', ItemTags.WOOL).pattern("WWW").pattern("WSW").pattern(" X ").unlockedBy("has_slab",
                                RecipeProvider.has(slab)).save(consumer));
            }
            ShapelessRecipeBuilder.shapeless(ModItems.AQUATIC_SCARAB_GEM.get()).requires(ModItems.SCARAB_GEM.get()).requires(AMBER_CHUNK_DOMINICAN.get())
                    .unlockedBy("has_scarab_gem", RecipeProvider.has(ModItems.AQUATIC_SCARAB_GEM.get())).save(consumer);
            var bonesLeg = ModItemTags.LEG_BONES;
            var bonesFoot = ModItemTags.FOOT_BONES;
            var bonesVertebrae = ModItemTags.VERTEBRAE_BONES;
            var bonesRibcage = ModItemTags.RIBCAGE_BONES;
            var bonesSkull = ModItemTags.SKULL_BONES;
            ShapedRecipeBuilder.shaped(ModItems.BONE_BOOTS.get()).define('L', bonesLeg).define('F', bonesFoot).pattern("L L").pattern("F F").unlockedBy("has_bones", RecipeProvider.has(bonesFoot)).save(consumer);
            ShapedRecipeBuilder.shaped(ModItems.BONE_CHESTPLATE.get()).define('B', Items.BONE).define('V', bonesVertebrae).define('R', bonesRibcage).pattern("B B").pattern(" V ").pattern("BRB").unlockedBy("has_bones", RecipeProvider.has(bonesRibcage)).save(consumer);
            ShapedRecipeBuilder.shaped(ModItems.BONE_HELMET.get()).define('B', Items.BONE).define('S', bonesSkull).pattern("BSB").pattern("B B").unlockedBy("has_bones", RecipeProvider.has(bonesSkull)).save(consumer);
            ShapedRecipeBuilder.shaped(ModItems.BONE_LEGGINGS.get()).define('B', Items.BONE).define('L', bonesLeg).pattern("BBB").pattern("L L").pattern("B B").unlockedBy("has_bones", RecipeProvider.has(bonesLeg)).save(consumer);
            ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL).requires(VOLCANIC_ASH.get(), 4).unlockedBy("has_volcanic_ash", RecipeProvider.has(VOLCANIC_ASH.get())).save(consumer, Fossil.MOD_ID + ":bone_meal_from_ash");
            ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL).requires(ModItemTags.ALL_BONES).unlockedBy("has_bone", RecipeProvider.has(ModItemTags.ALL_BONES)).save(consumer, Fossil.MOD_ID + ":bone_meal_from_bone");
            ShapedRecipeBuilder.shaped(ModItems.CHICKEN_ESSENCE.get(), 8).define('G', Items.GLASS_BOTTLE).define('C', ModItems.COOKED_CHICKEN_SOUP.get()).pattern("GGG").pattern("GCG").pattern("GGG").unlockedBy("has_cooked_chicken_soup", RecipeProvider.has(ModItems.COOKED_CHICKEN_SOUP.get())).save(consumer);
            ShapedRecipeBuilder.shaped(ModItems.STUNTED_ESSENCE.get()).define('P', Items.POISONOUS_POTATO).define('C', ModItems.CHICKEN_ESSENCE.get()).pattern(" P ").pattern("PCP").pattern(" P ").unlockedBy("has_chicken_essence", RecipeProvider.has(ModItems.CHICKEN_ESSENCE.get())).save(consumer);

            ShapedRecipeBuilder.shaped(ANALYZER.get()).define('I', Items.IRON_INGOT).define('R', ModItems.RELIC_SCRAP.get()).define('B', ModItems.BIO_FOSSIL.get()).pattern("IRI").pattern("IBI").unlockedBy("has_bio_fossil", RecipeProvider.has(ModItems.BIO_FOSSIL.get())).save(consumer);
            ShapedRecipeBuilder.shaped(BUBBLE_BLOWER.get()).define('I', Items.GOLD_INGOT).define('N', Items.GOLD_NUGGET).define('W', Items.WATER_BUCKET).pattern("NIN").pattern("IWI").pattern("NIN").unlockedBy("has_dino_egg", RecipeProvider.has(ModItemTags.DINO_EGGS)).save(consumer);
            ShapedRecipeBuilder.shaped(SIFTER.get()).define('I', Blocks.IRON_BARS).define('S', Items.STRING).define('P', ItemTags.PLANKS).pattern("SPS").pattern("PIP").pattern("PSP").unlockedBy("has_planks", RecipeProvider.has(ItemTags.PLANKS)).save(consumer);
            ShapedRecipeBuilder.shaped(WORKTABLE.get()).define('L', Items.LEATHER).define('C', Blocks.CRAFTING_TABLE).pattern("L").pattern("C").unlockedBy("has_crafting_table", RecipeProvider.has(Blocks.CRAFTING_TABLE)).save(consumer);
            ShapedRecipeBuilder.shaped(DRUM.get()).define('L', Items.LEATHER).define('R', Items.REDSTONE).define('P', ItemTags.PLANKS).pattern("LLL").pattern("PRP").pattern("PPP").unlockedBy("has_crafting_table", RecipeProvider.has(Blocks.CRAFTING_TABLE)).save(consumer);

            ShapelessRecipeBuilder.shapeless(ModItems.DINOPEDIA.get()).requires(Items.BOOK).requires(ModItems.BIO_FOSSIL.get()).unlockedBy("has_bio_fossil", RecipeProvider.has(ModItems.BIO_FOSSIL.get())).save(consumer);

            ShapelessRecipeBuilder.shapeless(ModItems.RAW_CHICKEN_SOUP.get()).requires(Items.BUCKET).requires(Items.CHICKEN).unlockedBy("has_chicken", RecipeProvider.has(Items.CHICKEN)).save(consumer);
            ShapelessRecipeBuilder.shapeless(ModItems.SKULL_STICK.get()).requires(Items.STICK).requires(SKULL_BLOCK.get()).unlockedBy("has_skull_block", RecipeProvider.has(SKULL_BLOCK.get())).save(consumer);

            ShapedRecipeBuilder.shaped(AMPHORA_VASE_DAMAGED.get()).define('P', ModItems.POTTERY_SHARD.get()).pattern("PP").pattern("PP").pattern("PP").unlockedBy("has_pottery_shard", RecipeProvider.has(ModItems.POTTERY_SHARD.get())).save(consumer);
            ShapedRecipeBuilder.shaped(KYLIX_VASE_DAMAGED.get()).define('P', ModItems.POTTERY_SHARD.get()).pattern("PPP").pattern(" P ").unlockedBy("has_pottery_shard", RecipeProvider.has(ModItems.POTTERY_SHARD.get())).save(consumer);
            ShapedRecipeBuilder.shaped(VOLUTE_VASE_DAMAGED.get()).define('P', ModItems.POTTERY_SHARD.get()).pattern("P P").pattern("P P").pattern("PPP").unlockedBy("has_pottery_shard", RecipeProvider.has(ModItems.POTTERY_SHARD.get())).save(consumer);
            for (RegistrySupplier<VaseBlock> vase : VASES) {
                String name = vase.get().getRegistryName().getPath();
                if (!name.contains("damaged") && !name.contains("restored")) {
                    VaseBlock restored;
                    if (vase.get() instanceof AmphoraVaseBlock) {
                        restored = AMPHORA_VASE_RESTORED.get();
                    } else if (vase.get() instanceof KylixVaseBlock) {
                        restored = KYLIX_VASE_RESTORED.get();
                    } else {
                        restored = VOLUTE_VASE_RESTORED.get();
                    }
                    String[] split = name.split("_");
                    DyeColor color = DyeColor.byName(split[split.length - 1], DyeColor.BLACK);
                    ShapelessRecipeBuilder.shapeless(vase.get()).requires(restored).requires(DyeItem.byColor(color)).unlockedBy("has_restored_vase", RecipeProvider.has(restored)).save(consumer);
                }
            }

            ShapelessRecipeBuilder.shapeless(DENSE_SAND.get()).requires(Blocks.SAND).requires(Items.QUARTZ).unlockedBy("has_sand", RecipeProvider.has(Blocks.SAND)).save(consumer);
            generateFamilyRecipes(ANCIENT_WOOD_PLANKS, consumer);
            generateFamilyRecipes(CALAMITES_PLANKS, consumer);
            generateFamilyRecipes(CORDAITES_PLANKS, consumer);
            generateFamilyRecipes(MUTANT_TREE_PLANKS, consumer);
            generateFamilyRecipes(PALM_PLANKS, consumer);
            generateFamilyRecipes(SIGILLARIA_PLANKS, consumer);
            generateFamilyRecipes(TEMPSKYA_PLANKS, consumer);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.ANCIENT_WOOD_PLANKS.get(), ModItemTags.ANCIENT_WOOD_LOGS);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.CALAMITES_PLANKS.get(), ModItemTags.CALAMITES_LOGS);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.CORDAITES_PLANKS.get(), ModItemTags.CORDAITES_LOGS);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.MUTANT_TREE_PLANKS.get(), ModItemTags.MUTANT_TREE_LOGS);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.PALM_PLANKS.get(), ModItemTags.PALM_LOGS);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.SIGILLARIA_PLANKS.get(), ModItemTags.SIGILLARIA_LOGS);
            RecipeProvider.planksFromLogs(consumer, ModBlocks.TEMPSKYA_PLANKS.get(), ModItemTags.TEMPSKYA_LOGS);
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
            stonecutter(consumer, ANCIENT_STONE_STAIRS.get(), ANCIENT_STONE.get());
            stonecutter(consumer, ANCIENT_STONE_WALL.get(), ANCIENT_STONE.get());
            stonecutter(consumer, ANCIENT_STONE_BRICKS.get(), ANCIENT_STONE.get());
            stonecutter(consumer, ANCIENT_STONE_SLAB.get(), ANCIENT_STONE_BRICKS.get(), 2);
            stonecutter(consumer, ANCIENT_STONE_STAIRS.get(), ANCIENT_STONE_BRICKS.get());
            stonecutter(consumer, ANCIENT_STONE_WALL.get(), ANCIENT_STONE_BRICKS.get());
            stonecutter(consumer, VOLCANIC_BRICK_SLAB.get(), VOLCANIC_ROCK.get(), 2);
            stonecutter(consumer, VOLCANIC_BRICK_STAIRS.get(), VOLCANIC_ROCK.get());
            stonecutter(consumer, VOLCANIC_BRICK_WALL.get(), VOLCANIC_ROCK.get());
            stonecutter(consumer, VOLCANIC_BRICKS.get(), VOLCANIC_ROCK.get());
            stonecutter(consumer, VOLCANIC_BRICK_SLAB.get(), VOLCANIC_BRICKS.get(), 2);
            stonecutter(consumer, VOLCANIC_BRICK_STAIRS.get(), VOLCANIC_BRICKS.get());
            stonecutter(consumer, VOLCANIC_BRICK_WALL.get(), VOLCANIC_BRICKS.get());

            stonecutter(consumer, VOLCANIC_TILE_SLAB.get(), VOLCANIC_BRICKS.get(), 2);
            stonecutter(consumer, VOLCANIC_TILE_STAIRS.get(), VOLCANIC_BRICKS.get());
            stonecutter(consumer, VOLCANIC_TILE_WALL.get(), VOLCANIC_BRICKS.get());
            stonecutter(consumer, VOLCANIC_TILES.get(), VOLCANIC_BRICKS.get());
            stonecutter(consumer, VOLCANIC_TILE_SLAB.get(), VOLCANIC_TILES.get(), 2);
            stonecutter(consumer, VOLCANIC_TILE_STAIRS.get(), VOLCANIC_TILES.get());
            stonecutter(consumer, VOLCANIC_TILE_WALL.get(), VOLCANIC_TILES.get());
        }

        if (analyzerRecipes) {
            AnalyzerRecipeBuilder plantFossil = analyzed(ModItems.PlANT_FOSSIL.get())
                    .addOutput(Blocks.SAND, 35)
                    .addOutput(Blocks.CACTUS, 20)
                    .addOutput(ModItems.FERN_SEED_FOSSIL.get(), 5)
                    .addOutput(ModItems.CALAMITES_SAPLING_FOSSIL.get(), 2.5)
                    .addOutput(ModItems.CORDAITES_SAPLING_FOSSIL.get(), 2.5)
                    .addOutput(ModItems.PALM_SAPLING_FOSSIL.get(), 2.5)
                    .addOutput(ModItems.SIGILLARIA_SAPLING_FOSSIL.get(), 2.5)
                    .addOutput(ModItems.TEMPSKYA_SAPLING_FOSSIL.get(), 2.5);

            double seedWeight = (100F - plantFossil.total) / (double) PrehistoricPlantInfo.plantsWithSeeds().size();
            for (PrehistoricPlantInfo info : PrehistoricPlantInfo.plantsWithSeeds()) {
                plantFossil.addOutput(info.getFossilizedPlantSeedItem(), seedWeight);
            }
            plantFossil.save(consumer);
            AnalyzerRecipeBuilder bioFossil = analyzed(ModItems.BIO_FOSSIL.get())
                    .addOutput(Blocks.SAND, 35)
                    .addOutput(Items.BONE_MEAL, 50);
            List<PrehistoricEntityInfo> bioFossilEntityList = PrehistoricEntityInfo.getTimePeriodList(TimePeriod.MESOZOIC, TimePeriod.PALEOZOIC);
            double bioFossilDNAChance = 15F / (double) bioFossilEntityList.size();
            for (PrehistoricEntityInfo info : bioFossilEntityList) {
                bioFossil.addOutput(info.dnaItem, bioFossilDNAChance);
            }
            bioFossil.save(consumer);
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
            analyzed(ModItems.TAR_DROP.get())
                    .addOutput(Items.COAL, 20)
                    .addOutput(Items.CHARCOAL, 20)
                    .addOutput(ModItems.TAR_FOSSIL.get(), 45)
                    .addOutput(VOLCANIC_ROCK.get(), 15).save(consumer);
            AnalyzerRecipeBuilder tarFossil = analyzed(ModItems.TAR_FOSSIL.get())
                    .addOutput(Items.BONE_MEAL, 15)
                    .addOutput(VOLCANIC_ROCK.get(), 30);
            List<PrehistoricEntityInfo> tarFossilEntityList = PrehistoricEntityInfo.getTimePeriodList(TimePeriod.CENOZOIC);
            double tarFossilDNAChance = 20 / (double) tarFossilEntityList.size();
            for (PrehistoricEntityInfo info : tarFossilEntityList) {
                tarFossil.addOutput(info.dnaItem, tarFossilDNAChance);
            }
            tarFossil.save(consumer);

            AnalyzerRecipeBuilder failuresaurusFlesh = analyzed(ModItems.FAILURESAURUS_FLESH.get())
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
            AnalyzerRecipeBuilder frozenMeat = analyzed(ModItems.FROZEN_MEAT.get())
                    .addOutput(Items.CHICKEN, 15)
                    .addOutput(Items.MUTTON, 15)
                    .addOutput(Items.BEEF, 15)
                    .addOutput(Items.PORKCHOP, 15)
                    .addOutput(Items.CHICKEN, 15)
                    .addOutput(ModItems.TAR_FOSSIL.get(), 20);
            for (PrehistoricEntityInfo info : tarFossilEntityList) {
                frozenMeat.addOutput(info.dnaItem, tarFossilDNAChance);
            }
            frozenMeat.save(consumer);
            analyzed(AMBER_CHUNK_DOMINICAN.get()).addOutput(Items.SPIDER_EYE, 9).addOutput(Items.STRING, 10).addOutput(Blocks.DIRT, 25).addOutput(Blocks.GRAVEL, 25)
                    .addOutput(Items.WHEAT_SEEDS, 1).addOutput(Items.BEETROOT_SEEDS, 1).addOutput(Items.PUMPKIN_SEEDS, 1).addOutput(Items.MELON_SEEDS, 1)
                    .addOutput(ModItems.CALAMITES_SAPLING_FOSSIL.get(), 1).addOutput(ModItems.CORDAITES_SAPLING_FOSSIL.get(), 1).addOutput(ModItems.PALM_SAPLING_FOSSIL.get(), 1).addOutput(ModItems.SIGILLARIA_SAPLING_FOSSIL.get(), 1).addOutput(ModItems.TEMPSKYA_SAPLING_FOSSIL.get(), 1).save(consumer);
            analyzed(Items.BEEF).addOutput(COW.dnaItem, 100).save(consumer);
            analyzed(Items.CHICKEN).addOutput(CHICKEN.dnaItem, 100).save(consumer);
            analyzed(Items.EGG).addOutput(CHICKEN.dnaItem, 100).save(consumer);
            analyzed(Items.FEATHER).addOutput(CHICKEN.dnaItem, 95).addOutput(PARROT.dnaItem, 5).save(consumer);
            analyzed(ItemTags.FISHES).addOutput(Items.PRISMARINE_CRYSTALS, 15).addOutput(Items.BONE_MEAL, 75).addOutput(POLARBEAR.dnaItem, 10).save(consumer);
            analyzed(Items.LEATHER).addOutput(COW.dnaItem, 60).addOutput(DONKEY.dnaItem, 10).addOutput(HORSE.dnaItem, 30).save(consumer);
            analyzed(Items.MUTTON).addOutput(SHEEP.dnaItem, 100).save(consumer);
            //Nautilus Shell
            analyzed(Items.PORKCHOP).addOutput(PIG.dnaItem, 100).save(consumer);
            analyzed(Items.RABBIT).addOutput(RABBIT.dnaItem, 100).save(consumer);
            analyzed(Items.RABBIT_FOOT).addOutput(RABBIT.dnaItem, 100).save(consumer);
            analyzed(Items.RABBIT_HIDE).addOutput(RABBIT.dnaItem, 100).save(consumer);
            analyzed(ModItems.RELIC_SCRAP.get()).addOutput(Blocks.GRAVEL, 30).addOutput(Items.FLINT, 18).addOutput(ModItems.POTTERY_SHARD.get(), 4).addOutput(ModItems.BROKEN_HELMET.get(), 4).addOutput(ModItems.BROKEN_SWORD.get(), 4).addOutput(ModItems.STONE_TABLET.get(), 30)
                    .addOutput(ANU_FIGURINE_DESTROYED.get(), 4).addOutput(ENDERMAN_FIGURINE_DESTROYED.get(), 4).addOutput(PIGLIN_FIGURINE_DESTROYED.get(), 4).addOutput(SKELETON_FIGURINE_DESTROYED.get(), 4).addOutput(STEVE_FIGURINE_DESTROYED.get(), 4).addOutput(ZOMBIE_FIGURINE_DESTROYED.get(), 4).save(consumer);
            analyzed(ItemTags.WOOL).addOutput(Items.STRING, 3, 60).addOutput(SHEEP.dnaItem, 27).addOutput(LLAMA.dnaItem, 13).save(consumer);
        }
    }

    public AnalyzerRecipeBuilder analyzed(ItemLike itemLike) {
        return new AnalyzerRecipeBuilder(Fossil.MOD_ID, itemLike);
    }

    public AnalyzerRecipeBuilder analyzed(TagKey<Item> tagKey) {
        return new AnalyzerRecipeBuilder(Fossil.MOD_ID, tagKey);
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

    private void fullCooking(TagKey<Item> ingredient, Item result, String resourceName, Consumer<FinishedRecipe> consumer, String suffix, float exp) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, exp, 200)
                .unlockedBy("has_" + resourceName + suffix, inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), result, exp, 600)
                .unlockedBy("has_" + resourceName + suffix, inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), result, exp, 100)
                .unlockedBy("has_" + resourceName + suffix, inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_smoking");
    }

    private void fullCooking(ItemLike ingredient, ItemLike result, String resourceName, Consumer<FinishedRecipe> consumer, String suffix, float exp) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, exp, 200)
                .unlockedBy("has_" + resourceName + suffix, inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), result, exp, 600)
                .unlockedBy("has_" + resourceName + suffix, inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), result, exp, 100)
                .unlockedBy("has_" + resourceName + suffix, inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_smoking");
    }

    private void fullOre(ItemLike ingredient, ItemLike result, Consumer<FinishedRecipe> consumer, float exp) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, exp, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_smelting_" + getItemName(ingredient));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), result, exp, 100)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_blasting_" + getItemName(ingredient));
    }

    protected void stonecutter(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike material) {
        stonecutter(consumer, result, material, 1);
    }

    protected void stonecutter(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder builder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), result, resultCount).unlockedBy(getHasName(material), has(material));
        String id = getConversionRecipeName(result, material);
        builder.save(finishedRecipeConsumer, Fossil.MOD_ID + ":" + id + "_stonecutting");
    }

    private static void generateFamilyRecipes(BlockFamily family, Consumer<FinishedRecipe> finishedRecipeConsumer) {
        family.getVariants().forEach((variant, block) -> {
            BiFunction<ItemLike, ItemLike, RecipeBuilder> biFunction = shapeBuilders.get(variant);
            Block itemLike = RecipeProvider.getBaseBlock(family, variant);
            if (biFunction != null) {
                RecipeBuilder recipeBuilder = biFunction.apply(block, itemLike);
                family.getRecipeGroupPrefix().ifPresent(string -> recipeBuilder.group(string + (variant == BlockFamily.Variant.CUT ? "" : "_" + variant.getName())));
                recipeBuilder.unlockedBy(family.getRecipeUnlockedBy().orElseGet(() -> RecipeProvider.getHasName(itemLike)), RecipeProvider.has(itemLike));
                recipeBuilder.save(finishedRecipeConsumer);
            }
            if (variant == BlockFamily.Variant.CRACKED) {
                RecipeProvider.smeltingResultFromBase(finishedRecipeConsumer, block, itemLike);
            }
        });
    }
}
