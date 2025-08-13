package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.Trilobite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import java.util.function.Function;

public class PrehistoricGeoModel<T extends Prehistoric> extends DefaultedEntityGeoModel<T> implements AdditiveAnimationModel {
    private final Function<ResourceLocation, RenderType> renderType;
    /**
     * @param assetName the asset files name (excluding extension)
     */
    public PrehistoricGeoModel(String assetName, Function<ResourceLocation, RenderType> renderType) {
        super(FossilMod.location(assetName), false);
        this.renderType = renderType;
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (Minecraft.getInstance().isPaused()) {
            return;
        }
        if (animatable instanceof PrehistoricSwimming && animatable.isInWater() || (animatable instanceof PrehistoricFlying flying && flying.isFlying())) {
            if (!animatable.isVehicle() && !(animatable instanceof Trilobite)) {
                CoreGeoBone root = getAnimationProcessor().getBone("pitch_root");
                if (root != null) {
                    float pitch = Mth.lerp(animationState.getPartialTick(), animatable.xRotO, animatable.getXRot());
                    root.setRotX(-pitch * Mth.DEG_TO_RAD);
                }
            } else if (animatable instanceof PrehistoricFlying flying) {
                CoreGeoBone root = getAnimationProcessor().getBone("pitch_root");
                if (root != null && animatable.getControllingPassenger() instanceof Player) {
                    //TODO: I would prefer using the molang query but its very laggy
                    //GeckoLibCache.getInstance().parser.setValue("rider_rot_x", () -> Mth.lerp(animationEvent.getPartialTick(), flying.prevPitch, flying.currentPitch));
                    //GeckoLibCache.getInstance().parser.setValue("rider_rot_z", () -> Mth.lerp(animationEvent.getPartialTick(), flying.prevYaw, flying.currentYaw));

                    float pitch = Mth.lerp(animationState.getPartialTick(), flying.prevPitch, flying.currentPitch);
                    root.setRotX(-pitch * Mth.DEG_TO_RAD);
                    float yaw = Mth.lerp(animationState.getPartialTick(), flying.prevYaw, flying.currentYaw);
                    root.setRotZ(yaw * Mth.DEG_TO_RAD);
                }
            }
        }
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        if (object.textureLocation == null) {
            //Fix for mods that call render on entities not placed in the world
            object.refreshTexturePath();
        }
        return object.textureLocation;
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return renderType.apply(texture);
    }
}
