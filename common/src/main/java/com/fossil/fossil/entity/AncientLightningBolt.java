package com.fossil.fossil.entity;

import com.fossil.fossil.entity.monster.FriendlyPiglin;
import com.google.common.collect.Sets;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class AncientLightningBolt extends LightningBolt {
    private static final int START_LIFE = 2;
    private int life;
    private int flashes;
    private boolean visualOnly;
    @Nullable
    private ServerPlayer cause;
    private final Set<Entity> hitEntities = Sets.newHashSet();
    private int blocksSetOnFire;

    public AncientLightningBolt(EntityType<AncientLightningBolt> entityType, Level level) {
        super(entityType, level);
        this.life = START_LIFE;
        this.flashes = random.nextInt(3) + 1;
    }

    public void setVisualOnly(boolean visualOnly) {
        this.visualOnly = visualOnly;
    }

    @Nullable
    public ServerPlayer getCause() {
        return cause;
    }

    public void setCause(@Nullable ServerPlayer cause) {
        this.cause = cause;
    }

    @Override
    public void tick() {
        List<Entity> nearbyEntities;
        baseTick();
        if (life == START_LIFE) {
            if (level.isClientSide()) {
                level.playLocalSound(getX(), getY(), getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000, 0.8f + random.nextFloat() * 0.2f, false);
                level.playLocalSound(getX(), getY(), getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 2, 0.5f + random.nextFloat() * 0.2f, false);
            } else {
                Difficulty difficulty = level.getDifficulty();
                if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
                    spawnFire(4);
                }
            }
        }
        --life;
        if (life < 0) {
            if (flashes == 0) {
                discard();
            } else if (life < -random.nextInt(10)) {
                --flashes;
                life = 1;
                seed = random.nextLong();
                spawnFire(0);
            }
        }
        if (life >= 0) {
            if (level.isClientSide) {
                level.setSkyFlashTime(2);
            } else if (!visualOnly) {
                nearbyEntities = level.getEntities(this, new AABB(getX() - 3, getY() - 3, getZ() - 3, getX() + 3, getY() + 6 + 3, getZ() + 3), Entity::isAlive);
                for (Entity entity : nearbyEntities) {
                    if (!(entity instanceof Player) && !(entity instanceof FriendlyPiglin) && !(entity instanceof Pig)) {
                        entity.thunderHit((ServerLevel) level, this);
                    }
                }
                hitEntities.addAll(nearbyEntities);
                if (cause != null) {
                    CriteriaTriggers.CHANNELED_LIGHTNING.trigger(cause, nearbyEntities);
                }
            }
        }
    }

    private void spawnFire(int extraIgnitions) {
        if (visualOnly || level.isClientSide || !level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            return;
        }
        BlockPos blockPos = blockPosition();
        BlockState blockState = BaseFireBlock.getState(level, blockPos);
        if (level.getBlockState(blockPos).isAir() && blockState.canSurvive(level, blockPos)) {
            level.setBlockAndUpdate(blockPos, blockState);
            ++blocksSetOnFire;
        }
        for (int i = 0; i < extraIgnitions; ++i) {
            BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
            blockState = BaseFireBlock.getState(level, blockPos2);
            if (!level.getBlockState(blockPos2).isAir() || !blockState.canSurvive(level, blockPos2)) continue;
            level.setBlockAndUpdate(blockPos2, blockState);
            ++blocksSetOnFire;
        }
    }

    public @NotNull Stream<Entity> getHitEntities() {
        return this.hitEntities.stream().filter(Entity::isAlive);
    }

    public int getBlocksSetOnFire() {
        return blocksSetOnFire;
    }
}
