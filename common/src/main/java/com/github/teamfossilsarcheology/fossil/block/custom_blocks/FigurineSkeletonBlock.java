package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FigurineSkeletonBlock extends FigurineBlock {
    private static final VoxelShape SHAPE = Block.box(2.5, 0, 2.5, 13.5, 18, 13.5);
    private static final VoxelShape SHAPE_DESTROYED = Block.box(2.5, 0, 2.5, 13.5, 14, 13.5);

    public FigurineSkeletonBlock(FigurineVariant variant) {
        super(variant);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return variant == FigurineVariant.DESTROYED ? SHAPE_DESTROYED : SHAPE;
    }

    @Override
    public ResourceLocation getTexture() {
        return variant.getSkeletonTexture();
    }
}
