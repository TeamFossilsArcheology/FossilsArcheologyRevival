package com.fossil.fossil.block.custom_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class AmphoraVaseBlock extends VaseBlock {
    private static final VoxelShape SHAPE = Block.box(1, 0, 2, 15, 21, 14);

    public AmphoraVaseBlock(VaseVariant variant) {
        super(variant);
    }

    @Override
    public ResourceLocation getVariantTexture() {
        return variant.getAmphoraTexture();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
