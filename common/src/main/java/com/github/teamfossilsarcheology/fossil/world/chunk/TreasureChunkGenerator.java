package com.github.teamfossilsarcheology.fossil.world.chunk;

import com.github.teamfossilsarcheology.fossil.world.biome.ModBiomes;
import com.github.teamfossilsarcheology.fossil.world.feature.structures.ModStructures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TreasureChunkGenerator extends ChunkGenerator {
    public static final Codec<TreasureChunkGenerator> CODEC =
            RecordCodecBuilder.create(instance -> TreasureChunkGenerator.commonCodec(instance)
                    .and(RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY)
                            .forGetter(source -> source.biomes))
                    .apply(instance, instance.stable(TreasureChunkGenerator::new)));

    private final Registry<Biome> biomes;

    public TreasureChunkGenerator(Registry<StructureSet> registry, Registry<Biome> registry2) {
        super(registry, Optional.empty(), new FixedBiomeSource(registry2.getOrCreateHolderOrThrow(ModBiomes.TREASURE_ROOM_KEY)));
        biomes = registry2;
    }

    private void generatePositions() {
        StructureSet structureSet = structureSets.get(ModStructures.TREASURE_ROOM_KEY.location());
        if (structureSet != null) {
            for (StructureSet.StructureSelectionEntry structure : structureSet.structures()) {
                placementsForStructure.computeIfAbsent(structure.structure().value(), configuredStructureFeature -> new ArrayList<>()).add(structureSet.placement());
            }
            if (structureSet.placement() instanceof ConcentricRingsStructurePlacement placement) {
                ringPositions.put(placement, CompletableFuture.completedFuture(List.of(new ChunkPos(0, 0))));
            }
        }
    }

    @Override
    public void ensureStructuresGenerated(RandomState random) {
        if (!hasGeneratedPositions) {
            generatePositions();
            hasGeneratedPositions = true;
        }
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {

    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {

    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureFeatureManager, RandomState random, ChunkAccess chunk) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(mutable.set(x, getMinY(), z), Blocks.BEDROCK.defaultBlockState(), false);
            }
        }
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {

    }

    @Override
    public int getGenDepth() {
        return 256;
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState random, StructureManager structureManager, ChunkAccess chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return -63;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level, RandomState random) {
        return 0;
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level, RandomState random) {
        return new NoiseColumn(level.getMinBuildHeight(), new BlockState[0]);
    }
}
