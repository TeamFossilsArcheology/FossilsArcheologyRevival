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

public class C2SSyncDebugInfoMessage {
    private final int entityId;
    private final String enumString;
    private final int age;
    private final int matingCooldown;
    private final int playingCooldown;
    private final int climbingCooldown;
    private final int hunger;
    private final int mood;

    public C2SSyncDebugInfoMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public C2SSyncDebugInfoMessage(int entityId, String enumString, int age, int matingCooldown, int playingCooldown, int climbingCooldown, int hunger, int mood) {
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
            if (player.level.isClientSide && Minecraft.getInstance().screen instanceof DebugScreen debugScreen) {
                if (debugScreen.entity == null || debugScreen.entity.getId() != entityId) {
                    return;
                }
            }
            if (player.level.getEntity(entityId) instanceof Prehistoric prehistoric) {
                if (Minecraft.getInstance().screen instanceof DebugScreen) {
                    prehistoric.setGender(Gender.valueOf(enumString));
                    prehistoric.setAgeInTicks(age);
                    prehistoric.setMatingCooldown(matingCooldown);
                    prehistoric.moodSystem.setPlayingCooldown(playingCooldown);
                    prehistoric.setClimbingCooldown(climbingCooldown);
                    prehistoric.setHunger(hunger);
                    prehistoric.moodSystem.setMood(mood);
                    prehistoric.refreshTexturePath();
                    prehistoric.refreshDimensions();
                    prehistoric.updateAbilities();
                }
            } else if (player.level.getEntity(entityId) instanceof PrehistoricSkeleton fossil) {
                    fossil.setType(PrehistoricEntityInfo.valueOf(enumString));
                    fossil.setAge(age);
                }
            }
        });
    }
}
