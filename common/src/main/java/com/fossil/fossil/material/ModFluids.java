package com.fossil.fossil.material;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.item.ModItems;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Fossil.MOD_ID, Registry.FLUID_REGISTRY);
    public static final ArchitecturyFluidAttributes TAR_ATTRIBUTES =
            SimpleArchitecturyFluidAttributes.ofSupplier(() -> ModFluids.TAR_FLOWING, () -> ModFluids.TAR).blockSupplier(() -> ModBlocks.TAR)
                    .bucketItemSupplier(() -> ModItems.TAR_BUCKET).sourceTexture(new ResourceLocation(Fossil.MOD_ID, "block/tar_still"))
                    .flowingTexture(new ResourceLocation(Fossil.MOD_ID, "block/tar_flowing")).temperature(400).density(3000).viscosity(8000).tickDelay(40);
    public static final RegistrySupplier<FlowingFluid> TAR = FLUIDS.register("tar", () -> new TarFluid.Source(TAR_ATTRIBUTES));
    public static final RegistrySupplier<FlowingFluid> TAR_FLOWING = FLUIDS.register("tar_flowing",
            () -> new TarFluid.Flowing(TAR_ATTRIBUTES));

    public static void register() {
        FLUIDS.register();
    }
}
