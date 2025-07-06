package com.github.teamfossilsarcheology.fossil.block;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.apache.commons.lang3.NotImplementedException;

public class ModBlockSetTypes {
    public static final BlockSetType CALAMITES = register(FossilMod.location("calamites"));
    public static final BlockSetType CORDAITES = register(FossilMod.location("cordaites"));
    public static final BlockSetType MUTANT_TREE = register(FossilMod.location("mutant_tree"));
    public static final BlockSetType PALM = register(FossilMod.location("palm"));
    public static final BlockSetType SIGILLARIA = register(FossilMod.location("sigillaria"));
    public static final BlockSetType TEMPSKYA = register(FossilMod.location("tempskya"));

    public static void register() {

    }

    @ExpectPlatform
    public static BlockSetType register(ResourceLocation id) {
        throw new NotImplementedException();
    }
}
