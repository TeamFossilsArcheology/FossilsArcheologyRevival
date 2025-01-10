package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Nautilus;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.animal.AbstractFish;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin {

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/animal/AbstractFish;onGround:Z", opcode = Opcodes.GETFIELD))
    private boolean preventNautilusBounce(boolean original) {
        //Prevent bouncing
        return !(((AbstractFish) (Object) (this)) instanceof Nautilus) && original;
    }
}
