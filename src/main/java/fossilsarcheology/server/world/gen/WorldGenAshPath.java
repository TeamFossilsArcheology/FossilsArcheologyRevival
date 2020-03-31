package fossilsarcheology.server.world.gen;

import fossilsarcheology.server.block.FABlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenAshPath extends WorldGenerator {
    private final Block block = FABlockRegistry.VOLCANIC_ASH;
    private final int basePathWidth;
    private boolean magma;

    public WorldGenAshPath(int basePathWidthIn, boolean magma) {
        this.basePathWidth = basePathWidthIn;
        this.magma = magma;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        while (worldIn.isAirBlock(position) && position.getY() > 2) {
            position = position.down();
        }

        if (worldIn.getBlockState(position).getBlock() != FABlockRegistry.VOLCANIC_ROCK) {
            return false;
        } else {
            int i = rand.nextInt(this.basePathWidth) + 2;
            int j = 1;

            for (int k = position.getX() - i - (int) (rand.nextFloat() * i); k <= position.getX() + i + (int) (rand.nextFloat() * i); ++k) {
                for (int l = position.getZ() - i - (int) (rand.nextFloat() * i); l <= position.getZ() + i + (int) (rand.nextFloat() * i); ++l) {
                    int i1 = k - position.getX();
                    int j1 = l - position.getZ();

                    if (i1 * i1 + j1 * j1 <= i * i) {
                        for (int k1 = position.getY() - 1; k1 <= position.getY() + 1; ++k1) {
                            BlockPos blockpos = new BlockPos(k, k1, l);
                            Block block = worldIn.getBlockState(blockpos).getBlock();
                            if (block == Blocks.DIRT || block == FABlockRegistry.VOLCANIC_ROCK || block == Blocks.OBSIDIAN) {
                                float chance = rand.nextFloat();
                                if (magma) {
                                    worldIn.setBlockState(blockpos, Blocks.MAGMA.getDefaultState(), 2);
                                } else {
                                    if (chance < 0.8F) {
                                        worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
                                    } else if (chance < 0.85F) {
                                        worldIn.setBlockState(blockpos, FABlockRegistry.FOSSIL.getDefaultState(), 2);
                                    } else {
                                        worldIn.setBlockState(blockpos, Blocks.STONE.getDefaultState(), 2);
                                    }
                                }

                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}