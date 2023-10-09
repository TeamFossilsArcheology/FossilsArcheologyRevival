package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.block.PrehistoricPlantType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class TallBerryBushBlock extends DoublePlantBlock implements BonemealableBlock {
    private final VoxelShape shape;
    private final PrehistoricPlantType type;

    public TallBerryBushBlock(VoxelShape shape, PrehistoricPlantType type) {
        super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().sound(SoundType.SWEET_BERRY_BUSH));
        this.shape = shape;
        this.type = type;
        this.registerDefaultState(stateDefinition.any().setValue(ageProperty(), 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    public abstract IntegerProperty ageProperty();

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return shape.move(0, -1, 0);
        }
        return shape;
    }

    @Override
    public BlockBehaviour.@NotNull OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && state.getValue(ageProperty()) < type.maxAge;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        int i = state.getValue(ageProperty());
        if (i < type.maxAge && random.nextInt(5) == 0 && level.getRawBrightness(pos.above(), 0) >= 9) {
            updateAge(level, pos, state, i + 1);
        }
    }

    private void updateAge(Level level, BlockPos pos, BlockState state, int nextAge) {
        level.setBlock(pos, state.setValue(ageProperty(), nextAge), 2);
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            level.setBlock(pos.above(), level.getBlockState(pos.above()).setValue(ageProperty(), nextAge), 2);
        } else {
            level.setBlock(pos.below(), level.getBlockState(pos.below()).setValue(ageProperty(), nextAge), 2);
        }
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int i = state.getValue(ageProperty());
        boolean isMaxAge = i == type.maxAge;
        if (!isMaxAge && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        }
        if (i >= type.berryAge) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            int dropAmount = 1 + level.random.nextInt(2);
            BlockPos sourcePos = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos;
            SweetBerryBushBlock.popResource(level, sourcePos, new ItemStack(type.berryItem.get(), dropAmount + (isMaxAge ? 1 : 0)));
            level.playSound(null, sourcePos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1, 0.8f + level.random.nextFloat() * 0.4f);
            updateAge(level, pos, state, 1);
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
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(ageProperty()) < type.maxAge;
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        updateAge(level, pos, state, state.getValue(ageProperty()) + 1);
    }
}

