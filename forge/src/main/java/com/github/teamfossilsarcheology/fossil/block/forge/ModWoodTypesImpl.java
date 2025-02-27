package com.github.teamfossilsarcheology.fossil.block.forge;

import com.github.teamfossilsarcheology.fossil.block.ModWoodTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MaterialColor;

/**
 * @see ModWoodTypes
 */
public class ModWoodTypesImpl {
    public static ModWoodTypes.WoodInfo register(ResourceLocation id, MaterialColor materialColor) {
        return new ModWoodTypes.WoodInfo(id.getPath(), materialColor);
    }
}
