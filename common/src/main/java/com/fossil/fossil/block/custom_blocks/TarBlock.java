package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.entity.monster.TarSlime;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TarBlock extends ArchitecturyLiquidBlock {
    public TarBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof TarSlime) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01, 0));
            entity.resetFallDistance();
        } else if (entity instanceof LivingEntity living) {
            living.makeStuckInBlock(state, new Vec3(0.1, 1, 0.1));
        } else if (entity instanceof ItemEntity) {
            entity.makeStuckInBlock(state, new Vec3(0.1, 0.1, 0.1));
            if (Mth.floor(entity.position().y + 0.875D) == pos.getY()) {
                entity.playSound(SoundEvents.GENERIC_SWIM, 0.4f, 0.6f + level.random.nextFloat() * 0.4f);
                entity.discard();
            }
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @ExpectPlatform
    public static TarBlock get(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        throw new NotImplementedException();
    }
}
