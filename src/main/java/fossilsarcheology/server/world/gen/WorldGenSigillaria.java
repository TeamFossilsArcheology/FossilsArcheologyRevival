package fossilsarcheology.server.world.gen;

import fossilsarcheology.server.block.FABlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenSigillaria extends WorldGenerator {
    private static final IBlockState LOG = FABlockRegistry.SIGILLARIA_LOG.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
    private static final IBlockState LEAF = FABlockRegistry.SIGILLARIA_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, true).withProperty(BlockLeaves.DECAYABLE, true);
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
        return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos)
                || world.getBlockState(pos).getBlock() == FABlockRegistry.SIGILLARIA_SAPLING || world.getBlockState(pos).getBlock().isLeaves(world.getBlockState(pos), world, pos);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        Block j1 = world.getBlockState(pos.down()).getBlock();
        keepGenerating = true;
        boolean twins = rand.nextInt(4) != 0;
        int height = rand.nextInt(7) + 15;
        if (j1 != Blocks.GRASS && j1 != Blocks.DIRT || pos.getY() >= 128 - 12 - 1) {
            return false;
        }
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            setBlockState(world, pos.offset(facing, 2), LOG.withProperty(BlockRotatedPillar.AXIS, facing.getAxis()));
        }
        for (int i = 0; i < height; i++) {
            setBlockState(world, pos.up(i), LOG);
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                if (i < (height - (twins ? -2 : 4)) * 0.65F) {
                    setBlockState(world, pos.up(i).offset(facing), LOG);
                }
            }
        }
        BlockPos top = pos.up(height);
        if (twins) {
            EnumFacing direction = EnumFacing.byHorizontalIndex(rand.nextInt(4));
            float bushWidth = rand.nextInt(2) + 2F;
            int secondHeight = 3 + rand.nextInt(2) + (int) bushWidth;
            for (int i = 0; i <= secondHeight; i++) {
                BlockPos offsetLeft = top.offset(direction, i).up(i);
                BlockPos offsetRight = top.offset(direction.getOpposite(), i).up(i);
                setBlockState(world, offsetLeft, LOG);
                setBlockState(world, offsetRight, LOG);
                int bushHeight = rand.nextInt(2) + 6;
                if (i == secondHeight) {
                    genCone(world, offsetLeft.up(bushHeight / 2).offset(direction, -1), bushWidth, bushHeight, rand);
                    genCone(world, offsetRight.up(bushHeight / 2).offset(direction.getOpposite(), -1), bushWidth, bushHeight, rand);
                }
            }
        } else {
            genCone(world, top.down(2), rand.nextInt(2) + 2F, rand.nextInt(2) + 6, rand);
        }
        return true;
    }

    private void genCone(World world, BlockPos pos, float size, float height, Random random) {
        float f = (size + height + size) * 0.333F + 0.5F;
        for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-size, -height, -size), pos.add(size, height, size))) {
            int distanceX = Math.abs(blockpos.getX() - pos.getX());
            int distanceZ = Math.abs(blockpos.getZ() - pos.getZ());
            int distanceY = Math.abs(blockpos.getY() - pos.getY());
            if (blockpos.distanceSq(pos) <= (double) (f * f)) {
                if (distanceX * distanceX + distanceZ * distanceZ < (f * f * (0.5F + random.nextFloat() * 0.5F)) * ((1 - distanceY % 2) + 0.25F)) {
                    setBlockState(world, blockpos, LEAF);
                }
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBox(pos.down((int) height), pos.up((int) height - 4))) {
            if (blockpos.distanceSq(pos) <= (double) (f * f)) {
                setBlockState(world, blockpos, LOG);
            }
        }
    }

    public void setBlockState(World world, BlockPos pos, IBlockState state) {
        if (isReplaceable(world, pos))
            world.setBlockState(pos, state);
    }
}
