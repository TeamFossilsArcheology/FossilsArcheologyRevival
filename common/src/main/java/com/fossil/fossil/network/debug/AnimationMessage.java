package com.fossil.fossil.network.debug;

import com.fossil.fossil.entity.animation.AttackAnimationLogic;
import com.fossil.fossil.entity.prehistoric.Nautilus;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class AnimationMessage {
    private final int entityId;
    private final String animation;

    public AnimationMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf());
    }

    public AnimationMessage(int entityId, String animation) {
        this.entityId = entityId;
        this.animation = animation;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(animation);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        if (entity instanceof PrehistoricAnimatable prehistoric) {
            contextSupplier.get().queue(() -> {
                if (prehistoric instanceof Nautilus && animation.startsWith("shell")) {
                    prehistoric.addActiveAnimation("Shell", prehistoric.getAllAnimations().get(animation));
                } else if (prehistoric.getAllAnimations().get(animation) instanceof AttackAnimationLogic.ServerAttackAnimationInfo) {
                    prehistoric.addActiveAnimation("Attack", prehistoric.getAllAnimations().get(animation));
                } else {
                    prehistoric.addActiveAnimation("Movement/Idle/Eat", prehistoric.getAllAnimations().get(animation));
                }
            });
        }
    }
}
