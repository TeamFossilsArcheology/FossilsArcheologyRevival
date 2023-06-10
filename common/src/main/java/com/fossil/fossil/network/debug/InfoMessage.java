package com.fossil.fossil.network.debug;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Gender;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class InfoMessage {
    private final int entityId;
    private final String gender;
    private final int ageInTicks;
    private final int ticksTillMate;
    private final int ticksTillPlay;
    private final int mood;

    public InfoMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public InfoMessage(int entityId, String gender, int ageInTicks, int ticksTillMate, int ticksTillPlay, int mood) {
        this.entityId = entityId;
        this.gender = gender;
        this.ageInTicks = ageInTicks;
        this.ticksTillMate = ticksTillMate;
        this.ticksTillPlay = ticksTillPlay;
        this.mood = mood;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(gender);
        buf.writeInt(ageInTicks);
        buf.writeInt(ticksTillMate);
        buf.writeInt(ticksTillPlay);
        buf.writeInt(mood);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof Prehistoric prehistoric) {
            contextSupplier.get().queue(() -> {
                prehistoric.setGender(Gender.valueOf(gender));
                prehistoric.setAgeinTicks(ageInTicks);
                prehistoric.setMatingTick(ticksTillMate);
                prehistoric.setPlayingTick(ticksTillPlay);
                prehistoric.setMood(mood);
            });
        }
    }
}
