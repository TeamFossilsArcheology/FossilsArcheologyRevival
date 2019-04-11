package fossilsarcheology.server.recipe;

import net.minecraft.item.ItemStack;

public class RecipeWorktable {

    private ItemStack input;
    private ItemStack output;
    private ItemStack fuel;
    public RecipeWorktable(ItemStack input, ItemStack output, ItemStack fuel){
        this.input = input;
        this.output = output;
        this.fuel = fuel;
    }

    public ItemStack getInput(){
        return input;
    }

    public ItemStack getOutput(){
        return output;
    }

    public ItemStack getFuel(){
        return fuel;
    }


}
