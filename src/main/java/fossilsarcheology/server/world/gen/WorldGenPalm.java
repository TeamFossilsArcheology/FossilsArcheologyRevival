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

public class WorldGenPalm extends WorldGenerator {
	private static final IBlockState LOG = FABlockRegistry.PALM_LOG.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
	private static final IBlockState LEAF = FABlockRegistry.PALM_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, true).withProperty(BlockLeaves.DECAYABLE, true);
	private boolean keepGenerating = true;

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		Block j1 = world.getBlockState(pos.down()).getBlock();
		keepGenerating = true;
		if (j1 != Blocks.GRASS && j1 != Blocks.DIRT || pos.getY() >= 128 - 12 - 1) {
			return false;
		}

		// int deltaY = 5;
		int deltaY = rand.nextInt(10);

		for (int y = (pos.getY() - 1) + 1; y <= pos.getY() + 10 + deltaY; y++) {
			setBlockState(world, new BlockPos(pos.getX(), y, pos.getZ()), LOG);
		}

		int var4 = pos.getY() - 5 + deltaY;
		setBlockState(world, new BlockPos(pos.getX(), var4 + 16, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 1, var4 + 15, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 2, var4 + 15, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 3, var4 + 15, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 4, var4 + 15, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 5, var4 + 14, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 15, pos.getZ() + 1), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 15, pos.getZ() + 2), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 15, pos.getZ() + 3), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 15, pos.getZ() + 4), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 14, pos.getZ() + 5), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 1, var4 + 15, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 2, var4 + 15, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 3, var4 + 15, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 4, var4 + 15, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 5, var4 + 14, pos.getZ()), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 15, pos.getZ() - 1), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 15, pos.getZ() - 2), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 15, pos.getZ() - 3), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 15, pos.getZ() - 4), LEAF);
		setBlockState(world, new BlockPos(pos.getX(), var4 + 14, pos.getZ() - 5), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 1, var4 + 15, pos.getZ() + 1), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 1, var4 + 15, pos.getZ() - 1), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 1, var4 + 15, pos.getZ() + 1), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 1, var4 + 15, pos.getZ() - 1), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 2, var4 + 15, pos.getZ() + 2), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 2, var4 + 15, pos.getZ() - 2), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 2, var4 + 15, pos.getZ() + 2), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 2, var4 + 15, pos.getZ() - 2), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 3, var4 + 14, pos.getZ() + 3), LEAF);
		setBlockState(world, new BlockPos(pos.getX() + 3, var4 + 14, pos.getZ() - 3), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 3, var4 + 14, pos.getZ() + 3), LEAF);
		setBlockState(world, new BlockPos(pos.getX() - 3, var4 + 14, pos.getZ() - 3), LEAF);
		return true;
	}

	public static boolean canGenTree(World world, BlockPos pos){
		for (int y = 0; y <= 10; y++) {
			if(!isReplaceable(world, pos.up(y))){
				return false;
			}
		}
		return true;
	}

	private static boolean isReplaceable(World world, BlockPos pos){
		return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos) || world.getBlockState(pos).getBlock() == FABlockRegistry.PALM_SAPLING;
	}

	public void setBlockState(World world, BlockPos pos, IBlockState state){
		world.setBlockState(pos, state);
	}
}
