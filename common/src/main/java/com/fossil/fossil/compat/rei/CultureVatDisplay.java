package com.fossil.fossil.compat.rei;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.recipe.CultureVatRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.resources.ResourceLocation;

public class CultureVatDisplay extends WithFuelDisplay {
    public static final CategoryIdentifier<CultureVatDisplay> ID = CategoryIdentifier.of(new ResourceLocation(Fossil.MOD_ID, "culture_vat"));

    public CultureVatDisplay(CultureVatRecipe recipe) {
        super(recipe);
    }

    protected CultureVatDisplay(EntryIngredient input, EntryIngredient fuel, EntryStack<?> output) {
        super(input, fuel, output);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ID;
    }
}
