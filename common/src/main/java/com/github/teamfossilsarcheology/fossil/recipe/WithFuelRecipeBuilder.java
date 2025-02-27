package com.github.teamfossilsarcheology.fossil.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class WithFuelRecipeBuilder implements RecipeBuilder {
    protected final String modId;
    protected final ItemLike itemInput;
    protected final ItemLike itemFuel;
    protected final ItemLike itemOutput;
    protected final int duration;

    protected WithFuelRecipeBuilder(String modId, ItemLike itemInput, ItemLike itemFuel, ItemLike itemOutput, int duration) {
        this.modId = modId;
        this.itemInput = itemInput;
        this.itemFuel = itemFuel;
        this.itemOutput = itemOutput;
        this.duration = duration;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull CriterionTriggerInstance criterionTrigger) {
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        //Method is unused
        return itemOutput.asItem();
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
        private final Ingredient input;
        private final Ingredient fuel;
        private final ItemLike output;
        private final int duration;

        protected Result(ResourceLocation recipeLocation, Ingredient input, Ingredient fuel, ItemLike output, int duration) {
            this.recipeLocation = recipeLocation;
            this.input = input;
            this.fuel = fuel;
            this.output = output;
            this.duration = duration;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", input.toJson());
            json.add("fuel", fuel.toJson());
            json.addProperty("result", BuiltInRegistries.ITEM.getKey(output.asItem()).toString());
            json.addProperty("duration", duration);
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
}
