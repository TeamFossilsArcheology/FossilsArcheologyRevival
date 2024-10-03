package com.github.teamfossilsarcheology.fossil.block.custom_blocks.fabric;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FlammableRotatedPillarBlockImpl {

    public static RotatedPillarBlock get(BlockBehaviour.Properties properties) {
        RotatedPillarBlock block = new RotatedPillarBlock(properties);
        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 5);
        return block;
    }

    public static void registerStripped(Block base, Block stripped) {
        StrippableBlockRegistry.register(base, stripped);
    }
}
