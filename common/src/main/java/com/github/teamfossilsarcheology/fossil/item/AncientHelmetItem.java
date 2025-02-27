package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AncientHelmetItem extends ArmorItem implements GeoItem {
    public static final ResourceLocation MODEL = FossilMod.location("geo/armor/ancient_helmet.geo.json");
    public static final ResourceLocation TEXTURE = FossilMod.location("textures/models/armor/ancient_helmet_texture.png");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected AncientHelmetItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @ExpectPlatform
    public static ArmorItem get(ArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
        throw new AssertionError();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
