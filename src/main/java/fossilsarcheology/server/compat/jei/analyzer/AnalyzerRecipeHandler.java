package fossilsarcheology.server.compat.jei.analyzer;

import fossilsarcheology.server.compat.jei.FAJEIPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

@SuppressWarnings("deprecation")
public class AnalyzerRecipeHandler implements IRecipeHandler<AnalyzerRecipeWrapper> {

    @Override
    public Class getRecipeClass() {
        return JEIRecipeAnalyzer.class;
    }

    @Override
    public String getRecipeCategoryUid(AnalyzerRecipeWrapper recipe) {
        return FAJEIPlugin.ANALYZER_UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(AnalyzerRecipeWrapper recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(AnalyzerRecipeWrapper recipe) {
        return true;
    }
}
