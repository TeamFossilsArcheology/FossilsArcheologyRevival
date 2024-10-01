package com.fossil.fossil.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import java.util.List;

public class AnalyzerDisplay implements Display {
    //TODO: Analyzer, Sifter, DNA sources
    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of();
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return null;
    }
}
