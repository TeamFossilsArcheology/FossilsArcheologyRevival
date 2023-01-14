package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import org.jetbrains.annotations.NotNull;

public class TarSlimeRenderer extends SlimeRenderer implements RendererFabricFix {
    private static final ResourceLocation TAR_SLIME_LOCATION = new ResourceLocation(Fossil.MOD_ID, "textures/entity/tar_slime.png");
    public TarSlimeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Slime entity) {
        return TAR_SLIME_LOCATION;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((Slime) entity);
    }
}
