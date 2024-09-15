package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class AncientHelmetItem {
    public static final ResourceLocation MODEL = new ResourceLocation(Fossil.MOD_ID, "geo/armor/ancient_helmet.geo.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/models/armor/ancient_helmet_texture.png");

    @ExpectPlatform
    public static ArmorItem get(ArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
        throw new AssertionError();
    }
}
