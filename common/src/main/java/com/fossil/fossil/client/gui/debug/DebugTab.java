package com.fossil.fossil.client.gui.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class DebugTab<E extends Entity> extends AbstractContainerEventHandler implements GuiEventListener, NarratableEntry {
    protected final DebugScreen debugScreen;
    protected final Minecraft minecraft;
    protected final E entity;
    protected final List<Widget> widgets = new ArrayList<>();
    protected final List<GuiEventListener> renderables = new ArrayList<>();
    protected int width;
    protected int height;

    protected DebugTab(DebugScreen debugScreen, E entity) {
        this.debugScreen = debugScreen;
        this.minecraft = Minecraft.getInstance();
        this.entity = entity;
    }

    protected void init(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        for (Widget widget : widgets) {
            widget.render(poseStack, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (getFocused() != null) {
            getFocused().mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseY >= 0 && mouseY <= height && mouseX >= 0 && mouseX <= width;
    }

    /**
     * On switch to this tab
     */
    protected void onOpen() {

    }

    /**
     * On switch to different tab
     */
    protected void onClose() {

    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return renderables;
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }

    protected <T extends Widget & GuiEventListener> T addWidget(T widget) {
        widgets.add(widget);
        renderables.add(widget);
        return widget;
    }
}
