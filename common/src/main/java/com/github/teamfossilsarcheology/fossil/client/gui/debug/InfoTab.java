package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.C2STameMessage;
import com.github.teamfossilsarcheology.fossil.network.debug.SyncDebugInfoMessage;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
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
        this.maxAgeInTicks = prehistoric.data().adultAgeInTicks();
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
        ageSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Age in ticks: "), Component.literal(""), 0, maxAgeInTicks, ageInTicks, 12000, 0, true) {
            @Override
            protected void applyValue() {
                ageInTicks = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }

            @Override
            public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                super.render(guiGraphics, mouseX, mouseY, partialTick);
                int j = active ? 16777215 : 10526880;
                EntityDataLoader.Data data = entity.data();
                DecimalFormat format = new DecimalFormat("0.00");
                float step = (data.maxScale() - data.minScale()) / ((data.adultAgeInTicks()) + 1);
                String min = format.format(data.minScale() + step * minValue);
                String max = format.format(data.minScale() + step * maxValue);
                guiGraphics.drawString(minecraft.font, min, getX(), getY() - 8, j | Mth.ceil(alpha * 255.0F) << 24);
                guiGraphics.drawString(minecraft.font, max, getX() + width - minecraft.font.width(max), getY() - 8, j | Mth.ceil(alpha * 255.0F) << 24);
            }
        };
        addWidget(Button.builder(Component.literal("Scale 1"), button -> {
            EntityDataLoader.Data data = entity.data();
            ageInTicks = (int) (((1 - data.minScale()) * (data.adultAgeInTicks() + 1)) / (data.maxScale() - data.minScale()));
            ageSlider.setValue(ageInTicks);
        }).bounds(275, 30, 150, 20).build());
        addWidget(Button.builder(Component.literal("Tame"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2STameMessage(entity.getId()));
        }).bounds(275, 55, 50, 20).build());
        scaleSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Scale: "), Component.literal(""), 0.1, Math.max(2.5, entity.data().maxScale()), entity.getScale(), 0.05, 2, true) {
            @Override
            protected void applyValue() {
                scaleOverride = (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                entity.setScaleOverride((float) scaleOverride);
            }

            @Override
            public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                super.render(guiGraphics, mouseX, mouseY, partialTick);
                int j = active ? 16777215 : 10526880;
                EntityDataLoader.Data data = entity.data();
                String min = String.valueOf(0);
                int minX = (int) (((data.minScale() - minValue) / (maxValue - minValue)) * width);
                String max = String.valueOf(entity.data().adultAgeDays());
                int maxX = (int) (((data.maxScale() - minValue) / (maxValue - minValue)) * (width - minecraft.font.width(max)));
                guiGraphics.drawString(minecraft.font, min, getX() + minX, getY() - 8, j | Mth.ceil(alpha * 255.0F) << 24);
                guiGraphics.drawString(minecraft.font, max, getX() + maxX, getY() - 8, j | Mth.ceil(alpha * 255.0F) << 24);
            }
        };
        matingSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Seconds till mating: "), Component.literal(""), 0, 900, matingCooldown / 20f, 1, 0, true) {
            @Override
            protected void applyValue() {
                matingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        playingSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Seconds till playing: "), Component.literal(""), 0, 120, playingCooldown / 20f, 1, 0, true) {
            @Override
            protected void applyValue() {
                playingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        climbingSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Seconds till climbing: "), Component.literal(""), 0, 120, climbingCooldown / 20f, 1, 0, true) {
            @Override
            protected void applyValue() {
                climbingCooldown = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        };
        hungerSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Hunger: "), Component.literal(""), 0, entity.getMaxHunger(), hunger, 1, 0, true) {
            @Override
            protected void applyValue() {
                hunger = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        moodSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Mood: "), Component.literal(""), -100, 100, mood, 1, 0, true) {
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

        addWidget(CycleButton.builder(Gender::getName).withValues(Arrays.stream(Gender.values()).toList())
                .withInitialValue(gender).create(20, yPos += 30, 150, 20, Component.literal("Gender"),
                        (cycleButton, gender) -> this.gender = gender));
        addWidget(Button.builder(Component.literal("Set Info"), button -> {
                    entity.setGender(gender);
                    entity.setScaleOverride(-1);
                    MessageHandler.DEBUG_CHANNEL.sendToServer(new SyncDebugInfoMessage(entity.getId(), gender.name(), ageInTicks, matingCooldown, playingCooldown, climbingCooldown, hunger, mood));
                })
                .bounds(20, yPos += 30, 150, 20)
                .tooltip(Tooltip.create(Component.literal("Set the info above on the server")))
                .build());
    }

    @Override
    protected void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int yPos = 5;
        guiGraphics.drawString(minecraft.font, Component.literal("Age: " + entity.getAge()), 175, yPos += 30, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Scale: " + entity.getScale()), 175, yPos += 30, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Mate: " + (entity.getMatingCooldown() / 20)), 175, yPos += 30, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Play: " + (entity.moodSystem.getPlayingCooldown() / 20)), 175, yPos += 30, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Climb: " + (entity.getClimbingCooldown() / 20)), 175, yPos += 30, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Hunger: " + entity.getHunger()), 175, yPos += 30, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Mood: " + entity.moodSystem.getMood()), 175, yPos += 30, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Gender: " + entity.getGender().name()), 175, yPos += 30, 16777215);
    }
}
