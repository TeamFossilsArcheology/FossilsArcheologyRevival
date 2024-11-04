package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class FishEggItem extends PrehistoricEntityItem {

    public FishEggItem(EntityInfo info) {
        super(new Properties().stacksTo(8), info, "fish_egg");
    }

    private boolean spawnFish(ServerPlayer player, ServerLevel level, BlockPos pos) {
        Entity entity = info.entityType().create(level);
        if (entity instanceof Mob mob) {
            ModTriggers.INCUBATE_EGG_TRIGGER.trigger(player, entity);
            entity.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, level.random.nextFloat() * 360, 0);
            mob.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(-1), null);
            level.addFreshEntity(entity);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide && spawnFish((ServerPlayer) context.getPlayer(), (ServerLevel) context.getLevel(), context.getClickedPos())) {
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
