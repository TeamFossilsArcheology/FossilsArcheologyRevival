package com.fossil.fossil.network.debug;

import com.fossil.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

import java.util.function.Supplier;

public class C2SMoveMessage {
    private final int entityId;
    private final double x;
    private final double y;
    private final double z;

    public C2SMoveMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public C2SMoveMessage(int entityId, double x, double y, double z) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
        contextSupplier.get().queue(() -> {
            if (Version.debugEnabled() && entity instanceof Mob mob) {
                mob.getNavigation().stop();
                mob.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
                mob.getNavigation().moveTo(x, y, z, 1);
                mob.getMoveControl().setWantedPosition(x, y, z, 1);
                mob.getLookControl().setLookAt(x, y, z, 180, 180);
            }
        });
    }
}
