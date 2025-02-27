package com.github.teamfossilsarcheology.fossil.world.chunk;

import com.github.teamfossilsarcheology.fossil.world.biome.ModBiomes;
import com.github.teamfossilsarcheology.fossil.world.feature.structures.ModStructures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
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
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TreasureChunkGenerator extends ChunkGenerator {
    public static final Codec<TreasureChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(RegistryOps.retrieveElement(ModBiomes.TREASURE_ROOM_KEY)).apply(instance, instance.stable(TreasureChunkGenerator::new))
    );

    public TreasureChunkGenerator(Holder.Reference<Biome> biomeReference) {
        super(new FixedBiomeSource(biomeReference));
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {

    }

    @Override
    public void createStructures(RegistryAccess registryAccess, ChunkGeneratorStructureState structureState, StructureManager structureManager, ChunkAccess chunk, StructureTemplateManager structureTemplateManager) {
        super.createStructures(registryAccess, structureState, structureManager, chunk, structureTemplateManager);
        if (chunk.getPos().x == 0 && chunk.getPos().z == 0) {
            HolderLookup<Structure> structures = registryAccess.lookupOrThrow(Registries.STRUCTURE);
            Structure structure = structures.getOrThrow(ModStructures.TREASURE_ROOM_KEY).value();
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
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState random, ChunkAccess chunk) {
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
    public @NotNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor height, RandomState random) {
        return new NoiseColumn(height.getMinBuildHeight(), new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {

    }
}
