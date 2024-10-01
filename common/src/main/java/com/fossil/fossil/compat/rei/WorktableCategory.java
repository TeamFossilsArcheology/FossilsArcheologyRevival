package com.fossil.fossil.compat.rei;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;

import static com.fossil.fossil.client.gui.WorktableScreen.*;

public class WorktableCategory implements DisplayCategory<WorktableDisplay> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/workbench.png");
    private static final ResourceLocation DARK_TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/workbench.png");


    @Override
    public CategoryIdentifier<? extends WorktableDisplay> getCategoryIdentifier() {
        return WorktableDisplay.ID;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("category.fossil.rei.repairing");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.WORKTABLE.get());
    }

    @Override
    public List<Widget> setupDisplay(WorktableDisplay display, Rectangle bounds) {
        //TODO: Fuel duration, recipe duration and dark theme
        Point startPoint = new Point(bounds.getCenterX() - 48, bounds.getCenterY() - 28);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createDrawableWidget((helper, matrices, mouseX, mouseY, delta) -> {
            RenderSystem.setShaderTexture(0, TEXTURE);
            helper.blit(matrices, startPoint.x, startPoint.y, 40, 16, 96, 55);
            long time = System.currentTimeMillis();
            int width = FUEL_HEIGHT- Mth.ceil(time / 250d % (double) FUEL_HEIGHT);
            helper.blit(matrices, startPoint.x + 41, startPoint.y + 20 + FUEL_HEIGHT - width, 176, FUEL_HEIGHT - width, FUEL_WIDTH, width);
            width = Mth.ceil(time / 250d % (double) PROGRESS_WIDTH);
            helper.blit(matrices, startPoint.x + 36, startPoint.y + 4, 176, FUEL_HEIGHT, width, PROGRESS_HEIGHT);
        }));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 5, startPoint.y + 5)).entries(display.getInputEntries().get(0)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 40, startPoint.y + 38)).entries(display.getInputEntries().get(1)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 75, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }
}
