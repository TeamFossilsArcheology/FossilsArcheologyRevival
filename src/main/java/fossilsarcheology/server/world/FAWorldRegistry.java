package fossilsarcheology.server.world;

import fossilsarcheology.Revival;
import fossilsarcheology.server.world.anu.WorldProviderAnu;
import fossilsarcheology.server.world.anu.WorldProviderTreasure;
import net.minecraft.init.Blocks;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;

public class FAWorldRegistry {

    public static DimensionType ANU_LAIR;
    public static DimensionType TREASURE_ROOM;
    public static Biome ANU_BIOME;
    public static Biome TREASURE_BIOME;
    public static Biome VOLCANO_BIOME;

    public static void register() {
        ANU_BIOME = new FADimensionBiome(true, new Biome.BiomeProperties("anu").setRainDisabled().setTemperature(2), Blocks.NETHERRACK);
        TREASURE_BIOME = new FADimensionBiome(false, new Biome.BiomeProperties("treasure").setRainDisabled().setTemperature(2), Blocks.AIR);
        ANU_LAIR = DimensionType.register("Anu Lair", "_anu", Revival.CONFIG_OPTIONS.dimensionIDDarknessLair, WorldProviderAnu.class, false);
        TREASURE_ROOM = DimensionType.register("Treasure Room", "_treasure", Revival.CONFIG_OPTIONS.dimensionIDTreasure, WorldProviderTreasure.class, false);
        VOLCANO_BIOME = new FAVolcanoBiome();
        DimensionManager.registerDimension(Revival.CONFIG_OPTIONS.dimensionIDDarknessLair, ANU_LAIR);
        DimensionManager.registerDimension(Revival.CONFIG_OPTIONS.dimensionIDTreasure, TREASURE_ROOM);

    }
}
