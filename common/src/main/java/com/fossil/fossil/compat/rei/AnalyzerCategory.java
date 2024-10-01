package com.fossil.fossil.compat.rei;

import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.network.chat.Component;

public class AnalyzerCategory implements DisplayCategory<AnalyzerDisplay> {
    @Override
    public CategoryIdentifier<? extends AnalyzerDisplay> getCategoryIdentifier() {
        return null;
    }

    @Override
    public Component getTitle() {
        return null;
    }

    @Override
    public Renderer getIcon() {
        return null;
    }
}
