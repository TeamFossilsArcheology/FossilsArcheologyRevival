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
import org.jetbrains.annotations.NotNull;

public class DinoEggItem extends PrehistoricEntityItem {
    public DinoEggItem(PrehistoricEntityType type) {
        super(type);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && entity instanceof ServerPlayer) {
            //TODO: Trigger
        }
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        BlockPos above = context.getClickedPos().above();
        Level level = context.getLevel();
        if (!level.isEmptyBlock(above) && !level.getBlockState(above).canBeReplaced(new BlockPlaceContext(context))) {
            return InteractionResult.FAIL;
        }
        BlockPos offset = above.offset(context.getClickedFace().getNormal());
        if (spawnEgg(level, offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, context.getPlayer())) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private boolean spawnEgg(Level level, double x, double y, double z, Player player) {
        if (!type.isVivariousAquatic()) {
            DinosaurEgg egg = ModEntities.DINOSAUR_EGG.get().create(level);
            if (egg == null) {
                return false;
            }
            egg.moveTo(x, y, z, level.random.nextFloat() * 360, 0);
            egg.setPrehistoricEntityType(type);
            level.addFreshEntity(egg);
            //TODO: Message
            return true;
        } else {
            return DinosaurEgg.hatchEgg(level, x, y, z, player, type, false) != null;
        }
    }
}
