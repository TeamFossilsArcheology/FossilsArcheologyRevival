package com.github.teamfossilsarcheology.fossil.recipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.NavigableMap;
import java.util.function.Consumer;

public class AnalyzerRecipeBuilder extends MultiOutputAndSlotsRecipeBuilder<AnalyzerRecipeBuilder> {
    public AnalyzerRecipeBuilder(String modId, ItemLike itemInput) {
        super(modId, itemInput);
    }

    public AnalyzerRecipeBuilder(String modId, TagKey<Item> tagInput) {
        super(modId, tagInput);
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation recipeId) {
        consumer.accept(new Result(recipeId, itemInput != null ? Ingredient.of(itemInput) : Ingredient.of(tagInput), weightedOutputs));
    }

    @Override
    protected ResourceLocation getDefaultRecipeId() {
        if (itemInput != null) {
            return new ResourceLocation(modId, "analyzer/" + BuiltInRegistries.ITEM.getKey(itemInput.asItem()).getPath());
        } else if (tagInput != null) {
            return new ResourceLocation(modId, "analyzer/" + tagInput.location().getPath());
        }
        return BuiltInRegistries.ITEM.getKey(Items.ENDER_PEARL);
    }

    public static class Result extends MultiOutputAndSlotsRecipeBuilder.Result {

        public Result(ResourceLocation recipeLocation, Ingredient input, NavigableMap<ItemHolder, Double> weightedOutputs) {
            super(recipeLocation, input, weightedOutputs);
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return AnalyzerRecipe.Serializer.INSTANCE;
        }
    }
}
