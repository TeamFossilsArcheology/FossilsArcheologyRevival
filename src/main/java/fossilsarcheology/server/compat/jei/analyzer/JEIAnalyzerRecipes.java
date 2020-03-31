package fossilsarcheology.server.compat.jei.analyzer;

import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeAnalyzer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JEIAnalyzerRecipes {
    public static List<JEIRecipeAnalyzer> getRecipes() {
        List<JEIRecipeAnalyzer> list = new ArrayList<>();
        for (RecipeAnalyzer analyzerRecipe : FAMachineRecipeRegistry.analyzerRecipes) {
            float prevMinus = 0;
            for (Map.Entry<Float, ItemStack> entry : analyzerRecipe.getDisplayMap().entrySet()) {
                list.add(new JEIRecipeAnalyzer(analyzerRecipe.getInput(), entry.getValue(), entry.getKey() - prevMinus));
                prevMinus += entry.getKey();
            }
        }
        return list;
    }
}
