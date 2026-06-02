package com.github.teamfossilsarcheology.fossil.compat.rei;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

public class SifterCategory extends MultiOutputCategory {

    @Override
    public CategoryIdentifier<? extends SifterDisplay> getCategoryIdentifier() {
        return SifterDisplay.ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("emi.category.fossil.sifter");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.SIFTER.get());
    }
}
