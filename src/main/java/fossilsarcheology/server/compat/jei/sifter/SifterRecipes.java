package fossilsarcheology.server.compat.jei.sifter;

import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeAnalyzer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SifterRecipes {

    public static List<RecipeSifter> getRecipes() {
        List<RecipeSifter> list = new ArrayList<>();
        for (RecipeAnalyzer analyzerRecipe : FAMachineRecipeRegistry.sifterRecipes) {
            int prevMinus = 0;
            for (Map.Entry<Float, ItemStack> entry : analyzerRecipe.getDisplayMap().entrySet()) {
                list.add(new RecipeSifter(analyzerRecipe.getInput(), entry.getValue(), entry.getKey().intValue() - prevMinus));
                prevMinus = entry.getKey().intValue();
            }
        }
        return list;
    }
}
