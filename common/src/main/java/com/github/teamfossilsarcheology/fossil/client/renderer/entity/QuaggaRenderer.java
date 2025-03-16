package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.model.QuaggaModel;
import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.entity.Quagga;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class QuaggaRenderer extends AbstractHorseRenderer<Quagga, HorseModel<Quagga>> implements RendererFabricFix {
    private static final ResourceLocation TEXTURE = FossilMod.location("textures/entity/quagga/quagga_saddled.png");

    public QuaggaRenderer(EntityRendererProvider.Context context) {
        super(context, new QuaggaModel<>(), 1.1f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Quagga entity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((Quagga) entity);
    }
}
