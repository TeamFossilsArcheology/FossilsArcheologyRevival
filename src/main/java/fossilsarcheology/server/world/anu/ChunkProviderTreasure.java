package fossilsarcheology.server.world.anu;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkProviderTreasure implements IChunkGenerator {
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    protected final Random rand;
    protected final World world;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    protected Biome[] biomesForGeneration;
    protected int chunkX = 0;
    protected int chunkZ = 0;
    double[] pnr;
    double[] ar;
    double[] br;
    private NoiseGeneratorOctaves lperlinNoise1;
    private NoiseGeneratorOctaves lperlinNoise2;
    private NoiseGeneratorOctaves perlinNoise1;
    private NoiseGeneratorSimplex islandNoise;
    private double[] buffer;

    public ChunkProviderTreasure(World worldObjIn, long seed) {
        this.world = worldObjIn;
        this.rand = new Random(seed);
        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.islandNoise = new NoiseGeneratorSimplex(this.rand);
        net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd ctx =
                new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd(lperlinNoise1, lperlinNoise2, perlinNoise1, noiseGen5, noiseGen6, islandNoise);
        ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(worldObjIn, this.rand, ctx);
        this.lperlinNoise1 = ctx.getLPerlin1();
        this.lperlinNoise2 = ctx.getLPerlin2();
        this.perlinNoise1 = ctx.getPerlin();
        this.noiseGen5 = ctx.getDepth();
        this.noiseGen6 = ctx.getScale();
        this.islandNoise = ctx.getIsland();
    }

    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        this.buffer = this.getHeights(this.buffer, x * 2, 0, z * 2, 3, 33, 3);
    }

    public void buildSurfaces(ChunkPrimer primer) {

    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.chunkX = x;
        this.chunkZ = z;
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.setBlocksInChunk(x, z, chunkprimer);
        this.buildSurfaces(chunkprimer);
        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] getHeights(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_, int p_185963_5_, int p_185963_6_, int p_185963_7_) {
        net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField event = new net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField(this, p_185963_1_, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) {
            return event.getNoisefield();
        }

        if (p_185963_1_ == null) {
            p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        d0 = d0 * 2.0D;
        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0 / 80.0D, 4.277575000000001D, d0 / 80.0D);
        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
        int i = p_185963_2_ / 2;
        int j = p_185963_4_ / 2;
        int k = 0;

        for (int l = 0; l < p_185963_5_; ++l) {
            for (int i1 = 0; i1 < p_185963_7_; ++i1) {
                float f = 15;

                for (int j1 = 0; j1 < p_185963_6_; ++j1) {
                    double d2 = this.ar[k] / 512.0D;
                    double d3 = this.br[k] / 512.0D;
                    double d5 = (this.pnr[k] / 10.0D + 1.0D) / 2.0D;
                    double d4;

                    if (d5 < 0.0D) {
                        d4 = d2;
                    } else if (d5 > 1.0D) {
                        d4 = d3;
                    } else {
                        d4 = d2 + (d3 - d2) * d5;
                    }

                    d4 = d4 - 8.0D;
                    d4 = d4 + (double) f;
                    int k1 = 2;

                    if (j1 > p_185963_6_ / 2 - k1) {
                        double d6 = (float) (j1 - (p_185963_6_ / 2 - k1)) / 64.0F;
                        d6 = MathHelper.clamp(d6, 0.0D, 1.0D);
                        d4 = d4 * (1.0D - d6) + -3000.0D * d6;
                    }

                    k1 = 8;

                    if (j1 < k1) {
                        double d7 = (float) (k1 - j1) / ((float) k1 - 1.0F);
                        d4 = d4 * (1.0D - d7) + -30.0D * d7;
                    }

                    p_185963_1_[k] = d4;
                    ++k;
                }
            }
        }

        return p_185963_1_;
    }

    @Override
    public void populate(int x, int z) {
        BlockFalling.fallInstantly = true;
        ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
        BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
        this.world.getBiome(blockpos.add(16, 0, 16)).decorate(this.world, this.world.rand, blockpos);
        ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
