package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.entity.LaserPointEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LaserPointRenderer extends EntityRenderer<LaserPointEntity> {
    public LaserPointRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LaserPointEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        // invisible :)
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(LaserPointEntity entity) {
        // doesn't matter, but we need to return some texture
        return new ResourceLocation("minecraft", "textures/particle/glitter_0.png");
    }
}
