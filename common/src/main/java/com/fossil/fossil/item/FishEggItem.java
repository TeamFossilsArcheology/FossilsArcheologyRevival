package com.fossil.fossil.item;

import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class FishEggItem extends PrehistoricEntityItem {

    public FishEggItem(EntityInfo type) {
        super(type);
    }

    private boolean spawnFish(ServerLevel level, BlockPos pos) {
        Entity entity = type.entityType().create(level);
        if (entity instanceof PrehistoricFish fish) {
            entity.moveTo(pos.getX(), pos.getY() + 1, pos.getZ(), level.random.nextFloat() * 360, 0);
            fish.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(-1), null);
            level.addFreshEntity(entity);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide && spawnFish((ServerLevel) context.getLevel(), context.getClickedPos())) {
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
