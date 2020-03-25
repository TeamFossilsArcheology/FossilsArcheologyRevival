package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FossilBlock extends Block implements DefaultRenderedItem {
	private int randomMeta;

	public FossilBlock() {
		super(Material.ROCK);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.STONE);
		this.setTranslationKey("fossil");
		this.setCreativeTab(FATabRegistry.BLOCKS);
		this.setHarvestLevel("pickaxe", 2);
		this.randomMeta = 0;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		int i = rand.nextInt(1100);
		int paleo_level = 1;
		int archeo_level = 1;
		if (i < archeo_level) {
			this.randomMeta = 0;
			return FAItemRegistry.SCARAB_GEM;
		} else if (i < 3 + 3 * archeo_level) {
			this.randomMeta = 0;
			return FAItemRegistry.BROKEN_SWORD;
		} else if (i < 6 + 5 * archeo_level) {
			this.randomMeta = 0;
			return FAItemRegistry.BROKEN_HELMET;
		} else if (i < 13 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			this.randomMeta = dropRandom;
			return FAItemRegistry.LEG_BONE;
		} else if (i < 15 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			this.randomMeta = dropRandom;
			return FAItemRegistry.SKULL;
		} else if (i < 17 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			this.randomMeta = dropRandom;
			return FAItemRegistry.UNIQUE_ITEM;
		} else if (i < 19 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			this.randomMeta = dropRandom;
			return FAItemRegistry.FOOT;
		} else if (i < 21 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			this.randomMeta = dropRandom;
			return FAItemRegistry.VERTEBRAE;
		} else if (i < 23 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			this.randomMeta = dropRandom;
			return FAItemRegistry.ARM_BONE;
		} else if (i < 25 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			this.randomMeta = dropRandom;
			return FAItemRegistry.RIBCAGE;
		} else if (i < 50 * (Math.max(paleo_level, archeo_level) * 1.5F)) {
			this.randomMeta = 0;
			return Item.getItemFromBlock(FABlockRegistry.SKULL_BLOCK);
		} else if (i < 350 * (0.5F + paleo_level * 0.5F)) {
			this.randomMeta = 0;
			return FAItemRegistry.BIOFOSSIL;
		} else if (i < 550 * (0.5F + archeo_level * 0.5F)) {
			this.randomMeta = 0;
			return FAItemRegistry.RELIC_SCRAP;
		} else if (i < 900 * (0.5F + paleo_level * 0.5F)) {
			this.randomMeta = 0;
			return Items.BONE;
		} else if (i < 1200 * (0.5F + paleo_level * 0.5F)) {
			this.randomMeta = 0;
			return FAItemRegistry.PLANT_FOSSIL;
		}
		this.randomMeta = 0;
		return Item.getItemFromBlock(Blocks.COBBLESTONE);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		Random random = new Random();
		int count = this.quantityDropped(state, fortune, random);
		for (int i = 0; i < count; i++) {
			Item item = this.getItemDropped(state, random, fortune);
			if (item != null) {
				drops.add(new ItemStack(item, 1, this.randomMeta));
			}
		}
		return drops;
	}

	public static ItemStack getItemDroppedWithEnchants(IBlockState state, Random rand, int paleo_level, int archeo_level) {
		int i = rand.nextInt(1100);
		if (i < 1 + (archeo_level - 1) * 5) {
			return new ItemStack(FAItemRegistry.SCARAB_GEM);
		} else if (i < 3 + 3 * archeo_level) {
			return new ItemStack(FAItemRegistry.BROKEN_SWORD);
		} else if (i < 6 + 5 * archeo_level) {
			return new ItemStack(FAItemRegistry.BROKEN_HELMET);
		} else if (i < 13 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			return new ItemStack(FAItemRegistry.LEG_BONE, 1, dropRandom);
		} else if (i < 15 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			return new ItemStack(FAItemRegistry.SKULL, 1, dropRandom);
		} else if (i < 17 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			return new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, dropRandom);
		} else if (i < 19 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			return new ItemStack(FAItemRegistry.FOOT, 1, dropRandom);
		} else if (i < 21 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			return new ItemStack(FAItemRegistry.VERTEBRAE, 1, dropRandom);
		} else if (i < 23 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			return new ItemStack(FAItemRegistry.ARM_BONE, 1, dropRandom);
		} else if (i < 25 * (paleo_level * 1.5F)) {
			int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
			return new ItemStack(FAItemRegistry.RIBCAGE, 1, dropRandom);
		} else if (i < 50 * (Math.max(paleo_level, archeo_level) * 1.5F)) {
			return new ItemStack(FABlockRegistry.SKULL_BLOCK);
		} else if (i < 350 * (0.5F + paleo_level * 0.5F)) {
			return new ItemStack(FAItemRegistry.BIOFOSSIL);
		} else if (i < 550 * (0.5F + archeo_level * 0.5F)) {
			return new ItemStack(FAItemRegistry.RELIC_SCRAP);
		} else if (i < 900 * (0.5F + paleo_level * 0.5F)) {
			return new ItemStack(Items.BONE);
		} else if (i < 1200 * (0.5F + paleo_level * 0.5F)) {
			return new ItemStack(FAItemRegistry.PLANT_FOSSIL);
		}
		return new ItemStack(Blocks.COBBLESTONE);
	}

}