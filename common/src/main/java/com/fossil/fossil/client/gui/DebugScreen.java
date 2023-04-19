package com.fossil.fossil.client.gui;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.network.*;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

public class DebugScreen extends Screen {
    private float rotYBase;
    private float rotXBase;
    public static CycleButton<Boolean> disableAI;
    private EditBox xPosInput;
    private EditBox yPosInput;
    private EditBox zPosInput;
    private float scale = 15;
    private final LivingEntity entity;
    public static boolean showPaths;

    public DebugScreen(LivingEntity entity) {
        super(entity == null ? new TextComponent("Debug Screen") : entity.getDisplayName());
        this.entity = entity;
        if (entity != null) {
            this.rotYBase = entity.yBodyRot;
            this.rotXBase = entity.getXRot();
        }
    }

    public static LivingEntity getHitResult(Minecraft mc) {
        Entity camera = mc.getCameraEntity();
        Vec3 view = camera.getViewVector(1.0f);
        double range = 30;
        Vec3 end = camera.getEyePosition().add(view.x * range, view.y * range, view.z * range);
        AABB aabb = camera.getBoundingBox().expandTowards(view.scale(range)).inflate(1);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(camera, camera.getEyePosition(), end, aabb,
                entity1 -> entity1 instanceof LivingEntity, range * range);
        return (entityHitResult != null) ? (LivingEntity) entityHitResult.getEntity() : null;
    }

