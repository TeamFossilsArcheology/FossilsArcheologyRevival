package com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.InstructionTab;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InstructionsList extends AbstractContainerEventHandler implements Renderable {
    static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/resource_packs.png");
    private static final int WIDTH = 200;
    private static final int ITEM_HEIGHT = 20;
    private static final int Y_0 = 5;
    private static final int Y_1 = Y_0 + 295;
    private static final int X_0 = 5;
    private static final int X_1 = X_0 + WIDTH;
    private static final int ROW_WIDTH = X_1 - X_0 - 10;
    private final List<InstructionEntry> children = new ArrayList<>();
    private final List<Instruction> instructions;
    private final Minecraft minecraft;
    @Nullable
    private InstructionEntry selected;
    @Nullable
    private InstructionEntry hovered;
    private final Button removeButton;
    private final Button upButton;
    private final Button downButton;
    private double dragStartX;
    private double dragStartY;
    private double dragMoveX;
    private double dragMoveY;
    private int dragIndex = -1;
    private boolean dragOffset;
    private InstructionEntry dragging;


    public InstructionsList(InstructionTab.Pair pair, Minecraft minecraft) {
        this.minecraft = minecraft;
        this.instructions = pair.instructions();
        instructions.forEach(instruction -> children.add(new InstructionEntry(instruction, minecraft)));
        removeButton = Button.builder(Component.literal("Remove item"), button -> {
            if (selected != null) {
                instructions.remove(children.indexOf(selected));
                children.remove(selected);
                selected = null;
                if (!children.isEmpty()) {
                    selected = children.get(0);
                }
            }
        }).bounds(X_0, Y_1 + 5, 70, 20).build();
        upButton = new MoveButton(X_0 + 110, Y_1 + 5, 20, 20, Component.literal(""), button -> {
            int i = children.indexOf(selected);
            if (i != 0) {
                Collections.swap(children, i, i - 1);
                Collections.swap(instructions, i, i - 1);
            }
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
                int left = getX() + width / 2;
                int top = getY() + (height - 8) / 2;
                guiGraphics.blit(ICON_OVERLAY_LOCATION, left - 8, top - 5, 111, 0, 32, 32, 256, 256);
            }
        };
        downButton = new MoveButton(X_0 + 80, Y_1 + 5, 20, 20, Component.literal(""), button -> {
            int i = children.indexOf(selected);
            if (i != children.size() - 1) {
                Collections.swap(children, i, i + 1);
                Collections.swap(instructions, i, i + 1);
            }
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
                int left = getX() + width / 2;
                int top = getY() + (height - 8) / 2;
                guiGraphics.blit(ICON_OVERLAY_LOCATION, left - 23, top - 4, 64, 16, 32, 32, 256, 256);
            }
        };
    }

    public void addInstruction(Instruction instruction) {
        children.add(new InstructionEntry(instruction, minecraft));
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return children;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        InstructionTab.highlightInstructionEntity = null;
        InstructionTab.highlightInstruction = null;
        if (isMouseOver(mouseX, mouseY)) {
            InstructionEntry entry = getEntryAtPosition(mouseX, mouseY);
            if (entry != null) {
                if (entry.mouseClicked(mouseX, mouseY, button)) {
                    setFocused(entry);
                    setDragging(true);
                    return true;
                }
            } else if (button == 0) {
                selected = null;
                return true;
            }
        }
        if (removeButton.isMouseOver(mouseX, mouseY)) {
            return removeButton.mouseClicked(mouseX, mouseY, button);
        }
        if (upButton.isMouseOver(mouseX, mouseY)) {
            return upButton.mouseClicked(mouseX, mouseY, button);
        }
        if (downButton.isMouseOver(mouseX, mouseY)) {
            return downButton.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (dragging != null) {
            if (dragging.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
                Pair<Vec3, Vec3> pair = getPositionOfEntry(dragging);
                Vec3 compare = pair.getFirst().add(dragMoveX, dragMoveY, 0);
                if (compare.x < getRowLeft()) {
                    compare = pair.getSecond().add(dragMoveX, dragMoveY, 0);
                }
                InstructionEntry above = getEntryAtPosition(compare.x, compare.y - ITEM_HEIGHT / 2d);
                InstructionEntry below = getEntryAtPosition(compare.x, compare.y + ITEM_HEIGHT / 2d);
                if (above == null && below != null) {
                    dragIndex = 0;
                } else if (below == null && above != null) {
                    dragIndex = children.size();
                } else {
                    dragIndex = children.indexOf(below);
                }
                dragOffset = children.indexOf(dragging) < dragIndex;
                return true;
            }
            dragIndex = -1;
            return false;
        }
        InstructionEntry entry = getEntryAtPosition(mouseX, mouseY);
        if (entry != null) {
            return entry.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
        return false;
    }

    private int getRowLeft() {
        return X_0 + WIDTH / 2 - ROW_WIDTH / 2;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (getFocused() != null) {
            getFocused().mouseReleased(mouseX, mouseY, button);
        }
        if (dragging != null && dragIndex != -1) {
            int dragTarget = dragOffset ? dragIndex - 1 : dragIndex;
            int prevIndex = children.indexOf(dragging);
            if (dragTarget != prevIndex) {
                if (prevIndex < dragTarget) {
                    Collections.rotate(children.subList(prevIndex, dragTarget + 1), -1);
                    Collections.rotate(instructions.subList(prevIndex, dragTarget + 1), -1);
                } else {
                    Collections.rotate(children.subList(dragTarget, prevIndex + 1), 1);
                    Collections.rotate(instructions.subList(dragTarget, prevIndex + 1), 1);
                }

            }
        }
        dragging = null;
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseY >= Y_0 && mouseY <= Y_1 && mouseX >= X_0 && mouseX <= X_1;
    }

    private Pair<Vec3, Vec3> getPositionOfEntry(InstructionEntry entry) {
        int left = getRowLeft();
        int right = left + ROW_WIDTH;
        int centerY = children.indexOf(entry) * ITEM_HEIGHT + Y_0 + 2 + ITEM_HEIGHT / 2;
        return new Pair<>(new Vec3(left, centerY, 0), new Vec3(right, centerY, 0));
    }

    @Nullable
    private InstructionEntry getEntryAtPosition(double mouseX, double mouseY) {
        int left = getRowLeft();
        int right = left + ROW_WIDTH;
        int m = Mth.floor(mouseY - Y_0) - 2;
        int n = m / ITEM_HEIGHT;
        return mouseX >= left && mouseX <= right && n >= 0 && m >= 0 && n < children.size() ? (InstructionEntry) children().get(n) : null;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        hovered = isMouseOver(mouseX, mouseY) ? getEntryAtPosition(mouseX, mouseY) : null;
        //if (renderBackground) {
        RenderSystem.setShaderTexture(0, Screen.BACKGROUND_LOCATION);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferBuilder.vertex(X_0, Y_1, 0).uv(X_0 / 32f, Y_1 / 32f).color(32, 32, 32, 255).endVertex();
        bufferBuilder.vertex(X_1, Y_1, 0).uv(X_1 / 32f, Y_1 / 32f).color(32, 32, 32, 255).endVertex();
        bufferBuilder.vertex(X_1, Y_0, 0).uv(X_1 / 32f, Y_0 / 32f).color(32, 32, 32, 255).endVertex();
        bufferBuilder.vertex(X_0, Y_0, 0).uv(X_0 / 32f, Y_0 / 32f).color(32, 32, 32, 255).endVertex();
        tesselator.end();
        //}

        int rowLeft = X_0 + WIDTH / 2 - ROW_WIDTH / 2 + 2;
        renderList(guiGraphics, rowLeft, Y_0 + 4, mouseX, mouseY, partialTick);
        removeButton.render(guiGraphics, mouseX, mouseY, partialTick);
        upButton.render(guiGraphics, mouseX, mouseY, partialTick);
        downButton.render(guiGraphics, mouseX, mouseY, partialTick);

        RenderSystem.disableBlend();
    }

    protected void renderList(GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY, float partialTick) {
        int itemCount = children.size();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        int rowLeft = getRowLeft();
        int rowRight = rowLeft + ROW_WIDTH;
        int rowHeight = ITEM_HEIGHT - 4;
        for (int i = 0; i < itemCount; ++i) {
            int rowTop = y + i * ITEM_HEIGHT;
            int rowBottom = rowTop + ITEM_HEIGHT;
            if (rowBottom >= Y_0 && rowTop <= Y_1) {
                InstructionEntry entry = children.get(i);
                if (Objects.equals(selected, entry)) {
                    renderEntry(tesselator, bufferBuilder, rowLeft, rowRight, rowTop, rowHeight);
                }
                entry.render(guiGraphics, rowTop, rowLeft, Objects.equals(hovered, entry) && dragging == null);

                if (Objects.equals(dragging, entry)) {
                    int dragLeft = (int) ((dragStartX + dragMoveX) + WIDTH / 2d - ROW_WIDTH / 2d);
                    int dragRight = (int) ((dragStartX + dragMoveX) + WIDTH / 2d + ROW_WIDTH / 2d);
                    int dragTop = (int) (rowTop + ((dragStartY + dragMoveY) - Y_0));
                    renderEntry(tesselator, bufferBuilder, dragLeft, dragRight, dragTop, rowHeight);
                    entry.render(guiGraphics, dragTop, dragLeft, true);
                }
            }
        }
        if (dragging != null && dragIndex != -1) {
            int arrowLeft = rowLeft - 2;
            int arrowHeight = rowHeight / 2;
            int arrowTop = y + dragIndex * ITEM_HEIGHT - arrowHeight;
            RenderSystem.setShader(GameRenderer::getPositionShader);
            if (dragIndex == children.indexOf(dragging) + (dragOffset ? 1 : 0)) {
                RenderSystem.setShaderColor(1, 0.5f, 0.5f, 1);
            } else {
                RenderSystem.setShaderColor(0.5f, 1, 0.5f, 1);
            }
            bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION);
            bufferBuilder.vertex(arrowLeft, arrowTop + arrowHeight + 4, 0.0).endVertex();
            bufferBuilder.vertex(arrowLeft + 6, arrowTop + arrowHeight / 2d + 2, 0.0).endVertex();
            bufferBuilder.vertex(arrowLeft, arrowTop, 0.0).endVertex();
            tesselator.end();
        }
    }

    private void renderEntry(Tesselator tesselator, BufferBuilder bufferBuilder, int rowLeft, int rowRight, int rowTop, int rowHeight) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        float f = 0.75f;
        RenderSystem.setShaderColor(f, f, f, 1);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        bufferBuilder.vertex(rowLeft, rowTop + rowHeight + 2, 0.0).endVertex();
        bufferBuilder.vertex(rowRight, rowTop + rowHeight + 2, 0.0).endVertex();
        bufferBuilder.vertex(rowRight, rowTop - 2, 0.0).endVertex();
        bufferBuilder.vertex(rowLeft, rowTop - 2, 0.0).endVertex();
        tesselator.end();
        RenderSystem.setShaderColor(0, 0, 0, 1);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        bufferBuilder.vertex(rowLeft + 1, rowTop + rowHeight + 1, 0.0).endVertex();
        bufferBuilder.vertex(rowRight - 1, rowTop + rowHeight + 1, 0.0).endVertex();
        bufferBuilder.vertex(rowRight - 1, rowTop - 1, 0.0).endVertex();
        bufferBuilder.vertex(rowLeft + 1, rowTop - 1, 0.0).endVertex();
        tesselator.end();
    }

    static class MoveButton extends Button {
        protected MoveButton(int x, int y, int width, int height, Component message, OnPress onPress) {
            super(x, y, width, height, message, onPress, Button.DEFAULT_NARRATION);
        }
    }

    class InstructionEntry implements GuiEventListener {
        private final Instruction instruction;
        private final Minecraft minecraft;

        public InstructionEntry(Instruction instruction, Minecraft minecraft) {
            this.instruction = instruction;
            this.minecraft = minecraft;
        }

        public void render(GuiGraphics guiGraphics, int top, int left, boolean isMouseOver) {
            if (isMouseOver) {
                guiGraphics.drawString(minecraft.font, Component.literal(instruction.toString()), left + 4, top + 4, Integer.parseUnsignedInt("a8a2a2", 16));
            } else {
                guiGraphics.drawString(minecraft.font, Component.literal(instruction.toString()), left + 4, top + 4, Integer.parseUnsignedInt("ffffff", 16));
            }
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
            if (button == 0) {
                if (InstructionsList.this.dragging == null) {
                    InstructionsList.this.dragStartX = X_0;
                    InstructionsList.this.dragStartY = Y_0;
                    InstructionsList.this.dragMoveX = 0;
                    InstructionsList.this.dragMoveY = 0;
                }
                InstructionsList.this.dragging = this;
                InstructionsList.this.dragMoveX += dragX;
                InstructionsList.this.dragMoveY += dragY;
                return true;
            }
            return false;
        }

        @Override
        public void setFocused(boolean focused) {

        }

        @Override
        public boolean isFocused() {
            return false;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
                InstructionsList.this.selected = this;
                InstructionTab.highlightInstruction = instruction;
                return true;
            } else {
                return false;
            }
        }
    }
}
