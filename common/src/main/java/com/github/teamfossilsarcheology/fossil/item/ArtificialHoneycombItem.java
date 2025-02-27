package com.github.teamfossilsarcheology.fossil.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class ArtificialHoneycombItem extends DescriptiveItem {
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
                Entity bee = EntityType.BEE.create(serverLevel);
                if (bee instanceof Mob mob) {
                    ItemStack stack = context.getItemInHand();
                    AgeableMob.AgeableMobGroupData groupData = new AgeableMob.AgeableMobGroupData(1);
                    groupData.increaseGroupSizeByOne();
                    mob.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.SPAWN_EGG, groupData, stack.getTag());
                    mob.playAmbientSound();
                    mob.moveTo(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5, Mth.wrapDegrees(level.random.nextFloat() * 360), 0.0F);
                    level.addFreshEntity(mob);
                    stack.shrink(1);
                    mob.gameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }
}
