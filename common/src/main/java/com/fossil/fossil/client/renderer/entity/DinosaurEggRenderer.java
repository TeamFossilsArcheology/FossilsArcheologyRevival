package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.model.DinosaurEggModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.prehistoric.base.DinosaurEgg;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class DinosaurEggRenderer extends LivingEntityRenderer<DinosaurEgg, DinosaurEggModel> implements RendererFabricFix {

    public DinosaurEggRenderer(EntityRendererProvider.Context context, DinosaurEggModel entityModel) {
        super(context, entityModel, 0.25f);
    }

    @Override
    protected void scale(DinosaurEgg egg, PoseStack poseStack, float partialTickTime) {
        float scale = egg.getPrehistoricEntityType().eggScale;
        poseStack.scale(scale, scale, scale);
        super.scale(egg, poseStack, partialTickTime);
    }

    @Override
    protected boolean shouldShowName(DinosaurEgg entity) {
        return false;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((DinosaurEgg) entity);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DinosaurEgg entity) {
        return DinosaurEggModel.TEXTURES.computeIfAbsent(entity.getPrehistoricEntityType().resourceName,
                name -> new ResourceLocation(Fossil.MOD_ID, "textures/entity/egg/egg_" + name + ".png"));
    }
}
