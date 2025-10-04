package com.github.teamfossilsarcheology.fossil.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public abstract class MultiOutputAndSlotsRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    protected final Ingredient input;
    private final NavigableMap<Double, ItemStack> weightedOutputs;

    protected MultiOutputAndSlotsRecipe(ResourceLocation resourceLocation, Ingredient input, NavigableMap<Double, ItemStack> weightedOutputs) {
        this.id = resourceLocation;
        this.input = input;
        this.weightedOutputs = weightedOutputs;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(input);
        return nonNullList;
    }

    public Ingredient getInput() {
        return input;
    }

    public NavigableMap<Double, ItemStack> getWeightedOutputs() {
        return weightedOutputs;
    }

    @Override
    public boolean matches(Container container, Level level) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (matches(container, i)) {
                return true;
            }
        }
        return false;
    }

    private boolean matches(Container container, int slot) {
        ItemStack itemStack = container.getItem(slot);
        if (itemStack.isEmpty()) return false;
        return input.test(itemStack);
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        if (container instanceof BlockEntity blockEntity) {
            return weightedOutputs.higherEntry(blockEntity.getLevel().random.nextDouble() * weightedOutputs.lastKey()).getValue().copy();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public static class Serializer<T extends MultiOutputAndSlotsRecipe> implements RecipeSerializer<T> {
        protected final Constructor<T> constructor;

        protected Serializer(Constructor<T> constructor) {
            this.constructor = constructor;
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
        public @NotNull T fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonElement jsonelement = GsonHelper.isArrayNode(json, "input") ? GsonHelper.getAsJsonArray(json,
                    "input") : GsonHelper.getAsJsonObject(json, "input");
            Ingredient input = Ingredient.fromJson(jsonelement);
            NavigableMap<Double, ItemStack> outputs = weightedItemsFromJson(GsonHelper.getAsJsonArray(json, "outputs"));
            return constructor.construct(recipeId, input, outputs);
        }

        @Override
        public @NotNull T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            NavigableMap<Double, ItemStack> outputs = new TreeMap<>();
            int outputSize = buffer.readVarInt();
            for (int i = 0; i < outputSize; i++) {
                outputs.put(buffer.readDouble(), buffer.readItem());
            }
            return constructor.construct(recipeId, input, outputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MultiOutputAndSlotsRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeVarInt(recipe.weightedOutputs.size());
            for (Map.Entry<Double, ItemStack> output : recipe.weightedOutputs.entrySet()) {
                buffer.writeDouble(output.getKey());
                buffer.writeItem(output.getValue());
            }
        }

        @FunctionalInterface
        public interface Constructor<R> {
            R construct(ResourceLocation recipeId, Ingredient input, NavigableMap<Double, ItemStack> outputs);
        }
    }
}
