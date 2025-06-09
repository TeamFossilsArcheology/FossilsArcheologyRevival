package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.world.feature.placement.LazyRandomSpreadPlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ModStructureSets {
    public static final ResourceKey<StructureSet> HELL_BOAT_KEY = createKey("hell_boat");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, FossilMod.location(name));
    }

    private static final Holder<StructureSet> HELL_BOAT = StructureSets.register(
            HELL_BOAT_KEY, ModStructures.HELL_BOAT, new LazyRandomSpreadPlacement(FossilConfig.HELL_SHIP_SPACING, FossilConfig.HELL_SHIP_SEPARATION,
                    RandomSpreadType.LINEAR, 92182587)
    );

    public static void register() {

    }

}
