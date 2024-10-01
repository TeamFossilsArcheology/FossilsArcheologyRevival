package com.fossil.fossil.compat.rei;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.recipe.WorktableRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.resources.ResourceLocation;

public class WorktableDisplay extends WithFuelDisplay {
    public static final CategoryIdentifier<WorktableDisplay> ID = CategoryIdentifier.of(new ResourceLocation(Fossil.MOD_ID, "worktable"));

    public WorktableDisplay(WorktableRecipe recipe) {
        super(recipe);
    }

    protected WorktableDisplay(EntryIngredient input, EntryIngredient fuel, EntryStack<?> output) {
        super(input, fuel, output);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ID;
    }
}
