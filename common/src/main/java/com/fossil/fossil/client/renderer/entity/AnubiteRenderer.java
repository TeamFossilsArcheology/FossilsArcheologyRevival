package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.model.AnubiteModel;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.monster.Anubite;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class AnubiteRenderer extends MobRenderer<Anubite, AnubiteModel> implements RendererFabricFix {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/anubite.png");

    public AnubiteRenderer(EntityRendererProvider.Context context, AnubiteModel entityModel) {
        super(context, entityModel, 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Anubite entity) {
        return new ResourceLocation(Fossil.MOD_ID, "textures/entity/anubite.png");
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((Anubite) entity);
    }
}
