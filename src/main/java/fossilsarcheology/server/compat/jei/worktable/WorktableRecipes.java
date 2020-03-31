package fossilsarcheology.server.compat.jei.worktable;

import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeWorktable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorktableRecipes {
    private static final Random RANDOM = new Random();

    public static List<JEIRecipeWorktable> getRecipes() {
        List<JEIRecipeWorktable> list = new ArrayList<>();
        for (RecipeWorktable worktableRecipe : FAMachineRecipeRegistry.worktableRecipes) {
            if (worktableRecipe.getInput().isItemStackDamageable()) {
                list.add(new JEIRecipeWorktable(createDamagedStack(worktableRecipe.getInput().getItem()), worktableRecipe.getOutput(), worktableRecipe.getFuel()));
            } else {
                list.add(new JEIRecipeWorktable(worktableRecipe.getInput(), worktableRecipe.getOutput(), worktableRecipe.getFuel()));
            }
        }
        return list;
    }

    private static ItemStack createDamagedStack(Item item) {
        ItemStack stack = new ItemStack(item);
        stack.attemptDamageItem((int) (stack.getMaxDamage() * 0.5F), RANDOM, null);
        return stack;
    }
}
