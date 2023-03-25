package com.fossil.fossil.item;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.ToyBall;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class ToyBallItem extends Item {
    private final DyeColor color;

    public ToyBallItem(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = new BlockPlaceContext(context).getClickedPos();
        if (level instanceof ServerLevel serverLevel) {
            ToyBall entity = ModEntities.TOY_BALL.get().create(serverLevel);
            if (entity == null) {
                return InteractionResult.FAIL;
            }
            entity.setColor(color);
            entity.moveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(entity);
            level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, entity);
        }
        context.getItemInHand().shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
