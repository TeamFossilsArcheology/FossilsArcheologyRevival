package com.fossil.fossil.item;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.DinosaurEgg;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class DinoEggItem extends PrehistoricEntityItem {
    public DinoEggItem(PrehistoricEntityInfo info) {
        super(new Properties().stacksTo(8), info);
    }

    public static boolean spawnEgg(Level level, PrehistoricEntityInfo info, double x, double y, double z, Player player) {
        if (player == null) {
            return false;
        }
        if (level.isClientSide) {
            return true;
        }
        if (!info.isVivariousAquatic()) {
            DinosaurEgg egg = ModEntities.DINOSAUR_EGG.get().create(level);
            if (egg == null) {
                return false;
            }
            egg.moveTo(x, y + 0.5, z, 0, 0);
            egg.setPrehistoricEntityInfo(info);
            level.addFreshEntity(egg);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, egg);
        } else {
            DinosaurEgg.hatchEgg(level, x, y, z, (ServerPlayer) player, info, false);
        }
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos offset = context.getClickedPos().relative(context.getClickedFace());
        if (!level.isEmptyBlock(offset) && !level.getBlockState(offset).canBeReplaced(new BlockPlaceContext(context))) {
            return InteractionResult.FAIL;
        }
        if (spawnEgg(level, (PrehistoricEntityInfo) info, offset.getX() + 0.5, offset.getY(), offset.getZ() + 0.5, context.getPlayer())) {
            if (!context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
