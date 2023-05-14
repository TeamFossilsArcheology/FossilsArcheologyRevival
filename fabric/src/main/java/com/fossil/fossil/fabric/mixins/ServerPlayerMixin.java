package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fabric has no PartEntity so we mixin our own
 *
 * @see ServerLevelMixin
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Shadow
    private Entity camera;

    @Shadow public abstract Entity getCamera();

    @Redirect(method = "setCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/server/level/ServerPlayer;camera:Lnet/minecraft/world/entity/Entity;", opcode = Opcodes.PUTFIELD))
    public void setCameraNoMultiPart(ServerPlayer instance, Entity entityToSpectate) {
        Entity entity = getCamera();
        if (PrehistoricPart.isMultiPart(entity)) {
            camera = PrehistoricPart.getParent(entity);
        }
        if (camera == null) {
            camera = entityToSpectate == null ? this.getCamera() : entityToSpectate;
        }
    }
}
