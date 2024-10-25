package com.github.teamfossilsarcheology.fossil.compat.rei;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.recipe.CultureVatRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.resources.ResourceLocation;

public class CultureVatDisplay extends WithFuelDisplay {
    public static final CategoryIdentifier<CultureVatDisplay> ID = CategoryIdentifier.of(FossilMod.location("culture_vat"));

    public CultureVatDisplay(CultureVatRecipe recipe) {
        super(recipe);
    }

    protected CultureVatDisplay(EntryIngredient input, EntryIngredient fuel, EntryStack<?> output, ResourceLocation location) {
        super(input, fuel, output, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ID;
    }
}
