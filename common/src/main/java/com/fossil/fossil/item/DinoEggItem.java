package com.fossil.fossil.item;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.DinosaurEgg;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class DinoEggItem extends PrehistoricEntityItem {
    public DinoEggItem(PrehistoricEntityType type) {
        super(type);
    }

    public static boolean spawnEgg(Level level, PrehistoricEntityType type, double x, double y, double z, Player player) {
        if (level.isClientSide) {
            return true;
        }
        if (!type.isVivariousAquatic()) {
            DinosaurEgg egg = ModEntities.DINOSAUR_EGG.get().create(level);
            if (egg == null) {
                return false;
            }
            egg.moveTo(x, y + 0.5, z, 0, 0);
            egg.setPrehistoricEntityType(type);
            level.addFreshEntity(egg);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, egg);
        } else {
            DinosaurEgg.hatchEgg(level, x, y, z, (ServerPlayer) player, type, false);
        }
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && entity instanceof ServerPlayer) {
            //TODO: Trigger
        }
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos offset = context.getClickedPos().offset(context.getClickedFace().getNormal());
        if (!level.isEmptyBlock(offset) && !level.getBlockState(offset).canBeReplaced(new BlockPlaceContext(context))) {
            return InteractionResult.FAIL;
        }
        if (spawnEgg(level, type, offset.getX() + 0.5, offset.getY(), offset.getZ() + 0.5, context.getPlayer())) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
