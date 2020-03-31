package fossilsarcheology.server.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FAFluidRegistry {

    public static final Fluid TAR_FLUID = new Fluid("tar", new ResourceLocation("fossil", "blocks/tar"), new ResourceLocation("fossil", "blocks/tar_flowing")).setDensity(3000).setTemperature(400).setViscosity(8000);
    public static final Material TAR_MATERIAL = new MaterialTar();

    public static void register() {
        FluidRegistry.registerFluid(TAR_FLUID);
        FluidRegistry.addBucketForFluid(TAR_FLUID);
    }
}