package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.ChatFormatting;

public enum PrehistoricMoodType {
    ANGRY(0, -71, ChatFormatting.DARK_RED), SAD(48, -36, ChatFormatting.GOLD), CALM(95, 0, ChatFormatting.YELLOW), CONTENT(142, 36, ChatFormatting.GREEN), HAPPY(190, 71, ChatFormatting.DARK_GREEN);
    public final int uv;
    public final int value;
    public final ChatFormatting color;


    PrehistoricMoodType(int uv, int value, ChatFormatting color) {
        this.uv = uv;
        this.value = value;
        this.color = color;
    }
}
