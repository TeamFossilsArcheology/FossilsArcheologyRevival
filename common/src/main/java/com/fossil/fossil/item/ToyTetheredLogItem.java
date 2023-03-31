package com.fossil.fossil.item;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.ToyTetheredLog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ToyTetheredLogItem extends Item {
    private final WoodType woodType;

    public ToyTetheredLogItem(WoodType woodType, Properties properties) {
        super(properties);
        this.woodType = woodType;
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        if (!(context.getClickedFace() == Direction.DOWN && level.isEmptyBlock(blockPos.below()) && level.isEmptyBlock(blockPos.below(2)))) {
            return InteractionResult.FAIL;
        }
        Vec3 vec3 = new Vec3(blockPos.getX() + 0.5, blockPos.getY() - 1.95, blockPos.getZ() + 0.5);
        AABB aABB = ModEntities.TOY_TETHERED_LOG.get().getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());
        if (!level.noCollision(null, aABB) || !level.getEntities(null, aABB).isEmpty()) {
            return InteractionResult.FAIL;
        }
        if (level instanceof ServerLevel serverLevel) {
            ToyTetheredLog entity = ModEntities.TOY_TETHERED_LOG.get().create(serverLevel);
            if (entity == null) {
                return InteractionResult.FAIL;
            }
            entity.setWoodType(woodType.name());
            entity.moveTo(blockPos.getX() + 0.5, blockPos.getY() - 1.95, blockPos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(entity);
            level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, entity);
        }
        context.getItemInHand().shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}

