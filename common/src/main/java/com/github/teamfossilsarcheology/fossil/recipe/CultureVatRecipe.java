package com.github.teamfossilsarcheology.fossil.recipe;

import com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public class CultureVatRecipe extends WithFuelRecipe {
    protected CultureVatRecipe(ResourceLocation location, Ingredient input, Ingredient fuel, ItemStack output, int duration, int fuelDuration) {
        super(location, input, fuel, output, duration, fuelDuration);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CultureVatRecipe> {
        public static final Type INSTANCE = new Type();

        private Type() {
        }
    }

    public static class Serializer extends WithFuelRecipeSerializer<CultureVatRecipe> {
        public static final Serializer INSTANCE = new Serializer(CultureVatRecipe::new);

        public Serializer(Constructor<CultureVatRecipe> constructor) {
            super(constructor);
        }

        @Override
        int defaultDuration() {
            return CultureVatMenu.CULTIVATION_DURATION;
        }

        @Override
        int defaultFuelDuration() {
            return CultureVatMenu.DEFAULT_FUEL_DURATION;
        }
    }
}
