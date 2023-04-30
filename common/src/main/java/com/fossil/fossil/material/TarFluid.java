package com.fossil.fossil.material;

import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.monster.TarSlime;
import com.fossil.fossil.sounds.ModSounds;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class TarFluid {

    private static void animateParticle(Level level, BlockPos pos, FluidState state, Random random) {
        if (random.nextInt(3) == 0) {
            double posX = pos.getX() + random.nextDouble();
            double posY = pos.getY() + Mth.absMax(1 - state.getHeight(level, pos), 0.25f);
            double posZ = pos.getZ() + random.nextDouble();
            double speedX = (random.nextDouble() - 0.5D) * 0.3D;
            double speedY = 0.3D * random.nextDouble() + 0.2D;
            double speedZ = (random.nextDouble() - 0.5D) * 0.3D;
            level.addParticle(ModBlockEntities.TAR_BUBBLE.get(), posX, posY, posZ, speedX, speedY, speedZ);
        }
    }

    private static void trySpawnTarSlime(Level level, BlockPos pos, boolean isSource) {
        int tarSlimeSpawnRate = FossilConfig.getInt("tarSlimesSpawnRate");
        if (level.getDifficulty() != Difficulty.PEACEFUL && FossilConfig.isEnabled("spawnTarSlimes") &&
                level.random.nextInt(isSource ? tarSlimeSpawnRate : tarSlimeSpawnRate * 15) == 0) {
            double spawnRange = 16;
            int k = level.getEntities(ModEntities.TAR_SLIME.get(), new AABB(pos, pos.offset(1, 1, 1)).inflate(spawnRange), Entity::isAlive).size();
            if (k < 6) {
                TarSlime slime = ModEntities.TAR_SLIME.get().create(level);
                slime.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null);
                slime.moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
                level.addFreshEntity(slime);
            }
        }
    }

    public static class Source extends ArchitecturyFlowingFluid.Source {

        public Source(ArchitecturyFluidAttributes attributes) {
            super(attributes);
        }

        @Override
        protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
            return direction == Direction.DOWN && this.isSame(fluid);
        }

        @Override
        public void tick(Level level, BlockPos pos, FluidState state) {
            super.tick(level, pos, state);
            trySpawnTarSlime(level, pos, isSource(state));
        }

        @Override
        protected void animateTick(Level level, BlockPos pos, FluidState state, Random random) {
            animateParticle(level, pos, state, random);
        }
    }

    public static class Flowing extends ArchitecturyFlowingFluid.Flowing {

        public Flowing(ArchitecturyFluidAttributes attributes) {
            super(attributes);
        }

        @Override
        protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
            return direction == Direction.DOWN && this.isSame(fluid);
        }

        @Override
        public void tick(Level level, BlockPos pos, FluidState state) {
            super.tick(level, pos, state);
            trySpawnTarSlime(level, pos, isSource(state));
        }

        @Override
        protected void animateTick(Level level, BlockPos pos, FluidState state, Random random) {
            if (!state.getValue(FALLING)) {
                if (random.nextInt(64) == 0) {
                    level.playLocalSound((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5,
                            new SoundEvent(ModSounds.TAR), SoundSource.BLOCKS, 0.3f, random.nextFloat() * 0.4f + 0.8f, false);
                }
            }
            animateParticle(level, pos, state, random);
        }


    }
}
