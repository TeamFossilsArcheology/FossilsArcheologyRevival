package com.github.teamfossilsarcheology.fossil.compat.jei;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.client.gui.AnalyzerScreen;
import com.github.teamfossilsarcheology.fossil.recipe.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class FossilJEIPlugin implements IModPlugin {
    public static final RecipeType<AnalyzerRecipe> ANALYZER = RecipeType.create(FossilMod.MOD_ID, "analyzer", AnalyzerRecipe.class);
    public static final RecipeType<CultureVatRecipe> CULTURE_VAT = RecipeType.create(FossilMod.MOD_ID, "culture_vat", CultureVatRecipe.class);
    public static final RecipeType<SifterRecipe> SIFTER = RecipeType.create(FossilMod.MOD_ID, "sifter", SifterRecipe.class);
    public static final RecipeType<WorktableRecipe> WORKTABLE = RecipeType.create(FossilMod.MOD_ID, "worktable", WorktableRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return FossilMod.location("jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new AnalyzerCategory(guiHelper));
        registration.addRecipeCategories(new CultureVatCategory(guiHelper));
        registration.addRecipeCategories(new SifterCategory(guiHelper));
        registration.addRecipeCategories(new WorktableCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        registration.addRecipes(ANALYZER, recipeManager.getAllRecipesFor(ModRecipes.ANALYZER_TYPE.get()));
        registration.addRecipes(CULTURE_VAT, recipeManager.getAllRecipesFor(ModRecipes.CULTURE_VAT_TYPE.get()));
        registration.addRecipes(SIFTER, recipeManager.getAllRecipesFor(ModRecipes.SIFTER_TYPE.get()));
        registration.addRecipes(WORKTABLE, recipeManager.getAllRecipesFor(ModRecipes.WORKTABLE_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ANALYZER.get()), ANALYZER);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CULTURE_VAT.get()), CULTURE_VAT);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SIFTER.get()), SIFTER);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WORKTABLE.get()), WORKTABLE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AnalyzerScreen.class, 80, 21, 23, 11, ANALYZER);
        //registration.addRecipeClickArea(CultureVatScreen.class, 78, 22, 21, 9, FossilRecipeTypes.CULTURE_VAT);
        //registration.addRecipeClickArea(WorktableScreen.class, 75, 19, 26, 16, FossilRecipeTypes.WORKTABLE);
        //registration.addRecipeClickArea(SifterScreen.class, 75, 33, 26, 26, FossilRecipeTypes.SIFTER);
    }

}
