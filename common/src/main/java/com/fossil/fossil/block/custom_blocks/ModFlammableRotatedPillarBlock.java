package com.fossil.fossil.block.custom_blocks;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModFlammableRotatedPillarBlock {
    @ExpectPlatform
    public static RotatedPillarBlock get(BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }
}