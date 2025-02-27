package com.github.teamfossilsarcheology.fossil.recipe;

import com.github.teamfossilsarcheology.fossil.inventory.WorktableMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WorktableRecipeBuilder extends WithFuelRecipeBuilder {
    public WorktableRecipeBuilder(String modId, ItemLike itemInput, ItemLike itemFuel, ItemLike itemOutput) {
        this(modId, itemInput, itemFuel, itemOutput, WorktableMenu.DEFAULT_DURATION);
    }

    public WorktableRecipeBuilder(String modId, ItemLike itemInput, ItemLike itemFuel, ItemLike itemOutput, int duration) {
        super(modId, itemInput, itemFuel, itemOutput, duration);
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation recipeId) {
        consumer.accept(new Result(recipeId, Ingredient.of(itemInput), Ingredient.of(itemFuel), itemOutput, duration));
    }

    @Override
    protected ResourceLocation getDefaultRecipeId() {
        return new ResourceLocation(modId, "worktable/" + BuiltInRegistries.ITEM.getKey(itemInput.asItem()).getPath() + "_with_" + BuiltInRegistries.ITEM.getKey(itemFuel.asItem()).getPath());
    }

    public static class Result extends WithFuelRecipeBuilder.Result {

        public Result(ResourceLocation recipeLocation, Ingredient input, Ingredient fuel, ItemLike output, int duration) {
            super(recipeLocation, input, fuel, output, duration);
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return WorktableRecipe.Serializer.INSTANCE;
        }
    }
}
