package com.fossil.fossil.compat.rei;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.recipe.WorktableRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.resources.ResourceLocation;

public class WorktableDisplay extends WithFuelDisplay {
    public static final CategoryIdentifier<WorktableDisplay> ID = CategoryIdentifier.of(Fossil.location("worktable"));

    public WorktableDisplay(WorktableRecipe recipe) {
        super(recipe);
    }

    protected WorktableDisplay(EntryIngredient input, EntryIngredient fuel, EntryStack<?> output, ResourceLocation location) {
        super(input, fuel, output, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ID;
    }
}
