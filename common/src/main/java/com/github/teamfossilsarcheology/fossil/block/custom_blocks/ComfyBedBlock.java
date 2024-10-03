package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Applies {@link com.github.teamfossilsarcheology.fossil.world.effect.ModEffects#COMFY_BED}
 */
public class ComfyBedBlock extends BedBlock {
    protected static final VoxelShape BASE = Block.box(0, 1, 0, 16, 9, 16);
    protected static final VoxelShape LEG_NORTH_WEST = Block.box(0, 0, 0, 3, 3, 3);
    protected static final VoxelShape LEG_SOUTH_WEST = Block.box(0, 0, 13, 3, 3, 16);
    protected static final VoxelShape LEG_NORTH_EAST = Block.box(13, 0, 0, 16, 3, 3);
    protected static final VoxelShape LEG_SOUTH_EAST = Block.box(13, 0, 13, 16, 3, 16);
    protected static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, LEG_NORTH_WEST, LEG_NORTH_EAST);
    protected static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, LEG_SOUTH_WEST, LEG_SOUTH_EAST);
    protected static final VoxelShape WEST_SHAPE = Shapes.or(BASE, LEG_NORTH_WEST, LEG_SOUTH_WEST);
    protected static final VoxelShape EAST_SHAPE = Shapes.or(BASE, LEG_NORTH_EAST, LEG_SOUTH_EAST);

    public ComfyBedBlock(Properties properties) {
        super(DyeColor.WHITE, properties);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction direction) {
        return adjacentBlockState.is(this);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide) {
            BlockPos blockPos = pos.relative(state.getValue(FACING));
            level.setBlock(blockPos, state.setValue(PART, BedPart.HEAD), 3);
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = BedBlock.getConnectedDirection(state).getOpposite();
        return switch (direction) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> EAST_SHAPE;
        };
    }
}
