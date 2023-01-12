package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.Fossil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class DrumBlock extends Block {
    private static final int DRUM_COUNT = 2;
    public static final IntegerProperty DRUMS = IntegerProperty.create("drums", 0, DRUM_COUNT);
    public static final ResourceLocation DRUM_SOUND = new ResourceLocation(Fossil.MOD_ID, "drum_single");

    public DrumBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(DRUMS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DRUMS);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int count = state.getValue(DRUMS);
        if (count < DRUM_COUNT) {
            level.setBlock(pos, state.setValue(DRUMS, count + 1), 3);
        } else {
            level.setBlock(pos, state.setValue(DRUMS, 0), 3);
        }
        level.playSound(player, pos, new SoundEvent(DRUM_SOUND), SoundSource.BLOCKS, 1, 1);
        return InteractionResult.SUCCESS;
    }
}
