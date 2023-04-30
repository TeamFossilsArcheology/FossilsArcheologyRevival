package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.model.SentryPiglinModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.monster.SentryPiglin;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class SentryPiglinRenderer extends HumanoidMobRenderer<SentryPiglin, SentryPiglinModel> implements RendererFabricFix {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/sentry_piglin.png");

    public SentryPiglinRenderer(EntityRendererProvider.Context context, SentryPiglinModel model) {
        super(context, model, 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PIGLIN_BRUTE_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PIGLIN_BRUTE_OUTER_ARMOR))));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SentryPiglin entity) {
        return new ResourceLocation(Fossil.MOD_ID, "textures/entity/sentry_piglin.png");
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((SentryPiglin) entity);
    }
}