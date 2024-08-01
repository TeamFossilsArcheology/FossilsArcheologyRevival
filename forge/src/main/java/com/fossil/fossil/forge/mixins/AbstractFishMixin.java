package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.entity.prehistoric.fish.Nautilus;
import net.minecraft.world.entity.animal.AbstractFish;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin {

    @Redirect(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/animal/AbstractFish;onGround:Z", opcode = Opcodes.GETFIELD))
    private boolean addMultiPartOnTrackingStart(AbstractFish abstractFish) {
        //Prevent bouncing
        return abstractFish instanceof Nautilus ? false : abstractFish.isOnGround();
    }
}
