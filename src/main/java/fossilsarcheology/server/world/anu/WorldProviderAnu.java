package fossilsarcheology.server.world.anu;


import fossilsarcheology.server.world.FAWorldRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAnu extends WorldProvider {

    @Override
    public void init() {
        this.biomeProvider = new BiomeProviderSingle(FAWorldRegistry.ANU_BIOME);
        this.doesWaterVaporize = true;
        this.nether = true;

    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderAnu(this.world, this.world.getSeed());
    }


    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.0F;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(0.2, 0.03, 0.03);
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    protected void generateLightBrightnessTable() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float) i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 8.0F;
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return this.world.getBlockState(this.world.getHeight(new BlockPos(x, 0, z))).getMaterial().blocksMovement();
    }

    @Override
    public int getAverageGroundLevel() {
        return 50;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    @Override
    public int getRespawnDimension(EntityPlayerMP player) {
        return 0;
    }

    @Override
    public DimensionType getDimensionType() {
        return FAWorldRegistry.ANU_LAIR;
    }

    public BlockPos getSpawnCoordinate() {
        return new BlockPos(-74, 63, -115);
    }

    public BlockPos getSpawnPoint() {
        return new BlockPos(-74, 63, -115);
    }

    public BlockPos getRandomizedSpawnPoint() {
        return getSpawnPoint();
    }
}
