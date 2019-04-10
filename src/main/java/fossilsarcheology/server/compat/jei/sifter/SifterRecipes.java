package fossilsarcheology.server.compat.jei.sifter;

import fossilsarcheology.server.block.entity.TileEntitySifter;
import fossilsarcheology.server.compat.jei.analyzer.JEIRecipeAnalyzer;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeAnalyzer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SifterRecipes {

    public static List<RecipeSifter> getRecipes(){
        List<RecipeSifter> list = new ArrayList<>();
        for(RecipeAnalyzer analyzerRecipe : FAMachineRecipeRegistry.sifterRecipes){
            int prevMinus = 0;
            for(Map.Entry<Float, ItemStack> entry : analyzerRecipe.getDisplayMap().entrySet()){
                list.add(new RecipeSifter(analyzerRecipe.getInput(), entry.getValue(), entry.getKey().intValue() - prevMinus));
                prevMinus = entry.getKey().intValue();
            }
        }
        return list;
    }
}
