package com.github.teamfossilsarcheology.fossil.block.fabric;

import com.github.teamfossilsarcheology.fossil.block.ModWoodTypes;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

/**
 * @see ModWoodTypes
 */
public class ModWoodTypesImpl {
    public static ModWoodTypes.WoodInfo register(ResourceLocation id, BlockSetType setType, MapColor mapColor) {
        return new ModWoodTypes.WoodInfo(id.getPath(), new WoodTypeBuilder().register(id, setType), setType, mapColor);
    }
}
