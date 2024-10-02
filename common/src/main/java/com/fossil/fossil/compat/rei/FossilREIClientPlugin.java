package com.fossil.fossil.compat.rei;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.item.ModTabs;
import com.fossil.fossil.recipe.*;
import com.fossil.fossil.tags.ModItemTags;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class FossilREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new WorktableCategory());
        registry.addWorkstations(WorktableDisplay.ID, EntryStacks.of(ModBlocks.WORKTABLE.get()));
        registry.add(new CultureVatCategory());
        registry.addWorkstations(CultureVatDisplay.ID, EntryStacks.of(ModBlocks.CULTURE_VAT.get()));
        registry.add(new AnalyzerCategory());
        registry.addWorkstations(AnalyzerDisplay.ID, EntryStacks.of(ModBlocks.ANALYZER.get()));
        registry.add(new SifterCategory());
        registry.addWorkstations(SifterDisplay.ID, EntryStacks.of(ModBlocks.SIFTER.get()));
    }

    @Override
    public void registerExclusionZones(ExclusionZones zones) {
        zones.register(CreativeModeInventoryScreen.class, screen -> {
            if (screen.getSelectedTab() == ModTabs.FAITEMTAB.getId()) {
                int leftPos = (screen.width - 195) / 2;
                int topPos = (screen.height - 136) / 2;
                return List.of(new Rectangle(leftPos - 30, topPos, 30, 120), new Rectangle(leftPos + 200, topPos, 15, 30));
            }
            return new ArrayList<>();
        });
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(WorktableRecipe.class, ModRecipes.WORKTABLE_TYPE.get(), WorktableDisplay::new);
        registry.registerRecipeFiller(CultureVatRecipe.class, ModRecipes.CULTURE_VAT_TYPE.get(), CultureVatDisplay::new);
        registry.registerRecipeFiller(AnalyzerRecipe.class, ModRecipes.ANALYZER_TYPE.get(), AnalyzerDisplay::new);
        registry.registerRecipeFiller(SifterRecipe.class, ModRecipes.SIFTER_TYPE.get(), SifterDisplay::new);
        registry.registerFiller(AnalyzerTagRecipe.class, AnalyzerDisplay::new);

        registry.add(new AnalyzerTagRecipe.Builder(ModItemTags.ALL_BONES)
                .addOutput(Items.BONE_MEAL, 30)
                .addOutput(Items.BONE, 35)
                .addOutput(ModItemTags.BONES_DNA, 35).build());
        registry.add(new AnalyzerTagRecipe.Builder(ModItemTags.UNCOOKED_MEAT)
                .addOutput(ModItemTags.MEAT_DNA, 100).build());
        registry.add(new AnalyzerTagRecipe.Builder(ModItemTags.DINO_EGGS)
                .addOutput(ModItemTags.DINO_DNA, 100).build());
        registry.add(new AnalyzerTagRecipe.Builder(ModItemTags.FISH_EGGS)
                .addOutput(ModItemTags.FISH_DNA, 100).build());
        registry.add(new AnalyzerTagRecipe.Builder(ModItemTags.EMBRYOS)
                .addOutput(ModItemTags.EMBRYO_DNA, 100).build());
    }
}
