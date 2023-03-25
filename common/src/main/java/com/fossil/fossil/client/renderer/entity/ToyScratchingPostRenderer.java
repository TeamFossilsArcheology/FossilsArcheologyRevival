package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.ToyScratchingPostModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.ToyScratchingPost;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ToyScratchingPostRenderer extends LivingEntityRenderer<ToyScratchingPost, ToyScratchingPostModel> implements RendererFabricFix {

    public ToyScratchingPostRenderer(EntityRendererProvider.Context context, ToyScratchingPostModel entityModel) {
        super(context, entityModel, 0.3f);
    }

    @Override
    protected void setupRotations(ToyScratchingPost entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
    }

    @Override
    protected boolean shouldShowName(ToyScratchingPost entity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(ToyScratchingPost entity) {
        return ToyScratchingPostModel.TEXTURE;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((ToyScratchingPost) entity);
    }
}
