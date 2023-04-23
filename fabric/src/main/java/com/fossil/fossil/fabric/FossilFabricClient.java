package com.fossil.fossil.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.client.ClientInit;
import com.fossil.fossil.client.renderer.OverlayRenderer;
import com.fossil.fossil.fabric.client.renderer.CustomItemRendererFabricImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class FossilFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientInit.immediate();
        ClientInit.later();
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANU_STATUE.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANUBITE_STATUE.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANCIENT_CHEST.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.SARCOPHAGUS.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        ClientSpriteRegistryCallback.event(TextureAtlas.LOCATION_BLOCKS).register((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(Fossil.MOD_ID, "block/tar_still"));
            registry.register(new ResourceLocation(Fossil.MOD_ID, "block/tar_flowing"));
        });
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            Minecraft mc = Minecraft.getInstance();
            OverlayRenderer.renderHelmet(mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
        });

    }
}
