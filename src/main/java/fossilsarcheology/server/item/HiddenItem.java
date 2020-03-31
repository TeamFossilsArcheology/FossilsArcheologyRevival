package fossilsarcheology.server.item;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.entity.utility.EntityToyTetheredLog;
import fossilsarcheology.server.entity.utility.EntityWorseDebugTest;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class HiddenItem extends Item implements DefaultRenderedItem {

    public HiddenItem() {
        this.setTranslationKey("pinkerton");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getBlockState(pos).getBlock() == Blocks.GOLD_BLOCK) {
            EntityWorseDebugTest weezer = new EntityWorseDebugTest(worldIn);
            weezer.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1.9, pos.getZ() + 0.5, 0, 0);
            if (!worldIn.isRemote) {
                worldIn.spawnEntity(weezer);
            }
            weezer.rotationYaw = playerIn.rotationYawHead;
            if (!playerIn.capabilities.isCreativeMode) {
                playerIn.getHeldItem(hand).shrink(1);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".desc"));
    }
}
