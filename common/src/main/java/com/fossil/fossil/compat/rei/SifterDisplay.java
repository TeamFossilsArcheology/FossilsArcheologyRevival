package com.fossil.fossil.compat.rei;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.recipe.MultiOutputAndSlotsRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.NavigableMap;

public class SifterDisplay extends MultiOutputDisplay {
    public static final CategoryIdentifier<SifterDisplay> ID = CategoryIdentifier.of(Fossil.location("sifter"));

    public SifterDisplay(MultiOutputAndSlotsRecipe recipe) {
        super(recipe);
    }

    public SifterDisplay(EntryIngredient input, NavigableMap<Double, EntryIngredient> output, @Nullable ResourceLocation location) {
        super(input, output, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ID;
    }
}
