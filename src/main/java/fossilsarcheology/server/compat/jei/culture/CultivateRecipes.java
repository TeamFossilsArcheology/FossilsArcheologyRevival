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

    private static Item[] FUEL = new Item[]{FAItemRegistry.BIOFOSSIL, Items.PORKCHOP, Items.FISH, Items.BEEF, Items.MUTTON, Items.EGG, Items.SLIME_BALL, Items.MILK_BUCKET, Items.RABBIT_FOOT, Items.RABBIT, Item.getItemFromBlock(Blocks.BROWN_MUSHROOM), Item.getItemFromBlock(Blocks.RED_MUSHROOM)};

    private static void addRecipe(List<RecipeCultivate> list, ItemStack input, ItemStack output){
        for(Item item : FUEL){
            list.add(new RecipeCultivate(input, output, new ItemStack(item)));

        }

    }
}
