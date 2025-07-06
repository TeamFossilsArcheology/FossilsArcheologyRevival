package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.client.model.SkeletonModel;
import com.github.teamfossilsarcheology.fossil.entity.PrehistoricSkeleton;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class SkeletonRenderer extends EntityRenderer<PrehistoricSkeleton> implements GeoRenderer<PrehistoricSkeleton> {
    private final GeoModel<PrehistoricSkeleton> geoModel;

    protected PrehistoricSkeleton animatable;

    protected Matrix4f entityRenderTranslations = new Matrix4f();
    protected Matrix4f modelRenderTranslations = new Matrix4f();

    public SkeletonRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.geoModel = new SkeletonModel();
    }

    @Override
    public GeoModel<PrehistoricSkeleton> getGeoModel() {
        return geoModel;
    }

    @Override
    public PrehistoricSkeleton getAnimatable() {
        return animatable;
    }

    @Override
    public long getInstanceId(PrehistoricSkeleton animatable) {
        return animatable.getId();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(PrehistoricSkeleton entity) {
        return GeoRenderer.super.getTextureLocation(animatable);
    }

    @Override
    public void preRender(PoseStack poseStack, PrehistoricSkeleton animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
        scaleModelForRender(animatable.getScale(), animatable.getScale(), poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }

    @Override
    public void render(PrehistoricSkeleton animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.animatable = animatable;
        defaultRender(poseStack, animatable, bufferSource, null, null, entityYaw, partialTick, packedLight);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, PrehistoricSkeleton animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        modelRenderTranslations = new Matrix4f(poseStack.last().pose());
        if (!animatable.isInvisibleTo(Minecraft.getInstance().player)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180f - animatable.getYRot()));
            GeoRenderer.super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
        poseStack.popPose();
    }

    @Override
    public void renderRecursively(PoseStack poseStack, PrehistoricSkeleton animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        RenderUtils.translateMatrixToBone(poseStack, bone);
        RenderUtils.translateToPivotPoint(poseStack, bone);
        RenderUtils.rotateMatrixAroundBone(poseStack, bone);
        RenderUtils.scaleMatrixForBone(poseStack, bone);

        if (bone.isTrackingMatrices()) {
            Matrix4f poseState = new Matrix4f(poseStack.last().pose());
            Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, entityRenderTranslations);

            bone.setModelSpaceMatrix(RenderUtils.invertAndMultiplyMatrices(poseState, modelRenderTranslations));
            bone.setLocalSpaceMatrix(localMatrix.translation(new Vector3f(getRenderOffset(this.animatable, 1).toVector3f())));
            bone.setWorldSpaceMatrix(new Matrix4f(localMatrix).translation(new Vector3f(this.animatable.position().toVector3f())));
        }

        RenderUtils.translateAwayFromPivotPoint(poseStack, bone);

        renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        if (!isReRender)
            applyRenderLayersForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);

        renderChildBones(poseStack, animatable, bone, renderType, bufferSource, buffer, false, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        poseStack.popPose();
    }

    @Override
    public RenderType getRenderType(PrehistoricSkeleton animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Override
    protected boolean shouldShowName(PrehistoricSkeleton entity) {
        return false;
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, PrehistoricSkeleton animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        if (!isReRender && (widthScale != 1 || heightScale != 1)) {
            poseStack.scale(widthScale, heightScale, widthScale);
        }
    }

    @Override
    public void updateAnimatedTextureFrame(PrehistoricSkeleton animatable) {

    }

    @Override
    public void fireCompileRenderLayersEvent() {

    }

    @Override
    public boolean firePreRenderEvent(PoseStack poseStack, BakedGeoModel model, MultiBufferSource bufferSource, float partialTick, int packedLight) {
        return true;
    }

    @Override
    public void firePostRenderEvent(PoseStack poseStack, BakedGeoModel model, MultiBufferSource bufferSource, float partialTick, int packedLight) {

    }
}
