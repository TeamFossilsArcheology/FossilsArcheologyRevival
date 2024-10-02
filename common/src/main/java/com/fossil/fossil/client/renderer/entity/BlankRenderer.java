package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class BlankRenderer<T extends Entity> extends EntityRenderer<T> {
    private static final ResourceLocation LOCATION = Fossil.location("textures/item/blank.png");
    public BlankRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        if (entityRenderDispatcher.shouldRenderHitBoxes()) {
            return super.shouldRender(livingEntity, camera, camX, camY, camZ);
        }
        return false;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(T entity) {
        return LOCATION;
    }
}
