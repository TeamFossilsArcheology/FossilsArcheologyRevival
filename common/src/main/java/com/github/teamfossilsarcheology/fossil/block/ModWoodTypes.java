package com.github.teamfossilsarcheology.fossil.block;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import org.apache.commons.lang3.NotImplementedException;

public class ModWoodTypes {
    public static final WoodInfo CALAMITES = register(FossilMod.location("calamites"), ModBlockSetTypes.CALAMITES, MapColor.COLOR_GREEN);
    public static final WoodInfo CORDAITES = register(FossilMod.location("cordaites"), ModBlockSetTypes.CORDAITES, MapColor.PODZOL);
    public static final WoodInfo MUTANT_TREE = register(FossilMod.location("mutant_tree"), ModBlockSetTypes.MUTANT_TREE, MapColor.CRIMSON_NYLIUM);
    public static final WoodInfo PALM = register(FossilMod.location("palm"), ModBlockSetTypes.PALM, MapColor.SAND);
    public static final WoodInfo SIGILLARIA = register(FossilMod.location("sigillaria"), ModBlockSetTypes.SIGILLARIA, MapColor.GRASS);
    public static final WoodInfo TEMPSKYA = register(FossilMod.location("tempskya"), ModBlockSetTypes.TEMPSKYA, MapColor.TERRACOTTA_BROWN);

    public static void register() {
    }

    @ExpectPlatform
    private static WoodInfo register(ResourceLocation id, BlockSetType setType, MapColor mapColor) {
        throw new NotImplementedException();
    }

    public record WoodInfo(String name, WoodType woodType, BlockSetType setType, MapColor mapColor) {

    }
}
