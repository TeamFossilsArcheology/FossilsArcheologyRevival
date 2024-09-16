package com.fossil.fossil.network.debug;

import com.fossil.fossil.client.gui.debug.DebugScreen;
import com.fossil.fossil.entity.PrehistoricSkeleton;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.util.Gender;
import com.fossil.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class SyncDebugInfoMessage {
    private final int entityId;
    private final String enumString;
    private final int age;
    private final int matingCooldown;
    private final int playingCooldown;
    private final int climbingCooldown;
    private final int hunger;
    private final int mood;

    public SyncDebugInfoMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public SyncDebugInfoMessage(int entityId, String enumString, int age, int matingCooldown, int playingCooldown, int climbingCooldown, int hunger, int mood) {
        this.entityId = entityId;
        this.enumString = enumString;
        this.age = age;
        this.matingCooldown = matingCooldown;
        this.playingCooldown = playingCooldown;
        this.climbingCooldown = climbingCooldown;
        this.hunger = hunger;
        this.mood = mood;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(enumString);
        buf.writeInt(age);
        buf.writeInt(matingCooldown);
        buf.writeInt(playingCooldown);
        buf.writeInt(climbingCooldown);
        buf.writeInt(hunger);
        buf.writeInt(mood);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            Player player = contextSupplier.get().getPlayer();
            if (Version.debugEnabled() && player != null) {
                if (player.level.isClientSide) {
                    if (Minecraft.getInstance().screen instanceof DebugScreen) {
                        if (DebugScreen.entity == null || DebugScreen.entity.getId() != entityId) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                if (player.level.getEntity(entityId) instanceof Prehistoric prehistoric) {
                    if (!player.level.isClientSide) {
                        prehistoric.setAgeInTicks(age);
                        prehistoric.setHunger(hunger);
                        prehistoric.moodSystem.setMood(mood);
                    }
                    prehistoric.setGender(Gender.valueOf(enumString));
                    prehistoric.setMatingCooldown(matingCooldown);
                    prehistoric.moodSystem.setPlayingCooldown(playingCooldown);
                    prehistoric.setClimbingCooldown(climbingCooldown);
                    prehistoric.refreshTexturePath();
                    prehistoric.refreshDimensions();
                    prehistoric.updateAbilities();
                } else if (player.level.getEntity(entityId) instanceof PrehistoricSkeleton fossil) {
                    fossil.setType(PrehistoricEntityInfo.valueOf(enumString));
                    fossil.setAge(age);
                }
            }
        });
    }
}
