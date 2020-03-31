package fossilsarcheology.server.item;

import fossilsarcheology.server.entity.utility.EntityToyBall;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToyBallItem extends Item {

    //Fixme: Toy balls do not show up in creative except for the F&A creative tab
    public ToyBallItem() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setTranslationKey("toyball");
        this.setCreativeTab(FATabRegistry.ITEMS);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        int i = stack.getMetadata();
        return super.getTranslationKey() + "." + EnumDyeColor.byDyeDamage(i).getTranslationKey();
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        EntityToyBall ball = new EntityToyBall(worldIn);
        ItemStack stack = playerIn.getHeldItem(hand);
        ball.setColor(stack.getItemDamage());
        ball.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
        if (!worldIn.isRemote)
            worldIn.spawnEntity(ball);
        stack.shrink(1);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void getSubItems(CreativeTabs creativeTabs, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(creativeTabs)) {
            for (int i = 0; i < 16; ++i) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }
}
