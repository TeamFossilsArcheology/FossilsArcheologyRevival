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

public class AnuLairChunkGenerator extends ChunkGenerator {
    public static final Codec<AnuLairChunkGenerator> CODEC =
            RecordCodecBuilder.create(instance -> AnuLairChunkGenerator.commonCodec(instance)
                    .and(RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY)
                            .forGetter(source -> source.biomes))
                    .apply(instance, instance.stable(AnuLairChunkGenerator::new)));

    private final Registry<Biome> biomes;

    public AnuLairChunkGenerator(Registry<StructureSet> registry, Registry<Biome> registry2) {
        super(registry, Optional.empty(), new FixedBiomeSource(registry2.getOrCreateHolderOrThrow(ModBiomes.ANU_LAIR_KEY)));
        biomes = registry2;
    }

    private void generatePositions() {
        //Bit of a hack but this way we can ensure a fixed position for the castle without having to place it ourselves
        StructureSet structureSet = structureSets.get(ModStructures.ANU_CASTLE_KEY.location());
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
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;
        BlockState netherrack = Blocks.NETHERRACK.defaultBlockState();
        int anuCastleMinY = 62;
        int islandMinY = anuCastleMinY - 16;
        if (chunkX > -1 && chunkX < 9 && chunkZ > -1 && chunkZ < 9) {
            for (int y = islandMinY; y < anuCastleMinY; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                    }
                }
            }
        }
        int offset = 0;
        if (chunkX == -1) {
            if (chunkZ >= 0 && chunkZ < 9) {
                for (int y = islandMinY; y < anuCastleMinY; y++) {
                    for (int x = offset; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                        }
                    }
                    offset++;
                }
            } else if (chunkZ == -1) {
                for (int y = islandMinY; y < anuCastleMinY; y++) {
                    for (int x = offset; x < 16; x++) {
                        for (int z = offset; z < 16; z++) {
                            chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                        }
                    }
                    offset++;
                }
            }
        }
        if (chunkZ == -1) {
            if (chunkX >= 0 && chunkX < 9) {
                for (int y = islandMinY; y < anuCastleMinY; y++) {
                    for (int x = 0; x < 16; x++) {
                        for (int z = offset; z < 16; z++) {
                            chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                        }
                    }
                    offset++;
                }
            } else if (chunkX == 9) {
                for (int y = islandMinY; y < anuCastleMinY; y++) {
                    for (int x = 15 - offset; x >= 0; x--) {
                        for (int z = offset; z < 16; z++) {
                            chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                        }
                    }
                    offset++;
                }
            }
        }
        if (chunkX == 9) {
            if (chunkZ >= 0 && chunkZ < 9) {
                for (int y = islandMinY; y < anuCastleMinY; y++) {
                    for (int x = 15 - offset; x >= 0; x--) {
                        for (int z = 0; z < 16; z++) {
                            chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                        }
                    }
                    offset++;
                }
            } else if (chunkZ == 9) {
                for (int y = islandMinY; y < anuCastleMinY; y++) {
                    for (int x = 15 - offset; x >= 0; x--) {
                        for (int z = 15 - offset; z >= 0; z--) {
                            chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                        }
                    }
                    offset++;
                }
            }
        }
        if (chunkZ == 9) {
            if (chunkX >= 0 && chunkX < 9) {
                for (int y = islandMinY; y < anuCastleMinY; y++) {
                    for (int x = 0; x < 16; x++) {
                        for (int z = 15 - offset; z >= 0; z--) {
                            chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                        }
                    }
                    offset++;
                }
            } else if (chunkX == -1) {
                for (int y = islandMinY; y < anuCastleMinY; y++) {
                    for (int x = offset; x < 16; x++) {
                        for (int z = 15 - offset; z >= 0; z--) {
                            chunk.setBlockState(mutable.set(x, y, z), netherrack, false);
                        }
                    }
                    offset++;
                }
            }
        }
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
        return 0;//Only used in generation but we have no generation
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level, RandomState random) {
        return new NoiseColumn(level.getMinBuildHeight(), new BlockState[0]);
    }
}
