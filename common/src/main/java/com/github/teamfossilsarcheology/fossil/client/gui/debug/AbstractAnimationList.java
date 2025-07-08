package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AbstractAnimationList extends DebugSelectionList<AbstractAnimationList.AnimationEntry> {
    private final Consumer<AnimationObject> consumer;
    protected String currentControllerName;
    protected double speed = 1;
    protected int transitionLength = 5;
    protected boolean loop;

    public AbstractAnimationList(int x0, int height, int itemHeight, int yOffset, Map<String, ? extends AnimationInfo> animations, Minecraft minecraft, Consumer<AnimationObject> function) {
        super(minecraft, 100, 215, height, yOffset, height + 60, itemHeight);
        this.x0 = x0;
        this.x1 = x0 + width;
        List<String> sortedAnimations = animations.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getKey).toList();
        sortedAnimations.forEach(animation -> addEntry(new AnimationEntry(animation)));
        setRenderBackground(false);
        setRenderTopAndBottom(false);
        this.consumer = function;
    }

    @Override
    protected @Nullable AnimationEntry getEntryAtPosition(double mouseX, double mouseY) {
        int x1 = getEntryLeftPos() + getRowWidth();
        int m = Mth.floor(mouseY - (double) y0) - headerHeight + (int) getScrollAmount() - 4;
        int n = m / itemHeight;
        if (mouseX < getScrollbarPosition() && mouseX >= x0 && mouseX <= x1 && n >= 0 && m >= 0 && n < getItemCount()) {
            return children().get(n);
        }
        return null;
    }

    protected int getEntryLeftPos() {
        return x0;
    }

    @Override
    protected int getScrollbarPosition() {
        return x0 + rowWidth + 6;
    }

    protected class AnimationEntry extends ContainerObjectSelectionList.Entry<AnimationEntry> {
        private final Button changeButton;

        AnimationEntry(String animation) {
            String[] split = animation.split("\\.");
            Component display = Component.literal(split.length > 0 ? StringUtils.capitalize(split[split.length - 1]) : "");
            changeButton = Button.builder(display, button -> consumer.accept(new AnimationObject(animation, currentControllerName, speed, transitionLength, loop)))
                    .bounds(0, 0, 100, 20).build();
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(changeButton);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver,
                           float partialTick) {
            changeButton.setX(getEntryLeftPos());
            changeButton.setY(top);
            changeButton.render(guiGraphics, mouseX, mouseY, partialTick);
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

    public record AnimationObject(String name, String controller, double speed, int transitionLength, boolean loop) {

    }
}
