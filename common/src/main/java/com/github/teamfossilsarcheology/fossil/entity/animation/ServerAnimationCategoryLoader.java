package com.github.teamfossilsarcheology.fossil.entity.animation;

public class ServerAnimationCategoryLoader extends AnimationCategoryLoader {
    public static final ServerAnimationCategoryLoader INSTANCE = new ServerAnimationCategoryLoader();

    private ServerAnimationCategoryLoader() {
        super(ServerAnimationInfoLoader.INSTANCE);
    }
}
