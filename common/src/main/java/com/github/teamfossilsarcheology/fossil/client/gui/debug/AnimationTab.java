package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.PausableAnimationController;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.C2SForceAnimationMessage;
import com.github.teamfossilsarcheology.fossil.network.debug.C2SRotationMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;

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
                new DebugSlider(20, 30 + (yLeft++) * 30, width / 4, 20, Component.literal("Rotation Y: "), Component.literal(""), 0, 360, 0, 5, 3,
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
                new DebugSlider(20, 30 + (yLeft++) * 30, width / 4, 20, Component.literal("Rotation X: "), Component.literal(""), 0, 360, 0, 5, 3,
                        true) {
                    @Override
                    protected void applyValue() {
                        float rotX = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                        float newRot = (rotXBase + rotX) % 360;
                        MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SRotationMessage(entity.getId(), newRot, C2SRotationMessage.X_ROT));
                        entity.setXRot(newRot);
                    }
                });
        addWidget(Button.builder(Component.literal("Reset Rotation"), button -> {
                    rotYBase = 0;
                    rotXBase = 0;
                    sliderY.setSliderValue(0, true);
                    sliderX.setSliderValue(0, true);
                })
                .bounds(20, 30 + (yLeft++) * 30, width / 6, 20)
                .tooltip(Tooltip.create(Component.literal("client side only")))
                .build());
        Map<String, AnimationController<GeoAnimatable>> controllers = entity.getAnimatableInstanceCache().getManagerForId(entity.getId()).getAnimationControllers();
        addWidget(new AnimationList(width - width / 4 + 20, entity.getAllAnimations(), controllers, minecraft, animationObject -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SForceAnimationMessage(animationObject.controller(), entity.getId(), animationObject.name(), animationObject.speed(), animationObject.transitionLength(), animationObject.loop()));
        }));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawString(minecraft.font, Component.literal("Rotation: " + entity.getYRot()), 20, 160, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Rotation Body: " + entity.yBodyRot), 20, 180, 16777215);
        guiGraphics.drawString(minecraft.font, Component.literal("Rotation Head: " + entity.getYHeadRot()), 20, 200, 16777215);
    }

    private static class AnimationList extends AbstractAnimationList {
        private final Map<String, AbstractWidget> pauseButtons = new HashMap<>();
        private final Map<String, DebugSlider> pauseSliders = new HashMap<>();

        public AnimationList(int x0, Map<String, ? extends AnimationInfo> animations, Map<String, AnimationController<GeoAnimatable>> controllers, Minecraft minecraft, Consumer<AnimationObject> function) {
            super(x0, 250, 21, 120, animations, minecraft, function);
            int buttonX = x0;
            int buttonY = y0 - 110;
            if (controllers.isEmpty()) {
                return;
            }
            List<String> controllerNames = controllers.keySet().stream().toList();
            currentControllerName = controllerNames.get(0);
            addWidget(CycleButton.builder(Component::literal).withValues(controllerNames).withInitialValue(currentControllerName)
                    .create(buttonX, buttonY, 200, 20, Component.literal("Controller"),
                            (button, controller) -> {
                                //Only show pause button for active controller
                                removeWidget(pauseButtons.get(currentControllerName));
                                removeWidget(pauseSliders.get(currentControllerName));
                                addWidget(pauseButtons.get(controller));
                                addWidget(pauseSliders.get(controller));
                                currentControllerName = controller;
                            }));
            addWidget(new DebugSlider(buttonX, buttonY + 21, 99, 20, Component.literal("Transition: "), Component.literal(""), 0, 20, transitionLength, 1, 3, true) {
                @Override
                protected void applyValue() {
                    transitionLength = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                }
            });
            addWidget(new DebugSlider(buttonX + 102, buttonY + 21, 99, 20, Component.literal("Speed: "), Component.literal(""), 0, 3, speed, 0.05, 3, true) {
                @Override
                protected void applyValue() {
                    speed = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                }
            });
            addWidget(OptionInstance.createBoolean("Loop", loop, newLoop -> this.loop = newLoop)
                    .createButton(Minecraft.getInstance().options, buttonX, buttonY + 42, 99));

            for (String controllerName : controllerNames) {
                if (controllers.get(controllerName) instanceof PausableAnimationController<?> pausableAnimationController) {
                    var slider = createPauseSlider(pausableAnimationController, buttonX, buttonY);
                    var button = CycleButton.onOffBuilder(pausableAnimationController.isPaused())
                            .create(buttonX + 102, buttonY + 42, 99, 20, Component.literal("Pause"), (cycleButton, paused) -> {
                                pausableAnimationController.pause(paused);
                                //Only show the pause slider when paused
                                slider.visible = paused;
                                if (pausableAnimationController.getCurrentAnimation() != null) {
                                    //Update max value of pause slider
                                    slider.maxValue = pausableAnimationController.getCurrentAnimation().animation().length();
                                }
                                if (Boolean.TRUE.equals(paused)) {
                                    slider.setSliderValue(pausableAnimationController.getCurrentTick() / slider.maxValue, true);
                                }
                            });
                    pauseSliders.put(controllerName, slider);
                    pauseButtons.put(controllerName, button);
                    if (controllerName.equals(currentControllerName)) {
                        addWidget(button);
                        addWidget(slider);
                    }
                }
            }
        }

        private static @NotNull DebugSlider createPauseSlider(PausableAnimationController<?> pausableAnimationController, int buttonX, int buttonY) {
            double tick = pausableAnimationController.getCurrentTick();
            double maxTick = tick;
            if (pausableAnimationController.getCurrentAnimation() != null) {
                maxTick = pausableAnimationController.getCurrentAnimation().animation().length() - 1;
            }
            var slider = new DebugSlider(buttonX, buttonY + 63, 200, 20, Component.literal("Time: "), Component.literal(""), 0, maxTick,
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