    public static void showPath(Player player, List<BlockPos> targets, List<BlockState> blocks) {
        if (showPaths) {
            for (int i = 0; i < targets.size(); i++) {
                player.level.setBlock(targets.get(i).below(), blocks.get(i), 3);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        int yLeft = 0;
        int yRight = 0;
        var builder = CycleButton.booleanBuilder(new TextComponent("On"), new TextComponent("Off")).withValues(
                ImmutableList.of(Boolean.TRUE, Boolean.FALSE));
        if (entity != null) {
            Slider sliderY = this.addRenderableWidget(
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
            Slider sliderX = this.addRenderableWidget(
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
            this.addRenderableWidget(
                    new Slider(20, 30 + (yLeft++) * 30, width / 4, 20, new TextComponent("Scale: "), new TextComponent(""), 0, 100, scale, 5, 3,
                            true) {
                        @Override
                        protected void applyValue() {
                            scale = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                        }
                    });
            this.addRenderableWidget(new Button(20, 30 + (yLeft++) * 30, width / 6, 20, new TextComponent("Reset Rotation"), button -> {
                rotYBase = 0;
                rotXBase = 0;
                sliderY.setSliderValue(0, true);
                sliderX.setSliderValue(0, true);
            }));
        }
        if (entity instanceof Prehistoric prehistoric) {
            builder.withInitialValue(prehistoric.isNoAi());
            disableAI = builder.create(20, height - 130, width / 6, 20, new TextComponent("Disable AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 0));
            });
            this.addRenderableWidget(disableAI);
            builder.withInitialValue(entity.getEntityData().get(Prehistoric.DEBUG).getBoolean("disableGoalAI"));
            this.addRenderableWidget(builder.create(20, height - 100, width / 6, 20, new TextComponent("Disable Goal AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 1));
            }));
            builder.withInitialValue(entity.getEntityData().get(Prehistoric.DEBUG).getBoolean("disableMoveAI"));
            this.addRenderableWidget(builder.create(20, height - 70, width / 6, 20, new TextComponent("Disable Move AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 2));
            }));
            builder.withInitialValue(entity.getEntityData().get(Prehistoric.DEBUG).getBoolean("disableLookAI"));
            this.addRenderableWidget(builder.create(20, height - 40, width / 6, 20, new TextComponent("Disable Look AI"), (cycleButton, object) -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AIMessage(entity.getId(), (Boolean) cycleButton.getValue(), (byte) 3));
            }));
            this.addRenderableWidget(new AnimationsList(minecraft, entity.getId(), prehistoric.getAllAnimations().keySet(), this));
           /* xPosInput = this.addRenderableWidget(new EditBox(this.font, 280, height - 40, 50, 20, new TextComponent("")));
            xPosInput.setValue(new DecimalFormat("#.0#", DecimalFormatSymbols.getInstance(Locale.US)).format(mob.getX()));
            yPosInput = this.addRenderableWidget(new EditBox(this.font, 340, height - 40, 50, 20, new TextComponent("")));
            yPosInput.setValue(new DecimalFormat("#.0#", DecimalFormatSymbols.getInstance(Locale.US)).format(mob.getY()));
            zPosInput = this.addRenderableWidget(new EditBox(this.font, 400, height - 40, 50, 20, new TextComponent("")));
            zPosInput.setValue(new DecimalFormat("#.0#", DecimalFormatSymbols.getInstance(Locale.US)).format(mob.getZ()));
            this.addRenderableWidget(new Button(210, height - 40, 130, 20, new TextComponent("Move to Target"), button -> {
                Player player = Minecraft.getInstance().player;
            }));*/
            this.addRenderableWidget(new Button(470, height - 40, 150, 20, new TextComponent("Move to player"), button -> {
                Player player = Minecraft.getInstance().player;
                MessageHandler.DEBUG_CHANNEL.sendToServer(new MovementMessage(entity.getId(), player.getX(), player.getY(), player.getZ()));
            }));
        }
        builder.withInitialValue(showPaths);
        this.addRenderableWidget(builder.create(240, height - 40, 200, 20, new TextComponent("Show Paths"), (cycleButton, object) -> {
            showPaths = (boolean) cycleButton.getValue();
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        drawCenteredString(poseStack, this.font, this.title, width / 2, 20, 16777215);
        if (entity != null) {
            drawString(poseStack, this.font, new TextComponent("Rotation: " + entity.getYRot()), 20, 160, 16777215);
            drawString(poseStack, this.font, new TextComponent("Rotation Body: " + entity.yBodyRot), 20, 180, 16777215);
            drawString(poseStack, this.font, new TextComponent("Rotation Head: " + entity.getYHeadRot()), 20, 200, 16777215);
            drawString(poseStack, this.font, new TextComponent("Start Animation:"), width - width / 4 + 20, 30, 16777215);
        }
        if (entity != null) {
            renderEntityInDebug(70, 280, entity, (float) scale);
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

    private static class AnimationsList extends ContainerObjectSelectionList<AnimationEntry> {

        public AnimationsList(Minecraft minecraft, int mobId, Set<String> animations, DebugScreen screen) {
            super(minecraft, 200, screen.height, 60, screen.height, 25);
            List<String> sortedAnimations = animations.stream().sorted().toList();
            for (String animation : sortedAnimations) {
                addEntry(new AnimationEntry(mobId, animation));
            }
            setRenderBackground(false);
            setRenderTopAndBottom(false);
            x0 = (screen.width - screen.width / 4);
            x1 = x0 + width;
        }

        @Override
        protected int getScrollbarPosition() {
            return x0 + width;
        }
    }

    private static class AnimationEntry extends ContainerObjectSelectionList.Entry<AnimationEntry> {
        private final Button changeButton;

        AnimationEntry(int id, String text) {
            changeButton = new Button(0, 0, 200, 20, new TextComponent(text), button -> {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new AnimationMessage(id, button.getMessage().getContents()));
            });
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
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
        public List<? extends GuiEventListener> children() {
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

    private static class Slider extends AbstractSliderButton {

        protected Component prefix;
        protected Component suffix;

        protected double minValue;
        protected double maxValue;

        protected double stepSize;

        protected boolean drawString;

        private final DecimalFormat format;

        public Slider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue,
                      double stepSize, int precision, boolean drawString) {
            super(x, y, width, height, new TextComponent(""), 0D);
            this.prefix = prefix;
            this.suffix = suffix;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.stepSize = Math.abs(stepSize);
            this.value = this.snapToNearest((currentValue - minValue) / (maxValue - minValue));
            this.drawString = drawString;

            if (stepSize == 0D) {
                precision = Math.min(precision, 4);

                StringBuilder builder = new StringBuilder("0");

                if (precision > 0) {
                    builder.append('.');
                }

                while (precision-- > 0) {
                    builder.append('0');
                }

                this.format = new DecimalFormat(builder.toString());
            } else if (Mth.equal(this.stepSize, Math.floor(this.stepSize))) {
                this.format = new DecimalFormat("0");
            } else {
                this.format = new DecimalFormat(Double.toString(this.stepSize).replaceAll("\\d", "0"));
            }

            this.updateMessage();
        }

        public double getValue() {
            return this.value * (maxValue - minValue) + minValue;
        }

        public void setValue(double value) {
            this.value = this.snapToNearest((value - this.minValue) / (this.maxValue - this.minValue));
            this.updateMessage();
        }

        public String getValueString() {
            return this.format.format(this.getValue());
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            this.setValueFromMouse(mouseX);
        }

        @Override
        protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
            super.onDrag(mouseX, mouseY, dragX, dragY);
            this.setValueFromMouse(mouseX);
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            boolean flag = keyCode == GLFW.GLFW_KEY_LEFT;
            if (flag || keyCode == GLFW.GLFW_KEY_RIGHT) {
                if (this.minValue > this.maxValue) {
                    flag = !flag;
                }
                float f = flag ? -1F : 1F;
                if (stepSize <= 0D) {
                    this.setSliderValue(this.value + (f / (this.width - 8)), false);
                } else {
                    this.setValue(this.getValue() + f * this.stepSize);
                }
            }

            return false;
        }

        private void setValueFromMouse(double mouseX) {
            this.setSliderValue((mouseX - (this.x + 4)) / (this.width - 8), false);
        }

        private void setSliderValue(double value, boolean force) {
            double oldValue = this.value;
            this.value = this.snapToNearest(value);
            if (force || !Mth.equal(oldValue, this.value)) {
                this.applyValue();
            }

            this.updateMessage();
        }

        private double snapToNearest(double value) {
            if (stepSize <= 0D) {
                return Mth.clamp(value, 0D, 1D);
            }

            value = Mth.lerp(Mth.clamp(value, 0D, 1D), this.minValue, this.maxValue);

            value = (stepSize * Math.round(value / stepSize));

            if (this.minValue > this.maxValue) {
                value = Mth.clamp(value, this.maxValue, this.minValue);
            } else {
                value = Mth.clamp(value, this.minValue, this.maxValue);
            }

            return Mth.map(value, this.minValue, this.maxValue, 0D, 1D);
        }

        @Override
        protected void updateMessage() {
            if (this.drawString) {
                this.setMessage(new TextComponent("").append(prefix).append(this.getValueString()).append(suffix));
            } else {
                this.setMessage(new TextComponent(""));
            }
        }

        @Override
        protected void applyValue() {
        }
    }
}
