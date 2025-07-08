package com.github.teamfossilsarcheology.fossil.compat.rei;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import me.shedaniel.clothconfig2.ClothConfigInitializer;
import me.shedaniel.clothconfig2.api.scroll.ScrollingContainer;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.math.impl.PointHelper;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.widgets.*;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class MultiOutputCategory implements DisplayCategory<MultiOutputDisplay> {

    @Override
    public int getDisplayHeight() {
        return 106;
    }

    @Override
    public List<Widget> setupDisplay(MultiOutputDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getMinX(), bounds.getMinY());
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(bounds.getCenterX() - 8, startPoint.y + 5)).entries(display.getInputEntries().get(0)).markInput());

        Rectangle rectangle = new Rectangle(bounds.getCenterX() - (bounds.width / 2) + 4, bounds.y + 23, bounds.width - 8, bounds.height - 28);
        widgets.add(Widgets.createSlotBase(rectangle));

        widgets.add(new ScrollableSlotsWidget(rectangle, display.outputs));
        return widgets;
    }


    private static class ScrollableSlotsWidget extends WidgetWithBounds {
        private static final float DEFAULT_SPACE = 25;
        private static final DecimalFormat FORMAT = new DecimalFormat("#.#'%'");
        private final Rectangle boundsRect;
        private final Map<Slot, Double> probabilities;
        private final List<Slot> widgets;
        private final ScrollingContainer scrolling = new ScrollingContainer() {
            private boolean draggingScrollBar;

            @Override
            public Rectangle getBounds() {
                Rectangle bounds = MultiOutputCategory.ScrollableSlotsWidget.this.getBounds();
                return new Rectangle(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);
            }

            @Override
            public int getMaxScrollHeight() {
                double numPerRow = Math.floor(boundsRect.width / DEFAULT_SPACE);
                return Mth.ceil(widgets.size() / numPerRow) * 24 + 4;
            }

            @Override
            public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy, boolean snapToRows, double rowSize) {
                if (button == 0 && this.draggingScrollBar) {
                    float height = getMaxScrollHeight();
                    Rectangle bounds = getBounds();
                    int actualHeight = bounds.height;
                    if (mouseY >= bounds.y && mouseY <= bounds.getMaxY()) {
                        double maxScroll = Math.max(1, getMaxScroll());
                        double int3 = Mth.clamp((double) (actualHeight * actualHeight) / (double) height, 32.0, (actualHeight - 8));
                        double double6 = Math.max(1.0, maxScroll / (actualHeight - int3));
                        float to = Mth.clamp((float) (scrollAmount() + dy * double6), 0.0F, getMaxScroll());
                        if (snapToRows) {
                            double nearestRow = Math.round(to / rowSize) * rowSize;
                            scrollTo(nearestRow, false);
                        } else {
                            scrollTo(to, false);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean updateDraggingState(double mouseX, double mouseY, int button) {
                if (!hasScrollBar()) {
                    return false;
                } else {
                    //Fix cloth config scrolling
                    int height = getMaxScroll() + boundsRect.height;
                    Rectangle bounds = getBounds();
                    int actualHeight = bounds.height;
                    if (height > actualHeight && mouseY >= bounds.y && mouseY <= bounds.getMaxY()) {
                        double scrollbarPositionMinX = this.getScrollBarX(bounds.getMaxX());
                        if (mouseX >= scrollbarPositionMinX - 1.0 && mouseX <= scrollbarPositionMinX + 8.0) {
                            this.draggingScrollBar = true;
                            return true;
                        }
                    }

                    this.draggingScrollBar = false;
                    return false;
                }
            }
        };

        public ScrollableSlotsWidget(Rectangle bounds, List<MultiOutputDisplay.WeightedItem> outputs) {
            this.boundsRect = Objects.requireNonNull(bounds);
            this.probabilities = new Object2DoubleOpenHashMap<>();
            this.widgets = new ArrayList<>();
            for (MultiOutputDisplay.WeightedItem weightedItem : outputs) {
                Slot slot = Widgets.createSlot(new Point(0, 0)).disableBackground().entries(weightedItem.item()).markOutput();
                widgets.add(slot);
                probabilities.put(slot, weightedItem.probability());
            }
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
            if (containsMouse(mouseX, mouseY)) {
                scrolling.offset(ClothConfigInitializer.getScrollStep() * -delta, true);
                return true;
            }
            return false;
        }

        @Override
        public Rectangle getBounds() {
            return boundsRect;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (scrolling.updateDraggingState(mouseX, mouseY, button))
                return true;
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            if (scrolling.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))
                return true;
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }

        private double calcActualSpace(Rectangle innerBounds, double numPerRow) {
            if (widgets.size() <= numPerRow) {
                return DEFAULT_SPACE;
            }
            return Math.floor(innerBounds.width / numPerRow);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
            scrolling.updatePosition(delta);
            Rectangle innerBounds = scrolling.getScissorBounds();
            try (CloseableScissors ignored = scissor(guiGraphics, innerBounds)) {
                double numPerRow = Math.floor(innerBounds.width / DEFAULT_SPACE);
                double actualSpace = calcActualSpace(innerBounds, numPerRow);
                double xOffset = (actualSpace - 18f) / 2;
                for (int y = 0; y < Math.ceil(widgets.size() / numPerRow); y++) {
                    for (int x = 0; x < numPerRow; x++) {
                        int index = (int) (y * numPerRow + x);
                        if (widgets.size() <= index)
                            break;
                        Slot widget = widgets.get(index);
                        widget.getBounds().setLocation(boundsRect.x + xOffset + x * actualSpace, boundsRect.y + 1 + y * DEFAULT_SPACE - scrolling.scrollAmountInt());
                        widget.render(guiGraphics, mouseX, mouseY, delta);
                        renderProbability(guiGraphics, Minecraft.getInstance().font, probabilities.get(widget), widget.getBounds().x, widget.getBounds().y);
                    }
                }
            }
            try (CloseableScissors ignored = scissor(guiGraphics, scrolling.getBounds())) {
                renderFixedScrollbar(0xff000000, 1, REIRuntime.getInstance().isDarkThemeEnabled() ? 0.8f : 1f);
            }
        }

        /**
         * Renders the stack size and/or damage bar for the given ItemStack.
         */
        private void renderProbability(GuiGraphics guiGraphics, Font fr, double stack, int xPosition, int yPosition) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0, 0.0, 100 + 200.0F);
            String string = FORMAT.format(stack);
            MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            float xOffset = Math.min(11, fr.width(string) / 2f);
            fr.drawInBatch(string, (xPosition + 8 - xOffset), (yPosition + 16 + 1), 16777215, true,
                    guiGraphics.pose().last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            bufferSource.endBatch();
            guiGraphics.pose().popPose();
        }

        /**
         * The z pos in {@link ScrollingContainer#renderScrollBar} is too low
         */
        private void renderFixedScrollbar(int background, float alpha, float scrollBarAlphaOffset) {
            Rectangle bounds = scrolling.getBounds();
            int maxScroll = scrolling.getMaxScroll() == 0 ? 1 : scrolling.getMaxScroll();
            int height = bounds.height * bounds.height / scrolling.getMaxScrollHeight();
            height = Mth.clamp(height, 32, bounds.height);
            height = (int) (height - Math.min(scrolling.scrollAmount() < 0.0 ? (int) (-scrolling.scrollAmount()) : (scrolling.scrollAmount() > (double) maxScroll ? (int) scrolling.scrollAmount() - maxScroll : 0), (double) height * 0.95));
            height = Math.max(10, height);
            int minY = Math.min(Math.max((int) scrolling.scrollAmount() * (bounds.height - height) / maxScroll + bounds.y, bounds.y), bounds.getMaxY() - height);
            int scrollbarPositionMinX = scrolling.getScrollBarX(bounds.getMaxX());
            int scrollbarPositionMaxX = scrollbarPositionMinX + 6;
            boolean hovered = (new Rectangle(scrollbarPositionMinX, minY, scrollbarPositionMaxX - scrollbarPositionMinX, height)).contains(PointHelper.ofMouse());
            float bottomC = (hovered ? 0.67F : 0.5F) * scrollBarAlphaOffset;
            float topC = (hovered ? 0.87F : 0.67F) * scrollBarAlphaOffset;
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            float a = (background >> 24 & 255) / 255.0F;
            float r = (background >> 16 & 255) / 255.0F;
            float g = (background >> 8 & 255) / 255.0F;
            float b = (background & 255) / 255.0F;
            buffer.vertex(scrollbarPositionMinX, bounds.getMaxY(), 10.0).color(r, g, b, a).endVertex();
            buffer.vertex(scrollbarPositionMaxX, bounds.getMaxY(), 10.0).color(r, g, b, a).endVertex();
            buffer.vertex(scrollbarPositionMaxX, bounds.y, 10.0).color(r, g, b, a).endVertex();
            buffer.vertex(scrollbarPositionMinX, bounds.y, 10.0).color(r, g, b, a).endVertex();
            buffer.vertex(scrollbarPositionMinX, (minY + height), 10.0).color(bottomC, bottomC, bottomC, alpha).endVertex();
            buffer.vertex(scrollbarPositionMaxX, (minY + height), 10.0).color(bottomC, bottomC, bottomC, alpha).endVertex();
            buffer.vertex(scrollbarPositionMaxX, minY, 10.0).color(bottomC, bottomC, bottomC, alpha).endVertex();
            buffer.vertex(scrollbarPositionMinX, minY, 10.0).color(bottomC, bottomC, bottomC, alpha).endVertex();
            buffer.vertex(scrollbarPositionMinX, (minY + height - 1), 10.0).color(topC, topC, topC, alpha).endVertex();
            buffer.vertex((scrollbarPositionMaxX - 1), (minY + height - 1), 10.0).color(topC, topC, topC, alpha).endVertex();
            buffer.vertex((scrollbarPositionMaxX - 1), minY, 10.0).color(topC, topC, topC, alpha).endVertex();
            buffer.vertex(scrollbarPositionMinX, minY, 10.0).color(topC, topC, topC, alpha).endVertex();
            tesselator.end();
            RenderSystem.disableBlend();
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return widgets;
        }
    }
}
