package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.entity.ToyBase;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class MoodSystem {
    private final Prehistoric prehistoric;
    private int moodCheckCooldown = 0;
    private int playingCooldown;
    private ToyBase toyTarget;

    public MoodSystem(Prehistoric prehistoric) {
        this.prehistoric = prehistoric;
        this.playingCooldown = prehistoric.getRandom().nextInt(6000) + 6000;
    }

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

    public PrehistoricMoodType getMoodFace() {
        int mood = getMood();
        if (mood >= 100) {
            return PrehistoricMoodType.HAPPY;
        } else if (mood >= 50) {
            return PrehistoricMoodType.CONTENT;
        } else if (mood <= -100) {
            return PrehistoricMoodType.ANGRY;
        } else if (mood <= -50) {
            return PrehistoricMoodType.SAD;
        } else {
            return PrehistoricMoodType.CALM;
        }
    }

    public int getMoodPosition() {
        return (int) (71 * -(getMood() * 0.01));
    }

    public int getMood() {
        return Mth.clamp(prehistoric.getEntityData().get(Prehistoric.MOOD), -100, 100);
    }

    public void setMood(int mood) {
        prehistoric.getEntityData().set(Prehistoric.MOOD, Mth.clamp(mood, -100, 100));
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

    public int getPlayingCooldown() {
        return playingCooldown;
    }

    public void setPlayingCooldown(int ticks) {
        this.playingCooldown = ticks;
    }

    public void useToy(int playBonus) {
        if (getPlayingCooldown() == 0) {
            setMood(getMood() + playBonus);
            prehistoric.level.broadcastEntityEvent(prehistoric, Prehistoric.HAPPY_VILLAGER_PARTICLES);
            setPlayingCooldown(prehistoric.getRandom().nextInt(600) + 600);
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
        if (getPlayingCooldown() > 0) {
            setPlayingCooldown(getPlayingCooldown() - 1);
        }
        if (moodCheckCooldown <= 0) {
            doMoodCheck();
            moodCheckCooldown = 3000 + prehistoric.getRandom().nextInt(5000);
        }
        moodCheckCooldown--;
    }

    public void setToyTarget(ToyBase toyTarget) {
        this.toyTarget = toyTarget;
    }

    public ToyBase getToyTarget() {
        return toyTarget;
    }
}
