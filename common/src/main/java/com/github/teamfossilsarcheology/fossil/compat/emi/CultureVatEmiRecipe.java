package com.github.teamfossilsarcheology.fossil.compat.emi;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.entity.CultureVatBlockEntity;
import com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu;
import com.github.teamfossilsarcheology.fossil.recipe.CultureVatRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static com.github.teamfossilsarcheology.fossil.client.gui.CultureVatScreen.*;

public class CultureVatEmiRecipe extends WithFuelEmiRecipe<CultureVatRecipe> {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/gui/culture_vat.png");
    private static final EmiTexture EMPTY_FUEL = new EmiTexture(TEXTURE, 81, 36, FUEL_WIDTH, FUEL_HEIGHT + 2);
    private static final EmiTexture FULL_FUEL = new EmiTexture(TEXTURE, 176, 0, FUEL_WIDTH, FUEL_HEIGHT);
    private static final EmiTexture EMPTY_PROGRESS = new EmiTexture(TEXTURE, 78, 22, PROGRESS_WIDTH, PROGRESS_HEIGHT);
    private static final EmiTexture FULL_PROGRESS = new EmiTexture(TEXTURE, 176, FUEL_HEIGHT, PROGRESS_WIDTH, PROGRESS_HEIGHT);

    protected CultureVatEmiRecipe(CultureVatRecipe recipe) {
        super(recipe);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return FossilEmiPlugin.CULTURE_VAT_CATEGORY;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        super.addWidgets(widgets);
        final int duration = CultureVatBlockEntity.getItemFuelTime(fuel.getEmiStacks().get(0).getItemStack());
        widgets.addTexture(EMPTY_FUEL, 41, 20);
        widgets.addAnimatedTexture(FULL_FUEL, 42, 21, duration, false, true, true).tooltip((mx, my) -> {
            return List.of(ClientTooltipComponent.create(Component.translatable("emi.cooking.time", duration / 20f).getVisualOrderText()));
        });

        widgets.addTexture(EMPTY_PROGRESS, 38, 6);
        widgets.addAnimatedTexture(FULL_PROGRESS, 38, 6, CultureVatMenu.CULTIVATION_DURATION, true, false, false).tooltip((mx, my) -> {
            return List.of(ClientTooltipComponent.create(Component.translatable("emi.cooking.time", CultureVatMenu.CULTIVATION_DURATION / 20f).getVisualOrderText()));
        });
    }
}
