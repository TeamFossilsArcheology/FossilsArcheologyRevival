package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.Fossil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class AncientHelmetItem {
    public static final ResourceLocation MODEL = Fossil.location("geo/armor/ancient_helmet.geo.json");
    public static final ResourceLocation TEXTURE = Fossil.location("textures/models/armor/ancient_helmet_texture.png");

    @ExpectPlatform
    public static ArmorItem get(ArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
        throw new AssertionError();
    }
}
