package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
    private static void renderMultipart(PoseStack matrixStack, VertexConsumer buffer, Entity entity, float partialTicks, CallbackInfo ci) {
        if (entity instanceof Prehistoric) {
            ci.cancel();
            //Unchanged original code
            AABB aABB = entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ());
            LevelRenderer.renderLineBox(matrixStack, buffer, aABB, 1.0f, 1.0f, 1.0f, 1.0f);

            //Changed code
            double d = -Mth.lerp(partialTicks, entity.xOld, entity.getX());
            double e = -Mth.lerp(partialTicks, entity.yOld, entity.getY());
            double f = -Mth.lerp(partialTicks, entity.zOld, entity.getZ());
            int kj = 0;
            for (Entity part : ((Prehistoric)entity).getPartsF()) {
                matrixStack.pushPose();
                double g = d + Mth.lerp(partialTicks, part.xOld, part.getX());
                double h = e + Mth.lerp(partialTicks, part.yOld, part.getY());
                double i = f + Mth.lerp(partialTicks, part.zOld, part.getZ());
                matrixStack.translate(g, h, i);
                if (kj == 0) {
                    LevelRenderer.renderLineBox(matrixStack, buffer, part.getBoundingBox().move(-part.getX(), -part.getY(), -part.getZ()), 0f, 1.0f, 1.0f, 1.0f);
                } else if (kj == 1) {
                    LevelRenderer.renderLineBox(matrixStack, buffer, part.getBoundingBox().move(-part.getX(), -part.getY(), -part.getZ()), 0f, 1.0f, 0.0f, 1.0f);
                } else {
                    LevelRenderer.renderLineBox(matrixStack, buffer, part.getBoundingBox().move(-part.getX(), -part.getY(), -part.getZ()), 1f, 0f, 0.0f, 1.0f);
                }
                matrixStack.popPose();
                kj++;
            }

            //Unchanged original code
            Vec3 vec3 = entity.getViewVector(partialTicks);
            Matrix4f matrix4f = matrixStack.last().pose();
            Matrix3f matrix3f = matrixStack.last().normal();
            buffer.vertex(matrix4f, 0.0f, entity.getEyeHeight(), 0.0f).color(0, 0, 255, 255).normal(matrix3f, (float)vec3.x, (float)vec3.y, (float)vec3.z).endVertex();
            buffer.vertex(matrix4f, (float)(vec3.x * 2.0), (float)((double)entity.getEyeHeight() + vec3.y * 2.0), (float)(vec3.z * 2.0)).color(0, 0, 255, 255).normal(matrix3f, (float)vec3.x, (float)vec3.y, (float)vec3.z).endVertex();
        }
    }
}
