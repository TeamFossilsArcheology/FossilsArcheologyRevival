package com.fossil.fossil.client.model.armor;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.item.AncientHelmetItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AncientHelmetModel extends AnimatedGeoModel<AncientHelmetItem> {
    private static final ResourceLocation MODEL = new ResourceLocation(Fossil.MOD_ID, "geo/armor/ancient_helmet.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/models/armor/ancient_helmet_texture.png");
    @Override
    public ResourceLocation getModelLocation(AncientHelmetItem object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(AncientHelmetItem object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AncientHelmetItem animatable) {
        return null;
    }
}
