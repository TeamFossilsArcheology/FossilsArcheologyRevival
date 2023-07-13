package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.InfoMessage;
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
    private int ticksTillMate;
    private int ticksTillPlay;
    private int mood;

    protected InfoTab(DebugScreen debugScreen, Prehistoric prehistoric) {
        super(debugScreen, prehistoric);
        this.maxAgeInTicks = prehistoric.data().adultAgeDays() * 24000;
        this.gender = prehistoric.getGender();
        this.ageInTicks = prehistoric.getAgeInTicks();
        this.ticksTillMate = prehistoric.getMatingTick();
        this.ticksTillPlay = prehistoric.getPlayingTick();
        this.mood = prehistoric.getMood();
    }

    @Override
    protected void init(int width, int height) {
        super.init(width, height);
        addWidget(new Slider(20, 30, 150, 20, new TextComponent("Age in ticks: "), new TextComponent(""), 0, maxAgeInTicks, ageInTicks, 12000, 0, true) {
            @Override
            protected void applyValue() {
                ageInTicks = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        });
        addWidget(new Slider(20, 60, 150, 20, new TextComponent("Seconds till mating: "), new TextComponent(""), 0, 300, ticksTillMate, 1, 0, true) {
            @Override
            protected void applyValue() {
                ticksTillMate = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        });
        addWidget(new Slider(20, 90, 150, 20, new TextComponent("Seconds till playing: "), new TextComponent(""), 0, 120, ticksTillPlay, 1, 0, true) {
            @Override
            protected void applyValue() {
                ticksTillPlay = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize) * 20);
            }
        });
        addWidget(new Slider(20, 120, 150, 20, new TextComponent("Mood: "), new TextComponent(""), -100, 100, mood, 1, 0, true) {
            @Override
            protected void applyValue() {
                mood = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        });

        addWidget(CycleOption.create("Gender", () -> Arrays.stream(Gender.values()).toList(),
                        Gender::getName, options -> gender, (options, option, gender) -> this.gender = gender)
                .createButton(Minecraft.getInstance().options, 20, 150, 150));
        addWidget(new Button(20, 180, 150, 20, new TextComponent("Set Info"), button -> {
            ((Prehistoric) entity).setGender(gender);
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InfoMessage(entity.getId(), gender.name(), ageInTicks, ticksTillMate, ticksTillPlay, mood));
        }));
    }

    @Override
    protected void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (entity instanceof Prehistoric prehistoric) {
            drawString(poseStack, minecraft.font, new TextComponent("Age: " + prehistoric.getAgeInTicks()), 175, 35, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Mate: " + (prehistoric.getMatingTick() / 20)), 175, 65, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Play: " + (prehistoric.getPlayingTick() / 20)), 175, 95, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Mood: " + prehistoric.getMood()), 175, 125, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Gender: " + prehistoric.getGender().name()), 175, 155, 16777215);
        }
    }
}
