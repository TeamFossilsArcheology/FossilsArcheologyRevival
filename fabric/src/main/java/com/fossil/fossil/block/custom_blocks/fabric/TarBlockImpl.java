package com.fossil.fossil.block.custom_blocks.fabric;

import com.fossil.fossil.block.custom_blocks.TarBlock;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class TarBlockImpl {
    public static TarBlock get(Supplier<? extends FlowingFluid> fluid, BlockBehaviour.Properties properties) {
        TarBlock block = new TarBlock(fluid, properties);
        FlammableBlockRegistry.getDefaultInstance().add(block, 30, 30);
        return block;
    }
}
