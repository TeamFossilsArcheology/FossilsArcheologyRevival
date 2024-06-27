package com.fossil.fossil.client.model;

import com.fossil.fossil.entity.prehistoric.Megalodon;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PrehistoricGeoModel<T extends Prehistoric> extends AnimatedGeoModel<T> {
    private final ResourceLocation modelLocation;
    private final ResourceLocation animationLocation;

    public PrehistoricGeoModel(ResourceLocation modelLocation, ResourceLocation animationLocation) {
        this.modelLocation = modelLocation;
        this.animationLocation = animationLocation;
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        if (animatable instanceof Megalodon) {
            IBone root = getAnimationProcessor().getBone("body");
            float partial = animationEvent.getPartialTick();
            float pitch = Mth.lerp(partial, animatable.xRotO, animatable.getXRot());
            root.setRotationX(-pitch * Mth.DEG_TO_RAD);
            //root.setPositionY(1);
        }
        if (animatable instanceof PrehistoricFlying flying) {
            if (flying.isFlying()) {
                if (!Minecraft.getInstance().isPaused()) {
                    //need to do this because the current bone rotation will not be reset if the game is paused
                    IBone root = getAnimationProcessor().getBone("body");
                    float partial = animationEvent.getPartialTick();
                    float pitch = Mth.lerp(partial, animatable.xRotO, animatable.getXRot());
                    root.setRotationX(-pitch * Mth.DEG_TO_RAD + root.getRotationX());
                }
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
