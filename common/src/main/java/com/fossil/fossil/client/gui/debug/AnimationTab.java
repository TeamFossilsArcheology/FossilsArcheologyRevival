package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.C2SForceAnimationMessage;
import com.fossil.fossil.network.debug.C2SRotationMessage;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.builder.Animation;

import java.util.List;
import java.util.Map;

public class AnimationTab extends DebugTab {
    private float rotYBase;
    private float rotXBase;
    private float scale = 15;
    private boolean loop;
    private double transitionLength = 5;
    private AnimationsList animations;
    private String currentController;

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
        Slider sliderY = addWidget(
                new Slider(20, 30 + (yLeft++) * 30, width / 4, 20, new TextComponent("Rotation Y: "), new TextComponent(""), 0, 360, 0, 5, 3,
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
        Slider sliderX = addWidget(
                new Slider(20, 30 + (yLeft++) * 30, width / 4, 20, new TextComponent("Rotation X: "), new TextComponent(""), 0, 360, 0, 5, 3,
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
                new Slider(20, 30 + (yLeft++) * 30, width / 4, 20, new TextComponent("Scale: "), new TextComponent(""), 0, 100, scale, 5, 3,
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
        }));
        if (entity instanceof PrehistoricAnimatable<?> prehistoric) {
            animations = addWidget(new AnimationsList(entity.getId(), prehistoric.getAllAnimations()));
            List<String> controllers = prehistoric.getFactory().getOrCreateAnimationData(entity.getId()).getAnimationControllers().keySet().stream().toList();
            if (!controllers.isEmpty()) {
                currentController = controllers.get(0);
                addWidget(CycleOption.create("Controller", () -> controllers, TextComponent::new,
                                options -> currentController, (options, option, controller) -> currentController = controller)
                        .createButton(Minecraft.getInstance().options, width / 2, 90, 100));
            }

            addWidget(
                    new Slider(width / 2, 120, 100, 20, new TextComponent("Transition: "), new TextComponent(""), 0, 20, transitionLength, 1, 3,
                            true) {
                        @Override
                        protected void applyValue() {
                            transitionLength = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                        }
                    });
            addWidget(CycleOption.createOnOff("Loop", options -> loop, (options, option, loop) -> this.loop = loop)
                    .createButton(Minecraft.getInstance().options, width / 2, 150, 100));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (entity != null) {
            renderEntityInDebug(70, 280, entity, scale);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation: " + entity.getYRot()), 20, 160, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation Body: " + (entity instanceof LivingEntity livingEntity ? livingEntity.yBodyRot : entity.getYRot())), 20, 180, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation Head: " + entity.getYHeadRot()), 20, 200, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Start Animation:"), width - width / 4 + 20, 30, 16777215);
        }
    }

    private class AnimationsList extends ContainerObjectSelectionList<AnimationsList.AnimationEntry> {

        public AnimationsList(int mobId, Map<String, Animation> animations) {
            super(AnimationTab.this.minecraft, 200, AnimationTab.this.height, 60, AnimationTab.this.height, 25);
            List<Map.Entry<String, Animation>> sortedAnimations = animations.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();
            for (Map.Entry<String, Animation> animation : sortedAnimations) {
                addEntry(new AnimationEntry(mobId, animation));
            }
            setRenderBackground(false);
            setRenderTopAndBottom(false);
            x0 = (AnimationTab.this.width - AnimationTab.this.width / 4);
            x1 = x0 + width;
        }

        @Override
        protected int getScrollbarPosition() {
            return x0 + width - 6;
        }

        private class AnimationEntry extends ContainerObjectSelectionList.Entry<AnimationEntry> {
            private final Button changeButton;

            AnimationEntry(int id, Map.Entry<String, Animation> animation) {
                changeButton = new Button(0, 0, 200, 20, new TextComponent(animation.getKey()), button -> {
                    if (entity instanceof Prehistoric prehistoric) {
                        /*double speed = 1 / Math.sqrt(prehistoric.getScale());
                        speed *= prehistoric.data().attributes().baseSpeed() / 0.26;//multiplier
                        speed *= animation.getValue().animationLength / 20;*/
                        MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SForceAnimationMessage(currentController, prehistoric.getId(), button.getMessage().getContents(), transitionLength, loop));
                    }
                });
            }

            @Override
            public @NotNull List<? extends NarratableEntry> narratables() {
                return ImmutableList.of(changeButton);
            }

            @Override
            public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver,
                               float partialTick) {
                changeButton.x = left;
                changeButton.y = top;
                changeButton.render(poseStack, mouseX, mouseY, partialTick);
            }

            @Override
            public @NotNull List<? extends GuiEventListener> children() {
                return ImmutableList.of(changeButton);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return changeButton.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return changeButton.mouseReleased(mouseX, mouseY, button);
            }
        }
    }
}
