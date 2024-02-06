package com.fossil.fossil.item;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.PrehistoricSkeleton;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.util.TimePeriod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

import java.util.List;

public class BioFossilItem extends Item {

    private final boolean isTar;

    public BioFossilItem(boolean isTar) {
        super(new Properties().tab(ModTabs.FAITEMTAB));
        this.isTar = isTar;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.FAIL;
        }
        Level level = context.getLevel();
        List<PrehistoricEntityInfo> entityInfos;
        if (isTar) {
            entityInfos = PrehistoricEntityInfo.entitiesWithSkeleton(TimePeriod.CENOZOIC);
        } else {
            entityInfos = PrehistoricEntityInfo.entitiesWithSkeleton(TimePeriod.MESOZOIC, TimePeriod.PALEOZOIC);
        }
        PrehistoricEntityInfo info = entityInfos.get(level.getRandom().nextInt(entityInfos.size()));
        if (info == null) {
            return InteractionResult.FAIL;
        }
        BlockPos blockPos = new BlockPlaceContext(context).getClickedPos();
        Vec3 vec3 = Vec3.atBottomCenterOf(blockPos);
        AABB aABB = info.entityType().getDimensions().scale(EntityDataManager.ENTITY_DATA.getData(info.resourceName).minScale()).makeBoundingBox(vec3.x, vec3.y, vec3.z);
        if (!level.noCollision(null, aABB) || !level.getEntities(null, aABB).isEmpty()) {
            return InteractionResult.FAIL;
        }
        if (level instanceof ServerLevel serverLevel) {
            PrehistoricSkeleton fossil = ModEntities.SKELETON.get().create(serverLevel, null, null, context.getPlayer(), blockPos, MobSpawnType.SPAWN_EGG, true, true);
            if (fossil == null) {
                return InteractionResult.FAIL;
            }
            fossil.moveTo(fossil.getX(), fossil.getY(), fossil.getZ(), -context.getPlayer().yHeadRot, 0);
            fossil.setType(info);
            serverLevel.addFreshEntity(fossil);
            level.playSound(null, fossil.getX(), fossil.getY(), fossil.getZ(), SoundEvents.SKELETON_AMBIENT, SoundSource.BLOCKS, 0.75f, 0.8f);
            level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, fossil);
        }
        context.getItemInHand().shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}