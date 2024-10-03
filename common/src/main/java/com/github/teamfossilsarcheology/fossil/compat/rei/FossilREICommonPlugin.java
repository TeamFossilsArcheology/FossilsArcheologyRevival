package com.github.teamfossilsarcheology.fossil.compat.rei;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;

public class FossilREICommonPlugin implements REIServerPlugin {

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(WorktableDisplay.ID, new WithFuelDisplay.Serializer<>(WorktableDisplay::new));
        registry.register(CultureVatDisplay.ID, new WithFuelDisplay.Serializer<>(CultureVatDisplay::new));
        registry.register(AnalyzerDisplay.ID, new MultiOutputDisplay.Serializer<>(AnalyzerDisplay::new));
        registry.register(SifterDisplay.ID, new MultiOutputDisplay.Serializer<>(SifterDisplay::new));
    }
}
