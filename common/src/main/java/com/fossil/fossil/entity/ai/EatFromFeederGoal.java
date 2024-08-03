package com.fossil.fossil.entity.ai;

import com.fossil.fossil.block.entity.FeederBlockEntity;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
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

    public EatFromFeederGoal(Prehistoric entity) {
        super(entity, 1, 32);
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            if (entity.level.getBlockEntity(targetPos) instanceof FeederBlockEntity feeder) {
                feedingTicks++;
                feeder.feedDinosaur(entity);
                entity.heal(0.1f);
                if (feedingTicks % 4 == 0) {
                    entity.makeEatingEffects();
                }
                entity.setStartEatAnimation(true);
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!super.isValidTarget(level, pos)) {
            return false;
        }
        return level.getBlockEntity(pos) instanceof FeederBlockEntity feeder && !feeder.isEmpty(entity.info().diet) && canSeeFood(pos);
    }

    private boolean isValidTarget(Map.Entry<BlockPos, BlockEntity> entry) {
        if (avoidCache.contains(entry.getKey().asLong())) {
            return false;
        }
        return entry.getValue() instanceof FeederBlockEntity feeder && !feeder.isEmpty(entity.info().diet) && canSeeFood(entry.getKey());
    }

    @Override
    protected boolean findNearestBlock() {
        BlockPos mobPos = entity.blockPosition();
        int radius = 2;//2 is 25 chunks. Should not be too slow
        Optional<BlockPos> target = ChunkPos.rangeClosed(new ChunkPos(mobPos), radius)
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
