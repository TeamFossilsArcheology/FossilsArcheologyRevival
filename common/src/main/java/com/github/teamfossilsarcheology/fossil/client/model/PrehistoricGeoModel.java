package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PrehistoricGeoModel<T extends Prehistoric> extends AnimatedGeoModel<T> {
    private final ResourceLocation modelLocation;
    private final ResourceLocation animationLocation;

    /**
     * @param modelName the file model name (including extension)
     * @param animationName the animation model name (including extension)
     */
    public PrehistoricGeoModel(String modelName, String animationName) {
        this.modelLocation = Fossil.location("geo/entity/" + modelName);
        this.animationLocation = Fossil.location("animations/" + animationName);
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        if (!Minecraft.getInstance().isPaused() && !animatable.isVehicle()) {
            IBone root = getAnimationProcessor().getBone("pitch_root");
            if (root != null) {
                float pitch = Mth.lerp(animationEvent.getPartialTick(), animatable.xRotO, animatable.getXRot());
                root.setRotationX(-pitch * Mth.DEG_TO_RAD + root.getRotationX());
            }
        }
    }

    @Override
    public ResourceLocation getModelLocation(T object) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(T object) {
        return object.textureLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return animationLocation;
    }
}
