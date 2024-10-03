package com.github.teamfossilsarcheology.fossil.block.custom_blocks.fabric;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.FossilLeavesBlock;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilLeavesBlockImpl {

    public static FossilLeavesBlock get(BlockBehaviour.Properties properties) {
        FossilLeavesBlock block = new FossilLeavesBlock(properties);
        FlammableBlockRegistry.getDefaultInstance().add(block, 60, 30);
        return block;
    }
}
