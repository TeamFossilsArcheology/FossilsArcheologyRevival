package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DebugSelectionList<E extends ContainerObjectSelectionList.Entry<E>> extends ContainerObjectSelectionList<E> {
    protected final List<Widget> widgets = new ArrayList<>();
    protected final List<GuiEventListener> renderables = new ArrayList<>();
    protected final int rowWidth;
    private GuiEventListener focused;

    public DebugSelectionList(Minecraft minecraft, int rowWidth, int width, int height, int y0, int y1, int itemHeight) {
        super(minecraft, width, height, y0, y1, itemHeight);
        this.rowWidth = rowWidth;
    }

    @Override
    public int getRowWidth() {
        return rowWidth;
    }

    @Override
    public @NotNull Optional<GuiEventListener> getChildAt(double mouseX, double mouseY) {
        for (GuiEventListener guiEventListener : renderables) {
            if (!guiEventListener.isMouseOver(mouseX, mouseY)) continue;
            return Optional.of(guiEventListener);
        }
        return super.getChildAt(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (GuiEventListener guiEventListener : renderables) {
            if (!guiEventListener.mouseClicked(mouseX, mouseY, button)) continue;
            focused = guiEventListener;
            if (button == 0) {
                setDragging(true);
            }
            return true;
        }
        focused = null;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (focused != null && isDragging() && button == 0) {
            return focused.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    protected void renderList(PoseStack poseStack, int x, int y, int mouseX, int mouseY, float partialTick) {
        super.renderList(poseStack, x, y, mouseX, mouseY, partialTick);
        widgets.forEach(widget -> widget.render(poseStack, mouseX, mouseY, partialTick));
    }

    protected <T extends Widget & GuiEventListener> T addWidget(T widget) {
        widgets.add(widget);
        renderables.add(widget);
        return widget;
    }
}
