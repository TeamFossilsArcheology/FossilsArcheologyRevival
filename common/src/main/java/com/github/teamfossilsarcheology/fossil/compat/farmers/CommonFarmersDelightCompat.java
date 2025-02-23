package com.github.teamfossilsarcheology.fossil.compat.farmers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import dev.architectury.platform.Platform;
import net.minecraft.server.MinecraftServer;

/**
 * Safe to call if mob not loaded
 */
public class CommonFarmersDelightCompat {

    public static void removeConflictingRecipes(MinecraftServer server) {
        var recipes = server.getRecipeManager().getRecipes();
        if (Platform.isModLoaded(ModConstants.FARMERS)) {
            FossilMod.LOGGER.info("Farmer's Delight detected and incompatible recipes removed.");
            recipes.removeIf(recipe -> {
                String name = recipe.getId().getPath();
                return name.startsWith("egg_to_cooked_egg") || name.startsWith("dino_eggs_to_cooked_egg");
            });
        } else {
            recipes.removeIf(recipe -> {
                String name = recipe.getId().getPath();
                return name.startsWith("fa_eggs_to_fried_egg");
            });
        }
        server.getRecipeManager().replaceRecipes(recipes);
    }
}
