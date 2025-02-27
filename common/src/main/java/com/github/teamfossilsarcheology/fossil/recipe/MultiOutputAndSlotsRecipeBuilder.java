package com.github.teamfossilsarcheology.fossil.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Consumer;

public abstract class MultiOutputAndSlotsRecipeBuilder<T extends MultiOutputAndSlotsRecipeBuilder<T>> implements RecipeBuilder {
    protected final String modId;
    protected final ItemLike itemInput;
    protected final TagKey<Item> tagInput;
    protected final NavigableMap<ItemHolder, Double> weightedOutputs = new TreeMap<>();
    public double total;

    protected MultiOutputAndSlotsRecipeBuilder(String modId, ItemLike itemInput) {
        this.modId = modId;
        this.itemInput = itemInput;
        this.tagInput = null;
    }

    protected MultiOutputAndSlotsRecipeBuilder(String modId, TagKey<Item> tagInput) {
        this.modId = modId;
        this.itemInput = null;
        this.tagInput = tagInput;
    }

    public MultiOutputAndSlotsRecipeBuilder<T> addOutput(ItemLike itemLike, double weight) {
        return addOutput(itemLike, 1, weight);
    }

    public MultiOutputAndSlotsRecipeBuilder<T> addOutput(ItemLike itemLike, int count, double weight) {
        total += weight;
        weightedOutputs.put(new ItemHolder(BuiltInRegistries.ITEM.getKey(itemLike.asItem()), count), weight);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        //Method is unused
        return Items.ENDER_PEARL;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer) {
        save(consumer, getDefaultRecipeId());
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull String recipeId) {
        ResourceLocation resourceLocation = getDefaultRecipeId();
        ResourceLocation resourceLocation2 = new ResourceLocation(recipeId);
        if (resourceLocation2.equals(resourceLocation)) {
            throw new IllegalStateException("Recipe " + recipeId + " should remove its 'save' argument as it is equal to default one");
        } else {
            save(consumer, resourceLocation2);
        }
    }

    protected abstract ResourceLocation getDefaultRecipeId();

    public abstract static class Result implements FinishedRecipe {
        private final ResourceLocation recipeLocation;
        private final Ingredient ingredient;
        private final NavigableMap<ItemHolder, Double> weightedOutputs;

        protected Result(ResourceLocation recipeLocation, Ingredient ingredient, NavigableMap<ItemHolder, Double> weightedOutputs) {
            this.recipeLocation = recipeLocation;
            this.ingredient = ingredient;
            this.weightedOutputs = weightedOutputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", ingredient.toJson());

            JsonArray outputs = new JsonArray();
            for (Map.Entry<ItemHolder, Double> output : weightedOutputs.entrySet()) {
                ItemHolder itemStack = output.getKey();
                JsonObject outputObject = new JsonObject();
                outputObject.addProperty("item", itemStack.location.toString());
                if (itemStack.count > 1) {
                    outputObject.addProperty("count", itemStack.count);
                }
                outputObject.addProperty("weight", output.getValue());
                outputs.add(outputObject);
            }
            json.add("outputs", outputs);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return recipeLocation;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

    public record ItemHolder(ResourceLocation location, int count) implements Comparable<ItemHolder> {

        @Override
        public int compareTo(@NotNull ItemHolder o) {
            return location.getPath().compareTo(o.location.getPath());
        }
    }
}
