package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.client.model.AnuBossModel;
import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.client.renderer.entity.layers.AnuBossGlowLayer;
import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class AnuBossRenderer extends MobRenderer<AnuBoss, AnuBossModel> implements RendererFabricFix {
    public AnuBossRenderer(EntityRendererProvider.Context context) {
        super(context, new AnuBossModel(), 0.5f);
        addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        addLayer(new AnuBossGlowLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(AnuBoss entity) {
        return AnuBossModel.TEXTURE;
    }

    @NotNull
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((AnuBoss) entity);
    }

}