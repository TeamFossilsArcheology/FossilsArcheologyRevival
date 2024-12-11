package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.client.model.ToyBallModel;
import com.github.teamfossilsarcheology.fossil.entity.ToyBall;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ToyBallRenderer extends ToyBaseRenderer<ToyBall> {

    public ToyBallRenderer(EntityRendererProvider.Context context) {
        super(context, new ToyBallModel(), 0.3f);
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
