package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import fossilsarcheology.server.world.gen.WorldGenCalamites;
import fossilsarcheology.server.world.gen.WorldGenPalm;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class FossilSaplingBlock extends BlockBush implements DefaultRenderedItem, IGrowable {
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public FossilSaplingBlock(String name) {
		super();
		this.setCreativeTab(FATabRegistry.BLOCKS);
		this.setSoundType(SoundType.PLANT);
		this.setHardness(0.2F);
		this.setResistance(1F);
		this.setTranslationKey(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));

	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SAPLING_AABB;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.isRemote) {
			super.updateTick(world, pos, state, rand);
			if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(world, pos, state, rand);
			}
		}
	}

	public void grow(World world, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(STAGE) == 0) {
			world.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			if(canGenerateTree(world, pos)){
				this.generateTree(world, pos, rand);
			}
		}
	}

	public boolean canGenerateTree(World world, BlockPos pos){
		if(this == FABlockRegistry.PALM_SAPLING){
			return WorldGenPalm.canGenTree(world, pos);
		}
		if(this == FABlockRegistry.CALAMITES_SAPLING){
			return WorldGenCalamites.canGenTree(world, pos);
		}
		return false;
	}

	public void generateTree(World world, BlockPos pos, Random rand) {
		if(this == FABlockRegistry.PALM_SAPLING) {
			WorldGenPalm palmGen = new WorldGenPalm();
			world.setBlockToAir(pos);
			palmGen.generate(world, rand, pos);
		}else{
			WorldGenCalamites calamitesGen = new WorldGenCalamites();
			world.setBlockToAir(pos);
			calamitesGen.generate(world, rand, pos);
		}
	}

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		return (double) world.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		this.grow(world, pos, state, rand);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(STAGE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STAGE);
	}
}
