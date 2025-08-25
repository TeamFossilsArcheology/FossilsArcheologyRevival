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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import org.jetbrains.annotations.NotNull;

public class AnuLairChunkGenerator extends SingleStructureChunkGenerator {
    public static final Codec<AnuLairChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(RegistryOps.retrieveElement(ModBiomes.ANU_LAIR_KEY)).apply(instance, instance.stable(AnuLairChunkGenerator::new))
    );

    public AnuLairChunkGenerator(Holder.Reference<Biome> biomeReference) {
        super(biomeReference, ModStructures.ANU_CASTLE_KEY);
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState random, ChunkAccess chunk) {
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
}
