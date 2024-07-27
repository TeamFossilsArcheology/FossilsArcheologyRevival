package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.ToyScratchingPostModel;
import com.fossil.fossil.entity.ToyScratchingPost;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

public class ToyScratchingPostRenderer extends ToyBaseRenderer<ToyScratchingPost> {

    public ToyScratchingPostRenderer(EntityRendererProvider.Context context, ToyScratchingPostModel entityModel) {
        super(context, entityModel, 0.4f);
    }

    @Override
    protected void setupRotations(PoseStack poseStack, float rotationYaw) {

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ToyScratchingPost entity) {
        if (!ToyScratchingPostModel.TEXTURES.containsKey(entity.getWoodTypeName())) {
            return ToyScratchingPostModel.TEXTURES.get(WoodType.OAK.name());
        }
        return ToyScratchingPostModel.TEXTURES.get(entity.getWoodTypeName());
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((ToyScratchingPost) entity);
    }
}
