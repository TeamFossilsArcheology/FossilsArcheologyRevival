package com.github.teamfossilsarcheology.fossil.compat.farmers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class FarmersDelightCompat {

    public static void removeConflictingRecipes(MinecraftServer server) {
        var recipes = server.getRecipeManager().getRecipes();
        var lst = recipes.stream().filter(recipe -> recipe.getId().getNamespace().equals(FossilMod.MOD_ID)).toList();
        for (Recipe<?> recipe : lst) {
            System.out.println(recipe.getType());
            System.out.println(recipe.getId());
        }
        if (recipes.removeIf(recipe -> recipe.getId().equals(ModItems.COOKED_EGG.getId()) && recipe instanceof SmeltingRecipe)) {
            FossilMod.LOGGER.info("Farmer's Delight detected and incompatible recipes removed.");
            server.getRecipeManager().replaceRecipes(recipes);
        }
    }
}
