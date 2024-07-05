package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.model.PrehistoricGeoModel;
import com.fossil.fossil.entity.prehistoric.Meganeura;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.ArrayList;
import java.util.List;

public class PrehistoricGeoRenderer<T extends Prehistoric> extends GeoEntityRenderer<T> {

    public PrehistoricGeoRenderer(EntityRendererProvider.Context renderManager, String model, String animation) {
        super(renderManager, new PrehistoricGeoModel<>(
                new ResourceLocation(Fossil.MOD_ID, "geo/entity/" + model),
                new ResourceLocation(Fossil.MOD_ID, "animations/" + animation)
        ));
    }

    public static List<BlockPos> pathTargets = new ArrayList<>();
    public static BlockPos entityTarget = null;

    public static void showPath(List<BlockPos> targets, BlockPos target) {
        pathTargets = targets;
        entityTarget = target;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.shadowRadius = entity.getBbWidth() * 0.45F;
        if (entity instanceof Meganeura meganeura && meganeura.getAttachmentPos() != null) {
            poseStack.pushPose();
            Vec3i normal = meganeura.getAttachmentFace().getNormal();
            double x = normal.getX() * 0.2;
            double y = normal.getY() * 0.2 + 0.2;
            double z = normal.getZ() * 0.2;
            poseStack.translate(x, y, z);
            super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
            poseStack.popPose();
        } else {
            double rx = entity.xo + (entity.getX() - entity.xo) * partialTick;
            double ry = entity.yo + (entity.getY() - entity.yo) * partialTick;
            double rz = entity.zo + (entity.getZ() - entity.zo) * partialTick;
            if (!pathTargets.isEmpty()) {
                for (int i = 0; i < pathTargets.size(); i++) {
                    AABB targetArea = new AABB(pathTargets.get(i)).move(-rx, -ry, -rz);
                    LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 1, (float) i / (pathTargets.size() - 1), 1, 0.25f);
                }
            }
            if (entityTarget != null) {
                AABB targetArea = new AABB(entityTarget.getX() - 0.25, entityTarget.getY() - 0.25, entityTarget.getZ() - 0.25, entityTarget.getX() + 0.25, entityTarget.getY() + 0.25, entityTarget.getZ() + 0.25);
                targetArea = targetArea.move(-rx, -ry, -rz);
                LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), targetArea, 0, 1, 1, 1);
            }
            super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        }
    }

    @Override
    protected float getSwingMotionAnimThreshold() {
        return 0.08f;
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTick, RenderType type, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        //TODO: Performance check
        return RenderType.entityTranslucent(texture);
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f - rotationYaw));
        if (animatable.deathTime > 0) {
            float deathRotation = (animatable.deathTime + partialTick - 1f) / 20f * 1.6f;

            poseStack.mulPose(Vector3f.ZP.rotationDegrees(Math.min(Mth.sqrt(deathRotation), 1) * getDeathMaxRotation(animatable)));
        }
    }

    @Override
    public boolean shouldShowName(T animatable) {
        //Calling super.shouldShowName in fabric crashes the game because the method doesn't exist in GeoEntityRenderer
        return false;
    }

    @Override
    public float getWidthScale(T animatable) {
        return animatable.getScale();
    }

    @Override
    public float getHeightScale(T entity) {
        return animatable.getScale();
    }
}
