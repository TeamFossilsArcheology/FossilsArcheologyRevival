package com.github.teamfossilsarcheology.fossil.compat.rei;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.recipe.MultiOutputAndSlotsRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.NavigableMap;

public class SifterDisplay extends MultiOutputDisplay {
    public static final CategoryIdentifier<SifterDisplay> ID = CategoryIdentifier.of(FossilMod.location("sifter"));

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
