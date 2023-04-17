package com.fossil.fossil.fabric.world.biome;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.world.biome.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.ParameterUtils.*;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class FabricFossilRegion extends Region {
    public FabricFossilRegion(String location, RegionType regionType, int weight) {
        super(new ResourceLocation(Fossil.MOD_ID, location), regionType, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            var hopfullyCorrectPoints = new ParameterUtils.ParameterPointListBuilder()
                    .temperature(Temperature.HOT, Temperature.WARM)
                    .humidity(Humidity.ARID, Humidity.DRY)
                    .continentalness(Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND))
                    .erosion(Erosion.EROSION_5, Erosion.EROSION_1, Erosion.EROSION_0)
                    .depth(Depth.SURFACE, Depth.FLOOR)
                    .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING).build();
            builder.replaceBiome(Biomes.BADLANDS, ModBiomes.VOLCANO_KEY);
            builder.replaceBiome(Biomes.ERODED_BADLANDS, ModBiomes.VOLCANO_KEY);
            hopfullyCorrectPoints.forEach(point -> builder.replaceBiome(point, ModBiomes.VOLCANO_KEY));//This does basically nothing :(
        });

    }

    @Override
    public int getWeight() {
        return 500;//TODO: Figure out weights
    }
}
