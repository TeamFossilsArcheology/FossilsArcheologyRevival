package fossilsarcheology.server.compat.jei.sifter;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class SifterRecipeWrapper implements IRecipeWrapper {

    private RecipeSifter recipeSifter;

    public SifterRecipeWrapper(RecipeSifter recipeSifter) {
        this.recipeSifter = recipeSifter;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, recipeSifter.getInput());
        ingredients.setOutput(ItemStack.class, recipeSifter.getOutput());
    }

    public RecipeSifter getRecipeSifter() {
        return recipeSifter;
    }
}
