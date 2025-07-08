package com.github.teamfossilsarcheology.fossil.block.fabric;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;

/**
 * @see com.github.teamfossilsarcheology.fossil.block.ModBlockSetTypes
 */
public class ModBlockSetTypesImpl {
    public static BlockSetType register(ResourceLocation id) {
        return new BlockSetTypeBuilder().build(id);
    }
}
