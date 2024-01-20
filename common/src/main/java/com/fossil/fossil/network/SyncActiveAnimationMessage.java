package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class SyncActiveAnimationMessage {
    private final int entityId;
    private final String controller;
    private final CompoundTag animationTag;

    public SyncActiveAnimationMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.controller = buf.readUtf();
        this.animationTag = new CompoundTag();
        animationTag.putString("Animation", buf.readUtf());
        animationTag.putDouble("EndTick", buf.readDouble());
        animationTag.putString("Category", buf.readUtf());
        animationTag.putBoolean("Forced", buf.readBoolean());
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
        buf.writeDouble(animationTag.getDouble("EndTick"));
        buf.writeUtf(animationTag.getString("Category"));
        buf.writeBoolean(animationTag.getBoolean("Forced"));
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof PrehistoricAnimatable<?> prehistoric) {
            contextSupplier.get().queue(() -> prehistoric.getAnimationLogic().addActiveAnimation(controller, animationTag));
        }
    }
}
