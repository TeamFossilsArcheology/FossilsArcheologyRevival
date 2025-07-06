package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.model.SentryPiglinModel;
import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.entity.monster.SentryPiglin;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class SentryPiglinRenderer extends HumanoidMobRenderer<SentryPiglin, SentryPiglinModel> implements RendererFabricFix {
    public static final ResourceLocation TEXTURE = FossilMod.location("textures/entity/sentry_piglin.png");

    public SentryPiglinRenderer(EntityRendererProvider.Context context) {
        super(context, new SentryPiglinModel(), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PIGLIN_BRUTE_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PIGLIN_BRUTE_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SentryPiglin entity) {
        return FossilMod.location("textures/entity/sentry_piglin.png");
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((SentryPiglin) entity);
    }
}