package fossilsarcheology.server.item;

import fossilsarcheology.server.block.EnumFossilPlant;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.block.TallFlowerBlock;
import fossilsarcheology.server.block.FourTallFlowerBlock;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class FossilSeedsItem extends Item implements IPlantable {
	public final boolean isFossil;

	public FossilSeedsItem(boolean isFossil) {
		super();
		this.setHasSubtypes(true);
		this.isFossil = isFossil;
		this.setCreativeTab(FATabRegistry.ITEMS);
		this.setTranslationKey(isFossil ? "fossilseed" : "seed");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < EnumFossilPlant.values().length; ++i) {
				list.add(new ItemStack(this, 1, i));
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (!isFossil && player.canPlayerEdit(pos, facing, stack) && player.canPlayerEdit(pos.up(), facing, stack)) {
			int meta = stack.getItemDamage();
			if (meta < 0 || meta >= EnumFossilPlant.values().length) {
				meta = 0;
			}
			EnumFossilPlant enumType = EnumFossilPlant.values()[meta];
			int clearance = enumType.getClearance();
			boolean hasClearance = true;
			for(int i = 0; i < clearance; i++){
				BlockPos up = pos.up(1 + i);
				if(!world.isAirBlock(up)){
					hasClearance = false;
				}
			}
			if (hasClearance && canPlant(world.getBlockState(pos)) && world.isAirBlock(pos.up()) && (world.getBlockState(pos).getBlock() != FABlockRegistry.WELWITSCHIA_FLOWER)) {
				if (this.placePlantBlock(stack, world, pos.up(), new Random())) {
					world.playSound(player, pos, SoundType.PLANT.getBreakSound(), SoundCategory.BLOCKS, 1F, new Random().nextFloat() * 0.1F + 0.8F);
				}
				stack.shrink(1);
				return EnumActionResult.SUCCESS;
			} else {
				return EnumActionResult.PASS;
			}
		} else {
			return EnumActionResult.PASS;
		}
	}

	public boolean canPlant(IBlockState state) {
		return state.getBlock() == net.minecraft.init.Blocks.GRASS || state.getMaterial() == Material.GROUND || state.getBlock() == net.minecraft.init.Blocks.FARMLAND;
	}

	private boolean placePlantBlock(ItemStack stack, World world, BlockPos pos, Random rand) {
		int meta = stack.getItemDamage();
		if (meta < 0 || meta >= EnumFossilPlant.values().length) {
			meta = 0;
		}
		EnumFossilPlant enumType = EnumFossilPlant.values()[meta];
		enumType.onPlace(world, pos);
		return true;
	}

	@Override
	public String getTranslationKey(ItemStack itemStack) {
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= EnumFossilPlant.values().length) {
			meta = 0;
		}

		return super.getTranslationKey() + "_" + EnumFossilPlant.values()[meta].name().toLowerCase();
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Plains;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		EnumFossilPlant enumType = EnumFossilPlant.values()[0];
		return enumType.defaultState();
	}
}
