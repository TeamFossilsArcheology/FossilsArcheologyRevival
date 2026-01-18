package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public abstract class TallBerryBushBlock extends DoublePlantBlock implements BerryBushBlock, BonemealableBlock {
    private final PrehistoricPlantInfo info;

    protected TallBerryBushBlock(PrehistoricPlantInfo info) {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().sound(SoundType.SWEET_BERRY_BUSH));
        this.info = info;
        this.registerDefaultState(stateDefinition.any().setValue(ageProperty(), 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            if (state.getValue(ageProperty()) == 0) {
                return Shapes.empty();
            }
            return getShapesByAge()[state.getValue(ageProperty())].move(0, -1, 0);
        }
        return getShapesByAge()[state.getValue(ageProperty())];
    }

    protected abstract VoxelShape[] getShapesByAge();

    protected abstract int getCreateUpperAge();

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER && getShapesByAge()[state.getValue(ageProperty())].bounds().maxY < 1) {
            return state;
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && state.getValue(ageProperty()) < info.maxAge();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = state.getValue(ageProperty());
        if (i < info.maxAge() && random.nextInt(5) == 0 && level.getRawBrightness(pos.above(), 0) >= 9) {
            updateAge(level, pos, state, i + 1);
        }
    }

    protected void updateAge(Level level, BlockPos pos, BlockState state, int nextAge) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos posAbove = pos.above();
            BlockState stateAbove = level.getBlockState(posAbove);
            if (stateAbove.is(info.getPlantBlock())) {
                level.setBlock(posAbove, stateAbove.setValue(ageProperty(), nextAge), 2);
            } else if (!stateAbove.isAir()) {
                return;
            } else if (nextAge >= getCreateUpperAge()) {
                level.setBlock(posAbove, DoublePlantBlock.copyWaterloggedFrom(level, posAbove, defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)).setValue(ageProperty(), nextAge), 3);
            }
        } else {
            level.setBlock(pos.below(), level.getBlockState(pos.below()).setValue(ageProperty(), nextAge), 2);
        }
        level.setBlock(pos, state.setValue(ageProperty(), nextAge), 2);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int i = state.getValue(ageProperty());
        boolean isMaxAge = i == info.maxAge();
        if (!isMaxAge && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        }
        if (i >= info.berryAge()) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            int dropAmount = 1 + level.random.nextInt(2) + i - info.berryAge();
            BlockPos sourcePos = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos;
            SweetBerryBushBlock.popResource(level, sourcePos, new ItemStack(info.berryItem().get(), dropAmount));
            level.playSound(null, sourcePos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1, 0.8f + level.random.nextFloat() * 0.4f);
            updateAge(level, pos, state, info.berryAge() - 1);
            return InteractionResult.CONSUME;
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ageProperty());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(ageProperty()) < info.maxAge();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        updateAge(level, pos, state, state.getValue(ageProperty()) + 1);
    }
}

