package fossilsarcheology.server.block;

import fossilsarcheology.Revival;
import fossilsarcheology.server.ServerProxy;
import fossilsarcheology.server.api.BlockEntity;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.block.entity.TileEntityWorktable;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class WorktableBlock extends BlockContainer implements DefaultRenderedItem, BlockEntity {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static boolean keepInventory = false;

    public WorktableBlock(boolean isActive) {
		super(Material.WOOD);
		this.setHarvestLevel("axe", 0);
		this.setHardness(2.5F);
		this.setSoundType(SoundType.WOOD);
        if (isActive) {
			setTranslationKey("worktable_active");
		} else {
			setTranslationKey("worktable");
			setCreativeTab(FATabRegistry.BLOCKS);
		}
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public static void setState(boolean isActive, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		keepInventory = true;
		EnumFacing facing = EnumFacing.NORTH;
		if(world.getBlockState(pos).getBlock() instanceof WorktableBlock){
			facing = world.getBlockState(pos).getValue(WorktableBlock.FACING);
		}
		if (isActive) {
			world.setBlockState(pos, FABlockRegistry.WORKTABLE_ACTIVE.getDefaultState().withProperty(WorktableBlock.FACING, facing));
		} else {
			world.setBlockState(pos, FABlockRegistry.WORKTABLE_IDLE.getDefaultState().withProperty(WorktableBlock.FACING, facing));
		}
		keepInventory = false;
		if (tile != null) {
			tile.validate();
			world.setTileEntity(pos, tile);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		return Item.getItemFromBlock(FABlockRegistry.WORKTABLE_IDLE);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@SuppressWarnings("deprecation")
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			player.openGui(Revival.INSTANCE, ServerProxy.GUI_WORKTABLE, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}


	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!keepInventory  || !(world.getBlockState(pos).getBlock() instanceof WorktableBlock)) {
			TileEntity entity = world.getTileEntity(pos);
			if (entity == null) {
				return;
			}
			if (entity instanceof TileEntityWorktable) {
				TileEntityWorktable analyzer = (TileEntityWorktable)entity;
				for (int i = 0; i < analyzer.getSizeInventory(); i++) {
					ItemStack stack = analyzer.getStackInSlot(i);
					if (!stack.isEmpty()) {
						InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
					}
				}
			}
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityWorktable();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(pos));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(FABlockRegistry.WORKTABLE_IDLE);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.byIndex(meta);
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rotation) {
		return state.withProperty(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror) {
		return state.withRotation(mirror.toRotation(state.getValue(FACING)));
	}

	@Override
	@SuppressWarnings("deprecation")
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public Class<? extends TileEntity> getEntity() {
		return TileEntityWorktable.class;
	}
}
