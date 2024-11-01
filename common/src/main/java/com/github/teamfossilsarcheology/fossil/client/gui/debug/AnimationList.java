package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AnimationList extends DebugSelectionList<AnimationList.AnimationEntry> {
    private final Consumer<AnimationObject> consumer;
    private String currentController;
    private double transitionLength = 1;
    private boolean loop;

    public AnimationList(int x0, int height, Map<String, ? extends AnimationInfo> animations, List<String> controllers, boolean instruction, Minecraft minecraft, Consumer<AnimationObject> function) {
        super(minecraft, 100, 215, height, 60, height + 60, 25);
        this.x0 = x0;
        this.x1 = x0 + width;
        int buttonX = x0 + rowWidth + 15;
        List<String> sortedAnimations = animations.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getKey).toList();
        sortedAnimations.forEach(animation -> addEntry(new AnimationEntry(animation)));
        if (!controllers.isEmpty()) {
            currentController = controllers.get(0);
            addWidget(CycleOption.create("", () -> controllers, TextComponent::new,
                            options -> currentController, (options, option, controller) -> currentController = controller)
                    .createButton(Minecraft.getInstance().options, buttonX, y0, 100));
        }
        DebugSlider a = new DebugSlider(buttonX, y0 + 25, 100, 20, new TextComponent(instruction ? "Count: " : "Transition: "), new TextComponent(""), 0, 20, transitionLength, 1, 3, true) {
            @Override
            protected void applyValue() {
                transitionLength = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        addWidget(a);
        addWidget(CycleOption.createOnOff(instruction ? "Time based" : "Loop", options -> loop, (options, option, loop) -> this.loop = loop)
                .createButton(Minecraft.getInstance().options, buttonX, y0 + 50, 100));
        setRenderBackground(false);
        setRenderTopAndBottom(false);
        this.consumer = function;
    }

    @Override
    protected @Nullable AnimationEntry getEntryAtPosition(double mouseX, double mouseY) {
        int x1 = x0 + getRowWidth();
        int m = Mth.floor(mouseY - (double) y0) - headerHeight + (int) getScrollAmount() - 4;
        int n = m / itemHeight;
        if (mouseX < getScrollbarPosition() && mouseX >= x0 && mouseX <= x1 && n >= 0 && m >= 0 && n < getItemCount()) {
            return children().get(n);
        }
        return null;
    }

    @Override
    protected int getScrollbarPosition() {
        return x0 + rowWidth + 6;
    }

    protected class AnimationEntry extends ContainerObjectSelectionList.Entry<AnimationEntry> {
        private final Button changeButton;

        AnimationEntry(String animation) {
            String[] split = animation.split("\\.");
            TextComponent display = new TextComponent(split.length > 0 ? StringUtils.capitalize(split[split.length - 1]) : "");
            changeButton = new Button(0, 0, 100, 20, display, button -> {
                consumer.accept(new AnimationObject(animation, currentController, transitionLength, loop));
            });
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(changeButton);
        }

        @Override
        public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver,
                           float partialTick) {
            changeButton.x = x0;
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

    public record AnimationObject(String name, String controller, double transitionLength, boolean loop) {

    }
}
