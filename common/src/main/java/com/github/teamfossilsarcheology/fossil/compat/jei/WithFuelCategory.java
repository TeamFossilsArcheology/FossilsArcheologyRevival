
package com.github.teamfossilsarcheology.fossil.compat.jei;

import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public abstract class WithFuelCategory<T extends WithFuelRecipe> implements IRecipeCategory<T> {
    private final IDrawable background;
    private final IDrawable icon;

    protected WithFuelCategory(IGuiHelper guiHelper, ItemLike iconItem, ResourceLocation backgroundTexture) {
        background = guiHelper.createDrawable(backgroundTexture, 40, 16, 96, 55);
        icon = guiHelper.createDrawableItemStack(new ItemStack(iconItem));
    }

    @Override
    public void draw(@NotNull T recipe, @NotNull IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, @NotNull IFocusGroup focuses) {
        int centerX = 96 / 2;
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 5).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.INPUT, centerX - 8, 38).addIngredients(recipe.getFuel());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96 - 16 - 5, 5).addIngredients(Ingredient.of(recipe.getResultItem(null)));
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }
}
