package com.fossil.fossil.recipe;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.entity.CustomBlockEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.architectury.core.AbstractRecipeSerializer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class AnalyzerRecipe implements Recipe<CustomBlockEntity> {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final NavigableMap<Double, ItemStack> weightedOutputs;

    public AnalyzerRecipe(ResourceLocation resourceLocation, Ingredient ingredient, NavigableMap<Double, ItemStack> weightedOutputs) {
        this.id = resourceLocation;
        this.ingredient = ingredient;
        this.weightedOutputs = weightedOutputs;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(ingredient);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean matches(CustomBlockEntity container, Level level) {
        for (int i = 0; i < container.getIngredientsSize(); i++) {
            if (matches(container, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean matches(CustomBlockEntity container, int slot) {
        ItemStack itemStack = container.getItem(slot);
        if (itemStack.isEmpty()) return false;
        return ingredient.test(itemStack);
    }

    public boolean inputMatches(ItemStack itemStack) {
        return ingredient.test(itemStack);
    }

    @Override
    public @NotNull ItemStack assemble(CustomBlockEntity container) {
        return weightedOutputs.higherEntry(container.getLevel().random.nextDouble() * weightedOutputs.lastKey()).getValue().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AnalyzerRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
/*
        @Override
        public <C extends Container> Optional<AnalyzerRecipe> tryMatch(Recipe<C> recipe, Level level, C container) {
            AnalyzerRecipe analyzerRecipe = (AnalyzerRecipe) recipe;
            return analyzerRecipe.matches(container, level) ? Optional.of((AnalyzerRecipe) recipe) : Optional.empty();
        }*/
    }

    public static class Serializer extends AbstractRecipeSerializer<AnalyzerRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull AnalyzerRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonElement jsonelement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json,
                    "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonelement);
            NavigableMap<Double, ItemStack> outputs = weightedItemsFromJson(GsonHelper.getAsJsonArray(json, "outputs"));
            return new AnalyzerRecipe(id, ingredient, outputs);
        }

        private static NavigableMap<Double, ItemStack> weightedItemsFromJson(JsonArray outputsArray) {
            NavigableMap<Double, ItemStack> items = new TreeMap<>();
            double total = 0;
            for (int i = 0; i < outputsArray.size(); ++i) {
                JsonObject object = outputsArray.get(i).getAsJsonObject();
                ItemStack item = ShapedRecipe.itemStackFromJson(object);
                if (item.isEmpty()) continue;
                total += GsonHelper.getAsDouble(object, "weight");
                items.put(total, item);
            }
            return items;
        }

        @Override
        public @NotNull AnalyzerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            NavigableMap<Double, ItemStack> outputs = new TreeMap<>();
            int outputSize = buffer.readVarInt();
            for (int i = 0; i < outputSize; i++) {
                outputs.put(buffer.readDouble(), buffer.readItem());
            }
            return new AnalyzerRecipe(recipeId, ingredient, outputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AnalyzerRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeVarInt(recipe.weightedOutputs.size());
            for (Map.Entry<Double, ItemStack> output : recipe.weightedOutputs.entrySet()) {
                buffer.writeDouble(output.getKey());
                buffer.writeItem(output.getValue());
            }
        }
    }

    public static class Builder {

        public final ItemLike item;
        private final NavigableMap<Double, ItemStack> map = new TreeMap<>();
        private double total;

        public Builder(ItemLike item) {
            this.item = item;
        }

        public Builder addOutput(ItemLike itemLike, double weight) {
            total += weight;
            map.put(total, new ItemStack(itemLike));
            return this;
        }

        public AnalyzerRecipe build() {
            return new AnalyzerRecipe(new ResourceLocation(Fossil.MOD_ID, item.toString()), Ingredient.of(item), map);
        }
    }
}
