package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.data.EntityHitboxManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import com.fossil.fossil.util.Version;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * Used for debugging multiple hitboxes
 */
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Inject(method = "renderHitbox", at = @At(value = "HEAD"), cancellable = true)
    private static void renderMultipartHitbox(PoseStack poseStack, VertexConsumer buffer, Entity entity, float partialTicks, CallbackInfo ci) {
        if (entity instanceof Prehistoric prehistoric) {
            ci.cancel();
            //Unchanged original code
            AABB aABB = entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ());
            LevelRenderer.renderLineBox(poseStack, buffer, aABB, 1, 1, 1, 1);

            //Changed code
            double d = -Mth.lerp(partialTicks, entity.xOld, entity.getX());
            double e = -Mth.lerp(partialTicks, entity.yOld, entity.getY());
            double f = -Mth.lerp(partialTicks, entity.zOld, entity.getZ());
            if (Version.debugEnabled()) {
                aABB = entity.getBoundingBoxForCulling().move(-entity.getX(), -entity.getY(), -entity.getZ());
                LevelRenderer.renderLineBox(poseStack, buffer, aABB, 1, 0, 1, 1);
                aABB = ((Prehistoric) entity).getAttackBounds().move(-entity.getX(), -entity.getY(), -entity.getZ());
                LevelRenderer.renderLineBox(poseStack, buffer, aABB, 0, 0, 1, 1);
                for (Map.Entry<EntityHitboxManager.Hitbox, Vec3> entry : prehistoric.activeAttackBoxes.entrySet()) {
                    Vec3 pos = entry.getValue();
                    EntityHitboxManager.Hitbox hitbox = entry.getKey();
                    EntityDimensions size = EntityDimensions.scalable(hitbox.width(), hitbox.height()).scale(prehistoric.getScale());
                    AABB aabb = size.makeBoundingBox(pos);
                    poseStack.pushPose();
                    double g = d + pos.x;
                    double h = e + pos.y;
                    double i = f + pos.z;
                    poseStack.translate(g, h, i);
                    if (Minecraft.getInstance().player.getBoundingBox().intersects(aabb)) {
                        LevelRenderer.renderLineBox(poseStack, buffer, aabb.move(-pos.x, -pos.y, -pos.z), 1, 0, 0, 1);
                    } else {
                        LevelRenderer.renderLineBox(poseStack, buffer, aabb.move(-pos.x, -pos.y, -pos.z), 0, 0, 1, 1);
                    }
                    poseStack.popPose();
                }
            }
            int kj = 0;
            for (MultiPart multiPart : prehistoric.getCustomParts()) {
                Entity part = multiPart.getEntity();
                poseStack.pushPose();
                double g = d + Mth.lerp(partialTicks, part.xOld, part.getX());
                double h = e + Mth.lerp(partialTicks, part.yOld, part.getY());
                double i = f + Mth.lerp(partialTicks, part.zOld, part.getZ());
                poseStack.translate(g, h, i);
                LevelRenderer.renderLineBox(poseStack, buffer, part.getBoundingBox().move(-part.getX(), -part.getY(), -part.getZ()), 0, 1, 0, 1);
                poseStack.popPose();
                kj++;
            }

            //Unchanged original code
            Vec3 vec3 = entity.getViewVector(partialTicks);
            Matrix4f matrix4f = poseStack.last().pose();
            Matrix3f matrix3f = poseStack.last().normal();
            buffer.vertex(matrix4f, 0, entity.getEyeHeight(), 0).color(0, 0, 255, 255).normal(matrix3f, (float) vec3.x, (float) vec3.y, (float) vec3.z).endVertex();
            buffer.vertex(matrix4f, (float) (vec3.x * 2), (float) (entity.getEyeHeight() + vec3.y * 2), (float) (vec3.z * 2)).color(0, 0, 255, 255).normal(matrix3f, (float) vec3.x, (float) vec3.y, (float) vec3.z).endVertex();
        }
    }
}
