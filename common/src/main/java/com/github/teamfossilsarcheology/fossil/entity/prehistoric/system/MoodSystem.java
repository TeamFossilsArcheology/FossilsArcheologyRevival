package com.github.teamfossilsarcheology.fossil.entity.prehistoric.system;

import com.github.teamfossilsarcheology.fossil.entity.ToyBase;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMoodType;
import com.github.teamfossilsarcheology.fossil.tags.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * This system handles the mobs mood as well as some parts of the playing logic.
 * <p>
 * A mobs mood can determine how aggressive they are, whether they can breed or whether they can play
 */
public class MoodSystem extends AISystem {
    private int moodCheckCooldown = 100;
    private int playingCooldown;
    private ToyBase toyTarget;

    public MoodSystem(Prehistoric prehistoric) {
        super(prehistoric);
        this.playingCooldown = prehistoric.getRandom().nextInt(6000) + 6000;
    }

    /**
     * @param entity the entity to check around
     * @param range  the radius. Vertical radius is halved
     * @return {@code true} if any block in range is of a plant-ish material
     */
    public static boolean arePlantsNearby(Entity entity, int range) {
        for (int i = Mth.floor(entity.getX() - range); i < Mth.ceil(entity.getX() + range); i++) {
            for (int j = Mth.floor(entity.getY() - range / 2.0); j < Mth.ceil(entity.getY() + range / 2.0); j++) {
                for (int k = Mth.floor(entity.getZ() - range); k < Mth.ceil(entity.getZ() + range); k++) {
                    if (j <= entity.level().getHeight() + 1D && isPlantBlock(entity.level().getBlockState(new BlockPos(i, j, k)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isPlantBlock(BlockState block) {
        return block.is(ModBlockTags.MOOD_BONUS);
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
        return Mth.clamp(mob.getEntityData().get(Prehistoric.MOOD), -100, 100);
    }

    public void setMood(int mood) {
        mob.getEntityData().set(Prehistoric.MOOD, Mth.clamp(mood, -100, 100));
    }

    public void increaseMood(int mood) {
        setMood(getMood() + mood);
    }

    /**
     * Increases or decreases the mood if certain conditions are or aren't met
     */
    public void doMoodCheck() {
        int overallMoodAddition = 0;
        if (arePlantsNearby(mob, 16)) {
            overallMoodAddition += 50;
        } else {
            overallMoodAddition -= 50;
        }
        if (mob.getNearbySpeciesMembers(40).size() < mob.data().maxPopulation()) {
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

    /**
     * Updates the mobs mood, sets a cooldown and displays some particles
     *
     * @param playBonus the amount of mood gained
     */
    public void useToy(int playBonus) {
        if (getPlayingCooldown() == 0) {
            setMood(getMood() + playBonus);
            mob.level().broadcastEntityEvent(mob, Prehistoric.HAPPY_VILLAGER_PARTICLES);
            setPlayingCooldown(mob.getRandom().nextInt(600) + 600);
        }
    }

    @Override
    public void serverTick() {
        if (getMood() > 100) {
            setMood(100);
        }
        if (getMood() < -100) {
            setMood(-100);
        }
        if (mob.isDeadlyHungry() && getMood() > -50) {
            setMood(-50);
        }
        if (getPlayingCooldown() > 0) {
            setPlayingCooldown(getPlayingCooldown() - 1);
        }
        if (moodCheckCooldown <= 0) {
            doMoodCheck();
            moodCheckCooldown = 3000 + mob.getRandom().nextInt(5000);
        }
        moodCheckCooldown--;
    }

    public void setToyTarget(ToyBase toyTarget) {
        this.toyTarget = toyTarget;
    }

    public ToyBase getToyTarget() {
        return toyTarget;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putInt("Mood", getMood());
        tag.putInt("PlayingCooldown", getPlayingCooldown());
        tag.putInt("MoodCheckCooldown", moodCheckCooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Mood")) {
            setMood(tag.getInt("Mood"));
        }
        if (tag.contains("PlayingCooldown")) {
            setPlayingCooldown(tag.getInt("PlayingCooldown"));
        }
        if (tag.contains("MoodCheckCooldown")) {
            moodCheckCooldown = tag.getInt("MoodCheckCooldown");
        }
    }
}
