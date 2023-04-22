package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.renderer.RendererFabricFix;
import com.fossil.fossil.entity.Javelin;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Tiers;
import org.jetbrains.annotations.NotNull;

public class JavelinRenderer extends ArrowRenderer<Javelin> implements RendererFabricFix {
    private static final ResourceLocation WOODEN_JAVELIN = new ResourceLocation(Fossil.MOD_ID, "textures/entity/wooden_javelin.png");
    private static final ResourceLocation STONE_JAVELIN = new ResourceLocation(Fossil.MOD_ID, "textures/entity/stone_javelin.png");
    private static final ResourceLocation IRON_JAVELIN = new ResourceLocation(Fossil.MOD_ID, "textures/entity/iron_javelin.png");
    private static final ResourceLocation GOLD_JAVELIN = new ResourceLocation(Fossil.MOD_ID, "textures/entity/gold_javelin.png");
    private static final ResourceLocation DIAMOND_JAVELIN = new ResourceLocation(Fossil.MOD_ID, "textures/entity/diamond_javelin.png");
    private static final ResourceLocation ANCIENT_JAVELIN = new ResourceLocation(Fossil.MOD_ID, "textures/entity/ancient_javelin.png");

    public JavelinRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Javelin entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(2, 2, 2);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Javelin entity) {
        if (entity.isAncient()) {
            return ANCIENT_JAVELIN;
        } else if (entity.getTier() instanceof Tiers tiers) {
            switch (tiers) {
                case WOOD -> {
                    return WOODEN_JAVELIN;
                }
                case STONE -> {
                    return STONE_JAVELIN;
                }
                case GOLD -> {
                    return GOLD_JAVELIN;
                }
                case IRON -> {
                    return IRON_JAVELIN;
                }
                case DIAMOND -> {
                    return DIAMOND_JAVELIN;
                }
            }
        }
        return ANCIENT_JAVELIN;
    }

    @Override
    public ResourceLocation _getTextureLocation(Entity entity) {
        return getTextureLocation((Javelin) entity);
    }
}
