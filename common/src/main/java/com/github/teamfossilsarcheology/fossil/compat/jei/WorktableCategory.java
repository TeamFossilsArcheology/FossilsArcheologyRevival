package com.github.teamfossilsarcheology.fossil.compat.jei;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.client.gui.WorktableScreen;
import com.github.teamfossilsarcheology.fossil.recipe.WorktableRecipe;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WorktableCategory extends WithFuelCategory<WorktableRecipe> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/workbench.png");
    protected final IDrawableStatic staticFuel;
    protected final IDrawableAnimated animatedFuel;
    protected final IDrawableStatic staticProgress;
    protected final IDrawableAnimated animatedProgress;

    public WorktableCategory(IGuiHelper guiHelper) {
        super(guiHelper, ModBlocks.WORKTABLE.get(), TEXTURE);
        staticFuel = guiHelper.createDrawable(TEXTURE, 176, 0, WorktableScreen.FUEL_WIDTH, WorktableScreen.FUEL_HEIGHT);
        animatedFuel = guiHelper.createAnimatedDrawable(staticFuel, 300, IDrawableAnimated.StartDirection.TOP, true);
        staticProgress = guiHelper.createDrawable(TEXTURE, 176, WorktableScreen.FUEL_HEIGHT, WorktableScreen.PROGRESS_WIDTH, WorktableScreen.PROGRESS_HEIGHT);
        animatedProgress = guiHelper.createAnimatedDrawable(staticProgress, 1000, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(@NotNull WorktableRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        animatedFuel.draw(guiGraphics, 41, 20);
        animatedProgress.draw(guiGraphics, 36, 4);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("category.fossil.rei.worktable");
    }

    @Override
    public @NotNull RecipeType<WorktableRecipe> getRecipeType() {
        return FossilJEIPlugin.WORKTABLE;
    }
}
