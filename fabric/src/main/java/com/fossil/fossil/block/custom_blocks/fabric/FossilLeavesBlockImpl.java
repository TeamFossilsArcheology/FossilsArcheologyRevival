package com.fossil.fossil.block.custom_blocks.fabric;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilLeavesBlockImpl {

    public static LeavesBlock get(BlockBehaviour.Properties properties) {
        LeavesBlock block = new LeavesBlock(properties);
        FlammableBlockRegistry.getDefaultInstance().add(block, 60, 30);
        return block;
    }
}
