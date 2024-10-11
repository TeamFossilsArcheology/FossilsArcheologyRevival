package com.github.teamfossilsarcheology.fossil.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class ArtificialHoneycombItem extends Item {
    public ArtificialHoneycombItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.is(Blocks.BEEHIVE)) {
            if (level instanceof ServerLevel serverLevel) {
                ItemStack stack = context.getItemInHand();
                if (EntityType.BEE.spawn(serverLevel, stack, context.getPlayer(), blockPos, MobSpawnType.SPAWN_EGG, true, false) != null) {
                    stack.shrink(1);
                    level.gameEvent(GameEvent.ENTITY_PLACE, blockPos);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }
}
