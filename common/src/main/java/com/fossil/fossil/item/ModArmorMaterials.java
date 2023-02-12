package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    ANCIENT_ARMOR("ancient_armor", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0f, 0.0f,
            () -> Ingredient.of(ModItems.SCARAB_GEM.get())),
    BONE("bone", 25, new int[]{2, 7, 6, 2}, 15, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0f, 0.0f, () -> Ingredient.of(Items.BONE));

    private static final int[] HEALTH_PER_SLOT;
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private ModArmorMaterials(String string2, int j, int[] is, int k, SoundEvent soundEvent, float f, float g, Supplier<Ingredient> supplier) {
        this.name = string2;
        this.durabilityMultiplier = j;
        this.slotProtections = is;
        this.enchantmentValue = k;
        this.sound = soundEvent;
        this.toughness = f;
        this.knockbackResistance = g;
        this.repairIngredient = new LazyLoadedValue<>(supplier);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    static {
        HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    }
}
