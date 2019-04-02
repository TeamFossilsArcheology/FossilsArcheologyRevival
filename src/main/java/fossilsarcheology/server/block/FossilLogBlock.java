package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FossilLogBlock extends BlockRotatedPillar implements DefaultRenderedItem {
	protected FossilLogBlock(String name) {
		super(Material.WOOD);
		this.setCreativeTab(FATabRegistry.BLOCKS);
		this.setSoundType(SoundType.WOOD);
		this.setHardness(1.4F);
		this.setResistance(1.0F);
		this.setTranslationKey(name);
	}


	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		int i = 4;
		int j = 5;
		if (world.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
			for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
				IBlockState iblockstate = world.getBlockState(blockpos);
				if (iblockstate.getBlock().isLeaves(iblockstate, world, blockpos)) {
					iblockstate.getBlock().beginLeavesDecay(iblockstate, world, blockpos);
				}
			}
		}
	}

	@Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
	@Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }

}
