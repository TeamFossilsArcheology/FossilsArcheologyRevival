package com.github.teamfossilsarcheology.fossil.item.fabric;

import com.github.teamfossilsarcheology.fossil.client.renderer.armor.AncientHelmetRenderer;
import com.github.teamfossilsarcheology.fossil.item.AncientHelmetItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AncientHelmetItemImpl extends AncientHelmetItem {
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public AncientHelmetItemImpl(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    public static ArmorItem get(ArmorMaterial material, Type type, Item.Properties properties) {
        return new AncientHelmetItemImpl(material, type, properties);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                if (renderer == null)
                    renderer = new AncientHelmetRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}
