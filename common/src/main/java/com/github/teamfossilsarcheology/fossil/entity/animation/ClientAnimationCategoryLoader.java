package com.github.teamfossilsarcheology.fossil.entity.animation;

public class ClientAnimationCategoryLoader extends AnimationCategoryLoader {
    public static final ClientAnimationCategoryLoader INSTANCE = new ClientAnimationCategoryLoader();

    private ClientAnimationCategoryLoader() {
        super(ClientAnimationInfoLoader.INSTANCE);
    }
}
