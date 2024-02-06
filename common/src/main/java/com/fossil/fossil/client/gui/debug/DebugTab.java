package com.fossil.fossil.client.gui.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class DebugTab extends GuiComponent implements ContainerEventHandler, NarratableEntry {
    protected final DebugScreen debugScreen;
    protected final Minecraft minecraft;
    protected final Entity entity;
    protected final List<Widget> widgets = new ArrayList<>();
    protected final List<GuiEventListener> renderables = new ArrayList<>();
    protected int width;
    protected int height;
    @Nullable
    private GuiEventListener focused;
    private boolean isDragging;

    protected DebugTab(DebugScreen debugScreen, Entity entity) {
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
    public @NotNull List<? extends GuiEventListener> children() {
        return renderables;
    }

    @Override
    public boolean isDragging() {
        return isDragging;
    }

    @Override
    public void setDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }

    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return focused;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener focused) {
        this.focused = focused;
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
