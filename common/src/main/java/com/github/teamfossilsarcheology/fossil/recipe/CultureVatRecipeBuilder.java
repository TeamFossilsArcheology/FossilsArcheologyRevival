package com.github.teamfossilsarcheology.fossil.recipe;

import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CultureVatRecipeBuilder extends WithFuelRecipeBuilder {
    public CultureVatRecipeBuilder(String modId, ItemLike itemInput, ItemLike itemOutput) {
        //TODO: Duration not actually used
        this(modId, itemInput, ModItems.BIO_GOO.get(), itemOutput, 6000);
    }

    public CultureVatRecipeBuilder(String modId, ItemLike itemInput, ItemLike itemFuel, ItemLike itemOutput, int duration) {
        super(modId, itemInput, itemFuel, itemOutput, duration);
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation recipeId) {
        consumer.accept(new Result(recipeId, Ingredient.of(itemInput), Ingredient.of(itemFuel), itemOutput, duration));
    }

    @Override
    protected ResourceLocation getDefaultRecipeId() {
        return new ResourceLocation(modId, "culture_vat/" + BuiltInRegistries.ITEM.getKey(itemInput.asItem()).getPath());
    }

    public static class Result extends WithFuelRecipeBuilder.Result {

        public Result(ResourceLocation recipeLocation, Ingredient input, Ingredient fuel, ItemLike output, int duration) {
            super(recipeLocation, input, fuel, output, duration);
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return CultureVatRecipe.Serializer.INSTANCE;
        }
    }
}
