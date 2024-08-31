package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.Fossil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class VoluteVaseBlock extends VaseBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 18, 16);
    private final MutableComponent name;

    public VoluteVaseBlock(DyeColor color) {
        super();
        this.name = new TranslatableComponent("block." + Fossil.MOD_ID + ".vase_volute_color", new TranslatableComponent("color.minecraft." + color.getName()));
    }

    public VoluteVaseBlock(VaseVariant vaseVariant) {
        super();
        this.name = new TranslatableComponent("block." + Fossil.MOD_ID + ".vase_volute_" + vaseVariant.getSerializedName());
    }

    @Override
    public @NotNull MutableComponent getName() {
        return name;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }
}
