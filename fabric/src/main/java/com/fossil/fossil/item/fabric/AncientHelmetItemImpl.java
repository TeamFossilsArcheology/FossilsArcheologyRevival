package com.fossil.fossil.item.fabric;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AncientHelmetItemImpl extends ArmorItem implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public AncientHelmetItemImpl(ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties builder) {
        super(materialIn, slot, builder);
    }

    public static ArmorItem get(ArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
        return new AncientHelmetItemImpl(material, slot, properties);
    }

    @Override
    public void registerControllers(AnimationData data) {
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
