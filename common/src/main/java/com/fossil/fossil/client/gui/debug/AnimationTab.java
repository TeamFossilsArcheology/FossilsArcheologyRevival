package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.AnimationMessage;
import com.fossil.fossil.network.debug.RotationMessage;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class AnimationTab extends DebugTab {
    private float rotYBase;
    private float rotXBase;
    private float scale = 15;
    private AnimationsList animations;

    protected AnimationTab(DebugScreen debugScreen, LivingEntity entity) {
        super(debugScreen, entity);
        if (entity != null) {
            this.rotYBase = entity.yBodyRot;
            this.rotXBase = entity.getXRot();
        }
    }

    public static void renderEntityInDebug(int posX, int posY, LivingEntity entity, float scale) {
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
                        MessageHandler.DEBUG_CHANNEL.sendToServer(new RotationMessage(entity.getId(), newRot, RotationMessage.Y_ROT));
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
                        MessageHandler.DEBUG_CHANNEL.sendToServer(new RotationMessage(entity.getId(), newRot, RotationMessage.X_ROT));
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
        if (entity instanceof PrehistoricAnimatable prehistoric) {
            animations = addWidget(new AnimationsList(entity.getId(), prehistoric.getAllAnimations().keySet()));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (entity != null) {
            renderEntityInDebug(70, 280, entity, scale);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation: " + entity.getYRot()), 20, 160, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation Body: " + entity.yBodyRot), 20, 180, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Rotation Head: " + entity.getYHeadRot()), 20, 200, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Start Animation:"), width - width / 4 + 20, 30, 16777215);
        }
    }

    private class AnimationsList extends ContainerObjectSelectionList<AnimationsList.AnimationEntry> {

        public AnimationsList(int mobId, Set<String> animations) {
            super(AnimationTab.this.minecraft, 200, AnimationTab.this.height, 60, AnimationTab.this.height, 25);
            List<String> sortedAnimations = animations.stream().sorted().toList();
            for (String animation : sortedAnimations) {
                addEntry(new AnimationEntry(mobId, animation));
            }
            setRenderBackground(false);
            setRenderTopAndBottom(false);
            x0 = (AnimationTab.this.width - AnimationTab.this.width / 4);
            x1 = x0 + width;
        }

        @Override
        protected int getScrollbarPosition() {
            return x0 + width;
        }

        private class AnimationEntry extends ContainerObjectSelectionList.Entry<AnimationEntry> {
            private final Button changeButton;

            AnimationEntry(int id, String text) {
                changeButton = new Button(0, 0, 200, 20, new TextComponent(text), button -> {
                    MessageHandler.DEBUG_CHANNEL.sendToServer(new AnimationMessage(id, button.getMessage().getContents()));
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
