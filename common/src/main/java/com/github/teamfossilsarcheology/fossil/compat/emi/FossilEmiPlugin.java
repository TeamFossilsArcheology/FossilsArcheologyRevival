package com.github.teamfossilsarcheology.fossil.compat.emi;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.recipe.*;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;

public class FossilEmiPlugin implements EmiPlugin {
    public static final EmiStack WORKTABLE = EmiStack.of(ModBlocks.WORKTABLE.get());
    public static final EmiStack CULTURE_VAT = EmiStack.of(ModBlocks.CULTURE_VAT.get());
    public static final EmiStack ANALYZER = EmiStack.of(ModBlocks.ANALYZER.get());
    public static final EmiStack SIFTER = EmiStack.of(ModBlocks.SIFTER.get());
    public static final EmiRecipeCategory WORKTABLE_CATEGORY = new EmiRecipeCategory(FossilMod.location("worktable"), WORKTABLE);
    public static final EmiRecipeCategory CULTURE_VAT_CATEGORY = new EmiRecipeCategory(FossilMod.location("culture_vat"), CULTURE_VAT);
    public static final EmiRecipeCategory ANALYZER_CATEGORY = new EmiRecipeCategory(FossilMod.location("analyzer"), ANALYZER);
    public static final EmiRecipeCategory SIFTER_CATEGORY = new EmiRecipeCategory(FossilMod.location("sifter"), SIFTER);

    @Override
    public void register(EmiRegistry registry) {
        // Tell EMI to add a tab for your category
        registry.addCategory(WORKTABLE_CATEGORY);
        registry.addCategory(CULTURE_VAT_CATEGORY);
        registry.addCategory(ANALYZER_CATEGORY);
        registry.addCategory(SIFTER_CATEGORY);

        // Add all the workstations your category uses
        registry.addWorkstation(WORKTABLE_CATEGORY, WORKTABLE);
        registry.addWorkstation(CULTURE_VAT_CATEGORY, CULTURE_VAT);
        registry.addWorkstation(ANALYZER_CATEGORY, ANALYZER);
        registry.addWorkstation(SIFTER_CATEGORY, SIFTER);

        RecipeManager recipeManager = registry.getRecipeManager();
        for (WorktableRecipe recipe : recipeManager.getAllRecipesFor(ModRecipes.WORKTABLE_TYPE.get())) {
            registry.addRecipe(new WorktableEmiRecipe(recipe));
        }
        for (CultureVatRecipe recipe : recipeManager.getAllRecipesFor(ModRecipes.CULTURE_VAT_TYPE.get())) {
            registry.addRecipe(new CultureVatEmiRecipe(recipe));
        }
        for (AnalyzerRecipe recipe : recipeManager.getAllRecipesFor(ModRecipes.ANALYZER_TYPE.get())) {
            registry.addRecipe(new AnalyzerEmiRecipe(recipe));
        }
        for (SifterRecipe recipe : recipeManager.getAllRecipesFor(ModRecipes.SIFTER_TYPE.get())) {
            registry.addRecipe(new SifterEmiRecipe(recipe));
        }

        registry.addRecipe(new AnalyzerEmiRecipe.Builder(ModItemTags.ALL_BONES)
                .addOutput(Items.BONE_MEAL, 30)
                .addOutput(Items.BONE, 35)
                .addOutput(ModItemTags.BONES_DNA, 35).build());
        registry.addRecipe(new AnalyzerEmiRecipe.Builder(ModItemTags.UNCOOKED_MEAT)
                .addOutput(ModItemTags.MEAT_DNA, 100).build());
        registry.addRecipe(new AnalyzerEmiRecipe.Builder(ModItemTags.DINO_EGGS)
                .addOutput(ModItemTags.DINO_DNA, 100).build());
        registry.addRecipe(new AnalyzerEmiRecipe.Builder(ModItemTags.FISH_EGGS)
                .addOutput(ModItemTags.FISH_DNA, 100).build());
        registry.addRecipe(new AnalyzerEmiRecipe.Builder(ModItemTags.EMBRYOS)
                .addOutput(ModItemTags.EMBRYO_DNA, 100).build());
    }
}
