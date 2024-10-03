package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.client.model.PrehistoricFishGeoModel;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Nautilus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PrehistoricFishGeoRenderer<T extends PrehistoricFish> extends GeoEntityRenderer<T> {

    public PrehistoricFishGeoRenderer(EntityRendererProvider.Context renderManager, String model, String animation, String texture) {
        super(renderManager, new PrehistoricFishGeoModel<>(
                Fossil.location("geo/entity/" + model),
                Fossil.location("animations/" + animation),
                Fossil.location("textures/entity/" + texture + "/texturemap.png")
        ));
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable instanceof Nautilus) {
            poseStack.mulPose(Vector3f.YP.rotation(180));
        }
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.shadowRadius = entity.getBbWidth() * 0.45F;
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public boolean shouldShowName(T animatable) {
        //Calling super.shouldShowName in fabric crashes the game because the method doesn't exist in GeoEntityRenderer
        return false;
    }

    @Override
    public float getWidthScale(T animatable) {
        return animatable.getScale();
    }

    @Override
    public float getHeightScale(T entity) {
        return animatable.getScale();
    }
}