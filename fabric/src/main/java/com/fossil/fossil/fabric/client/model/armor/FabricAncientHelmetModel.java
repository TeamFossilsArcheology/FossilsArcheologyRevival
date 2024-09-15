package com.fossil.fossil.fabric.client.model.armor;

import com.fossil.fossil.item.AncientHelmetItem;
import com.fossil.fossil.item.fabric.AncientHelmetItemImpl;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FabricAncientHelmetModel extends AnimatedGeoModel<AncientHelmetItemImpl> {

    @Override
    public ResourceLocation getModelLocation(AncientHelmetItemImpl object) {
        return AncientHelmetItem.MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(AncientHelmetItemImpl object) {
        return AncientHelmetItem.TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AncientHelmetItemImpl animatable) {
        return null;
    }
}