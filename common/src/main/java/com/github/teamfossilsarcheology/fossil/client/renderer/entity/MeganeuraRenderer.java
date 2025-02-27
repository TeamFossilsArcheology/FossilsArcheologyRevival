package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.Meganeura;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MeganeuraRenderer extends PrehistoricGeoRenderer<Meganeura> {
    public MeganeuraRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, "meganeura", RenderType::entityTranslucent);
    }

    @Override
    public void render(Meganeura meganeura, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        if (meganeura.getAttachSystem().attachStarted()) {
            Direction face = meganeura.getAttachSystem().getAttachmentFace();
            Vec3i normal = face.getNormal();
            float yRot = toPoseStackRotation(face.getOpposite().toYRot());
            float xRot = (float) -Mth.atan2(Mth.sqrt(normal.getX() * normal.getX() + normal.getZ() * normal.getZ()), 0) * Mth.RAD_TO_DEG;
            float translate = meganeura.getBbWidth() / 2;
            float yTranslate = 0.2f;
            if (!meganeura.getAttachSystem().isAttached()) {
                //Is approaching
                Vec3 pos = meganeura.getAttachSystem().getAttachmentPos();
                double dist = pos.distanceTo(meganeura.position());
                if (dist < 1) {
                    //TODO: Use initial dist as base value so that it still looks good when starting closer than 1
                    xRot *= (float) Math.min((-dist + 1) * 1.2, 1);
                    translate *= (float) Math.min((-dist + 1) * 1.2, 1);
                    yTranslate *= (float) Math.min((-dist + 1) * 1.2, 1);
                } else {
                    xRot = 0;
                    translate = 0;
                    yTranslate = 0;
                }
                Vec3 offset = pos.subtract(meganeura.position());
                double yawDiff = (Mth.atan2(offset.z, offset.x) * Mth.RAD_TO_DEG);
                yRot = toPoseStackRotation(Util.clampTo360(Util.yawToYRot(yawDiff)));
            }
            yRot += entityYaw;
            poseStack.translate(-normal.getX() * translate, yTranslate, -normal.getZ() * translate);
            //Could apply z rotation depending on approach angle
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        }
        super.render(meganeura, entityYaw, partialTick, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    private static float toPoseStackRotation(float rot) {
        return -rot;
    }
}
