package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.entity.monster.TarSlime;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ServerEntity.class)
public class ServerEntityMixin {

    @Shadow
    @Final
    private Entity entity;

    @WrapOperation(method = "sendChanges", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 0))
    public void sendPassengersToPlayerIfPlayerIsVehicle(Consumer<Packet<?>> instance, Object t, Operation<Void> original) {
        if (entity instanceof ServerPlayer serverPlayer && fossilsArcheologyRevival$hasFAMobAsPassenger(serverPlayer.getPassengers())) {
            serverPlayer.connection.send((Packet<?>) t);
        }
        original.call(instance, t);
    }

    @Unique
    private boolean fossilsArcheologyRevival$hasFAMobAsPassenger(List<Entity> passengers) {
        return passengers.stream().anyMatch(entity1 -> entity1 instanceof PrehistoricLeaping || entity1 instanceof TarSlime);
    }
}
