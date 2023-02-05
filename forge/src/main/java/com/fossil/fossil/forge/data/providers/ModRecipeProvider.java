package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

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
    }

    private void fullCooking(Item ingredient, Item result, String resourceName, Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, 1.5f, 200)
                .unlockedBy("has_" + resourceName + "_meat", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), result, 1.5f, 600)
                .unlockedBy("has_" + resourceName + "_meat", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result)+"_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), result, 1.5f, 100)
                .unlockedBy("has_" + resourceName + "_meat", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(result)+"_from_smoking");
    }
}
