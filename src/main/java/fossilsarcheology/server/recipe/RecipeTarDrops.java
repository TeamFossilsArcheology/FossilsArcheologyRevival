package fossilsarcheology.server.recipe;

import fossilsarcheology.server.block.FAFluidRegistry;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RecipeTarDrops extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private int number = 1000;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        int bucketCount = 0;
        for (int i = 0; i < 9; i++) {
            if (inv.getStackInSlot(i).getItem() == ForgeModContainer.getInstance().universalBucket) {
                FluidStack fluid = ForgeModContainer.getInstance().universalBucket.getFluid(inv.getStackInSlot(i));
                if (fluid.getFluid() == FAFluidRegistry.TAR_FLUID) {
                    bucketCount++;
                }
            }
        }
        return bucketCount == 1;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack stack = new ItemStack(FAItemRegistry.TARDROP, 4);
        return stack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        ItemStack stack = new ItemStack(FAItemRegistry.TARDROP, 4);
        return stack;
    }
}
