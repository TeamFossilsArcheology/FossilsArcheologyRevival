package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.entity.Quagga;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class QuaggaRenderer extends AbstractHorseRenderer<Quagga, HorseModel<Quagga>> implements RendererFabricFix {
    private static final ResourceLocation TEXTURE = Fossil.location("textures/entity/quagga/quagga_saddled.png");

    public QuaggaRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel<>(LayerDefinition.create(HorseModel.createBodyMesh(CubeDeformation.NONE), 64, 64).bakeRoot()), 1.1f);
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
