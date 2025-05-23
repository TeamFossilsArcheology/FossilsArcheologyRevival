package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.client.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class VolcanoAshVent extends AirBlock {
    public VolcanoAshVent() {
        super(Properties.copy(Blocks.AIR));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        level.addParticle(ModParticles.VOLCANO_VENT_ASH_EMITTER.get(), pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble(), 0, 0, 0);
    }
}
