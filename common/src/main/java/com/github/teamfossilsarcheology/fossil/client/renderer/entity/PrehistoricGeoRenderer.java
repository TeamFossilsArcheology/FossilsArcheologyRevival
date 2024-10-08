package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.darkpred.morehitboxes.internal.MultiPartGeoEntityRenderer;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.InstructionTab;
import com.github.teamfossilsarcheology.fossil.client.model.PrehistoricGeoModel;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.Arthropleura;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.util.Version;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.function.Function;

public class PrehistoricGeoRenderer<T extends Prehistoric> extends GeoEntityRenderer<T> {
    private final Function<ResourceLocation, RenderType> renderType;

    /**
     * @param model the file model name (including extension)
     * @param animation the animation model name (including extension)
     */
    public PrehistoricGeoRenderer(EntityRendererProvider.Context renderManager, String model, String animation, Function<ResourceLocation, RenderType> renderType) {
        super(renderManager, new PrehistoricGeoModel<>(model, animation));
        this.renderType = renderType;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.shadowRadius = entity.getBbWidth() * 0.45F;
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public Color getRenderColor(T animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight) {
        if (Version.debugEnabled()) {
            if (InstructionTab.entityListHighlight != null && InstructionTab.entityListHighlight.getId() == animatable.getId()) {
                return Color.RED;
            } else if (InstructionTab.highlightInstructionEntity != null && InstructionTab.highlightInstructionEntity.getId() == animatable.getId()) {
                return Color.RED;
            }
        }
        if (animatable instanceof Arthropleura arthropleura && arthropleura.isBee()) {
            return Color.YELLOW;
        }
        return super.getRenderColor(animatable, partialTick, poseStack, bufferSource, buffer, packedLight);
    }

    @Override
    protected float getSwingMotionAnimThreshold() {
        return Util.SWING_ANIM_THRESHOLD;
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTick, RenderType type, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        if (this instanceof MultiPartGeoEntityRenderer renderer) {
            renderer.updateTickForEntity(animatable);
        }
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return renderType.apply(texture);
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f - rotationYaw));
        if (animatable.deathTime > 0) {
            float deathRotation = (animatable.deathTime + partialTick - 1f) / 20f * 1.6f;

            poseStack.mulPose(Vector3f.ZP.rotationDegrees(Math.min(Mth.sqrt(deathRotation), 1) * getDeathMaxRotation(animatable)));
        } else if (animatable.hasCustomName()) {
            String name = ChatFormatting.stripFormatting(animatable.getName().getString());

            if (name != null && (name.equals("Dinnerbone") || name.equalsIgnoreCase("Grumm"))) {
                poseStack.translate(0, animatable.getBbHeight() + 0.1f, 0);
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180f));
            }
        }
    }

    @Override
    public boolean shouldShowName(T animatable) {
        //Calling super.shouldShowName in fabric crashes the game because the method doesn't exist in GeoEntityRenderer
        return animatable.hasCustomName() && (animatable == entityRenderDispatcher.crosshairPickEntity || animatable.shouldShowName());
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
