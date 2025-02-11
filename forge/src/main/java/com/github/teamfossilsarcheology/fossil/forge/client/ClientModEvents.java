package com.github.teamfossilsarcheology.fossil.forge.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingRenderer;
import com.github.teamfossilsarcheology.fossil.client.renderer.OverlayRenderer;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FossilMod.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void tarOverlay(RenderBlockOverlayEvent event) {
        if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK && event.getBlockState().is(ModBlocks.TAR.get())) {
            event.setCanceled(true);
            OverlayRenderer.renderTar(event.getPoseStack());
        }
    }

    public static void registerOverlays() {
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HELMET_ELEMENT, "fossil_helmets",
                (gui, poseStack, partialTick, screenWidth, screenHeight) -> OverlayRenderer.renderHelmet(screenWidth, screenHeight));
        if (Version.debugEnabled()) {
            OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT, "debug_overlay",
                    (gui, poseStack, partialTick, screenWidth, screenHeight) -> PathingRenderer.renderOverlay(poseStack));
        }
    }
}
