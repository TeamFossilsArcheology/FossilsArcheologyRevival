package com.github.teamfossilsarcheology.fossil.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class WithFuelRecipe implements Recipe<WithFuelRecipe.ContainerWithAnyFuel> {
    private final ResourceLocation location;
    private final Ingredient input;
    private final Ingredient fuel;
    private final ItemStack result;
    private final int duration;
    private final int fuelDuration;

    protected WithFuelRecipe(ResourceLocation location, Ingredient input, Ingredient fuel, ItemStack result, int duration, int fuelDuration) {
        this.location = location;
        this.input = input;
        this.fuel = fuel;
        this.result = result;
        this.duration = duration;
        this.fuelDuration = fuelDuration;
    }

    @Override
    public boolean matches(ContainerWithAnyFuel container, Level level) {
        return input.test(container.getItem(0)) && (container.anyFuel || fuel.test(container.getItem(1)));
    }

    @Override
    public @NotNull ItemStack assemble(ContainerWithAnyFuel container) {
        ItemStack itemStack = result.copy();
        CompoundTag compoundTag = container.getItem(0).getTag();
        if (compoundTag != null) {
            itemStack.setTag(compoundTag.copy());
        }

        return itemStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(input);
        return nonNullList;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return location;
    }

    public boolean isFuel(ItemStack itemStack) {
        return fuel.test(itemStack);
    }

    public Ingredient getInput() {
        return input;
    }

    public Ingredient getFuel() {
        return fuel;
    }

    public int getDuration() {
        return duration;
    }

    public int getFuelDuration() {
        return fuelDuration;
    }

    public static class ContainerWithAnyFuel extends SimpleContainer {
        public final boolean anyFuel;

        public ContainerWithAnyFuel(boolean anyFuel, ItemStack... items) {
            super(items);
            this.anyFuel = anyFuel;
        }

        public ContainerWithAnyFuel(ItemStack input, ItemStack fuel) {
            super(input, fuel);
            this.anyFuel = false;
        }
    }

    public static abstract class WithFuelRecipeSerializer<T extends WithFuelRecipe> implements RecipeSerializer<T> {
        protected final Constructor<T> constructor;

        protected WithFuelRecipeSerializer(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        abstract int defaultDuration();

        abstract int defaultFuelDuration();

        @Override
        public @NotNull T fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            JsonElement jsonElement = GsonHelper.getAsJsonObject(serializedRecipe, "input");
            Ingredient input = Ingredient.fromJson(jsonElement);
            jsonElement = GsonHelper.getAsJsonObject(serializedRecipe, "fuel");
            Ingredient fuel = Ingredient.fromJson(jsonElement);
            String result = GsonHelper.getAsString(serializedRecipe, "result");
            ItemStack output = new ItemStack(
                    BuiltInRegistries.ITEM.getOptional(new ResourceLocation(result)).orElseThrow(() -> new IllegalStateException("Item: " + result + " does not exist")));
            int duration = GsonHelper.getAsInt(serializedRecipe, "duration", defaultDuration());
            int fuelDuration = GsonHelper.getAsInt(serializedRecipe, "fuel_duration", defaultFuelDuration());
            return constructor.construct(recipeId, input, fuel, output, duration, fuelDuration);
        }

        @Override
        public @NotNull T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            Ingredient fuel = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int duration = buffer.readInt();
            int fuelDuration = buffer.readInt();
            return constructor.construct(recipeId, input, fuel, output, duration, fuelDuration);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WithFuelRecipe recipe) {
            recipe.input.toNetwork(buffer);
            recipe.fuel.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeInt(recipe.duration);
            buffer.writeInt(recipe.fuelDuration);
        }

        @FunctionalInterface
        public interface Constructor<R> {
            R construct(ResourceLocation recipeId, Ingredient input, Ingredient fuel, ItemStack output, int duration, int fuelDuration);
        }
    }
}
