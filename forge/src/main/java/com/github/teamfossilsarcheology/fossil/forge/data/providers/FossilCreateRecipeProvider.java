package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.DEEPSLATE_FOSSIL;
import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.TUFF_FOSSIL;

public class FossilCreateRecipeProvider {
    public static void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        baseFossil(ModBlocks.CALCITE_FOSSIL::get, Blocks.CALCITE, consumer);
        baseFossil(ModBlocks.DRIPSTONE_FOSSIL::get, Blocks.DRIPSTONE_BLOCK, consumer);
        baseFossil(ModBlocks.RED_SANDSTONE_FOSSIL::get, Blocks.RED_SANDSTONE, consumer);
        baseFossil(ModBlocks.SANDSTONE_FOSSIL::get, Blocks.SANDSTONE, consumer);
        baseFossil(ModBlocks.STONE_FOSSIL::get, Blocks.COBBLESTONE, consumer);
        deepslateFossil(DEEPSLATE_FOSSIL::get, Blocks.COBBLED_DEEPSLATE, consumer);
        deepslateFossil(TUFF_FOSSIL::get, Blocks.TUFF, consumer);
    }

    private static void baseFossil(Supplier<ItemLike> singleIngredient, Block block, Consumer<FinishedRecipe> consumer) {
        create(AllRecipeTypes.CRUSHING, singleIngredient, builder -> builder.duration(250)
                .output(0.2f, ModItems.BIO_FOSSIL.get())
                .output(0.1f, ModItems.PlANT_FOSSIL.get())
                .output(0.1f, ModItems.RELIC_SCRAP.get())
                .output(0.35f, Items.BONE)
                .output(0.125f, ModBlocks.SKULL_BLOCK.get())
                .output(0.125f, block)
                .whenModLoaded(Create.ID)).register(consumer);
    }

    private static void deepslateFossil(Supplier<ItemLike> singleIngredient, Block block, Consumer<FinishedRecipe> consumer) {
        create(AllRecipeTypes.CRUSHING, singleIngredient, builder -> builder.duration(250)
                .output(0.25f, ModItems.SHALE_FOSSIL.get())
                .output(0.05f, ModItems.PlANT_FOSSIL.get())
                .output(0.05f, ModItems.RELIC_SCRAP.get())
                .output(0.35f, Items.BONE)
                .output(0.125f, ModBlocks.SKULL_BLOCK.get())
                .output(0.175f, block)
                .whenModLoaded(Create.ID)).register(consumer);
    }

    private static <T extends ProcessingRecipe<?>> CreateRecipeProvider.GeneratedRecipe create(IRecipeTypeInfo recipeType,
                                                                                          Supplier<ItemLike> singleIngredient,
                                                                                          UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        ProcessingRecipeSerializer<T> serializer = recipeType.getSerializer();
        return c -> {
            ItemLike itemLike = singleIngredient.get();
            transform
                    .apply(new ProcessingRecipeBuilder<>(serializer.getFactory(),
                            new ResourceLocation(FossilMod.MOD_ID, RegisteredObjects.getKeyOrThrow(itemLike.asItem())
                                    .getPath())).withItemIngredients(Ingredient.of(itemLike)))
                    .build(c);
        };
    }
}
