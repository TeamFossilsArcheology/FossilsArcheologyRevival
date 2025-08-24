package com.github.teamfossilsarcheology.fossil.world.chunk;

import com.github.teamfossilsarcheology.fossil.world.biome.ModBiomes;
import com.github.teamfossilsarcheology.fossil.world.feature.structures.ModStructures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import org.jetbrains.annotations.NotNull;

public class TreasureChunkGenerator extends SingleStructureChunkGenerator {
    public static final Codec<TreasureChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(RegistryOps.retrieveElement(ModBiomes.TREASURE_ROOM_KEY)).apply(instance, instance.stable(TreasureChunkGenerator::new))
    );

    public TreasureChunkGenerator(Holder.Reference<Biome> biomeReference) {
        super(biomeReference, ModStructures.TREASURE_ROOM_KEY);
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
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
}
