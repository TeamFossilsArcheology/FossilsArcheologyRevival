package fossilsarcheology.server.compat.jei.worktable;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WorktableRecipeWrapper implements IRecipeWrapper {

    private JEIRecipeWorktable recipeWorktable;

    public WorktableRecipeWrapper(JEIRecipeWorktable recipeWorktable) {
        this.recipeWorktable = recipeWorktable;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> list = new ArrayList<>();
        list.add(recipeWorktable.getInput());
        list.add(recipeWorktable.getFuel());
        ingredients.setInputs(ItemStack.class, list);
        ingredients.setOutput(ItemStack.class, recipeWorktable.getOutput());
    }

    public JEIRecipeWorktable getRecipeWorktable() {
        return recipeWorktable;
    }
}
