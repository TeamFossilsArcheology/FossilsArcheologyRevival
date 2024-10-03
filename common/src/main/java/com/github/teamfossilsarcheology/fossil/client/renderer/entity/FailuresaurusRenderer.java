package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.client.model.*;
import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.entity.monster.Failuresaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class FailuresaurusRenderer extends EntityRenderer<Failuresaurus> implements RendererFabricFix {
    private static final FailuresaurusModel DODO_MODEL = new FailuresaurusDodoModel();
    private static final FailuresaurusModel FISH_MODEL = new FailuresaurusFishModel();
    private static final FailuresaurusModel FLYING_MODEL = new FailuresaurusFlyingModel();
    private static final FailuresaurusModel SAUROPOD_MODEL = new FailuresaurusSauropodModel();
    private static final FailuresaurusModel THERAPOD_MODEL = new FailuresaurusTherapodModel();

    public FailuresaurusRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    private FailuresaurusModel getModel(Failuresaurus entity) {
        switch (Failuresaurus.Variant.valueOf(entity.getVariant())) {
            case FISH -> {
                return FISH_MODEL;
            }
            case FLYING -> {
                return FLYING_MODEL;
            }
            case SAUROPOD -> {
                return SAUROPOD_MODEL;
            }
            case THERAPOD -> {
                return THERAPOD_MODEL;
            }
            default -> {
                return DODO_MODEL;
            }
        }
    }

    @Override
    public void render(Failuresaurus entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float k;
        poseStack.pushPose();
        FailuresaurusModel model = getModel(entity);
        model.attackTime = entity.getAttackAnim(partialTicks);
        model.riding = entity.isPassenger();
        model.young = entity.isBaby();
        float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        float g = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
        float h = g - f;
        if (entity.isPassenger() && entity.getVehicle() instanceof LivingEntity livingEntity) {
            f = Mth.rotLerp(partialTicks, livingEntity.yBodyRotO, livingEntity.yBodyRot);
            h = g - f;
            float i = Mth.wrapDegrees(h);
            if (i < -85) {
                i = -85;
            }
            if (i >= 85) {
                i = 85;
            }
            f = g - i;
            if (i * i > 2500) {
                f += i * 0.2f;
            }
            h = g - f;
        }
        float j = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        if (LivingEntityRenderer.isEntityUpsideDown(entity)) {
            j *= -1;
            h *= -1;
        }
        float i = entity.tickCount + partialTicks;
        setupRotations(entity, poseStack, i, f, partialTicks);
        poseStack.scale(-1, -1, 1);
        poseStack.translate(0.0, -1.501f, 0.0);
        k = 0;
        float l = 0;
        if (!entity.isPassenger() && entity.isAlive()) {
            k = Mth.lerp(partialTicks, entity.animationSpeedOld, entity.animationSpeed);
            l = entity.animationPosition - entity.animationSpeed * (1 - partialTicks);
            if (entity.isBaby()) {
                l *= 3;
            }
            if (k > 1) {
                k = 1;
            }
        }
        model.prepareMobModel(entity, l, k, partialTicks);
        model.setupAnim(entity, l, k, i, h, j);
        boolean visible = !entity.isInvisible();
        boolean translucent = !visible && !entity.isInvisibleTo(Minecraft.getInstance().player);
        RenderType renderType = getRenderType(entity, visible, translucent, Minecraft.getInstance().shouldEntityAppearGlowing(entity));
        if (renderType != null) {
            VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
            int m = LivingEntityRenderer.getOverlayCoords(entity, 0);
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, m, 1, 1, 1, translucent ? 0.15f : 1);
        }
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Nullable
    protected RenderType getRenderType(Failuresaurus livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
        ResourceLocation resourceLocation = getTextureLocation(livingEntity);
        if (translucent) {
            return RenderType.itemEntityTranslucentCull(resourceLocation);
        }
        if (bodyVisible) {
            return getModel(livingEntity).renderType(resourceLocation);
        }
        if (glowing) {
            return RenderType.outline(resourceLocation);
        }
        return null;
    }

    protected boolean isShaking(Failuresaurus entity) {
        return entity.isFullyFrozen();
    }

    protected void setupRotations(Failuresaurus entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        if (isShaking(entityLiving)) {
            rotationYaw += (float) (Math.cos((double) entityLiving.tickCount * 3.25) * Math.PI * 0.4f);
        }
        if (entityLiving.getPose() != Pose.SLEEPING) {
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180 - rotationYaw));
        }
        if (entityLiving.deathTime > 0) {
            float f = ((float) entityLiving.deathTime + partialTicks - 1) / 20 * 1.6f;
            if ((f = Mth.sqrt(f)) > 1) {
                f = 1;
            }
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 90));
        } else if (LivingEntityRenderer.isEntityUpsideDown(entityLiving)) {
            matrixStack.translate(0.0, entityLiving.getBbHeight() + 0.1f, 0.0);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        }
    }

    @Override
    protected boolean shouldShowName(Failuresaurus entity) {
        return false;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((Failuresaurus) entity);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Failuresaurus entity) {
        return getModel(entity).getTexture();
    }

    public abstract static class FailuresaurusModel extends EntityModel<Failuresaurus> {

        protected FailuresaurusModel(Function<ResourceLocation, RenderType> function) {
            super(function);
        }

        @Override
        public void setupAnim(Failuresaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        public abstract ResourceLocation getTexture();
    }
}
