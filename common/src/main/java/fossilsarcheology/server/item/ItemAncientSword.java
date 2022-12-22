package fossilsarcheology.server.item;

import fossilsarcheology.Revival;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class ItemAncientSword extends SwordItem {

    public ItemAncientSword() {
        super(FAItemRegistry.ANCIENT_SWORD_MATERIAL, 3, -2.4F, new Properties().group(Revival.TAB_ITEMS));
        this.setRegistryName("fossil:ancient_sword");
    }
}
