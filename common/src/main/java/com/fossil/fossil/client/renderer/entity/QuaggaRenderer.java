package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.prehistoric.Quagga;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class QuaggaRenderer extends AbstractHorseRenderer<Quagga, HorseModel<Quagga>> implements RendererFabricFix {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/quagga/quagga_saddled.png");

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
