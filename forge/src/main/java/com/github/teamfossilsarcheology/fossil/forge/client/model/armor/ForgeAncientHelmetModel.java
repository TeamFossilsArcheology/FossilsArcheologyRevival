package com.github.teamfossilsarcheology.fossil.forge.client.model.armor;

import com.github.teamfossilsarcheology.fossil.item.AncientHelmetItem;
import com.github.teamfossilsarcheology.fossil.item.forge.AncientHelmetItemImpl;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ForgeAncientHelmetModel extends AnimatedGeoModel<AncientHelmetItemImpl> {

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