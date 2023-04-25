package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.AnuModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.Anu;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class AnuRenderer extends MobRenderer<Anu, AnuModel> implements RendererFabricFix {
    public AnuRenderer(EntityRendererProvider.Context context, AnuModel entityModel) {
        super(context, entityModel, 0.5f);
        addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Anu entity) {
        return AnuModel.TEXTURE;
    }

    @NotNull
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((Anu) entity);
    }

}