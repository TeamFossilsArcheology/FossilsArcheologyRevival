package com.github.teamfossilsarcheology.fossil.fabric.compat.emi;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.recipe.WorktableRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static com.github.teamfossilsarcheology.fossil.client.gui.WorktableScreen.*;

public class WorktableEmiRecipe extends WithFuelEmiRecipe<WorktableRecipe> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/workbench.png");
    private static final EmiTexture EMPTY_FUEL = new EmiTexture(TEXTURE, 81, 36, FUEL_WIDTH, FUEL_HEIGHT + 1);
    private static final EmiTexture FULL_FUEL = new EmiTexture(TEXTURE, 176, 0, FUEL_WIDTH, FUEL_HEIGHT);
    private static final EmiTexture EMPTY_PROGRESS = new EmiTexture(TEXTURE, 75, 19, PROGRESS_WIDTH + 2, PROGRESS_HEIGHT + 2);
    private static final EmiTexture FULL_PROGRESS = new EmiTexture(TEXTURE, 176, FUEL_HEIGHT, PROGRESS_WIDTH, PROGRESS_HEIGHT);

    protected WorktableEmiRecipe(WorktableRecipe recipe) {
        super(recipe);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return FossilEmiPlugin.WORKTABLE_CATEGORY;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        super.addWidgets(widgets);
        widgets.addTexture(EMPTY_FUEL, 41, 20);
        widgets.addAnimatedTexture(FULL_FUEL, 41, 20, recipe.getFuelDuration() * 20, false, true, true).tooltip((mx, my) -> {
            return List.of(ClientTooltipComponent.create(new TranslatableComponent("emi.cooking.time", recipe.getFuelDuration() / 20f).getVisualOrderText()));
        });

        widgets.addTexture(EMPTY_PROGRESS, 35, 3);
        widgets.addAnimatedTexture(FULL_PROGRESS, 36, 4, recipe.getDuration() * 20, true, false, false).tooltip((mx, my) -> {
            return List.of(ClientTooltipComponent.create(new TranslatableComponent("emi.cooking.time", recipe.getDuration() / 20f).getVisualOrderText()));
        });
    }
}
