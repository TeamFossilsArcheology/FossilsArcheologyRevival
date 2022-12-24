package com.fossil.fossil.block.custom_blocks.fabric;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CordaitesLeavesImpl {

    public static Block get(BlockBehaviour.Properties properties) {
        Block block = new LeavesBlock(properties);
        FlammableBlockRegistry.getDefaultInstance().add(block, 60, 30);
        return block;
    }
}
