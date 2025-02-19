package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Consumer;

public class FossilFarmersRecipeProvider extends RecipeProvider {
    public FossilFarmersRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        fullCooking(ModItemTags.DINO_EGGS, ModItems.FRIED_EGG.get(), "fa_eggs_to_fried_egg", consumer, 0.5f);
    }

    private static void fullCooking(Ingredient ingredient, ItemPredicate predicate, ItemLike result, String ingredientName, String resultName, Consumer<FinishedRecipe> consumer2, float exp) {
        ResourceLocation baseLocation = FossilMod.location(resultName);
        ConditionalRecipe.builder().addCondition(new ModLoadedCondition(ModConstants.FARMERS)).addRecipe(consumer -> {
            var furnace = SimpleCookingRecipeBuilder.smelting(ingredient, result, exp, 200)
                    .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
            furnace.save(consumer, baseLocation);
        }).build(consumer2, baseLocation);
        ConditionalRecipe.builder().addCondition(new ModLoadedCondition(ModConstants.FARMERS)).addRecipe(consumer -> {
            var campfire = SimpleCookingRecipeBuilder.campfireCooking(ingredient, result, exp, 600)
                    .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
            campfire.save(consumer, baseLocation + "_from_campfire_cooking");
        }).build(consumer2, baseLocation.getNamespace(), baseLocation.getPath() + "_from_campfire_cooking");
        ConditionalRecipe.builder().addCondition(new ModLoadedCondition(ModConstants.FARMERS)).addRecipe(consumer -> {
            var smoker = SimpleCookingRecipeBuilder.smoking(ingredient, result, exp, 100)
                    .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
            smoker.save(consumer, baseLocation + "_from_smoking");
        }).build(consumer2, baseLocation.getNamespace(), baseLocation.getPath() + "_from_smoking");
    }

    private static void fullCooking(TagKey<Item> ingredient, ItemLike result, String resultName, Consumer<FinishedRecipe> consumer, float exp) {
        fullCooking(Ingredient.of(ingredient), ItemPredicate.Builder.item().of(ingredient).build(), result, ingredient.location().getPath(), resultName, consumer, exp);
    }

    private static void fullCooking(ItemLike ingredient, ItemLike result, String resultName, Consumer<FinishedRecipe> consumer, float exp) {
        fullCooking(Ingredient.of(ingredient), ItemPredicate.Builder.item().of(ingredient).build(), result, RecipeBuilder.getDefaultRecipeId(ingredient).getPath(), resultName, consumer, exp);
    }

    private static void fullCooking(ItemLike ingredient, ItemLike result, Consumer<FinishedRecipe> consumer, float exp) {
        fullCooking(ingredient, result, RecipeBuilder.getDefaultRecipeId(result).toString(), consumer, exp);
    }

    private static void a(RecipeBuilder builder) {

    }
}
