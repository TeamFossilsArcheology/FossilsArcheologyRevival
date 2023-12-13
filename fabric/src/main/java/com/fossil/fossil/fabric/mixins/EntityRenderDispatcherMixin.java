package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Used for debugging multiple hitboxes
 */
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Inject(method = "renderHitbox", at = @At(value = "HEAD"), cancellable = true)
    private static void renderMultipartHitbox(PoseStack poseStack, VertexConsumer buffer, Entity entity, float partialTicks, CallbackInfo ci) {
        if (entity instanceof PrehistoricPart) {
            ci.cancel();
            AABB aABB = entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ());
            LevelRenderer.renderLineBox(poseStack, buffer, aABB, 0, 1, 1, 1);
        }
    }
}
