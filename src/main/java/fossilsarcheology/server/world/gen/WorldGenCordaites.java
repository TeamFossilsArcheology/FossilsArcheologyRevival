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

public class WorldGenCordaites extends WorldGenerator {
    private static final IBlockState LOG = FABlockRegistry.CORDAITES_LOG.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
    private static final IBlockState LEAF = FABlockRegistry.CORDAITES_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, true).withProperty(BlockLeaves.DECAYABLE, true);
    private boolean keepGenerating = true;

    public static boolean canGenTree(World world, BlockPos pos) {
        for (int y = 0; y <= 20; y++) {
            if (!isReplaceable(world, pos.up(y))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isReplaceable(World world, BlockPos pos) {
        return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos)
                || world.getBlockState(pos).getBlock() == FABlockRegistry.CORDAITES_SAPLING || world.getBlockState(pos).getBlock().isLeaves(world.getBlockState(pos), world, pos);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        Block j1 = world.getBlockState(pos.down()).getBlock();
        keepGenerating = true;
        boolean twins = rand.nextInt(4) != 0;
        int height = rand.nextInt(8) + 10;
        if (j1 != Blocks.GRASS && j1 != Blocks.DIRT || pos.getY() >= 128 - 12 - 1) {
            return false;
        }
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            setBlockState(world, pos.offset(facing), LOG.withProperty(BlockRotatedPillar.AXIS, facing.getAxis()));
        }
        for (int i = 0; i < height; i++) {
            setBlockState(world, pos.up(i), LOG);
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                if (i > height * 0.6F && rand.nextInt(1) == 0 && isReplaceable(world, pos.up(i - 1).offset(facing))) {
                    int heightMinus = (int) (i - height * 0.6F);
                    genBranch(world, pos.up(i), Math.max(1, 2 + rand.nextInt(2) - heightMinus), facing, rand);
                }
            }
        }
        setBlockState(world, pos.up(height), LEAF);
        setBlockState(world, pos.up(height + 1), LEAF);
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            setBlockState(world, pos.up(height).offset(facing), LEAF);
        }
        return true;
    }

    private void genBranch(World world, BlockPos pos, int length, EnumFacing direction, Random random) {
        int yOffset = 0;
        for (int i = 1; i <= length; i++) {
            setBlockState(world, pos.offset(direction, i).up(yOffset), LOG.withProperty(BlockRotatedPillar.AXIS, direction.getAxis()));
            if (i == length) {
                setBlockState(world, pos.offset(direction, i + 1).up(yOffset), LEAF);
                setBlockState(world, pos.offset(direction, i).up(yOffset).offset(direction.rotateYCCW()), LEAF);
                setBlockState(world, pos.offset(direction, i).up(yOffset).offset(direction.rotateY()), LEAF);
                setBlockState(world, pos.offset(direction, i + 1).up(yOffset).offset(direction.rotateYCCW(), 1), LEAF);
                setBlockState(world, pos.offset(direction, i + 1).up(yOffset).offset(direction.rotateY(), 1), LEAF);
                setBlockState(world, pos.offset(direction, i + 2).up(yOffset + 1), LEAF);
            }
            if (i > 2 && random.nextBoolean()) {
                yOffset++;
            }
        }
    }

    public void setBlockState(World world, BlockPos pos, IBlockState state) {
        if (isReplaceable(world, pos))
            world.setBlockState(pos, state);
    }
}
