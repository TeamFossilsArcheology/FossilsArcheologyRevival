package com.fossil.fossil.world.feature.structures;

import com.fossil.fossil.Fossil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeConfiguration;

public class ModStructures {
    public static final Tuple<JigsawConfiguration, CastleFeature> ANU_CASTLE = createStructure("anu_castle", new CastleFeature());
    public static final Tuple<JigsawConfiguration, ConfigurableStructureFeature> CONFIGURABLE_STRUCTURE =
            createStructure("configurable_structure", new ConfigurableStructureFeature());
    public static final Tuple<RangeConfiguration, HellBoatFeature> HELL_BOAT = createStructure("hell_boat", new HellBoatFeature());
    public static final Tuple<NoneFeatureConfiguration, TreasureRoomFeature> TREASURE_ROOM = createStructure("treasure_room", new TreasureRoomFeature());

    private static <C extends FeatureConfiguration, F extends StructureFeature<C>> Tuple<C, F> createStructure(String name, F feature) {
        return new Tuple<>(new ResourceLocation(Fossil.MOD_ID, name), feature);
    }

    @ExpectPlatform
    public static void register() {
    }

    public record Tuple<C extends FeatureConfiguration, F extends StructureFeature<C>>(ResourceLocation location,
                                                                                       F feature) {

    }
}
