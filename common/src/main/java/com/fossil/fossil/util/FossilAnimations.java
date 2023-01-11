package com.fossil.fossil.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

public enum FossilAnimations {
    IDLE,
    WALK,
    SLEEP,
    SLEEP_BABY,
    THREAT,
    ATTACK1,
    ATTACK2,
    EAT;

    public static final EntityDataSerializer<FossilAnimations> SERIALIZER = new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf buffer, FossilAnimations value) {
            for (int i = 0; i < values().length; i++) {
                if (values()[i] == value) {
                    buffer.writeInt(i);
                    return;
                }
            }
            throw new IllegalStateException("Unable to serialize animation!");
        }

        @Override
        public @NotNull FossilAnimations read(FriendlyByteBuf buffer) {
            return values()[buffer.readInt()];
        }

        @Override
        public @NotNull FossilAnimations copy(FossilAnimations value) {
            return value; // It's enum, so no copying
        }
    };
}
