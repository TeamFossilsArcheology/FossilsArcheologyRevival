package fossilsarcheology.server.item.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentArcheology extends Enchantment {

    private boolean archeology;

    protected EnchantmentArcheology(boolean archeology) {
        super(Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.archeology = archeology;
        setName(archeology ? "fossil.archeology" : "fossil.paleontology");
        setRegistryName(archeology ? "fossil:archeology" : "fossil:paleontology");
    }

    public int getMaxLevel() {
        return 3;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMinEnchantability(int enchantmentLevel) {
        return 1 + 10 * (enchantmentLevel - 1);
    }

    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.SILK_TOUCH && (this.archeology && ench != FAEnchantmentRegistry.ENCHANTMENT_PALEONTOLOGY || !this.archeology && ench != FAEnchantmentRegistry.ENCHANTMENT_ARCHEOLOGY);
    }
}