package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.PrehistoricSkeleton;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.util.TimePeriod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FossilItem extends Item {

    private final @Nullable TimePeriod timePeriod;

    public FossilItem(@Nullable TimePeriod timePeriod) {
        super(new Properties().arch$tab(ModTabs.FA_OTHER_ITEM_TAB));
        this.timePeriod = timePeriod;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (timePeriod == null) {
            return InteractionResult.FAIL;
        }
        Level level = context.getLevel();
        List<PrehistoricEntityInfo> entityInfos = PrehistoricEntityInfo.entitiesWithSkeleton(timePeriod);
        PrehistoricEntityInfo info = entityInfos.get(level.getRandom().nextInt(entityInfos.size()));
        if (info == null) {
            return InteractionResult.FAIL;
        }
        BlockPos blockPos = new BlockPlaceContext(context).getClickedPos();
        Vec3 vec3 = Vec3.atBottomCenterOf(blockPos);
        AABB aABB = info.entityType().getDimensions().scale(EntityDataLoader.INSTANCE.getData(info.resourceName).minScale()).makeBoundingBox(vec3.x, vec3.y, vec3.z);
        if (!level.noCollision(null, aABB) || !level.getEntities(null, aABB).isEmpty()) {
            return InteractionResult.FAIL;
        }
        if (level instanceof ServerLevel serverLevel) {
            PrehistoricSkeleton fossil = ModEntities.SKELETON.get().create(serverLevel, null, null, blockPos, MobSpawnType.SPAWN_EGG, false, false);
            if (fossil == null) {
                return InteractionResult.FAIL;
            }
            fossil.moveTo(fossil.getX(), fossil.getY(), fossil.getZ(), -context.getPlayer().yHeadRot, 0);
            fossil.setInfoType(info);
            serverLevel.addFreshEntityWithPassengers(fossil);
            level.playSound(null, fossil.getX(), fossil.getY(), fossil.getZ(), SoundEvents.SKELETON_AMBIENT, SoundSource.BLOCKS, 0.75f, 0.8f);
            fossil.gameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
        }
        context.getItemInHand().shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}