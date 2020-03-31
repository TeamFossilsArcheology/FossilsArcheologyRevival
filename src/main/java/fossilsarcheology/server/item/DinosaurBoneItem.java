package fossilsarcheology.server.item;

import fossilsarcheology.server.api.SubtypeRenderedItem;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class DinosaurBoneItem extends Item implements SubtypeRenderedItem {
    private final String type;

    public DinosaurBoneItem(String type) {
        super();
        this.setMaxDamage(0);
        this.setCreativeTab(FATabRegistry.ITEMS);
        this.setHasSubtypes(true);
        this.setTranslationKey("bone_" + type);
        this.type = type;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.getItemDamage() >= 0 && stack.getItemDamage() < DinosaurBoneType.values().length) {
            DinosaurBoneType type = DinosaurBoneType.values()[stack.getItemDamage()];
            if (this == FAItemRegistry.UNIQUE_ITEM) {
                return I18n.translateToLocal(I18n.translateToLocal("item.bone_unique_item." + type.getResourceName() + ".name"));
            } else {
                return I18n.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", I18n.translateToLocal("entity.fossil." + type.getResourceName() + ".name"));
            }
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int meta = 0; meta < DinosaurBoneType.values().length; meta++) {
                list.add(new ItemStack(this, 1, meta));
            }
        }
    }

    @Override
    public int[] getUsedSubtypes() {
        int[] usedSubtypes = new int[DinosaurBoneType.values().length];
        for (int i = 0; i < usedSubtypes.length; i++) {
            usedSubtypes[i] = i;
        }
        return usedSubtypes;
    }

    @Override
    public String getResource(ResourceLocation name, int metadata) {
        DinosaurBoneType type = DinosaurBoneType.values()[metadata];
        return "bones/" + type.getResourceName() + "/" + this.type;
    }
}
