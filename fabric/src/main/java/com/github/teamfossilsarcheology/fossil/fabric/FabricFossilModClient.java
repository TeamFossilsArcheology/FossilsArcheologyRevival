package com.github.teamfossilsarcheology.fossil.fabric;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.client.ClientInit;
import com.github.teamfossilsarcheology.fossil.fabric.client.model.PlantModelPlugin;
import com.github.teamfossilsarcheology.fossil.fabric.client.renderer.CustomItemRendererFabricImpl;
import com.github.teamfossilsarcheology.fossil.world.effect.ComfyBedEffect;
import com.github.teamfossilsarcheology.fossil.world.effect.ModEffects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Optional;

public class FabricFossilModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientInit.immediate();
        ClientInit.later();
        PreparableModelLoadingPlugin.register(new PlantModelPlugin.Loader(), new PlantModelPlugin());
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANU_STATUE.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANUBITE_STATUE.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.ANCIENT_CHEST.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.SARCOPHAGUS.get().asItem(), CustomItemRendererFabricImpl.INSTANCE);
        EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) -> {
            if (ComfyBedEffect.canApply(Optional.of(sleepingPos), player.level())) {
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
            return InteractionResult.PASS;
        });
        EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
            if (ComfyBedEffect.canApply(Optional.of(sleepingPos), entity.level())) {
                entity.addEffect(new MobEffectInstance(ModEffects.COMFY_BED.get(), 24000, 0));
            }
        });
    }
}
