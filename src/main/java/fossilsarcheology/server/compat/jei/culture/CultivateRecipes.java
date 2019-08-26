package fossilsarcheology.server.compat.jei.culture;

import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CultivateRecipes {

    public static List<RecipeCultivate> getRecipes() {
        List<RecipeCultivate> list = new ArrayList<>();
        for(Map.Entry<ItemStack, ItemStack> recipe : FAMachineRecipeRegistry.cultivateRecipes.entrySet()){
            addRecipe(list, recipe.getKey(), recipe.getValue());
        }
        return list;
    }

    public static int getFuelValue(ItemStack stack) {
        for(Map.Entry<ItemStack, Integer> values : FAMachineRecipeRegistry.cultivateFuelValues.entrySet()){
            if(values.getKey().isItemEqual(stack)){
                return values.getValue();
            }
        }
        return 0;
    }

    private static void addRecipe(List<RecipeCultivate> list, ItemStack input, ItemStack output){
        for(ItemStack item : FAMachineRecipeRegistry.cultivateFuelValues.keySet()){
            list.add(new RecipeCultivate(input, output, item));
        }

    }
}
