package com.fossil.fossil.compat.rei;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.recipe.MultiOutputAndSlotsRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class AnalyzerDisplay extends MultiOutputDisplay {
    public static final CategoryIdentifier<AnalyzerDisplay> ID = CategoryIdentifier.of(new ResourceLocation(Fossil.MOD_ID, "analyzer"));

    public AnalyzerDisplay(MultiOutputAndSlotsRecipe recipe) {
        super(recipe);
    }

    public AnalyzerDisplay(EntryIngredient input, NavigableMap<Double, EntryIngredient> output, @Nullable ResourceLocation location) {
        super(input, output, location);
    }

    public AnalyzerDisplay(AnalyzerTagRecipe analyzerTagRecipe) {
        super(EntryIngredients.ofItemTag(analyzerTagRecipe.input()), map(analyzerTagRecipe.weightedOutputs()), null);
    }

    private static NavigableMap<Double, EntryIngredient> map(NavigableMap<Double, Ingredient> outputs) {
        NavigableMap<Double, EntryIngredient> newMap = new TreeMap<>();
        for (Map.Entry<Double, Ingredient> entry : outputs.entrySet()) {
            newMap.put(entry.getKey(), EntryIngredients.ofItemStacks(List.of(entry.getValue().getItems())));
        }
        return newMap;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ID;
    }
}
