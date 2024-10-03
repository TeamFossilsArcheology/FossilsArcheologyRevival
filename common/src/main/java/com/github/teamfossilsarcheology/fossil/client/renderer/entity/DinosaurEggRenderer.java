package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.client.model.DinosaurEggModel;
import com.github.teamfossilsarcheology.fossil.client.renderer.RendererFabricFix;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataManager;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.DinosaurEgg;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DinosaurEggRenderer extends LivingEntityRenderer<DinosaurEgg, DinosaurEggModel> implements RendererFabricFix {
    private static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();
    private static final ResourceLocation GOLDEN_EGG = Fossil.location("textures/entity/egg/golden.png");

    public DinosaurEggRenderer(EntityRendererProvider.Context context, DinosaurEggModel entityModel) {
        super(context, entityModel, 0.25f);
    }

    @Override
    protected void scale(DinosaurEgg egg, PoseStack poseStack, float partialTickTime) {
        float scale = EntityDataManager.ENTITY_DATA.getData(egg.getPrehistoricEntityInfo().resourceName).eggScale();
        shadowRadius = 0.25f * scale;
        poseStack.scale(scale, scale, scale);
        super.scale(egg, poseStack, partialTickTime);
    }

    @Override
    protected @Nullable RenderType getRenderType(DinosaurEgg egg, boolean bodyVisible, boolean translucent, boolean glowing) {
        if (bodyVisible) {
            if (egg.isGoldenEgg()) {
                return model.renderType(GOLDEN_EGG);
            }
            ResourceLocation resourceLocation = getTextureLocation(egg);
            return model.renderType(resourceLocation);
        }
        return null;
    }

    @Override
    protected boolean shouldShowName(DinosaurEgg entity) {
        return false;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((DinosaurEgg) entity);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DinosaurEgg entity) {

        return TEXTURES.computeIfAbsent(entity.getPrehistoricEntityInfo().resourceName,
                name -> {
                    ResourceLocation rl = Fossil.location("textures/entity/egg/egg_" + name + ".png");
                    //Calling getTexture twice is needed because the first call will not return the missing texture
                    Minecraft.getInstance().getTextureManager().getTexture(rl);
                    AbstractTexture tex = Minecraft.getInstance().getTextureManager().getTexture(rl);
                    if (tex == MissingTextureAtlasSprite.getTexture()) {
                        return Fossil.location("textures/entity/egg/fallback.png");
                    } else {
                        return rl;
                    }
                });
    }
}
