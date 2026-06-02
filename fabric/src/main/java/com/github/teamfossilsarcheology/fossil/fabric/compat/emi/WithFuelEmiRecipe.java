package com.github.teamfossilsarcheology.fossil.fabric.compat.emi;

import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class WithFuelEmiRecipe<T extends WithFuelRecipe> implements EmiRecipe {
    private final ResourceLocation id;
    protected final EmiIngredient input;
    protected final EmiIngredient fuel;
    protected final EmiStack output;
    protected final T recipe;

    protected WithFuelEmiRecipe(T recipe) {
        this.id = recipe.getId();
        this.input = EmiIngredient.of(recipe.getInput());
        this.fuel = EmiIngredient.of(recipe.getFuel());
        this.output = EmiStack.of(recipe.getResultItem());
        this.recipe = recipe;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 96;
    }

    @Override
    public int getDisplayHeight() {
        return 55;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(input, 0, 0).output(true);
        widgets.addSlot(fuel, 39, 37);
        widgets.addSlot(output, 70, 0).output(true).recipeContext(this);
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }
}
