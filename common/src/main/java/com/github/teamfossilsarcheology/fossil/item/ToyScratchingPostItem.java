package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.ToyScratchingPost;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ToyScratchingPostItem extends Item {
    private final WoodType woodType;

    public ToyScratchingPostItem(WoodType woodType, Properties properties) {
        super(properties);
        this.woodType = woodType;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        BlockPlaceContext context1 = new BlockPlaceContext(context);
        Level level = context.getLevel();
        BlockPos blockPos = context1.getClickedPos();
        if (!context1.canPlace()) {
            return InteractionResult.FAIL;
        }
        if (context.getClickedFace() != Direction.UP || !level.getBlockState(blockPos).canBeReplaced(context1)
                || !level.getBlockState(blockPos.above()).canBeReplaced(context1)) {
            return InteractionResult.FAIL;
        }
        Vec3 blockCenter = Vec3.atBottomCenterOf(blockPos);
        AABB aABB = ModEntities.TOY_SCRATCHING_POST.get().getDimensions().makeBoundingBox(blockCenter.x(), blockCenter.y(), blockCenter.z());
        if (!level.noCollision(null, aABB) || !level.getEntities(null, aABB).isEmpty()) {
            return InteractionResult.FAIL;
        }
        if (level instanceof ServerLevel serverLevel) {
            ToyScratchingPost entity = ModEntities.TOY_SCRATCHING_POST.get().create(serverLevel);
            if (entity == null) {
                return InteractionResult.FAIL;
            }
            entity.setWoodType(woodType.name());
            entity.moveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(entity);
        }
        context.getItemInHand().shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
