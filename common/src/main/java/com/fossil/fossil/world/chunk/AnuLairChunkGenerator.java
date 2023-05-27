package com.fossil.fossil.world.chunk;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.world.biome.ModBiomes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
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
                            .forGetter((source) -> source.biomes))
                    .apply(instance, instance.stable(AnuLairChunkGenerator::new)));

    private final Registry<Biome> biomes;

    public AnuLairChunkGenerator(Registry<StructureSet> registry, Registry<Biome> registry2) {
        super(registry, Optional.empty(), new FixedBiomeSource(registry2.getOrCreateHolder(ModBiomes.ANU_LAIR_KEY)));
        biomes = registry2;
    }

    private void generatePositions() {
        //Bit of a hack but this way we can ensure a fixed position for the castle without having to place it ourselves
        StructureSet structureSet = structureSets.get(new ResourceLocation(Fossil.MOD_ID, "anu_castle"));
        if (structureSet != null) {
            for (StructureSet.StructureSelectionEntry structure : structureSet.structures()) {
                placementsForFeature.computeIfAbsent(structure.structure().value(), configuredStructureFeature -> new ArrayList<>()).add(structureSet.placement());
            }
            if (structureSet.placement() instanceof ConcentricRingsStructurePlacement placement) {
                ringPositions.put(placement, CompletableFuture.completedFuture(List.of(new ChunkPos(0, 0))));
            }
        }
    }

    @Override
    public void ensureStructuresGenerated() {
        if (!hasGeneratedPositions) {
            generatePositions();
            hasGeneratedPositions = true;
        }
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public @NotNull ChunkGenerator withSeed(long seed) {
        return this;
    }

    @Override
    public Climate.@NotNull Sampler climateSampler() {
        return Climate.empty();
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, BiomeManager biomeManager, StructureFeatureManager structureFeatureManager, ChunkAccess chunk, GenerationStep.Carving step) {

    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureFeatureManager structureFeatureManager, ChunkAccess chunk) {
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
        return 256;//TODO: Height
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager structureFeatureManager, ChunkAccess chunk) {

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
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level) {
        return 0;//TODO: Height
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level) {
        return new NoiseColumn(level.getMinBuildHeight(), new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, BlockPos pos) {

    }
}
