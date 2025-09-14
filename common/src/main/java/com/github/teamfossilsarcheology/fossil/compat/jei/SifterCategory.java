package com.github.teamfossilsarcheology.fossil.compat.jei;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.recipe.SifterRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class SifterCategory extends MultiOutputCategory<SifterRecipe> {
    public SifterCategory(IGuiHelper guiHelper) {
        super(guiHelper, ModBlocks.SIFTER.get());
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("category.fossil.rei.sifter");
    }

    @Override
    public @NotNull RecipeType<SifterRecipe> getRecipeType() {
        return FossilJEIPlugin.SIFTER;
    }
}
