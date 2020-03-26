package fossilsarcheology.server.compat.jei.tar;

import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TarBucketRecipeMaker {

    public static List<TarBucketRecipeWrapper> getTarBucketRecipes() {
        List<TarBucketRecipeWrapper> recipes = new ArrayList<>();
        recipes.add(new TarBucketRecipeWrapper());
        return recipes;
    }

    private TarBucketRecipeMaker() {

    }

}
