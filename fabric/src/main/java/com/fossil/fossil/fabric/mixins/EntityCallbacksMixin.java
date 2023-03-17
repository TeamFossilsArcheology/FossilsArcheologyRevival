package com.fossil.fossil.fabric.mixins;


import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.fabric.MultiPartServerLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fabric has no PartEntity so we mixin our own
 *
 * @see ServerLevelMixin
 * @see MultiPartServerLevel
 * @see LevelMixin
 */
@Mixin(targets = "net/minecraft/server/level/ServerLevel$EntityCallbacks")
public abstract class EntityCallbacksMixin {

    @Final
    @Shadow
    ServerLevel field_26936;

    @Inject(method = "onTrackingStart(Lnet/minecraft/world/entity/Entity;)V", at = @At(value = "RETURN"))
    public void addMultiPart(Entity entity, CallbackInfo ci) {
        if (entity instanceof Prehistoric prehistoric) {
            for (Entity part : prehistoric.getCustomParts()) {
                ((MultiPartServerLevel) field_26936).addMultiPart(part);
            }
        }
    }
    @Inject(method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V", at = @At(value = "RETURN"))
    public void removeMultiPart(Entity entity, CallbackInfo ci) {
        if (entity instanceof Prehistoric prehistoric) {
            for (Entity part : prehistoric.getCustomParts()) {
                ((MultiPartServerLevel) field_26936).removeMultiPart(part);
            }
        }
    }
}