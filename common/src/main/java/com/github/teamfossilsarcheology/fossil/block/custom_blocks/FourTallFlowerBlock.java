package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.tags.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FourTallFlowerBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty LAYER = IntegerProperty.create("layer", 0, 3);
    private final VoxelShape shape;

    public FourTallFlowerBlock(VoxelShape shape) {
        super(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().noOcclusion().sound(SoundType.GRASS).offsetType(OffsetType.XZ));
        this.shape = shape;
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYER, 0));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || (state.is(Blocks.SAND) && defaultBlockState().is(ModBlockTags.PLANTABLE_ON_SAND));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return shape.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockPos.getY() < level.getMaxBuildHeight() - 3 && level.getBlockState(blockPos.above()).canBeReplaced(context) && level.getBlockState(
                blockPos.above(2)).canBeReplaced(context) && level.getBlockState(blockPos.above(3)).canBeReplaced(context)) {
            return super.getStateForPlacement(context);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), defaultBlockState().setValue(LAYER, 1), 3);
        level.setBlock(pos.above(2), defaultBlockState().setValue(LAYER, 2), 3);
        level.setBlock(pos.above(3), defaultBlockState().setValue(LAYER, 3), 3);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        switch (state.getValue(LAYER)) {
            case 0 -> {
                return super.canSurvive(state, level, pos);
            }
            case 1 -> {
                BlockState blockState = level.getBlockState(pos.below());
                return blockState.is(this) && blockState.getValue(LAYER) == 0;
            }
            case 2 -> {
                BlockState blockState = level.getBlockState(pos.below());
                return blockState.is(this) && blockState.getValue(LAYER) == 1;
            }
            case 3 -> {
                BlockState blockState = level.getBlockState(pos.below());
                return blockState.is(this) && blockState.getValue(LAYER) == 2;
            }
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (player.getAbilities().instabuild) {
                if (state.getValue(LAYER) == 0) {
                    for (int i = 0; i < 3; i++) {
                        if (level.getBlockState(pos.above(i)).getBlock() == this) {
                            level.destroyBlock(pos.above(i), false);
                        }
                    }
                } else {
                    level.destroyBlock(pos, false);
                }
            } else {
                dropResources(state, level, pos, null, player, player.getMainHandItem());
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), blockEntity, tool);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYER);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int maxTries = random.nextInt(3);
        int tries = 0;
        int timeout = 0;
        while (tries < maxTries && timeout < 101) {
            timeout++;
            BlockPos tryPos = pos.offset(random.nextInt(10) - 4, random.nextInt(8) - 4, random.nextInt(10) - 4);
            if (level.isEmptyBlock(tryPos.above()) && level.isEmptyBlock(tryPos.above(2)) && level.isEmptyBlock(
                    tryPos.above(3)) && level.isEmptyBlock(tryPos.above(4)) && canPlant(level, tryPos)) {
                tries++;
                level.setBlock(tryPos.above(), defaultBlockState().setValue(LAYER, 0), 2);
                level.setBlock(tryPos.above(2), defaultBlockState().setValue(LAYER, 1), 2);
                level.setBlock(tryPos.above(3), defaultBlockState().setValue(LAYER, 2), 2);
                level.setBlock(tryPos.above(4), defaultBlockState().setValue(LAYER, 3), 2);
            }
        }
    }

    private boolean canPlant(Level level, BlockPos blockPos) {
        return this.mayPlaceOn(level.getBlockState(blockPos), level, blockPos);
    }
}
