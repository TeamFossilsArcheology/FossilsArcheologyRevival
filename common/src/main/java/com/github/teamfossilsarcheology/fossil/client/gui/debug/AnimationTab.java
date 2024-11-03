package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.C2SForceAnimationMessage;
import com.github.teamfossilsarcheology.fossil.network.debug.C2SRotationMessage;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class AnimationTab extends DebugTab<Entity> {
    private float rotYBase;
    private float rotXBase;
    private float scale = 15;

    protected AnimationTab(DebugScreen debugScreen, Entity entity) {
        super(debugScreen, entity);
        if (entity != null) {
            this.rotYBase = entity instanceof LivingEntity livingEntity ? livingEntity.yBodyRot : entity.getYRot();
            this.rotXBase = entity.getXRot();
        }
    }

    public static void renderEntityInDebug(int posX, int posY, Entity entity, float scale) {
        float g = -entity.getXRot() / 20f;
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(posX, posY, 1050);
        poseStack.scale(1, 1, -1);
        RenderSystem.applyModelViewMatrix();
        PoseStack poseStack2 = new PoseStack();
        poseStack2.translate(0.0, 0.0, 1000.0);
        poseStack2.scale(scale, scale, scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0f);
        Quaternion quaternion2 = Vector3f.XP.rotationDegrees(g * 20.0f);
        quaternion.mul(quaternion2);
        poseStack2.mulPose(quaternion);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion2.conj();
        entityRenderDispatcher.overrideCameraOrientation(quaternion2);
        entityRenderDispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, poseStack2, bufferSource, 0xF000F0));
        bufferSource.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    @Override
    protected void init(int width, int height) {
        super.init(width, height);
        int yLeft = 0;
        int yRight = 0;
        DebugSlider sliderY = addWidget(
                new DebugSlider(20, 30 + (yLeft++) * 30, width / 4, 20, new TextComponent("Rotation Y: "), new TextComponent(""), 0, 360, 0, 5, 3,
                        true) {
                    @Override
                    protected void applyValue() {
                        float rotY = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                        float newRot = (rotYBase + rotY) % 360;
                        MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SRotationMessage(entity.getId(), newRot, C2SRotationMessage.Y_ROT));
                        entity.setYBodyRot(newRot);
                        entity.setYRot(newRot);
                        entity.setYHeadRot(newRot);
                    }
                });
        DebugSlider sliderX = addWidget(
                new DebugSlider(20, 30 + (yLeft++) * 30, width / 4, 20, new TextComponent("Rotation X: "), new TextComponent(""), 0, 360, 0, 5, 3,
                        true) {
                    @Override
                    protected void applyValue() {
                        float rotX = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                        float newRot = (rotXBase + rotX) % 360;
                        MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SRotationMessage(entity.getId(), newRot, C2SRotationMessage.X_ROT));
                        entity.setXRot(newRot);
                    }
                });
        addWidget(
                new DebugSlider(20, 30 + (yLeft++) * 30, width / 4, 20, new TextComponent("Scale: "), new TextComponent(""), 0, 100, scale, 5, 3,
                        true) {
                    @Override
                    protected void applyValue() {
                        scale = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                    }
                });
        addWidget(new Button(20, 30 + (yLeft++) * 30, width / 6, 20, new TextComponent("Reset Rotation"), button -> {
            rotYBase = 0;
            rotXBase = 0;
            sliderY.setSliderValue(0, true);
            sliderX.setSliderValue(0, true);
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("client side only"), i, j);
        }));
        if (entity instanceof PrehistoricAnimatable<?> prehistoric) {
            List<String> controllers = prehistoric.getFactory().getOrCreateAnimationData(entity.getId()).getAnimationControllers().keySet().stream().toList();
            addWidget(new AnimationList(width - width / 4, height, prehistoric.getAllAnimations(), controllers, false, minecraft, animationObject -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SForceAnimationMessage(animationObject.controller(), entity.getId(), animationObject.name(), animationObject.transitionLength(), animationObject.loop()));
            }));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (entity != null) {
           // renderEntityInDebug(70, 280, entity, scale);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation: " + entity.getYRot()), 20, 160, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation Body: " + (entity instanceof LivingEntity livingEntity ? livingEntity.yBodyRot : entity.getYRot())), 20, 180, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation Head: " + entity.getYHeadRot()), 20, 200, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Start Animation:"), width - width / 4 + 20, 30, 16777215);
        }
    }
}
