package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileUtil.class)
public abstract class ProjectileUtilMixin {

    @Inject(method = "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;", at = @At("RETURN"), cancellable = true)
    private static void modifyPartEntity(CallbackInfoReturnable<EntityHitResult> cir) {
        if (cir.getReturnValue() == null) {
            return;
        }
        Entity entity = cir.getReturnValue().getEntity();
        if (entity instanceof PrehistoricPart part) {
            cir.setReturnValue(new EntityHitResult(part.getParent(), cir.getReturnValue().getLocation()));
        }
    }
}
