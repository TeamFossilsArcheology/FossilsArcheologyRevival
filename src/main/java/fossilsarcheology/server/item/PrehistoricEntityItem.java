package fossilsarcheology.server.item;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class PrehistoricEntityItem extends Item implements DefaultRenderedItem {
    public final String resourceName;
    protected PrehistoricEntityType type;

    public PrehistoricEntityItem(String name, PrehistoricEntityType type) {
        super();
        this.type = type;
        this.setTranslationKey(name + type.friendlyName);
        this.resourceName = type.resourceName + "_" + name;
        this.setCreativeTab(FATabRegistry.ITEMS);
    }

    @Override
    public String getResource(ResourceLocation registryName) {
        return resourceName;
    }
}
