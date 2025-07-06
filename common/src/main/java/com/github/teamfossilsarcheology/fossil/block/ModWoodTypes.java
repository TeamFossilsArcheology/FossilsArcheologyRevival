package com.github.teamfossilsarcheology.fossil.block;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MaterialColor;
import org.apache.commons.lang3.NotImplementedException;

public class ModWoodTypes {
    public static final WoodInfo CALAMITES = register(FossilMod.location("calamites"), ModBlockSetTypes.CALAMITES, MaterialColor.COLOR_GREEN);
    public static final WoodInfo CORDAITES = register(FossilMod.location("cordaites"), ModBlockSetTypes.CORDAITES, MaterialColor.PODZOL);
    public static final WoodInfo MUTANT_TREE = register(FossilMod.location("mutant_tree"), ModBlockSetTypes.MUTANT_TREE, MaterialColor.CRIMSON_NYLIUM);
    public static final WoodInfo PALM = register(FossilMod.location("palm"), ModBlockSetTypes.PALM, MaterialColor.SAND);
    public static final WoodInfo SIGILLARIA = register(FossilMod.location("sigillaria"), ModBlockSetTypes.SIGILLARIA, MaterialColor.GRASS);
    public static final WoodInfo TEMPSKYA = register(FossilMod.location("tempskya"), ModBlockSetTypes.TEMPSKYA, MaterialColor.TERRACOTTA_BROWN);

    public static void register() {
    }

    @ExpectPlatform
    private static WoodInfo register(ResourceLocation id, BlockSetType setType, MaterialColor materialColor) {
        throw new NotImplementedException();
    }

    public record WoodInfo(String name, WoodType woodType, BlockSetType setType, MaterialColor materialColor) {

    }
}
