package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.util.DinopediaInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum PrehistoricMoodType implements DinopediaInfo {
    ANGRY(0, -71, ChatFormatting.DARK_RED),
    SAD(48, -36, ChatFormatting.GOLD),
    CALM(95, 0, ChatFormatting.YELLOW),
    CONTENT(142, 36, ChatFormatting.GREEN),
    HAPPY(190, 71, ChatFormatting.DARK_GREEN);
    public final int uOffset;
    public final int value;
    public final ChatFormatting color;

    PrehistoricMoodType(int uOffset, int value, ChatFormatting color) {
        this.uOffset = uOffset;
        this.value = value;
        this.color = color;
    }

    @Override
    public Component getName() {
        return new TranslatableComponent("pedia.fossil.mood." + name().toLowerCase()).withStyle(style -> style.withColor(color));
    }

    @Override
    public Component getDescription() {
        return new TranslatableComponent("pedia.fossil.mood.desc." + name().toLowerCase()).withStyle(style -> style.withColor(ChatFormatting.GRAY));
    }
}
