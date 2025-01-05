package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.resource.GeckoLibCache;

public class PrehistoricGeoModel<T extends Prehistoric> extends PrehistoricAnimatableModel<T> {
    private final ResourceLocation modelLocation;
    private final ResourceLocation animationLocation;

    /**
     * @param modelName     the file model name (including extension)
     * @param animationName the animation model name (including extension)
     */
    public PrehistoricGeoModel(String modelName, String animationName) {
        this.modelLocation = FossilMod.location("geo/entity/" + modelName);
        this.animationLocation = FossilMod.location("animations/" + animationName);
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        if (Minecraft.getInstance().isPaused()) {
            return;
        }
        if (animatable instanceof PrehistoricSwimming && animatable.isInWater() || (animatable instanceof PrehistoricFlying flying && flying.isFlying())) {
            if (!animatable.isVehicle()) {
                IBone root = getAnimationProcessor().getBone("pitch_root");
                if (root != null) {
                    float pitch = Mth.lerp(animationEvent.getPartialTick(), animatable.xRotO, animatable.getXRot());
                    root.setRotationX(-pitch * Mth.DEG_TO_RAD + root.getRotationX());
                }
            } else if (animatable instanceof PrehistoricFlying flying) {
                IBone root = getAnimationProcessor().getBone("pitch_root");
                if (root != null && animatable.getControllingPassenger() instanceof Player) {
                    GeckoLibCache.getInstance().parser.setValue("rider_rot_x", () -> Mth.lerp(animationEvent.getPartialTick(), flying.prevPitch, flying.currentPitch));
                    GeckoLibCache.getInstance().parser.setValue("rider_rot_z", () -> Mth.lerp(animationEvent.getPartialTick(), flying.prevYaw, flying.currentYaw));
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
        if (object.textureLocation == null) {
            //Fix for mods that call render on entities not placed in the world
            object.refreshTexturePath();
        }
        return object.textureLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return animationLocation;
    }
}
