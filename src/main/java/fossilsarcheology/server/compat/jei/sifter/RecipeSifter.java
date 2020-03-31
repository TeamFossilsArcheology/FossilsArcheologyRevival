package fossilsarcheology.server.compat.jei.sifter;

import net.minecraft.item.ItemStack;

public class RecipeSifter {

    private ItemStack input;
    private ItemStack output;
    private int chance;

    public RecipeSifter(ItemStack input, ItemStack output, int chance) {
        this.input = input;
        this.output = output;
        this.chance = chance;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getOutputChance() {
        return chance;
    }
}
