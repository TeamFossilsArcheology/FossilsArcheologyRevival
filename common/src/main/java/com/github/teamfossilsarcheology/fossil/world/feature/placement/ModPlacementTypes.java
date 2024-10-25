package com.github.teamfossilsarcheology.fossil.world.feature.placement;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

public class ModPlacementTypes {
    private static void register(String name, PlacementModifierType<?> type) {
        Registry.register(Registry.PLACEMENT_MODIFIERS, FossilMod.MOD_ID + ":" + name, type);
    }

    private static void register(String name, StructurePlacementType<?> type) {
        Registry.register(Registry.STRUCTURE_PLACEMENT_TYPE, FossilMod.MOD_ID + ":" + name, type);
    }

    public static void register() {
        register("lazy_rarity_filter", LazyRarityFilter.TYPE);
        register("lazy_random_spread", LazyRandomSpreadPlacement.TYPE);
        register("lazy_count", LazyCountPlacement.TYPE);
    }
}
