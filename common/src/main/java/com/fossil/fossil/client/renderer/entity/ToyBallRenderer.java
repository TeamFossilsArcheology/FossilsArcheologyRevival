package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.ToyBallModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.ToyBall;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ToyBallRenderer extends LivingEntityRenderer<ToyBall, ToyBallModel> implements RendererFabricFix {


    public ToyBallRenderer(EntityRendererProvider.Context context, ToyBallModel entityModel) {
        super(context, entityModel, 0.3f);
    }

    @Override
    protected void setupRotations(ToyBall entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
    }

    @Override
    protected boolean shouldShowName(ToyBall entity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(ToyBall entity) {
        return ToyBallModel.TEXTURE;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((ToyBall) entity);
    }
}
