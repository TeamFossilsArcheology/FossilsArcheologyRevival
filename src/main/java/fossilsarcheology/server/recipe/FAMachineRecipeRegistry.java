package fossilsarcheology.server.recipe;

import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.FossilSeedsItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class FAMachineRecipeRegistry {
    public static List<RecipeAnalyzer> analyzerRecipes = new ArrayList<>();
    public static void init() {
        RecipeAnalyzer plantFossil = new RecipeAnalyzer(FAItemRegistry.PLANT_FOSSIL)
                .addOutput(new ItemStack(Blocks.SAND, 2), 35F)
                .addOutput(new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL, 1), 5F)
                .addOutput(new ItemStack(FAItemRegistry.CALAMITES_SAPLING_FOSSIL, 1), 5F)
                .addOutput(new ItemStack(Items.DYE, 1, 2), 20F)
                .addOutput(new ItemStack(FAItemRegistry.FERN_SEED, 1), 5F);
        float seedWeight = (100F - plantFossil.getTotalWeight()) / FossilSeedsItem.fossilSeeds.length;
        for(int i = 0; i < FossilSeedsItem.fossilSeeds.length; i++){
            plantFossil.addOutput(new ItemStack(FAItemRegistry.FOSSIL_SEED, 1, i), seedWeight);
        }
        register(plantFossil);

    }

    private static void register(RecipeAnalyzer recipe){
        analyzerRecipes.add(recipe);
    }

    public static RecipeAnalyzer getAnalyzerRecipeForItem(ItemStack stack){
        for(RecipeAnalyzer recipe : analyzerRecipes){
            if(OreDictionary.itemMatches(recipe.getInput(), stack, false)){
                return recipe;
            }
        }
        return null;
    }
}
