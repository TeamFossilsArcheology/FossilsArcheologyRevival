package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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

    private static <T extends ProcessingRecipe<?>> BaseRecipeProvider.GeneratedRecipe create(IRecipeTypeInfo recipeType,
                                                                                             Supplier<ItemLike> singleIngredient,
                                                                                             UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        ProcessingRecipeSerializer<T> serializer = recipeType.getSerializer();
        return c -> {
            ItemLike itemLike = singleIngredient.get();
            transform
                    .apply(new CustomProcessingRecipeBuilder<>(serializer.getFactory(),
                            new ResourceLocation(FossilMod.MOD_ID, ForgeRegistries.ITEMS.getKey(itemLike.asItem())
                                    .getPath())).withItemIngredients(Ingredient.of(itemLike)))
                    .build(c);
        };
    }

    /**
     * With serializer for fabric conditions
     */
    private static class CustomProcessingRecipeBuilder<T extends ProcessingRecipe<?>> extends ProcessingRecipeBuilder<T> {

        private CustomProcessingRecipeBuilder(ProcessingRecipeFactory<T> factory, ResourceLocation recipeId) {
            super(factory, recipeId);
        }

        @Override
        public void build(Consumer<FinishedRecipe> consumer) {
            consumer.accept(new CustomDataGenResult<>(build(), recipeConditions));
        }

        private static class CustomDataGenResult<S extends ProcessingRecipe<?>> implements FinishedRecipe {
            private final List<ICondition> recipeConditions;
            private final ProcessingRecipeSerializer<S> serializer;
            private final ResourceLocation id;
            private final S recipe;

            @SuppressWarnings("unchecked")
            public CustomDataGenResult(S recipe, List<ICondition> recipeConditions) {
                this.recipe = recipe;
                this.recipeConditions = recipeConditions;
                IRecipeTypeInfo recipeType = this.recipe.getTypeInfo();
                ResourceLocation typeId = recipeType.getId();

                if (!(recipeType.getSerializer() instanceof ProcessingRecipeSerializer))
                    throw new IllegalStateException("Cannot datagen ProcessingRecipe of type: " + typeId);

                this.id = new ResourceLocation(recipe.getId().getNamespace(),
                        typeId.getPath() + "/" + recipe.getId().getPath());
                this.serializer = (ProcessingRecipeSerializer<S>) recipe.getSerializer();
            }

            @Override
            public void serializeRecipeData(JsonObject json) {
                serializer.write(json, recipe);
                if (recipeConditions.isEmpty())
                    return;

                JsonArray conds = new JsonArray();
                recipeConditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
                json.add("conditions", conds);

                JsonArray fabricConditions = new JsonArray();
                recipeConditions.forEach(c -> fabricConditions.add(toFabric(CraftingHelper.serialize(c))));
                json.add("fabric:load_conditions", fabricConditions);
            }

            @Override
            public @NotNull ResourceLocation getId() {
                return id;
            }

            @Override
            public @NotNull RecipeSerializer<?> getType() {
                return serializer;
            }

            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }

        }

        private static JsonObject toFabric(JsonObject json) {
            JsonObject toReturn = new JsonObject();
            if (json.has("type")) {
                String type = json.get("type").getAsString();
                if (type.equals(ModLoadedCondition.Serializer.INSTANCE.getID().toString())) {
                    toReturn.addProperty("condition", "fabric:any_mod_loaded");
                    JsonArray mods = new JsonArray();
                    mods.add(json.get("modid").getAsString());
                    toReturn.add("values", mods);
                }
            }
            return toReturn;
        }

    }
}
