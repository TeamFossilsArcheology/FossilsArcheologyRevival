package com.fossil.fossil.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.client.ClientInit;
import com.fossil.fossil.client.gui.debug.PathingScreen;
import com.fossil.fossil.client.gui.debug.navigation.*;
import com.fossil.fossil.client.renderer.OverlayRenderer;
import com.fossil.fossil.client.renderer.armor.AncientHelmetRenderer;
import com.fossil.fossil.fabric.client.model.PlantModelProvider;
import com.fossil.fossil.fabric.client.renderer.CustomItemRendererFabricImpl;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.util.Version;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class FossilFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientInit.immediate();
        ClientInit.later();
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(PlantModelProvider::new);
        GeoArmorRenderer.registerArmorRenderer(new AncientHelmetRenderer(), ModItems.ANCIENT_HELMET.get());
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
        if (Version.debugEnabled()) {
            HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
                PathingRenderer.renderOverlay(matrixStack);
            });
            ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(player -> {
                PathingDebug.pathNavigation1 = new PlayerPathNavigation(player, Minecraft.getInstance().level, "Base");
                PathingDebug.pathNavigation3 = new DebugCenteredPathNavigation(player, Minecraft.getInstance().level);
                PathingDebug.pathNavigation4 = new SweepPathNavigation(player, Minecraft.getInstance().level);
                PathingDebug.pathNavigation5 = new WaterPathNavigation(player, Minecraft.getInstance().level);
            });
            ClientTickEvent.CLIENT_POST.register(minecraft -> {
                if (Minecraft.getInstance().isPaused()) return;
                while (ClientInit.DEBUG_HELP_KEY.consumeClick()) {
                    PathingDebug.showHelpMenu = !PathingDebug.showHelpMenu;
                }
                while (ClientInit.PATHING_SCREEN_KEY.consumeClick()) {
                    if (PathingDebug.showHelpMenu) {
                        minecraft.setScreen(new PathingScreen());
                    }
                }
                while (ClientInit.DEBUG_REPATH_KEY.consumeClick()) {
                    if (PathingDebug.showHelpMenu) {
                        PathingDebug.rePath();
                    }
                }
                while (ClientInit.DEBUG_ADVANCE_KEY.consumeClick()) {
                    if (PathingDebug.showHelpMenu) {
                        PathingRenderer.advanceIndex();
                    }
                }
                while (ClientInit.DEBUG_REVERSE_KEY.consumeClick()) {
                    if (PathingDebug.showHelpMenu) {
                        PathingRenderer.reverseIndex();
                    }
                }
                PathingDebug.tick();
            });
        }
    }
}
