package fossilsarcheology.server.item;

import fossilsarcheology.Revival;
import net.minecraft.item.Item;

public class ItemBioFossil extends Item {

    private boolean tar = false;

    public ItemBioFossil(boolean tar) {
        super(new Properties().group(Revival.TAB_ITEMS));
        this.tar = tar;
        this.setRegistryName(tar ? "fossil:tar_fossil" : "fossil:bio_fossil");
    }
}
