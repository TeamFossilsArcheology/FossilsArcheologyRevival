package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fabric has no PartEntity so we mixin our own
 *
 * @see ServerLevelMixin
 */
@Mixin(Player.class)
public abstract class PlayerMixin {

    private Entity multiPart;

    @ModifyVariable(method = "attack", argsOnly = true, at = @At("HEAD"))
    private Entity replacePartWithParent(Entity target) {
        if (target instanceof MultiPart part) {
            multiPart = part.getEntity();
            return part.getParent();
        }
        return target;
    }

    //TODO: Replace with WRAP_OPERATIOn from MixinExtras
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean attackMultiPart(Entity target, DamageSource source, float amount) {
        if (target instanceof Prehistoric && multiPart != null) {
            return multiPart.hurt(source, amount);
        } else {
            return target.hurt(source, amount);
        }
    }
}
