package com.fossil.fossil.client.renderer;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.item.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class OverlayRenderer {
    private static final ResourceLocation ANCIENT_HELMET = new ResourceLocation(Fossil.MOD_ID, "textures/gui/ancient_helmet_blur.png");
    private static final ResourceLocation BONE_HELMET = new ResourceLocation(Fossil.MOD_ID, "textures/gui/bone_helmet_blur.png");
    private static final ResourceLocation TAR = new ResourceLocation(Fossil.MOD_ID, "textures/block/tar_still.png");
    private static final TagKey<Fluid> TAR_FLUID = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(Fossil.MOD_ID, "tar"));
    public static void renderHelmet(int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        if (FossilConfig.isEnabled("helmetOverlays") && mc.options.getCameraType().isFirstPerson()) {
            ItemStack helmet = mc.player.getItemBySlot(EquipmentSlot.HEAD);
            boolean ancient = helmet.is(ModItems.ANCIENT_HELMET.get());
            if (ancient || helmet.is(ModItems.BONE_HELMET.get())) {
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1);
                RenderSystem.setShaderTexture(0, ancient ? ANCIENT_HELMET : BONE_HELMET);
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder bufferbuilder = tesselator.getBuilder();
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(0.0, screenHeight, -90.0).uv(0.0f, 1.0f).endVertex();
                bufferbuilder.vertex(screenWidth, screenHeight, -90.0).uv(1.0f, 1.0f).endVertex();
                bufferbuilder.vertex(screenWidth, 0.0, -90.0).uv(1.0f, 0.0f).endVertex();
                bufferbuilder.vertex(0.0, 0.0, -90.0).uv(0.0f, 0.0f).endVertex();
                tesselator.end();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    public static void renderTar(PoseStack poseStack) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.isEyeInFluid(TAR_FLUID)) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TAR);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.8f);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            Matrix4f matrix4f = poseStack.last().pose();
            vertex(bufferbuilder, matrix4f, -1, -1, -0.5f).uv(0, 1).endVertex();
            vertex(bufferbuilder, matrix4f, 1, -1, -0.5f).uv(1, 1).endVertex();
            vertex(bufferbuilder, matrix4f, 1, 1, -0.5f).uv(1, 0).endVertex();
            vertex(bufferbuilder, matrix4f, -1, 1, -0.5f).uv(0, 0).endVertex();
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private static VertexConsumer vertex(BufferBuilder bufferBuilder, Matrix4f matrix4f, float x, float y, float z) {
        Vector4f vector4f = new Vector4f(x, y, z, 1);
        vector4f.transform(matrix4f);
        return bufferBuilder.vertex(vector4f.x(), vector4f.y(), vector4f.z());
    }
}
