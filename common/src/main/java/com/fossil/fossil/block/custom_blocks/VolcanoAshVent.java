package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.client.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class VolcanoAshVent extends AirBlock {
    public VolcanoAshVent() {
        super(Properties.copy(Blocks.AIR).randomTicks());
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        level.addParticle(ModParticles.VOLCANO_VENT_ASH_EMITTER.get(), pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble(), 0, 0, 0);
    }
}
