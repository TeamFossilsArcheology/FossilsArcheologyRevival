package com.github.teamfossilsarcheology.fossil.forge.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingRenderer;
import com.github.teamfossilsarcheology.fossil.client.renderer.OverlayRenderer;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FossilMod.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void tarOverlay(RenderBlockScreenEffectEvent event) {
        if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.BLOCK && event.getBlockState().is(ModBlocks.TAR.get())) {
            event.setCanceled(true);
            OverlayRenderer.renderTar(event.getPoseStack());
        }
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HELMET.id(), "fossil_helmets", (gui, poseStack, partialTick, screenWidth, screenHeight) ->
                OverlayRenderer.renderHelmet(screenWidth, screenHeight));
    }
}
