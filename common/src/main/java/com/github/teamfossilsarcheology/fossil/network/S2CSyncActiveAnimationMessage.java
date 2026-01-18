package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

/**
 * Sync currently active animation from server to clients
 */
public class S2CSyncActiveAnimationMessage {
    private final int entityId;
    private final String controller;
    private final String animationName;
    private final AnimationCategory category;
    private final int ticks;
    private final double speed;
    private final boolean loop;

    private S2CSyncActiveAnimationMessage(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.controller = buf.readUtf();
        this.animationName = buf.readUtf();
        String name = buf.readUtf();
        this.category = AnimationCategory.CATEGORIES.stream().filter(category1 -> category1.name().equals(name)).findFirst().orElse(AnimationCategory.NONE);
        this.ticks = buf.readInt();
        this.speed = buf.readDouble();
        this.loop = buf.readBoolean();
    }

    public S2CSyncActiveAnimationMessage(Entity entity, String controller, AnimationLogic.ActiveAnimationInfo activeAnimationInfo) {
        this.entityId = entity.getId();
        this.controller = controller;
        this.animationName = activeAnimationInfo.animationName();
        this.category = activeAnimationInfo.category();
        this.ticks = activeAnimationInfo.transitionLength();
        this.speed = activeAnimationInfo.speed();
        this.loop = activeAnimationInfo.loop();
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(controller);
        buf.writeUtf(animationName);
        buf.writeUtf(category.name());
        buf.writeInt(ticks);
        buf.writeDouble(speed);
        buf.writeBoolean(loop);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        if (contextSupplier.get().getEnvironment() == Env.SERVER) return;
        contextSupplier.get().queue(() -> {
            if (contextSupplier.get().getPlayer() == null) {//Can happen on world load
                return;
            }
            Entity entity = contextSupplier.get().getPlayer().level().getEntity(entityId);
            if (entity instanceof PrehistoricAnimatable<?> prehistoric) {
                if (prehistoric.getAllAnimations().containsKey(animationName)) {
                    double endTick = entity.level().getGameTime() + prehistoric.getAnimation(animationName).animation.length();
                    AnimationLogic.ActiveAnimationInfo activeAnimationInfo = new AnimationLogic.Builder(animationName, endTick, category)
                            .forced().transitionLength(ticks).speed(speed).loop(loop).build();
                    prehistoric.getAnimationLogic().addNextAnimation(controller, activeAnimationInfo);
                }
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CSyncActiveAnimationMessage.class, S2CSyncActiveAnimationMessage::write, S2CSyncActiveAnimationMessage::new, S2CSyncActiveAnimationMessage::apply);
    }
}
