package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.C2SSyncDebugInfoMessage;
import com.fossil.fossil.util.Gender;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;

import java.util.Arrays;

public class InfoTab extends DebugTab {
    private final int maxAgeInTicks;
    private Gender gender;
    private int ageInTicks;
    private int matingCooldown;
    private int playingCooldown;
    private int climbingCooldown;
    private int hunger;
    private int mood;
    private Slider ageSlider;
    private Slider matingSlider;
    private Slider playingSlider;
    private Slider climbingSlider;
    private Slider hungerSlider;
    private Slider moodSlider;

    protected InfoTab(DebugScreen debugScreen, Prehistoric prehistoric) {
        super(debugScreen, prehistoric);
        this.maxAgeInTicks = prehistoric.data().adultAgeDays() * 24000;
        this.gender = prehistoric.getGender();
        this.ageInTicks = prehistoric.getAge();
        this.matingCooldown = prehistoric.getMatingCooldown();
        this.playingCooldown = prehistoric.moodSystem.getPlayingCooldown();
        this.climbingCooldown = prehistoric.getClimbingCooldown();
        this.hunger = prehistoric.getHunger();
        this.mood = prehistoric.moodSystem.getMood();
    }

    @Override
    protected void init(int width, int height) {
        super.init(width, height);
        ageSlider = new Slider(20, 30, 150, 20, new TextComponent("Age in ticks: "), new TextComponent(""), 0, maxAgeInTicks, ageInTicks, 12000, 0, true) {
            @Override
            protected void applyValue() {
                ageInTicks = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        addWidget(new Button(275, 30, 150, 20, new TextComponent("Scale 1"), button -> {
            if (entity instanceof Prehistoric prehistoric) {
                EntityDataManager.Data data = prehistoric.data();
                ageInTicks = (int) (((1 - data.minScale()) * (data.adultAgeDays() * 24000 + 1)) / (data.maxScale() - data.minScale()));
                ageSlider.setValue(ageInTicks);
            }
        }));
        matingSlider = new Slider(20, 60, 150, 20, new TextComponent("Seconds till mating: "), new TextComponent(""), 0, 300, matingCooldown, 1, 0, true) {
            @Override
            protected void applyValue() {
                matingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        playingSlider = new Slider(20, 90, 150, 20, new TextComponent("Seconds till playing: "), new TextComponent(""), 0, 120, playingCooldown, 1, 0, true) {
            @Override
            protected void applyValue() {
                playingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        climbingSlider = new Slider(20, 120, 150, 20, new TextComponent("Seconds till climbing: "), new TextComponent(""), 0, 120, climbingCooldown, 1, 0, true) {
            @Override
            protected void applyValue() {
                climbingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        hungerSlider = new Slider(20, 150, 150, 20, new TextComponent("Hunger: "), new TextComponent(""), 0, ((Prehistoric)entity).getMaxHunger(), hunger, 1, 0, true) {
            @Override
            protected void applyValue() {
                hunger = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        moodSlider = new Slider(20, 180, 150, 20, new TextComponent("Mood: "), new TextComponent(""), -100, 100, mood, 1, 0, true) {
            @Override
            protected void applyValue() {
                mood = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        addWidget(ageSlider);
        addWidget(matingSlider);
        addWidget(playingSlider);
        addWidget(climbingSlider);
        addWidget(hungerSlider);
        addWidget(moodSlider);

        addWidget(CycleOption.create("Gender", () -> Arrays.stream(Gender.values()).toList(),
                        Gender::getName, options -> gender, (options, option, gender) -> this.gender = gender)
                .createButton(Minecraft.getInstance().options, 20, 210, 150));
        addWidget(new Button(20, 240, 150, 20, new TextComponent("Set Info"), button -> {
            ((Prehistoric) entity).setGender(gender);
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SSyncDebugInfoMessage(entity.getId(), gender.name(), ageInTicks, matingCooldown, playingCooldown, climbingCooldown, hunger, mood));
        }));
    }

    @Override
    protected void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (entity instanceof Prehistoric prehistoric) {
            drawString(poseStack, minecraft.font, new TextComponent("Age: " + prehistoric.getAge()), 175, 35, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Mate: " + (prehistoric.getMatingCooldown() / 20)), 175, 65, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Play: " + (prehistoric.moodSystem.getPlayingCooldown() / 20)), 175, 95, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Climb: " + (prehistoric.getClimbingCooldown() / 20)), 175, 125, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Hunger: " + prehistoric.getHunger()), 175, 155, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Mood: " + prehistoric.moodSystem.getMood()), 175, 185, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Gender: " + prehistoric.getGender().name()), 175, 215, 16777215);
        }
    }
}
