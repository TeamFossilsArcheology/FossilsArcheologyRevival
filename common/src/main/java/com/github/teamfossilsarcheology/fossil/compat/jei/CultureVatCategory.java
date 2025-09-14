package com.github.teamfossilsarcheology.fossil.compat.jei;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.client.gui.CultureVatScreen;
import com.github.teamfossilsarcheology.fossil.recipe.CultureVatRecipe;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CultureVatCategory extends WithFuelCategory<CultureVatRecipe> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/culture_vat.png");
    protected final IDrawableStatic staticFuel;
    protected final IDrawableAnimated animatedFuel;
    protected final IDrawableStatic staticProgress;
    protected final IDrawableAnimated animatedProgress;
    public CultureVatCategory(IGuiHelper guiHelper) {
        super(guiHelper, ModBlocks.CULTURE_VAT.get(), TEXTURE);
        staticFuel = guiHelper.createDrawable(TEXTURE, 176, 0, CultureVatScreen.FUEL_WIDTH, CultureVatScreen.FUEL_HEIGHT);
        animatedFuel = guiHelper.createAnimatedDrawable(staticFuel, 1000, IDrawableAnimated.StartDirection.TOP, true);
        staticProgress = guiHelper.createDrawable(TEXTURE, 176, CultureVatScreen.FUEL_HEIGHT, CultureVatScreen.PROGRESS_WIDTH, CultureVatScreen.PROGRESS_HEIGHT);
        animatedProgress = guiHelper.createAnimatedDrawable(staticProgress, 300, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(@NotNull CultureVatRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        animatedFuel.draw(guiGraphics, 42, 21);
        animatedProgress.draw(guiGraphics, 38, 6);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("category.fossil.rei.culture_vat");
    }

    @Override
    public @NotNull RecipeType<CultureVatRecipe> getRecipeType() {
        return FossilJEIPlugin.CULTURE_VAT;
    }
}
