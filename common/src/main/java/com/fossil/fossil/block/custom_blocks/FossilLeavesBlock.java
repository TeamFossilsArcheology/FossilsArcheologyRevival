package com.fossil.fossil.block.custom_blocks;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilLeavesBlock {
    @ExpectPlatform
    public static LeavesBlock get(BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }
}
