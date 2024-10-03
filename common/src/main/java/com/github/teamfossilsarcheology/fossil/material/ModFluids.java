package com.github.teamfossilsarcheology.fossil.material;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Fossil.MOD_ID, Registry.FLUID_REGISTRY);

    public static void register() {
        FLUIDS.register();
    }

    public static final ArchitecturyFluidAttributes TAR_ATTRIBUTES =
            SimpleArchitecturyFluidAttributes.ofSupplier(() -> ModFluids.TAR_FLOWING, () -> ModFluids.TAR).blockSupplier(() -> ModBlocks.TAR)
                    .bucketItemSupplier(() -> ModItems.TAR_BUCKET).sourceTexture(Fossil.location("block/tar_still"))
                    .flowingTexture(Fossil.location("block/tar_flowing")).temperature(400).density(3000).viscosity(8000).tickDelay(40);
    public static final RegistrySupplier<FlowingFluid> TAR = FLUIDS.register("tar", () -> new TarFluid.Source(TAR_ATTRIBUTES));
    public static final RegistrySupplier<FlowingFluid> TAR_FLOWING = FLUIDS.register("tar_flowing",
            () -> new TarFluid.Flowing(TAR_ATTRIBUTES));


}
