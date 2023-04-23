package com.fossil.fossil.client.renderer;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.item.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class OverlayRenderer {
    private static final ResourceLocation ANCIENT_HELMET = new ResourceLocation(Fossil.MOD_ID, "textures/gui/ancient_helmet_blur.png");
    private static final ResourceLocation BONE_HELMET = new ResourceLocation(Fossil.MOD_ID, "textures/gui/bone_helmet_blur.png");
    public static void renderHelmet(int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.getCameraType().isFirstPerson()) {
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
}
