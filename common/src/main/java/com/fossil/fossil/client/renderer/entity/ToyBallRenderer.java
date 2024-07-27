package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.ToyBallModel;
import com.fossil.fossil.entity.ToyBall;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ToyBallRenderer extends ToyBaseRenderer<ToyBall> {

    public ToyBallRenderer(EntityRendererProvider.Context context, ToyBallModel entityModel) {
        super(context, entityModel, 0.3f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ToyBall entity) {
        return ToyBallModel.TEXTURE;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((ToyBall) entity);
    }
}
