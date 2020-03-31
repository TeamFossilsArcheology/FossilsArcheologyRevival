package fossilsarcheology.server.compat.jei.culture;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CultivateRecipeWrapper implements IRecipeWrapper {

    private RecipeCultivate recipeCultivate;

    public CultivateRecipeWrapper(RecipeCultivate recipeCultivate) {
        this.recipeCultivate = recipeCultivate;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> list = new ArrayList<>();
        list.add(recipeCultivate.getInput());
        list.add(recipeCultivate.getFuel());
        ingredients.setInputs(ItemStack.class, list);
        ingredients.setOutput(ItemStack.class, recipeCultivate.getOutput());
    }

    public RecipeCultivate getRecipeCultivate() {
        return recipeCultivate;
    }
}
