package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.model.AnubiteModel;
import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.entity.monster.Anubite;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class AnubiteRenderer extends MobRenderer<Anubite, AnubiteModel> implements RendererFabricFix {
    public static final ResourceLocation TEXTURE = FossilMod.location("textures/entity/anubite.png");

    public AnubiteRenderer(EntityRendererProvider.Context context) {
        super(context, new AnubiteModel(), 0.3f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Anubite entity) {
        return FossilMod.location("textures/entity/anubite.png");
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((Anubite) entity);
    }
}
