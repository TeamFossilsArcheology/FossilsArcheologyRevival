package fossilsarcheology.server.item;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BasicItem extends Item implements DefaultRenderedItem {
    public BasicItem(String name) {
        super();
        this.setTranslationKey(name);
        this.setCreativeTab(FATabRegistry.ITEMS);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (this == FAItemRegistry.STUNTED_ESSENCE) {
            tooltip.add(I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".desc"));
        }
    }
}
