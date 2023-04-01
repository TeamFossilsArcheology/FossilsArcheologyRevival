package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Fabric has no PartEntity so we mixin our own
 *
 * @see ServerLevelMixin
 */
@Mixin(Player.class)
public abstract class PlayerMixin {

    @ModifyVariable(method = "attack", at = @At(value = "STORE"), ordinal = 1)//Can't use the name of the variable because that crashes in production
    private Entity replaceHurtEntity(Entity value) {
        if (PrehistoricPart.isMultiPart(value)) {
            return PrehistoricPart.getParent(value);
        }
        return value;
    }
}
