package com.fossil.fossil.client.renderer;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Gender;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobType;
import org.jetbrains.annotations.NotNull;

// TODO Migrate to geckolib rendering as all dinosaurs should be rendered with it
public class RenderPrehistoric extends MobRenderer<Prehistoric, EntityModel<Prehistoric>> {

    public RenderPrehistoric(EntityRendererProvider.Context context, EntityModel<Prehistoric> model) {
        super(context, model, 0.3F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(Prehistoric entity) {
        return entity.textureLocation;
    }

    @NotNull
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((Prehistoric) entity);
    }

    @Override
    protected void scale(Prehistoric entity, PoseStack matrixStackIn, float partialTickTime) {
        float xRot = 0;
        if(entity.getMobType() == MobType.WATER){
            //   xRot = aquatic.prevBreachPitch + (aquatic.getBreachPitch() - aquatic.prevBreachPitch) * f;
        //    GlStateManager.rotate(xRot, 1.0F, 0F, 0F);
        }
        float scale = entity.getGender() == Gender.FEMALE ? entity.getMaleSize() * entity.getAgeScale() : 1 * entity.getAgeScale();
        matrixStackIn.scale(scale, scale, scale);
        this.shadowRadius = entity.getBbWidth() * 0.45F;
    }

    @Override
    protected float getFlipDegrees(Prehistoric entity) {
        return entity.getDeathRotation();
    }


}
