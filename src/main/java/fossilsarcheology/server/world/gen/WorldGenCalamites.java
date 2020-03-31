package fossilsarcheology.server.world.gen;

import fossilsarcheology.server.block.FABlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenCalamites extends WorldGenerator {
    private static final IBlockState LOG = FABlockRegistry.CALAMITES_LOG.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
    private static final IBlockState LEAF = FABlockRegistry.CALAMITES_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, true).withProperty(BlockLeaves.DECAYABLE, true);
    private boolean keepGenerating = true;

    public static boolean canGenTree(World world, BlockPos pos) {
        for (int y = 0; y <= 30; y++) {
            if (!isReplaceable(world, pos.up(y))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isReplaceable(World world, BlockPos pos) {
        return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos) || world.getBlockState(pos).getBlock() == FABlockRegistry.CALAMITES_SAPLING;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        Block j1 = world.getBlockState(pos.down()).getBlock();
        keepGenerating = true;
        if (j1 != Blocks.GRASS && j1 != Blocks.DIRT || pos.getY() >= 128 - 12 - 1) {
            return false;
        }
        int treeHeight = 15 + rand.nextInt(15);
        BlockPos canopyCenter = pos.up(rand.nextInt(1) + 1);
        int minWidth = 2;
        int maxWidth = 4;
        float widthStep = (float) (maxWidth - minWidth) / treeHeight;
        while (canopyCenter.getY() < pos.up(treeHeight - 1).getY()) {
            int difference = pos.up(treeHeight).getY() - canopyCenter.getY();
            float canopyWidth = minWidth + (widthStep * difference);
            canopyCenter = canopyCenter.up(4);
            genCircle(world, canopyCenter, canopyWidth - 2, false);
            genCircle(world, canopyCenter.up(), canopyWidth - 1, false);
            if (difference > 4) {
                genCircle(world, canopyCenter.up(2), canopyWidth, true);
                genCircle(world, canopyCenter.up(3), canopyWidth + 1, true);
                setBlockState(world, canopyCenter.north(), LOG.withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z));
                setBlockState(world, canopyCenter.west(), LOG.withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X));
                setBlockState(world, canopyCenter.east(), LOG.withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X));
                setBlockState(world, canopyCenter.south(), LOG.withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z));
            } else {
                setBlockState(world, canopyCenter, LOG);
                setBlockState(world, canopyCenter.up(), LOG);
                setBlockState(world, canopyCenter.down(), LOG);
            }
        }
        for (int y = (pos.getY() - 1) + 1; y <= pos.getY() + treeHeight; y++) {
            setBlockState(world, new BlockPos(pos.getX(), y, pos.getZ()), LOG);
        }
        return true;
    }

    private void genCircle(World world, BlockPos pos, float size, boolean spikes) {
        float f = size;
        for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-size, 0, -size), pos.add(size, 0, size))) {
            int distanceX = Math.abs(blockpos.getX() - pos.getX());
            int distanceZ = Math.abs(blockpos.getZ() - pos.getZ());
            boolean corner = blockpos.getX() == pos.getX() || blockpos.getZ() == pos.getZ() || distanceX == distanceZ;
            if (spikes) {
                if (corner && blockpos.distanceSq(pos) > (double) (f - 1) * (f - 1) && blockpos.distanceSq(pos) <= (double) (f * f)) {
                    setBlockState(world, blockpos, LEAF);
                }
            } else {
                if (blockpos.distanceSq(pos) <= (double) (f * f)) {
                    setBlockState(world, blockpos, LEAF);
                }
            }

        }

    }

    public void setBlockState(World world, BlockPos pos, IBlockState state) {
        world.setBlockState(pos, state);
    }
}
