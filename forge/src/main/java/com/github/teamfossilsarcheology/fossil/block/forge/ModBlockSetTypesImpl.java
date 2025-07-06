package com.github.teamfossilsarcheology.fossil.block.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;

/**
 * @see com.github.teamfossilsarcheology.fossil.block.ModBlockSetTypes
 */
public class ModBlockSetTypesImpl {

    public static BlockSetType register(ResourceLocation id) {
        return BlockSetType.register(new BlockSetType(id.toString()));
    }
}
