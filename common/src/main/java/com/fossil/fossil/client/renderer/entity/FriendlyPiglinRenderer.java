package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.FriendlyPiglinModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.monster.FriendlyPiglin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FriendlyPiglinRenderer extends HumanoidMobRenderer<FriendlyPiglin, FriendlyPiglinModel> implements RendererFabricFix {
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/piglin/zombified_piglin.png");

    public FriendlyPiglinRenderer(EntityRendererProvider.Context context, FriendlyPiglinModel model) {
        super(context, model, 0.5f);
    }

    @Override
    public @NotNull Vec3 getRenderOffset(FriendlyPiglin entity, float partialTicks) {
        if (entity.isInSittingPose()) {
            return new Vec3(0.0, entity.isBaby() ? -0.2 : -0.4, 0.0);
        }
        return super.getRenderOffset(entity, partialTicks);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(FriendlyPiglin entity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((FriendlyPiglin) entity);
    }
}
