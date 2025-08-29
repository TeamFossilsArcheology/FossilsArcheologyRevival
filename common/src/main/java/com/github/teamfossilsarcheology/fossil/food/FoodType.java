package com.github.teamfossilsarcheology.fossil.food;

import net.minecraft.network.FriendlyByteBuf;

public enum FoodType {
    MEAT(20, 7), PLANT(17, 5), FISH(10, 7), EGG(15, 7);
    private final int fallback;
    private final int multiplier;

    FoodType(int fallback, int multiplier) {
        this.fallback = fallback;
        this.multiplier = multiplier;
    }

    public int fallback() {
        return fallback;
    }

    public int multiplier() {
        return multiplier;
    }

    public static FoodType readBuf(FriendlyByteBuf buf) {
        return FoodType.valueOf(buf.readUtf());
    }

    public static void writeBuf(FriendlyByteBuf buf, FoodType type) {
        buf.writeUtf(type.name());
    }

}
