package com.github.teamfossilsarcheology.fossil.fabric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.client.ClientInit;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingRenderer;
import com.github.teamfossilsarcheology.fossil.client.renderer.OverlayRenderer;
import com.github.teamfossilsarcheology.fossil.fabric.client.model.PlantModelProvider;
import com.github.teamfossilsarcheology.fossil.fabric.client.renderer.CustomItemRendererFabricImpl;
import com.github.teamfossilsarcheology.fossil.fabric.client.renderer.armor.FabricAncientHelmetRenderer;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.util.Version;
import com.github.teamfossilsarcheology.fossil.world.effect.ComfyBedEffect;
import com.github.teamfossilsarcheology.fossil.world.effect.ModEffects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.util.Optional;

public class FabricFossilModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientInit.immediate();
        ClientInit.later();
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(PlantModelProvider::new);
        GeoArmorRenderer.registerArmorRenderer(new FabricAncientHelmetRenderer(), ModItems.ANCIENT_HELMET.get());
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANU_STATUE.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANUBITE_STATUE.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANCIENT_CHEST.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.SARCOPHAGUS.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        ClientSpriteRegistryCallback.event(TextureAtlas.LOCATION_BLOCKS).register((atlasTexture, registry) -> {
            registry.register(FossilMod.location("block/tar_still"));
            registry.register(FossilMod.location("block/tar_flowing"));
        });
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            Minecraft mc = Minecraft.getInstance();
            OverlayRenderer.renderHelmet(mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
        });
        EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) -> {
            if (ComfyBedEffect.canApply(Optional.of(sleepingPos), player.level)) {
                return InteractionResult.sidedSuccess(player.level.isClientSide);
            }
            return InteractionResult.PASS;
        });
        EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
            if (ComfyBedEffect.canApply(Optional.of(sleepingPos), entity.level)) {
                entity.addEffect(new MobEffectInstance(ModEffects.COMFY_BED.get(), 24000, 0));
            }
        });
        if (Version.debugEnabled()) {
            HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
                PathingRenderer.renderOverlay(matrixStack);
            });
        }
    }
}
