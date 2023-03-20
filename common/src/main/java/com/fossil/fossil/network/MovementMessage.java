package com.fossil.fossil.network;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class MovementMessage {
    private final int id;
    private final double x;
    private final double y;
    private final double z;

    public MovementMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public MovementMessage(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public MovementMessage(int id, Vec3 target) {
        this.id = id;
        this.x = target.x();
        this.y = target.y();
        this.z = target.z();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        Entity entity = contextSupplier.get().getPlayer().level.getEntity(id);
        contextSupplier.get().queue(() -> {
            if (entity instanceof Prehistoric mob) {
                Path path = mob.getNavigation().createPath(x, y, z, 1);
                mob.getNavigation().moveTo(path, 1);
            }
        });
    }
}
