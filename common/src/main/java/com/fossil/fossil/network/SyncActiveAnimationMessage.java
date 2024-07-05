package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

/**
 * Sync active animation from server to clients
 */
public class SyncActiveAnimationMessage {
    private final int entityId;
    private final String controller;
    private final CompoundTag animationTag;

    public SyncActiveAnimationMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.controller = buf.readUtf();
        this.animationTag = new CompoundTag();
        animationTag.putString("Animation", buf.readUtf());
        animationTag.putDouble("StartTick", buf.readDouble());
        animationTag.putString("Category", buf.readUtf());
        animationTag.putBoolean("Forced", buf.readBoolean());
        animationTag.putDouble("Speed", buf.readDouble());
    }

    public SyncActiveAnimationMessage(int entityId, String controller, CompoundTag animationTag) {
        this.entityId = entityId;
        this.controller = controller;
        this.animationTag = animationTag;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(controller);
        buf.writeUtf(animationTag.getString("Animation"));
        buf.writeDouble(animationTag.getDouble("StartTick"));
        buf.writeUtf(animationTag.getString("Category"));
        buf.writeBoolean(animationTag.getBoolean("Forced"));
        buf.writeDouble(animationTag.getDouble("Speed"));
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof PrehistoricAnimatable<?> prehistoric) {
            animationTag.putDouble("EndTick", animationTag.getDouble("StartTick") + prehistoric.getAllAnimations().get(animationTag.getString("Animation")).animationLength);
            contextSupplier.get().queue(() -> prehistoric.getAnimationLogic().addActiveAnimation(controller, animationTag));
        }
    }
}
