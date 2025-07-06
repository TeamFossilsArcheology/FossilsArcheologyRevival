package com.github.teamfossilsarcheology.fossil.item.forge;

import com.github.teamfossilsarcheology.fossil.client.renderer.armor.AncientHelmetRenderer;
import com.github.teamfossilsarcheology.fossil.item.AncientHelmetItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

public class AncientHelmetItemImpl extends AncientHelmetItem {

    public AncientHelmetItemImpl(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    public static ArmorItem get(ArmorMaterial material, Type type, Item.Properties properties) {
        return new AncientHelmetItemImpl(material, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null)
                    renderer = new AncientHelmetRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return renderer;
            }
        });
    }
}
