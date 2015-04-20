package mods.fossil.items.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockTeir3 extends ItemBlock{

	public ItemBlockTeir3(Block block) {
		super(block);
	}
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
        list.add(StatCollector.translateToLocal("tile.cultureVat.teir3"));

	}
}
