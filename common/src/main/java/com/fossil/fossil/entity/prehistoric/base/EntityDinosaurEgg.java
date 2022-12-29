package com.fossil.fossil.entity.prehistoric.base;


import com.fossil.fossil.util.PrehistoricEntityType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EntityDinosaurEgg extends LivingEntity {
    public PrehistoricEntityType selfType;

    protected EntityDinosaurEgg(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return null;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }
}
