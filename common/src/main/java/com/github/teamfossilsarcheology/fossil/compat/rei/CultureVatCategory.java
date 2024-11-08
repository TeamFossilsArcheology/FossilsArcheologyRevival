package com.github.teamfossilsarcheology.fossil.compat.rei;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.config.ConfigObject;
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

import static com.github.teamfossilsarcheology.fossil.client.gui.CultureVatScreen.*;

public class CultureVatCategory implements DisplayCategory<CultureVatDisplay> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/culture_vat.png");
    private static final ResourceLocation DARK_TEXTURE = FossilMod.location("textures/gui/culture_vat_dark.png");


    @Override
    public CategoryIdentifier<? extends CultureVatDisplay> getCategoryIdentifier() {
        return CultureVatDisplay.ID;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("category.fossil.rei.culture_vat");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.CULTURE_VAT.get());
    }

    @Override
    public List<Widget> setupDisplay(CultureVatDisplay display, Rectangle bounds) {
        //TODO: Fuel duration, recipe duration
        Point startPoint = new Point(bounds.getCenterX() - 48, bounds.getCenterY() - 28);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createDrawableWidget((helper, matrices, mouseX, mouseY, delta) -> {
            RenderSystem.setShaderTexture(0, ConfigObject.getInstance().isUsingDarkTheme() ? DARK_TEXTURE : TEXTURE);
            helper.blit(matrices, startPoint.x, startPoint.y, 40, 16, 96, 55);
            long time = System.currentTimeMillis();
            int progress = FUEL_HEIGHT - Mth.floor(time / 250d % (double) FUEL_HEIGHT);
            helper.blit(matrices, startPoint.x + 41, startPoint.y + 20 + FUEL_HEIGHT - progress, 176, FUEL_HEIGHT - progress, FUEL_WIDTH, progress +1);
            progress = Mth.ceil(time / 250d % (double) PROGRESS_WIDTH);
            helper.blit(matrices, startPoint.x + 38, startPoint.y + 6, 176, FUEL_HEIGHT + 2, progress, PROGRESS_HEIGHT);
        }));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 5, startPoint.y + 5)).entries(display.getInputEntries().get(0)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 40, startPoint.y + 38)).entries(display.getInputEntries().get(1)).disableBackground().markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 75, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }
}
