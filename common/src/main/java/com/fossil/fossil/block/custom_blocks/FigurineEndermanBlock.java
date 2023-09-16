package com.fossil.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FigurineEndermanBlock extends FigurineBlock {
    private static final VoxelShape SHAPE = Block.box(2.5, 0, 2.5, 13.5, 21, 13.5);
    public FigurineEndermanBlock(FigurineVariant variant) {
        super(variant);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public ResourceLocation getTexture() {
        return variant.getEndermanTexture();
    }
}
