package com.fossil.fossil.network.debug;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Gender;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class SyncDebugInfoMessage {
    private final int entityId;
    private final String gender;
    private final int ageInTicks;
    private final int matingCooldown;
    private final int playingCooldown;
    private final int climbingCooldown;
    private final int mood;

    public SyncDebugInfoMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public SyncDebugInfoMessage(int entityId, String gender, int ageInTicks, int matingCooldown, int playingCooldown, int climbingCooldown, int mood) {
        this.entityId = entityId;
        this.gender = gender;
        this.ageInTicks = ageInTicks;
        this.matingCooldown = matingCooldown;
        this.playingCooldown = playingCooldown;
        this.climbingCooldown = climbingCooldown;
        this.mood = mood;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(gender);
        buf.writeInt(ageInTicks);
        buf.writeInt(matingCooldown);
        buf.writeInt(playingCooldown);
        buf.writeInt(climbingCooldown);
        buf.writeInt(mood);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Player player = contextSupplier.get().getPlayer();
        if (player != null && player.level.getEntity(entityId) instanceof Prehistoric prehistoric) {
            contextSupplier.get().queue(() -> {
                prehistoric.setGender(Gender.valueOf(gender));
                prehistoric.setAgeInTicks(ageInTicks);
                prehistoric.setMatingCooldown(matingCooldown);
                prehistoric.moodSystem.setPlayingCooldown(playingCooldown);
                prehistoric.setClimbingCooldown(climbingCooldown);
                prehistoric.moodSystem.setMood(mood);
                prehistoric.refreshTexturePath();
                prehistoric.refreshDimensions();
                prehistoric.updateAbilities();
            });
        }
    }
}
