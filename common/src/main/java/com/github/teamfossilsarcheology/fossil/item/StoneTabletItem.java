package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.StoneTablet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class StoneTabletItem extends Item {
    public StoneTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos placePos = blockPos.relative(direction);
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        if (player != null && !mayPlace(player, direction, itemStack, placePos)) {
            return InteractionResult.FAIL;
        }
        Level level = context.getLevel();
        StoneTablet stoneTablet = new StoneTablet(level, placePos, direction);
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null) {
            EntityType.updateCustomEntityTag(level, player, stoneTablet, compoundTag);
        }
        if (stoneTablet.survives()) {
            if (!level.isClientSide) {
                stoneTablet.playPlacementSound();
                stoneTablet.gameEvent(GameEvent.ENTITY_PLACE, player);
                level.addFreshEntity(stoneTablet);
            }
            itemStack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.CONSUME;
    }

    protected boolean mayPlace(Player player, Direction direction, ItemStack hangingEntityStack, BlockPos pos) {
        return !direction.getAxis().isVertical() && player.mayUseItemAt(pos, direction, hangingEntityStack);
    }
}
