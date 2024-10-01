package com.fossil.fossil.compat.rei;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;

public class FossilREICommonPlugin implements REIServerPlugin {

    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        //registry.register(WorktableDisplay.ID, WorktableMenu.class, SimpleMenuInfoProvider.of(WorktableMenuInfo::new));
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(WorktableDisplay.ID, new WithFuelDisplay.Serializer<>(WorktableDisplay::new));
        registry.register(CultureVatDisplay.ID, new WithFuelDisplay.Serializer<>(CultureVatDisplay::new));
    }
}
