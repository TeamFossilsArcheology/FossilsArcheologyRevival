package com.github.teamfossilsarcheology.fossil.entity.ai;

import com.github.teamfossilsarcheology.fossil.block.entity.FeederBlockEntity;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;


/**
 * A Goal that will move the entity to the closest feeder if the entity is hungry, the feeder contains its food and the entity can see it.
 * Afterwards it will feed the entity until it is no longer hungry.
 */
public class EatFromFeederGoal extends MoveToFoodGoal {
    private final int chunkRadius;

    public EatFromFeederGoal(Prehistoric entity) {
        super(entity, 0.75, 32);
        this.chunkRadius = 2;
    }

    public EatFromFeederGoal(Prehistoric entity, int chunkRadius) {
        super(entity, 0.75, 32);
        this.chunkRadius = chunkRadius;
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            //Only start if entity has stopped because eating and moving animations cant stack
            if (entity.level.getBlockEntity(targetPos) instanceof FeederBlockEntity feeder && Math.abs(entity.animationSpeed) <= Util.SWING_ANIM_THRESHOLD) {
                feedingTicks++;
                feeder.feedDinosaur(entity);
                entity.heal(0.1f);
                if (entity.level.getGameTime() > animEndTick) {
                    AnimationInfo animationInfo = entity.nextEatingAnimation();
                    entity.getAnimationLogic().triggerAnimation(AnimationLogic.IDLE_CTRL, animationInfo, AnimationCategory.EAT);
                    animEndTick = (long) (entity.level.getGameTime() + animationInfo.animation.animationLength);
                }
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!super.isValidTarget(level, pos)) {
            return false;
        }
        return level.getBlockEntity(pos) instanceof FeederBlockEntity feeder && !feeder.isEmpty(entity.data().diet()) && Util.canSeeFood(entity, pos);
    }

    private boolean isValidTarget(Map.Entry<BlockPos, BlockEntity> entry) {
        if (avoidCache.contains(entry.getKey().asLong())) {
            return false;
        }
        return entry.getValue() instanceof FeederBlockEntity feeder && !feeder.isEmpty(entity.data().diet()) && Util.canSeeFood(entity, entry.getKey());
    }

    @Override
    protected boolean findNearestBlock() {
        BlockPos mobPos = entity.blockPosition();
        //chunkRadius of 2 is 25 chunks. Should not be too slow
        Optional<BlockPos> target = ChunkPos.rangeClosed(new ChunkPos(mobPos), chunkRadius)
                .flatMap(chunkPos -> entity.level.getChunk(chunkPos.x, chunkPos.z).getBlockEntities().entrySet().stream())
                .filter(this::isValidTarget)
                .map(Map.Entry::getKey)
                .min(Comparator.comparingInt(pos -> pos.distManhattan(mobPos)));
        if (target.isPresent()) {
            targetPos = target.get();
            return true;
        }
        clearTicks = !avoidCache.isEmpty() ? CLEAR_TICKS : 0;
        return false;
    }
}
