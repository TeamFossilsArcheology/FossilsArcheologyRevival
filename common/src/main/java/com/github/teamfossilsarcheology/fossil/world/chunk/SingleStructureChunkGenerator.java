package com.github.teamfossilsarcheology.fossil.world.chunk;

import net.minecraft.SharedConstants;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class SingleStructureChunkGenerator extends ChunkGenerator {
    private final ResourceKey<Structure> key;

    public SingleStructureChunkGenerator(Holder.Reference<Biome> biomeReference, ResourceKey<Structure> key) {
        super(new FixedBiomeSource(biomeReference));
        this.key = key;
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {

    }

    @Override
    public void createStructures(RegistryAccess registryAccess, ChunkGeneratorStructureState structureState, StructureManager structureManager, ChunkAccess chunk, StructureTemplateManager structureTemplateManager) {
        if (chunk.getPos().x == 0 && chunk.getPos().z == 0) {
            HolderLookup<Structure> structures = registryAccess.lookupOrThrow(Registries.STRUCTURE);
            Structure structure = structures.getOrThrow(key).value();
            SectionPos sectionPos = SectionPos.bottomOf(chunk);

            StructureStart structureStart = structureManager.getStartForStructure(sectionPos, structure, chunk);
            int i = structureStart != null ? structureStart.getReferences() : 0;
            structureStart = structure.generate(registryAccess, this, this.biomeSource, structureState.randomState(),
                    structureTemplateManager, structureState.getLevelSeed(), chunk.getPos(), i, chunk, structure.biomes()::contains);
            if (structureStart.isValid()) {
                structureManager.setStartForStructure(sectionPos, structure, structureStart, chunk);
            }
        }
    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
        ChunkPos chunkPos = chunk.getPos();
        if (!SharedConstants.debugVoidTerrain(chunkPos)) {
            WorldgenRandom worldgenRandom = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.generateUniqueSeed()));
            Structure structure = level.registryAccess().lookupOrThrow(Registries.STRUCTURE).getOrThrow(key).value();
            level.setCurrentlyGenerating(key::toString);
            structureManager.startsForStructure(SectionPos.of(chunkPos, level.getMinSection()), structure).forEach(structureStart -> structureStart.placeInChunk(level, structureManager, this, worldgenRandom, getWritableArea(chunk), chunkPos));
            level.setCurrentlyGenerating(null);
        }
    }

    private static BoundingBox getWritableArea(ChunkAccess chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.getMinBlockX();
        int j = chunkPos.getMinBlockZ();
        LevelHeightAccessor levelHeightAccessor = chunk.getHeightAccessorForGeneration();
        int k = levelHeightAccessor.getMinBuildHeight() + 1;
        int l = levelHeightAccessor.getMaxBuildHeight() - 1;
        return new BoundingBox(i, k, j, i + 15, l, j + 15);
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {

    }

    @Override
    public int getGenDepth() {
        return 0;//Only used in generation but we have no generation
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState random, StructureManager structureManager, ChunkAccess chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return 0;//Only used in generation but we have no generation
    }

    @Override
    public int getMinY() {
        return 0;//Only used in generation, but we could later make it match dimension type minY
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level, RandomState random) {
        return 0;
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor height, RandomState random) {
        return new NoiseColumn(height.getMinBuildHeight(), new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {

    }
}
