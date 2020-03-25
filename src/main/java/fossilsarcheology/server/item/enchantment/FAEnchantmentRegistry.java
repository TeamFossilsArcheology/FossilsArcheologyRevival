package fossilsarcheology.server.item.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDigging;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class FAEnchantmentRegistry {
    public static final Enchantment ENCHANTMENT_ARCHEOLOGY = new EnchantmentArcheology(true);
    public static final Enchantment ENCHANTMENT_PALEONTOLOGY = new EnchantmentArcheology(false);


}
