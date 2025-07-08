package com.github.teamfossilsarcheology.fossil.block.forge;

import com.github.teamfossilsarcheology.fossil.block.ModWoodTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

/**
 * @see com.github.teamfossilsarcheology.fossil.block.ModWoodTypes
 */
public class ModWoodTypesImpl {
    public static ModWoodTypes.WoodInfo register(ResourceLocation id, BlockSetType setType, MapColor mapColor) {
        return new ModWoodTypes.WoodInfo(id.getPath(), WoodType.register(new WoodType(id.toString(), setType)), setType, mapColor);
    }
}
