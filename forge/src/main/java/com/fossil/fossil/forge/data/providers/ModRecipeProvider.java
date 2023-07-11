package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ToyBallItem;
import com.fossil.fossil.item.ToyScratchingPostItem;
import com.fossil.fossil.item.ToyTetheredLogItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator arg) {
        super(arg);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            if (type.foodItem != null && type.cookedFoodItem != null) {
                fullCooking(type.foodItem, type.cookedFoodItem, type.resourceName, consumer);
            }
            if (type.fishItem != null && type.cookedFoodItem != null) {
                fullCooking(type.fishItem, type.cookedFoodItem, type.resourceName, consumer);
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
}
