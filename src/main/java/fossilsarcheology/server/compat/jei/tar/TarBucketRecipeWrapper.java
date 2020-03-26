package fossilsarcheology.server.compat.jei.tar;

import fossilsarcheology.server.block.FAFluidRegistry;
import fossilsarcheology.server.item.FAItemRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.Arrays;
import java.util.List;

public class TarBucketRecipeWrapper implements IShapedCraftingRecipeWrapper {
    private final ItemStack output;

    public TarBucketRecipeWrapper() {
        this.output = new ItemStack(FAItemRegistry.TARDROP, 4);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack filledBucket = FluidUtil.getFilledBucket(new FluidStack(FAFluidRegistry.TAR_FLUID, 1000));
        ingredients.setInput(ItemStack.class, filledBucket);
        ingredients.setOutput(ItemStack.class, this.output);
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }
}
