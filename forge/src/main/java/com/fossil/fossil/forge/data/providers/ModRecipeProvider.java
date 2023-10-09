package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ToyBallItem;
import com.fossil.fossil.item.ToyScratchingPostItem;
import com.fossil.fossil.item.ToyTetheredLogItem;
import com.fossil.fossil.tags.ModItemTags;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public static final BlockFamily CALAMITES_PLANKS = new BlockFamily.Builder(ModBlocks.CALAMITES_PLANKS.get()).button(ModBlocks.CALAMITES_BUTTON.get()).fence(ModBlocks.CALAMITES_FENCE.get()).fenceGate(ModBlocks.CALAMITES_FENCE_GATE.get()).pressurePlate(ModBlocks.CALAMITES_PRESSURE_PLATE.get()).slab(ModBlocks.CALAMITES_SLAB.get()).stairs(ModBlocks.CALAMITES_STAIRS.get()).door(ModBlocks.CALAMITES_DOOR.get()).trapdoor(ModBlocks.CALAMITES_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily CORDAITES_PLANKS = new BlockFamily.Builder(ModBlocks.CORDAITES_PLANKS.get()).button(ModBlocks.CORDAITES_BUTTON.get()).fence(ModBlocks.CORDAITES_FENCE.get()).fenceGate(ModBlocks.CORDAITES_FENCE_GATE.get()).pressurePlate(ModBlocks.CORDAITES_PRESSURE_PLATE.get()).slab(ModBlocks.CORDAITES_SLAB.get()).stairs(ModBlocks.CORDAITES_STAIRS.get()).door(ModBlocks.CORDAITES_DOOR.get()).trapdoor(ModBlocks.CORDAITES_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily PALM_PLANKS = new BlockFamily.Builder(ModBlocks.PALM_PLANKS.get()).button(ModBlocks.PALM_BUTTON.get()).fence(ModBlocks.PALM_FENCE.get()).fenceGate(ModBlocks.PALM_FENCE_GATE.get()).pressurePlate(ModBlocks.PALM_PRESSURE_PLATE.get()).slab(ModBlocks.PALM_SLAB.get()).stairs(ModBlocks.PALM_STAIRS.get()).door(ModBlocks.PALM_DOOR.get()).trapdoor(ModBlocks.PALM_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily SIGILLARIA_PLANKS = new BlockFamily.Builder(ModBlocks.SIGILLARIA_PLANKS.get()).button(ModBlocks.SIGILLARIA_BUTTON.get()).fence(ModBlocks.SIGILLARIA_FENCE.get()).fenceGate(ModBlocks.SIGILLARIA_FENCE_GATE.get()).pressurePlate(ModBlocks.SIGILLARIA_PRESSURE_PLATE.get()).slab(ModBlocks.SIGILLARIA_SLAB.get()).stairs(ModBlocks.SIGILLARIA_STAIRS.get()).door(ModBlocks.SIGILLARIA_DOOR.get()).trapdoor(ModBlocks.SIGILLARIA_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily TEMPSKYA_PLANKS = new BlockFamily.Builder(ModBlocks.TEMPSKYA_PLANKS.get()).button(ModBlocks.TEMPSKYA_BUTTON.get()).fence(ModBlocks.TEMPSKYA_FENCE.get()).fenceGate(ModBlocks.TEMPSKYA_FENCE_GATE.get()).pressurePlate(ModBlocks.TEMPSKYA_PRESSURE_PLATE.get()).slab(ModBlocks.TEMPSKYA_SLAB.get()).stairs(ModBlocks.TEMPSKYA_STAIRS.get()).door(ModBlocks.TEMPSKYA_DOOR.get()).trapdoor(ModBlocks.TEMPSKYA_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();


    public ModRecipeProvider(DataGenerator arg) {
        super(arg);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            if (type.foodItem != null && type.cookedFoodItem != null) {
                fullCooking(type.foodItem, type.cookedFoodItem, type.resourceName, consumer);
            }
        }
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
        //TODO: Amphora recipes
        ShapelessRecipeBuilder.shapeless(ModItems.BIO_GOO.get()).requires(Items.EGG).requires(Items.WHEAT)
                .requires(Items.ROTTEN_FLESH).requires(Items.MILK_BUCKET).unlockedBy("has_egg", RecipeProvider.has(Items.EGG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.AQUATIC_SCARAB_GEM.get()).requires(ModItems.SCARAB_GEM.get()).requires(ModBlocks.AMBER_CHUNK_DOMINICAN.get())
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
        ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL).requires(ModBlocks.VOLCANIC_ASH.get(), 4).unlockedBy("has_volcanic_ash", RecipeProvider.has(ModBlocks.VOLCANIC_ASH.get())).save(consumer, Fossil.MOD_ID+":ash_to_bone_meal");
        ShapedRecipeBuilder.shaped(ModItems.CHICKEN_ESSENCE.get()).define('G', Items.GLASS_BOTTLE).define('C', ModItems.COOKED_CHICKEN_SOUP.get()).pattern("GGG").pattern("GCG").pattern("GGG").unlockedBy("has_cooked_chicken_soup", RecipeProvider.has(ModItems.COOKED_CHICKEN_SOUP.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.STUNTED_ESSENCE.get()).define('P', Items.POISONOUS_POTATO).define('C', ModItems.CHICKEN_ESSENCE.get()).pattern(" P ").pattern("PCP").pattern(" P ").unlockedBy("has_chicken_essence", RecipeProvider.has(ModItems.CHICKEN_ESSENCE.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ANALYZER.get()).define('I', Items.IRON_INGOT).define('R', ModItems.RELIC_SCRAP.get()).define('B', ModItems.BIO_FOSSIL.get()).pattern("IRI").pattern("IBI").unlockedBy("has_bio_fossil", RecipeProvider.has(ModItems.BIO_FOSSIL.get())).save(consumer);
        var fabricGlass = ItemTags.create(new ResourceLocation("c:glass_blocks"));
        ShapedRecipeBuilder.shaped(ModBlocks.CULTIVATE.get()).define('I', Items.IRON_INGOT).define('S', Items.SLIME_BALL).define('G', fabricGlass).define('W', Items.WATER_BUCKET).pattern("GSG").pattern("GWG").pattern("III").unlockedBy("has_dna", RecipeProvider.has(ModItemTags.DNA))
                .save(consumer, Fossil.MOD_ID+":cultivate_fabric");
        var forgeGlass = ItemTags.create(new ResourceLocation("forge:glass"));
        ShapedRecipeBuilder.shaped(ModBlocks.CULTIVATE.get()).define('I', Items.IRON_INGOT).define('S', Items.SLIME_BALL).define('G', forgeGlass).define('W', Items.WATER_BUCKET).pattern("GSG").pattern("GWG").pattern("III").unlockedBy("has_dna", RecipeProvider.has(ModItemTags.DNA))
                .save(consumer, Fossil.MOD_ID+":cultivate_forge");
        ShapedRecipeBuilder.shaped(ModBlocks.FEEDER.get()).define('I', Items.IRON_INGOT).define('S', Blocks.STONE).define('B', Blocks.STONE_BUTTON).define('W', Items.BUCKET).define('G', fabricGlass).pattern("IGI").pattern("BWS").pattern("SSS").unlockedBy("has_dino_egg", RecipeProvider.has(ModItemTags.DINO_EGG))
                .save(consumer, Fossil.MOD_ID+":feeder_fabric");
        ShapedRecipeBuilder.shaped(ModBlocks.FEEDER.get()).define('I', Items.IRON_INGOT).define('S', Blocks.STONE).define('B', Blocks.STONE_BUTTON).define('W', Items.BUCKET).define('G', forgeGlass).pattern("IGI").pattern("BWS").pattern("SSS").unlockedBy("has_dino_egg", RecipeProvider.has(ModItemTags.DINO_EGG))
                .save(consumer, Fossil.MOD_ID+":feeder_forge");
        ShapedRecipeBuilder.shaped(ModBlocks.BUBBLE_BLOWER.get()).define('I', Items.GOLD_INGOT).define('N', Items.GOLD_NUGGET).define('W', Items.WATER_BUCKET).pattern("NIN").pattern("IWI").pattern("NIN").unlockedBy("has_dino_egg", RecipeProvider.has(ModItemTags.DINO_EGG)).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.SIFTER.get()).define('I', Blocks.IRON_BARS).define('S', Items.STRING).define('P', ItemTags.PLANKS).pattern("SPS").pattern("PIP").pattern("PSP").unlockedBy("has_planks", RecipeProvider.has(ItemTags.PLANKS)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.WORKTABLE.get()).define('L', Items.LEATHER).define('C', Blocks.CRAFTING_TABLE).pattern("L").pattern("C").unlockedBy("has_crafting_table", RecipeProvider.has(Blocks.CRAFTING_TABLE)).save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.DINOPEDIA.get()).requires(Items.BOOK).requires(ModItems.BIO_FOSSIL.get()).unlockedBy("has_bio_fossil", RecipeProvider.has(ModItems.BIO_FOSSIL.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.RAW_CHICKEN_SOUP.get()).requires(Items.BUCKET).requires(Items.CHICKEN).unlockedBy("has_chicken", RecipeProvider.has(Items.CHICKEN)).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.SKULL_STICK.get()).requires(Items.STICK).requires(ModBlocks.SKULL_BLOCK.get()).unlockedBy("has_skull_block", RecipeProvider.has(ModBlocks.SKULL_BLOCK.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.AMPHORA_VASE_DAMAGED.get()).define('P', ModItems.POTTERY_SHARD.get()).pattern("PP").pattern("PP").pattern("PP").unlockedBy("has_pottery_shard", RecipeProvider.has(ModItems.POTTERY_SHARD.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.KYLIX_VASE_DAMAGED.get()).define('P', ModItems.POTTERY_SHARD.get()).pattern("PPP").pattern(" P ").unlockedBy("has_pottery_shard", RecipeProvider.has(ModItems.POTTERY_SHARD.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.VOLUTE_VASE_DAMAGED.get()).define('P', ModItems.POTTERY_SHARD.get()).pattern("P P").pattern("P P").pattern("PPP").unlockedBy("has_pottery_shard", RecipeProvider.has(ModItems.POTTERY_SHARD.get())).save(consumer);


        generateFamilyRecipes(CALAMITES_PLANKS, consumer);
        generateFamilyRecipes(CORDAITES_PLANKS, consumer);
        generateFamilyRecipes(PALM_PLANKS, consumer);
        generateFamilyRecipes(SIGILLARIA_PLANKS, consumer);
        generateFamilyRecipes(TEMPSKYA_PLANKS, consumer);
        RecipeProvider.planksFromLogs(consumer, ModBlocks.CALAMITES_PLANKS.get(), ModItemTags.CALAMITES_LOGS);
        RecipeProvider.planksFromLogs(consumer, ModBlocks.CORDAITES_PLANKS.get(), ModItemTags.CORDAITES_LOGS);
        RecipeProvider.planksFromLogs(consumer, ModBlocks.PALM_PLANKS.get(), ModItemTags.PALM_LOGS);
        RecipeProvider.planksFromLogs(consumer, ModBlocks.SIGILLARIA_PLANKS.get(), ModItemTags.SIGILLARIA_LOGS);
        RecipeProvider.planksFromLogs(consumer, ModBlocks.TEMPSKYA_PLANKS.get(), ModItemTags.TEMPSKYA_LOGS);
        RecipeProvider.woodFromLogs(consumer, ModBlocks.CALAMITES_WOOD.get(), ModBlocks.CALAMITES_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.CORDAITES_WOOD.get(), ModBlocks.CORDAITES_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.PALM_WOOD.get(), ModBlocks.PALM_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.SIGILLARIA_WOOD.get(), ModBlocks.SIGILLARIA_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.TEMPSKYA_WOOD.get(), ModBlocks.TEMPSKYA_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.STRIPPED_CALAMITES_WOOD.get(), ModBlocks.STRIPPED_CALAMITES_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.STRIPPED_CORDAITES_WOOD.get(), ModBlocks.STRIPPED_CORDAITES_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.STRIPPED_PALM_WOOD.get(), ModBlocks.STRIPPED_PALM_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.STRIPPED_SIGILLARIA_WOOD.get(), ModBlocks.STRIPPED_SIGILLARIA_LOG.get());
        RecipeProvider.woodFromLogs(consumer, ModBlocks.STRIPPED_TEMPSKYA_WOOD.get(), ModBlocks.STRIPPED_TEMPSKYA_LOG.get());
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

    private void fullCooking(Item ingredient, Item result, String resourceName, Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, 1.5f, 200)
                .unlockedBy("has_" + resourceName + "_meat", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), result, 1.5f, 600)
                .unlockedBy("has_" + resourceName + "_meat", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), result, 1.5f, 100)
                .unlockedBy("has_" + resourceName + "_meat", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result) + "_from_smoking");
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
