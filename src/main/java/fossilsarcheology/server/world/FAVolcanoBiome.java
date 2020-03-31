package fossilsarcheology.server.world;

import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.entity.monster.EntitySentryPigman;
import fossilsarcheology.server.world.gen.WorldGenAshPath;
import fossilsarcheology.server.world.gen.WorldGenVolcanoCone;
import fossilsarcheology.server.world.gen.WorldGenVolcanoFossils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class FAVolcanoBiome extends Biome {
    protected static final IBlockState LAVA = Blocks.LAVA.getDefaultState();

    public FAVolcanoBiome() {
        super(new BiomeProperties("Volcano").setBaseHeight(1.5F).setHeightVariation(0.5F).setTemperature(2.0F).setRainfall(0.0F).setWaterColor(0X300000));
        this.topBlock = FABlockRegistry.VOLCANIC_ROCK.getDefaultState();
        this.fillerBlock = FABlockRegistry.VOLCANIC_ROCK.getDefaultState();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 4;

    }

    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, rand, pos));
        DiamondGen gen = new DiamondGen();
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, rand, gen, pos, OreGenEvent.GenerateMinable.EventType.DIAMOND))
            gen.generate(worldIn, rand, pos);

        if (rand.nextInt(50) == 0) {
            (new WorldGenVolcanoCone()).generate(worldIn, rand, worldIn.getHeight(pos).up(3));
        }
        if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), DecorateBiomeEvent.Decorate.EventType.CUSTOM))
            if (rand.nextInt(10) == 0) {
                (new WorldGenVolcanoFossils()).generate(worldIn, rand, worldIn.getHeight(pos).down(5 + rand.nextInt(12)));
            }
        if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FOSSIL))
            for (int l = 0; l < 2; ++l) {
                int i1 = rand.nextInt(16) + 8;
                int j1 = rand.nextInt(16) + 8;
                new WorldGenAshPath(4 + rand.nextInt(6), false).generate(worldIn, rand, worldIn.getHeight(pos.add(i1, worldIn.getHeight(pos.getX(), pos.getZ()), j1)));
            }
        if (rand.nextInt(3) == 0) {
            int i1 = rand.nextInt(16) + 8;
            int j1 = rand.nextInt(16) + 8;
            new WorldGenAshPath(2 + rand.nextInt(3), true).generate(worldIn, rand, worldIn.getHeight(pos.add(i1, worldIn.getHeight(pos.getX(), pos.getZ()), j1)));
        }
        for (int i = 0; i < 5; i++) {
            int i2 = rand.nextInt(16) + 8;
            int k3 = rand.nextInt(16) + 8;
            if (rand.nextInt(8) == 0) {
                (new WorldGenLakes(Blocks.LAVA)).generate(worldIn, rand, pos.add(i2, worldIn.getHeight(pos.getX(), pos.getZ()) + 3, k3));
            }
        }
    }

    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        this.generateBiomeTerrainVolcano(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    public void generateBiomeTerrainVolcano(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        int i = worldIn.getSeaLevel();
        IBlockState iblockstate = this.topBlock;
        IBlockState iblockstate1 = this.fillerBlock;
        int j = -1;
        int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int i1 = x & 15;
        int l = z & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j1 = 255; j1 >= 0; --j1) {
            if (j1 <= rand.nextInt(5)) {
                chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
            } else {
                IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
                if (iblockstate2.getMaterial() == Material.AIR) {
                    j = -1;
                } else if (iblockstate2.getBlock() == Blocks.STONE) {
                    if (j == -1) {
                        if (k <= 0) {
                            iblockstate = AIR;
                            iblockstate1 = STONE;
                        } else if (j1 >= i - 4 && j1 <= i + 1) {
                            iblockstate = this.topBlock;
                            iblockstate1 = this.fillerBlock;
                        }

                        if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                            iblockstate = LAVA;
                        }

                        j = k;

                        if (j1 >= i - 1) {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
                        } else if (j1 < i - 7 - k) {
                            iblockstate = AIR;
                            iblockstate1 = STONE;
                            chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
                        } else {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                        }
                    } else if (j > 0) {
                        --j;
                        chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);

                        if (j == 0 && iblockstate1.getBlock() == FABlockRegistry.VOLCANIC_ROCK && k > 1) {
                            j = rand.nextInt(4) + Math.max(0, j1 - 63);
                            iblockstate1 = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
                        }
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float currentTemperature) {
        return 0X110000;
    }

    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos) {
        return 0X808080;
    }

    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos){
        return 0X808080;
    }


    private static class DiamondGen extends WorldGenerator {
        @Override
        public boolean generate(World worldIn, Random rand, BlockPos pos) {
            int count = 3 + rand.nextInt(6);
            for (int i = 0; i < count; i++) {
                int offset = net.minecraftforge.common.ForgeModContainer.fixVanillaCascading ? 8 : 0; // MC-114332
                BlockPos blockpos = pos.add(rand.nextInt(16) + offset, rand.nextInt(38) + 4, rand.nextInt(16) + offset);

                net.minecraft.block.state.IBlockState state = worldIn.getBlockState(blockpos);
                if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos, net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.STONE))) {
                    worldIn.setBlockState(blockpos, Blocks.DIAMOND_ORE.getDefaultState(), 16 | 2);
                }
            }
            return true;
        }
    }
}
