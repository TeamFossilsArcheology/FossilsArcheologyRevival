package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Unique
    private Entity fossilsArcheologyRevival$multiPart;

    @ModifyVariable(method = "attack", argsOnly = true, at = @At("HEAD"))
    private Entity replacePartWithParent(Entity target) {
        if (target instanceof MultiPart part) {
            fossilsArcheologyRevival$multiPart = part.getEntity();
            return part.getParent();
        }
        return target;
    }

    //TODO: Replace with WRAP_OPERATIOn from MixinExtras
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean attackMultiPart(Entity target, DamageSource source, float amount) {
        if (target instanceof Prehistoric && fossilsArcheologyRevival$multiPart != null) {
            return fossilsArcheologyRevival$multiPart.hurt(source, amount);
        } else {
            return target.hurt(source, amount);
        }
    }
}
