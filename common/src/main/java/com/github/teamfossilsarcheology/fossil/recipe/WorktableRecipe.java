package com.github.teamfossilsarcheology.fossil.recipe;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public class WorktableRecipe extends WithFuelRecipe {
    protected WorktableRecipe(ResourceLocation location, Ingredient input, Ingredient fuel, ItemStack output, int duration) {
        super(location, input, fuel, output, duration);
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.WORKTABLE.get());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<WorktableRecipe> {
        public static final Type INSTANCE = new Type();

        private Type() {
        }
    }

    public static class Serializer extends WithFuelRecipeSerializer<WorktableRecipe> {
        public static final Serializer INSTANCE = new Serializer(WorktableRecipe::new);

        public Serializer(Constructor<WorktableRecipe> constructor) {
            super(constructor);
        }
    }
}
