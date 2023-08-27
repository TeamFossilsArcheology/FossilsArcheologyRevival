package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class MoodSystem {
    public static final EntityDataAccessor<Integer> MOOD = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PLAYING_TICK = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private final Prehistoric prehistoric;
    private int moodCheckCooldown = 0;

    public static boolean arePlantsNearby(Entity entity, int range) {
        for (int i = Mth.floor(entity.getX() - range); i < Mth.ceil(entity.getX() + range); i++) {
            for (int j = Mth.floor(entity.getY() - range / 2.0); j < Mth.ceil(entity.getY() + range / 2.0); j++) {
                for (int k = Mth.floor(entity.getZ() - range); k < Mth.ceil(entity.getZ() + range); k++) {
                    if (j <= entity.level.getHeight() + 1D && isPlantBlock(entity.level.getBlockState(new BlockPos(i, j, k)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isPlantBlock(BlockState block) {
        Material material = block.getMaterial();
        return material == Material.CACTUS || material == Material.PLANT || material == Material.LEAVES ||
                material == Material.MOSS || material == Material.VEGETABLE;
    }

    public MoodSystem(Prehistoric prehistoric) {
        this.prehistoric = prehistoric;
    }

    public PrehistoricMoodType getMoodFace() {
        if (getMood() == 100) {
            return PrehistoricMoodType.HAPPY;
        } else if (getMood() >= 50) {
            return PrehistoricMoodType.CONTENT;
        } else if (getMood() == -100) {
            return PrehistoricMoodType.ANGRY;
        } else if (getMood() <= -50) {
            return PrehistoricMoodType.SAD;
        } else {
            return PrehistoricMoodType.CALM;
        }
    }

    public int getMoodPosition() {
        return (int) (71 * -(getMood() * 0.01));
    }

    public int getMood() {
        return Mth.clamp(prehistoric.getEntityData().get(MOOD), -100, 100);
    }

    public void setMood(int mood) {
        prehistoric.getEntityData().set(MOOD, Mth.clamp(mood, -100, 100));
    }

    public void increaseMood(int mood) {
        setMood(getMood() + mood);
    }

    public void doMoodCheck() {
        int overallMoodAddition = 0;
        if (arePlantsNearby(prehistoric, 16)) {
            overallMoodAddition += 50;
        } else {
            overallMoodAddition -= 50;
        }
        if (prehistoric.getNearbySpeciesMembers(40).size() <= prehistoric.data().maxPopulation()) {
            overallMoodAddition += 50;
        } else {
            overallMoodAddition -= 50;
        }
        setMood(getMood() + overallMoodAddition);
    }

    public int getPlayingTick() {
        return prehistoric.getEntityData().get(PLAYING_TICK);
    }

    public void setPlayingTick(int ticks) {
        prehistoric.getEntityData().set(PLAYING_TICK, ticks);
    }

    public void useToy(int playBonus) {
        if (getPlayingTick() == 0) {
            setMood(getMood() + playBonus);
            setPlayingTick(prehistoric.getRandom().nextInt(600) + 600);
        }
    }

    public void tick() {
        if (getMood() > 100) {
            setMood(100);
        }
        if (getMood() < -100) {
            setMood(-100);
        }
        if (prehistoric.isDeadlyHungry() && getMood() > -50) {
            setMood(-50);
        }
        if (getPlayingTick() > 0) {
            setPlayingTick(getPlayingTick() - 1);
        }
        if (moodCheckCooldown-- <= 0) {
            doMoodCheck();
            moodCheckCooldown = 3000 + prehistoric.getRandom().nextInt(5000);
        }
    }
}
