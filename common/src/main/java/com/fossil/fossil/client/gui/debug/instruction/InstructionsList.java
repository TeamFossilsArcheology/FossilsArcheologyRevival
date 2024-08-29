package com.fossil.fossil.client.gui.debug.instruction;

import com.fossil.fossil.client.gui.debug.InstructionTab;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InstructionsList extends AbstractContainerEventHandler implements Widget {
    static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/resource_packs.png");
    private final List<InstructionEntry> children = new ArrayList<>();
    private final Minecraft minecraft;
    @Nullable
    private InstructionEntry selected;
    @Nullable
    private InstructionEntry hovered;
    protected int width = 200;
    protected int y0 = 5;
    protected int y1 = y0 + 200;
    protected int x0 = 5;
    protected int x1 = x0 + width;
    protected int itemHeight = 20;
    protected int rowWidth = x1 - x0 - 10;
    private final Button removeButton;
    private final Button upButton;
    private final Button downButton;


    public InstructionsList(List<Instruction> instructions, Minecraft minecraft) {
        this.minecraft = minecraft;
        instructions.forEach(instruction -> children.add(new InstructionEntry(instruction, minecraft)));
        removeButton = new Button(x0, y1 + 5, 70, 20, new TextComponent("Remove item"), button -> {
            if (selected != null) {
                instructions.remove(children.indexOf(selected));
                children.remove(selected);
                selected = null;
                if (!children.isEmpty()) {
                    selected = children.get(0);
                }
            }
        });
        upButton = new Button(x0 + 110, y1 + 5, 20, 20, new TextComponent(""), button -> {
            int i = children.indexOf(selected);
            if (i != 0) {
                Collections.swap(children, i, i - 1);
                Collections.swap(instructions, i, i - 1);
            }
        }) {
            @Override
            public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
                super.renderButton(poseStack, mouseX, mouseY, partialTick);
                RenderSystem.setShaderTexture(0, ICON_OVERLAY_LOCATION);
                int left = x + width / 2;
                int top = y + (height - 8) / 2;
                GuiComponent.blit(poseStack, left - 8, top - 5, 111, 0, 32, 32, 256, 256);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
            }
        };
        downButton = new Button(x0 + 80, y1 + 5, 20, 20, new TextComponent(""), button -> {
            int i = children.indexOf(selected);
            if (i != children.size() - 1) {
                Collections.swap(children, i, i + 1);
                Collections.swap(instructions, i, i + 1);
            }
        }) {
            @Override
            public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
                super.renderButton(poseStack, mouseX, mouseY, partialTick);
                RenderSystem.setShaderTexture(0, ICON_OVERLAY_LOCATION);
                int left = x + width / 2;
                int top = y + (height - 8) / 2;
                GuiComponent.blit(poseStack, left - 23, top - 4, 64, 16, 32, 32, 256, 256);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
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
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (getFocused() != null) {
            getFocused().mouseReleased(mouseX, mouseY, button);
        }

        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseY >= y0 && mouseY <= y1 && mouseX >= x0 && mouseX <= x1;
    }

    @Nullable
    private InstructionEntry getEntryAtPosition(double mouseX, double mouseY) {
        int i = rowWidth / 2;
        int xCenter = x0 + width / 2;
        int k = xCenter - i;
        int l = xCenter + i;
        int m = Mth.floor(mouseY - y0) - 4;
        int n = m / itemHeight;
        return mouseX >= k && mouseX <= l && n >= 0 && m >= 0 && n < children.size() ? (InstructionEntry) children().get(n) : null;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        y1 = Math.max(200, 200);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        hovered = isMouseOver(mouseX, mouseY) ? getEntryAtPosition(mouseX, mouseY) : null;
        //if (renderBackground) {
        RenderSystem.setShaderTexture(0, GuiComponent.BACKGROUND_LOCATION);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferBuilder.vertex(x0, y1, 0).uv(x0 / 32f, y1 / 32f).color(32, 32, 32, 255).endVertex();
        bufferBuilder.vertex(x1, y1, 0).uv(x1 / 32f, y1 / 32f).color(32, 32, 32, 255).endVertex();
        bufferBuilder.vertex(x1, y0, 0).uv(x1 / 32f, y0 / 32f).color(32, 32, 32, 255).endVertex();
        bufferBuilder.vertex(x0, y0, 0).uv(x0 / 32f, y0 / 32f).color(32, 32, 32, 255).endVertex();
        tesselator.end();
        //}

        int rowLeft = x0 + width / 2 - rowWidth / 2 + 2;
        renderList(poseStack, rowLeft, y0 + 4, mouseX, mouseY, partialTick);
        removeButton.render(poseStack, mouseX, mouseY, partialTick);
        upButton.render(poseStack, mouseX, mouseY, partialTick);
        downButton.render(poseStack, mouseX, mouseY, partialTick);

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    protected void renderList(PoseStack poseStack, int x, int y, int mouseX, int mouseY, float partialTick) {
        int itemCount = children.size();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        for (int i = 0; i < itemCount; ++i) {
            int rowTop = y0 + 4 + i * itemHeight;
            int rowBottom = rowTop + itemHeight;
            if (rowBottom >= y0 && rowTop <= y1) {
                int m = y + i * itemHeight;
                int n = itemHeight - 4;
                InstructionEntry entry = children.get(i);
                int rowLeft;
                if (Objects.equals(selected, entry)) {
                    rowLeft = x0 + width / 2 - rowWidth / 2;
                    int q = x0 + width / 2 + rowWidth / 2;
                    RenderSystem.disableTexture();
                    RenderSystem.setShader(GameRenderer::getPositionShader);
                    float f = false ? 1 : 0.5f;
                    RenderSystem.setShaderColor(f, f, f, 1);
                    bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
                    bufferBuilder.vertex(rowLeft, m + n + 2, 0.0).endVertex();
                    bufferBuilder.vertex(q, m + n + 2, 0.0).endVertex();
                    bufferBuilder.vertex(q, m - 2, 0.0).endVertex();
                    bufferBuilder.vertex(rowLeft, m - 2, 0.0).endVertex();
                    tesselator.end();
                    RenderSystem.setShaderColor(0, 0, 0, 1);
                    bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
                    bufferBuilder.vertex(rowLeft + 1, m + n + 1, 0.0).endVertex();
                    bufferBuilder.vertex(q - 1, m + n + 1, 0.0).endVertex();
                    bufferBuilder.vertex(q - 1, m - 1, 0.0).endVertex();
                    bufferBuilder.vertex(rowLeft + 1, m - 1, 0.0).endVertex();
                    tesselator.end();
                    RenderSystem.enableTexture();
                }

                rowLeft = x0 + width / 2 - rowWidth / 2 + 2;
                entry.render(poseStack, i, rowTop, rowLeft, rowWidth, n, mouseX, mouseY, Objects.equals(hovered, entry), partialTick);
            }
        }

    }

    class InstructionEntry implements GuiEventListener {
        private final Instruction instruction;
        private final Minecraft minecraft;

        public InstructionEntry(Instruction instruction, Minecraft minecraft) {
            this.instruction = instruction;
            this.minecraft = minecraft;
        }

        public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            if (isMouseOver) {
                drawString(poseStack, minecraft.font, new TextComponent(instruction.toString()), left, top, Integer.parseUnsignedInt("a8a2a2", 16));
            } else {
                drawString(poseStack, minecraft.font, new TextComponent(instruction.toString()), left, top, Integer.parseUnsignedInt("ffffff", 16));
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
                InstructionsList.this.selected = this;
                if (instruction instanceof Instruction.Attack attack) {
                    InstructionTab.highlightInstructionEntity = minecraft.level.getEntity(attack.targetId);
                } else if (instruction instanceof Instruction.MoveTo) {
                    InstructionTab.highlightInstruction = instruction;
                }
                return true;
            } else {
                return false;
            }
        }
    }
}
