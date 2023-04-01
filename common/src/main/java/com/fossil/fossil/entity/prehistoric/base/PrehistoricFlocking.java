package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public abstract class PrehistoricFlocking extends Prehistoric {
    private int groupSize = 1;
    private PrehistoricFlocking groupLeader;

    public PrehistoricFlocking(EntityType<? extends Prehistoric> entityType, PrehistoricEntityType type,
                               Level level, boolean isMultiPart, boolean isCannibalistic, float minScale, float maxScale,
                               float baseKnockBackResistance, float maxKnockBackResistance, int teenAgeDays, int adultAgeDays, double baseDamage,
                               double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed, double baseArmor,
                               double maxArmor) {
        super(entityType, type, level, isMultiPart, isCannibalistic, minScale, maxScale, baseKnockBackResistance, maxKnockBackResistance, teenAgeDays,
                adultAgeDays, baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed, baseArmor, maxArmor);
    }

    public void leaveGroup() {
        groupLeader.decreaseGroupSize();
        groupLeader = null;
    }

    public boolean hasGroupLeader() {
        return groupLeader != null && groupLeader.isAlive();
    }

    public void increaseGroupSize() {
        groupSize++;
    }

    public void decreaseGroupSize() {
        groupSize--;
    }

    public boolean canGroupGrow() {
        return isGroupLeader() && groupSize < getMaxGroupSize();
    }

    public boolean isGroupLeader() {
        return groupSize > 1;
    }

    public boolean inRangeOfGroupLeader() {
        return distanceToSqr(groupLeader) <= 121;
    }

    public void pathToGroupLeader(double speed) {
        if (hasGroupLeader()) {
            getNavigation().moveTo(groupLeader, speed);
        }
    }

    public PrehistoricFlocking startFollowing(PrehistoricFlocking groupLeader) {
        this.groupLeader = groupLeader;
        groupLeader.increaseGroupSize();
        return groupLeader;
    }

    /**
     * Adds followers from a stream until the group size limit has been reached.
     */
    public void addFollowers(Stream<? extends PrehistoricFlocking> followers) {
        followers.limit(getMaxGroupSize() - groupSize).filter(dino -> dino != this).forEach(dino -> dino.startFollowing(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (isGroupLeader() && level.random.nextInt(200) == 1) {
            if (level.getEntitiesOfClass(getClass(), getBoundingBox().inflate(getFlockDistance())).size() <= 1) {
                groupSize = 1;
            }
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
        if (spawnDataIn == null) {
            spawnDataIn = new GroupData(this);
        } else {
            startFollowing(((GroupData) spawnDataIn).groupLeader);
        }
        return spawnDataIn;
    }

    protected abstract int getMaxGroupSize();

    public int getFlockDistance() {
        return 32;
    }


    public record GroupData(PrehistoricFlocking groupLeader) implements SpawnGroupData {
    }
}
