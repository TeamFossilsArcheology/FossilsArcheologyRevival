package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.client.model.SkeletonModel;
import com.github.teamfossilsarcheology.fossil.entity.PrehistoricSkeleton;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;
import software.bernie.geckolib3.util.IRenderCycle;
import software.bernie.geckolib3.util.RenderUtils;

public class SkeletonRenderer extends EntityRenderer<PrehistoricSkeleton> implements IGeoRenderer<PrehistoricSkeleton> {

    private final GeoModelProvider<PrehistoricSkeleton> geoModel;
    private IRenderCycle currentModelRenderCycle = EModelRenderCycle.INITIAL;
    private PrehistoricSkeleton animatable;
    protected Matrix4f dispatchedMat = new Matrix4f();
    protected Matrix4f renderEarlyMat = new Matrix4f();
    private MultiBufferSource rtb;

    public SkeletonRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.geoModel = new SkeletonModel();
    }

    @Override
    public void renderEarly(PrehistoricSkeleton animatable, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.animatable = animatable;
        this.renderEarlyMat = poseStack.last().pose().copy();
        this.rtb = bufferSource;

        IGeoRenderer.super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void render(GeoModel model, PrehistoricSkeleton animatable, float partialTick, RenderType type, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        IGeoRenderer.super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void render(PrehistoricSkeleton animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
        poseStack.pushPose();
        dispatchedMat = poseStack.last().pose().copy();
        GeoModel model = geoModel.getModel(geoModel.getModelLocation(animatable));
        poseStack.translate(0, 0.01f, 0);
        RenderSystem.setShaderTexture(0, getTextureLocation(animatable));

        if (!animatable.isInvisibleTo(Minecraft.getInstance().player)) {
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180f - animatable.getYRot()));

            Color renderColor = getRenderColor(animatable, partialTick, poseStack, bufferSource, null, packedLight);
            RenderType renderType = getRenderType(animatable, partialTick, poseStack, bufferSource, null, packedLight, getTextureLocation(animatable));

            VertexConsumer glintBuffer = bufferSource.getBuffer(RenderType.entityGlintDirect());
            VertexConsumer translucentBuffer = bufferSource.getBuffer(RenderType.entityTranslucentCull(getTextureLocation(animatable)));

            render(model, animatable, partialTick, renderType, poseStack, bufferSource,
                    glintBuffer != translucentBuffer ? VertexMultiConsumer.create(glintBuffer, translucentBuffer) : null,
                    packedLight, OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(false)), renderColor.getRed() / 255f, renderColor.getGreen() / 255f, renderColor.getBlue() / 255f, renderColor.getAlpha() / 255f);
        }

        poseStack.popPose();
        super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        RenderUtils.translateMatrixToBone(poseStack, bone);
        RenderUtils.translateToPivotPoint(poseStack, bone);

        boolean rotOverride = bone.rotMat != null;

        if (rotOverride) {
            poseStack.last().pose().multiply(bone.rotMat);
            poseStack.last().normal().mul(new Matrix3f(bone.rotMat));
        } else {
            RenderUtils.rotateMatrixAroundBone(poseStack, bone);
        }

        RenderUtils.scaleMatrixForBone(poseStack, bone);

        if (bone.isTrackingXform()) {
            Matrix4f poseState = poseStack.last().pose().copy();
            Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);

            bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
            localMatrix.translate(new Vector3f(getRenderOffset(this.animatable, 1)));
            bone.setLocalSpaceXform(localMatrix);

            Matrix4f worldState = localMatrix.copy();

            worldState.translate(new Vector3f(this.animatable.position()));
            bone.setWorldSpaceXform(worldState);
        }

        RenderUtils.translateAwayFromPivotPoint(poseStack, bone);

        if (!bone.isHidden) {
            if (!bone.cubesAreHidden()) {
                for (GeoCube geoCube : bone.childCubes) {
                    poseStack.pushPose();
                    renderCube(geoCube, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
                    poseStack.popPose();
                }
            }

            for (GeoBone childBone : bone.childBones) {
                renderRecursively(childBone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            }
        }

        poseStack.popPose();
    }

    @Override
    public RenderType getRenderType(PrehistoricSkeleton animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Override
    public int getInstanceId(PrehistoricSkeleton entity) {
        return entity.getId();
    }

    @Override
    public float getWidthScale(PrehistoricSkeleton entity) {
        return entity.getScale();
    }

    @Override
    public float getHeightScale(PrehistoricSkeleton entity) {
        return entity.getScale();
    }

    @Override
    protected boolean shouldShowName(PrehistoricSkeleton entity) {
        return false;
    }

    @Override
    public GeoModelProvider<PrehistoricSkeleton> getGeoModelProvider() {
        return geoModel;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(PrehistoricSkeleton entity) {
        return geoModel.getTextureLocation(entity);
    }

    @Override
    public void setCurrentRTB(MultiBufferSource bufferSource) {
        this.rtb = bufferSource;
    }

    @Override
    public MultiBufferSource getCurrentRTB() {
        return this.rtb;
    }

    @Override
    public @NotNull IRenderCycle getCurrentModelRenderCycle() {
        return currentModelRenderCycle;
    }

    @Override
    public void setCurrentModelRenderCycle(IRenderCycle currentModelRenderCycle) {
        this.currentModelRenderCycle = currentModelRenderCycle;
    }
}
