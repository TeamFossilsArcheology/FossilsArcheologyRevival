package com.github.teamfossilsarcheology.fossil.compat.jei;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.recipe.AnalyzerRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class AnalyzerCategory extends MultiOutputCategory<AnalyzerRecipe> {
    public AnalyzerCategory(IGuiHelper guiHelper) {
        super(guiHelper, ModBlocks.ANALYZER.get());
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("category.fossil.rei.analyzer");
    }

    @Override
    public @NotNull RecipeType<AnalyzerRecipe> getRecipeType() {
        return FossilJEIPlugin.ANALYZER;
    }
}
