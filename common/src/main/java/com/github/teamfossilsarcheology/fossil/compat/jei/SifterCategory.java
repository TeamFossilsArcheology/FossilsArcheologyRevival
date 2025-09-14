package com.github.teamfossilsarcheology.fossil.compat.jei;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.recipe.SifterRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

public class SifterCategory extends MultiOutputCategory<SifterRecipe> {
    public SifterCategory(IGuiHelper guiHelper) {
        super(guiHelper, ModBlocks.SIFTER.get());
    }

    @Override
    public @NotNull Component getTitle() {
        return new TranslatableComponent("category.fossil.rei.sifter");
    }

    @Override
    public @NotNull RecipeType<SifterRecipe> getRecipeType() {
        return FossilJEIPlugin.SIFTER;
    }

    @Override
    public Class<? extends SifterRecipe> getRecipeClass() {
        return SifterRecipe.class;
    }
}
