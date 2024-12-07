package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.C2SForceAnimationMessage;
import com.github.teamfossilsarcheology.fossil.network.debug.C2SRotationMessage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.controller.AnimationController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AnimationTab<T extends Mob & PrehistoricAnimatable<?>> extends DebugTab<T> {
    private float rotYBase;
    private float rotXBase;

    protected AnimationTab(DebugScreen debugScreen, @NotNull T mob) {
        super(debugScreen, mob);
        this.rotYBase = mob.yBodyRot;
        this.rotXBase = mob.getXRot();
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
        addWidget(new Button(20, 30 + (yLeft++) * 30, width / 6, 20, new TextComponent("Reset Rotation"), button -> {
            rotYBase = 0;
            rotXBase = 0;
            sliderY.setSliderValue(0, true);
            sliderX.setSliderValue(0, true);
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("client side only"), i, j);
        }));
        Map<String, AnimationController> controllers = entity.getFactory().getOrCreateAnimationData(entity.getId()).getAnimationControllers();
        addWidget(new AnimationList(width - width / 4 + 20, entity.getAllAnimations(), controllers, minecraft, animationObject -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SForceAnimationMessage(animationObject.controller(), entity.getId(), animationObject.name(), animationObject.speed(), animationObject.transitionLength(), animationObject.loop()));
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        drawString(poseStack, minecraft.font, new TextComponent("Rotation: " + entity.getYRot()), 20, 160, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Rotation Body: " + entity.yBodyRot), 20, 180, 16777215);
        drawString(poseStack, minecraft.font, new TextComponent("Rotation Head: " + entity.getYHeadRot()), 20, 200, 16777215);
    }

    private static class AnimationList extends AbstractAnimationList {
        private final Map<String, AbstractWidget> pauseButtons = new HashMap<>();
        private final Map<String, DebugSlider> pauseSliders = new HashMap<>();

        public AnimationList(int x0, Map<String, ? extends AnimationInfo> animations, Map<String, AnimationController> controllers, Minecraft minecraft, Consumer<AnimationObject> function) {
            super(x0, 250, 21, 120, animations, minecraft, function);
            int buttonX = x0;
            int buttonY = y0 - 110;
            if (!controllers.isEmpty()) {
                List<String> controllerNames = controllers.keySet().stream().toList();
                currentControllerName = controllerNames.get(0);
                addWidget(CycleOption.create("Controller", () -> controllerNames, TextComponent::new,
                                options -> currentControllerName, (options, option, controller) -> {
                                    //Only show pause button for active controller
                                    removeWidget(pauseButtons.get(currentControllerName));
                                    removeWidget(pauseSliders.get(currentControllerName));
                                    addWidget(pauseButtons.get(controller));
                                    addWidget(pauseSliders.get(controller));
                                    currentControllerName = controller;
                                })
                        .createButton(Minecraft.getInstance().options, buttonX, buttonY, 200));
                addWidget(new DebugSlider(buttonX, buttonY + 21, 99, 20, new TextComponent("Transition: "), new TextComponent(""), 0, 20, transitionLength, 1, 3, true) {
                    @Override
                    protected void applyValue() {
                        transitionLength = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                    }
                });
                addWidget(new DebugSlider(buttonX + 102, buttonY + 21, 99, 20, new TextComponent("Speed: "), new TextComponent(""), 0, 3, speed, 0.05, 3, true) {
                    @Override
                    protected void applyValue() {
                        speed = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                    }
                });
                addWidget(CycleOption.createOnOff("Loop", options -> loop, (options, option, loop) -> this.loop = loop)
                        .createButton(Minecraft.getInstance().options, buttonX, buttonY + 42, 99));

                for (String controllerName : controllerNames) {
                    if (controllers.get(controllerName) instanceof PausableAnimationController<?> pausableAnimationController) {
                        var slider = createPauseSlider(pausableAnimationController, buttonX, buttonY);
                        var button = CycleOption.createOnOff("Pause", options -> pausableAnimationController.isPaused(), (options, option, paused) -> {
                            pausableAnimationController.pause(paused);
                            //Only show the pause slider when paused
                            slider.visible = paused;
                            if (pausableAnimationController.getCurrentAnimation() != null) {
                                //Update max value of pause slider
                                slider.maxValue = pausableAnimationController.getCurrentAnimation().animationLength;
                            }
                            if (Boolean.TRUE.equals(paused)) {
                                slider.setSliderValue(pausableAnimationController.getCurrentTick() / slider.maxValue, true);
                            }
                        }).createButton(Minecraft.getInstance().options, buttonX + 102, buttonY + 42, 99);
                        pauseSliders.put(controllerName, slider);
                        pauseButtons.put(controllerName, button);
                        if (controllerName.equals(currentControllerName)) {
                            addWidget(button);
                            addWidget(slider);
                        }
                    }
                }
            }
        }

        private @NotNull DebugSlider createPauseSlider(PausableAnimationController<?> pausableAnimationController, int buttonX, int buttonY) {
            double tick = pausableAnimationController.getCurrentTick();
            double maxTick = tick;
            if (pausableAnimationController.getCurrentAnimation() != null) {
                maxTick = pausableAnimationController.getCurrentAnimation().animationLength - 1;
            }
            var slider = new DebugSlider(buttonX, buttonY + 63, 200, 20, new TextComponent("Time: "), new TextComponent(""), 0, maxTick,
                    tick, 1, 3, true) {
                @Override
                protected void applyValue() {
                    pausableAnimationController.overrideTick(stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                }
            };
            slider.visible = pausableAnimationController.isPaused();
            return slider;
        }

        @Override
        protected int getEntryLeftPos() {
            return super.getEntryLeftPos() + width / 2;
        }

        @Override
        protected int getScrollbarPosition() {
            return super.getScrollbarPosition() + width / 2 - 4;
        }
    }
}
