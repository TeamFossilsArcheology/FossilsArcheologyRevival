package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class PrehistoricFishGeoModel<T extends PrehistoricFish> extends DefaultedEntityGeoModel<T> implements AdditiveAnimationModel {

    public PrehistoricFishGeoModel(String assetName) {
        super(FossilMod.location(assetName), false);
        withAltTexture(FossilMod.location(assetName + "/texturemap"));
    }
}