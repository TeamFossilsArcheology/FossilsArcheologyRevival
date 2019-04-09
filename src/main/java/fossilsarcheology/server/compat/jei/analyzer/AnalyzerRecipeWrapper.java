package fossilsarcheology.server.compat.jei.analyzer;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class AnalyzerRecipeWrapper implements IRecipeWrapper {

    private JEIRecipeAnalyzer recipeAnalyzer;

    public AnalyzerRecipeWrapper(JEIRecipeAnalyzer recipeAnalyzer){
       this.recipeAnalyzer = recipeAnalyzer;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, recipeAnalyzer.getInput());
        ingredients.setOutput(ItemStack.class,recipeAnalyzer.getOutput());
    }

    public JEIRecipeAnalyzer getRecipeAnalyzer() {
        return recipeAnalyzer;
    }
}
