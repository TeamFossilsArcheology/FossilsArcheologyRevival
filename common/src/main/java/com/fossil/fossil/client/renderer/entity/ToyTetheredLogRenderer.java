package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.ToyTetheredLogModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.ToyTetheredLog;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ToyTetheredLogRenderer extends LivingEntityRenderer<ToyTetheredLog, ToyTetheredLogModel> implements RendererFabricFix {


    public ToyTetheredLogRenderer(EntityRendererProvider.Context context, ToyTetheredLogModel entityModel) {
        super(context, entityModel, 0.3f);
    }

    @Override
    protected void setupRotations(ToyTetheredLog entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {

    }

    @Override
    protected boolean shouldShowName(ToyTetheredLog entity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(ToyTetheredLog entity) {
        return ToyTetheredLogModel.TEXTURES.get(entity.getWoodTypeName());
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((ToyTetheredLog) entity);
    }
}
