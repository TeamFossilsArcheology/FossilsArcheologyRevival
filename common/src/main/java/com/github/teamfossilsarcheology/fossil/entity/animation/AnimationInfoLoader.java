package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.google.gson.Gson;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;

import java.util.Map;

public abstract class AnimationInfoLoader<T extends AnimationInfo> extends SimpleJsonResourceReloadListener {
    protected AnimationInfoLoader(Gson gson, String directory) {
        super(gson, directory);
    }

    public abstract Map<ResourceLocation, BakedAnimationInfo<T>> getAnimationInfos();

    public abstract BakedAnimationInfo<T> getAnimations(ResourceLocation animationFile);
}
