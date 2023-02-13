package com.fossil.fossil.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class ArcheologyEnchantment extends Enchantment {
    protected ArcheologyEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMaxCost(int level) {
        return getMinCost(level) + 50;
    }

    @Override
    public int getMinCost(int level) {
        return 1 + 10 * (level - 1);
    }


    @Override
    public boolean checkCompatibility(Enchantment other) {
        if (other == Enchantments.SILK_TOUCH || other instanceof PaleontologyEnchantment) {
            return false;
        }
        return super.checkCompatibility(other);
    }
}
