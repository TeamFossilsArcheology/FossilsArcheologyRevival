package fossilsarcheology.server.item;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BasicFoodItem extends ItemFood implements DefaultRenderedItem {

	public BasicFoodItem(int amount, float saturation, boolean meat, String name) {
		super(amount, saturation, meat);
		this.setCreativeTab(FATabRegistry.ITEMS);
		this.setTranslationKey(name);
	}

	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player){
		if(stack.getItem() == FAItemRegistry.RAW_CHICKEN_SOUP || stack.getItem() == FAItemRegistry.COOKED_CHICKEN_SOUP){
			player.addItemStackToInventory(new ItemStack(Items.BUCKET));
		}
	}
}
