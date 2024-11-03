package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.C2STameMessage;
import com.github.teamfossilsarcheology.fossil.network.debug.SyncDebugInfoMessage;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;

import java.text.DecimalFormat;
import java.util.Arrays;

public class InfoTab extends DebugTab<Prehistoric> {
    private final int maxAgeInTicks;
    private Gender gender;
    private int ageInTicks;
    private double scaleOverride;
    private int matingCooldown;
    private int playingCooldown;
    private int climbingCooldown;
    private int hunger;
    private int mood;
    private DebugSlider ageSlider;
    private DebugSlider scaleSlider;
    private DebugSlider matingSlider;
    private DebugSlider playingSlider;
    private DebugSlider climbingSlider;
    private DebugSlider hungerSlider;
    private DebugSlider moodSlider;

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
        int yPos = 0;
        ageSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Age in ticks: "), new TextComponent(""), 0, maxAgeInTicks, ageInTicks, 12000, 0, true) {
            @Override
            protected void applyValue() {
                ageInTicks = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }

            @Override
            public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
                super.render(poseStack, mouseX, mouseY, partialTick);
                int j = active ? 16777215 : 10526880;
                EntityDataLoader.Data data = entity.data();
                DecimalFormat format = new DecimalFormat("0.00");
                float step = (data.maxScale() - data.minScale()) / ((data.adultAgeDays() * 24000) + 1);
                String min = format.format(data.minScale() + step * minValue);
                String max = format.format(data.minScale() + step * maxValue);
                drawString(poseStack, minecraft.font, min, x, y - 8, j | Mth.ceil(alpha * 255.0F) << 24);
                drawString(poseStack, minecraft.font, max, x + width - minecraft.font.width(max), y - 8, j | Mth.ceil(alpha * 255.0F) << 24);
            }
        };
        addWidget(new Button(275, 30, 150, 20, new TextComponent("Scale 1"), button -> {
            EntityDataLoader.Data data = entity.data();
            ageInTicks = (int) (((1 - data.minScale()) * (data.adultAgeDays() * 24000 + 1)) / (data.maxScale() - data.minScale()));
            ageSlider.setValue(ageInTicks);
        }));
        addWidget(new Button(275, 55, 50, 20, new TextComponent("Tame"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2STameMessage(entity.getId()));
        }));
        scaleSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Scale: "), new TextComponent(""), 0.1, Math.max(2.5, entity.data().maxScale()), entity.getScale(), 0.05, 2, true) {
            @Override
            protected void applyValue() {
                scaleOverride = (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                entity.setScaleOverride((float) scaleOverride);
            }

            @Override
            public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
                super.render(poseStack, mouseX, mouseY, partialTick);
                int j = active ? 16777215 : 10526880;
                EntityDataLoader.Data data = entity.data();
                String min = String.valueOf(0);
                int minX = (int) (((data.minScale() - minValue) / (maxValue - minValue)) * width);
                String max = String.valueOf(entity.data().adultAgeDays());
                int maxX = (int) (((data.maxScale() - minValue) / (maxValue - minValue)) * (width - minecraft.font.width(max)));
                drawString(poseStack, minecraft.font, min, x + minX, y - 8, j | Mth.ceil(alpha * 255.0F) << 24);
                drawString(poseStack, minecraft.font, max, x + maxX, y - 8, j | Mth.ceil(alpha * 255.0F) << 24);
            }
        };
        matingSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Seconds till mating: "), new TextComponent(""), 0, 900, matingCooldown / 20f, 1, 0, true) {
            @Override
            protected void applyValue() {
                matingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        playingSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Seconds till playing: "), new TextComponent(""), 0, 120, playingCooldown / 20f, 1, 0, true) {
            @Override
            protected void applyValue() {
                playingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        climbingSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Seconds till climbing: "), new TextComponent(""), 0, 120, climbingCooldown / 20f, 1, 0, true) {
            @Override
            protected void applyValue() {
                climbingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        hungerSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Hunger: "), new TextComponent(""), 0, entity.getMaxHunger(), hunger, 1, 0, true) {
            @Override
            protected void applyValue() {
                hunger = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        moodSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Mood: "), new TextComponent(""), -100, 100, mood, 1, 0, true) {
            @Override
            protected void applyValue() {
                mood = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        addWidget(ageSlider);
        addWidget(scaleSlider);
        addWidget(matingSlider);
        addWidget(playingSlider);
        addWidget(climbingSlider);
        addWidget(hungerSlider);
        addWidget(moodSlider);

        addWidget(CycleOption.create("Gender", () -> Arrays.stream(Gender.values()).toList(),
                        Gender::getName, options -> gender, (options, option, gender) -> this.gender = gender)
                .createButton(Minecraft.getInstance().options, 20, yPos += 30, 150));
        addWidget(new Button(20, yPos += 30, 150, 20, new TextComponent("Set Info"), button -> {
            entity.setGender(gender);
            entity.setScaleOverride(-1);
            MessageHandler.DEBUG_CHANNEL.sendToServer(new SyncDebugInfoMessage(entity.getId(), gender.name(), ageInTicks, matingCooldown, playingCooldown, climbingCooldown, hunger, mood));
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Set the info above on the server"), i, j);
        }));
    }

    @Override
    protected void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        int yPos = 5;
        drawString(poseStack, minecraft.font, new TextComponent("Age: " + entity.getAge()), 175, yPos += 30, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Scale: " + entity.getScale()), 175, yPos += 30, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Mate: " + (entity.getMatingCooldown() / 20)), 175, yPos += 30, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Play: " + (entity.moodSystem.getPlayingCooldown() / 20)), 175, yPos += 30, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Climb: " + (entity.getClimbingCooldown() / 20)), 175, yPos += 30, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Hunger: " + entity.getHunger()), 175, yPos += 30, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Mood: " + entity.moodSystem.getMood()), 175, yPos += 30, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Gender: " + entity.getGender().name()), 175, yPos += 30, 16777215);
    }
}
