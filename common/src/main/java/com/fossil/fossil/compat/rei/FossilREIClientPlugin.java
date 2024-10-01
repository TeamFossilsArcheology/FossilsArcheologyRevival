package com.fossil.fossil.compat.rei;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.client.gui.CultureVatScreen;
import com.fossil.fossil.client.gui.WorktableScreen;
import com.fossil.fossil.item.ModTabs;
import com.fossil.fossil.recipe.CultureVatRecipe;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.recipe.WorktableRecipe;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;

import java.util.ArrayList;

public class FossilREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new WorktableCategory());
        registry.addWorkstations(WorktableDisplay.ID, EntryStacks.of(ModBlocks.WORKTABLE.get()));
        registry.add(new CultureVatCategory());
        registry.addWorkstations(CultureVatDisplay.ID, EntryStacks.of(ModBlocks.CULTURE_VAT.get()));
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(97, 16, 14, 30), WorktableScreen.class, WorktableDisplay.ID);
        registry.registerContainerClickArea(new Rectangle(97, 16, 14, 30), CultureVatScreen.class, CultureVatDisplay.ID);
    }

    @Override
    public void registerExclusionZones(ExclusionZones zones) {
        zones.register(CreativeModeInventoryScreen.class, screen -> {
            if (screen.getSelectedTab() == ModTabs.FAITEMTAB.getId()) {

            }
            return new ArrayList<>();
        });
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(WorktableRecipe.class, ModRecipes.WORKTABLE_TYPE.get(), WorktableDisplay::new);
        registry.registerRecipeFiller(CultureVatRecipe.class, ModRecipes.CULTURE_VAT_TYPE.get(), CultureVatDisplay::new);
    }
}
