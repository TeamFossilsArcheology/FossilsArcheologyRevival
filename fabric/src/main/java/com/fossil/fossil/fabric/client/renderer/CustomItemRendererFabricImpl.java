package com.fossil.fossil.fabric.client.renderer;

import com.fossil.fossil.client.renderer.CustomItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class CustomItemRendererFabricImpl implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static final CustomItemRendererFabricImpl INSTANCE = new CustomItemRendererFabricImpl();

    private CustomItemRendererFabricImpl() {
        super();
    }

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light,
                       int overlay) {
        CustomItemRenderer.INSTANCE.renderByItem(stack, mode, matrices, vertexConsumers, light, overlay);
    }
}
