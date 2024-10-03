package com.github.teamfossilsarcheology.fossil.forge.event;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.capabilities.forge.ModCapabilitiesImpl;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataManager;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.event.ModEvents;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.mammal.MammalCapProvider;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.player.FirstHatchCapProvider;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CMammalCapMessage;
import com.github.teamfossilsarcheology.fossil.network.S2CSyncEntityInfoMessage;
import com.github.teamfossilsarcheology.fossil.villager.ModTrades;
import com.github.teamfossilsarcheology.fossil.villager.ModVillagers;
import com.github.teamfossilsarcheology.fossil.world.effect.ComfyBedEffect;
import com.github.teamfossilsarcheology.fossil.world.effect.ModEffects;
import dev.architectury.platform.Platform;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Fossil.MOD_ID)
public class ForgeModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.ARCHEOLOGIST.get()) {
            var trades = event.getTrades();
            trades.get(1).addAll(ModTrades.getArcheoList(1));
            trades.get(2).addAll(ModTrades.getArcheoList(2));
            trades.get(3).addAll(ModTrades.getArcheoList(3));
            trades.get(4).addAll(ModTrades.getArcheoList(4));
            trades.get(5).addAll(ModTrades.getArcheoList(5));
        } else if (event.getType() == ModVillagers.PALEONTOLOGIST.get()) {
            var trades = event.getTrades();
            trades.get(1).addAll(ModTrades.getPaleoList(1));
            trades.get(2).addAll(ModTrades.getPaleoList(2));
            trades.get(3).addAll(ModTrades.getPaleoList(3));
            trades.get(4).addAll(ModTrades.getPaleoList(4));
            trades.get(5).addAll(ModTrades.getPaleoList(5));
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Animal animal) {
            int currentProgress = ModCapabilities.getEmbryoProgress(animal);
            if (currentProgress == 0) {
                return;
            }
            if (currentProgress >= FossilConfig.getInt(FossilConfig.PREGNANCY_DURATION)) {
                if (!animal.level.isClientSide) {
                    ModEvents.growEntity(ModCapabilities.getEmbryo(animal), animal);
                    ModCapabilities.stopPregnancy(animal);
                }
            } else {
                ModCapabilities.setEmbryoProgress(animal, currentProgress + 1);
            }
        }
    }

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Animal animal && PrehistoricEntityInfo.isMammal(animal)) {
            MammalCapProvider mammalProvider = new MammalCapProvider();
            event.addListener(mammalProvider::invalidate);
            event.addCapability(MammalCapProvider.IDENTIFIER, mammalProvider);
        } else if (event.getObject() instanceof Player) {
            FirstHatchCapProvider playerProvider = new FirstHatchCapProvider();
            event.addListener(playerProvider::invalidate);
            event.addCapability(FirstHatchCapProvider.IDENTIFIER, playerProvider);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        ModCapabilitiesImpl.getFirstHatchCap(event.getOriginal()).ifPresent(originalCap -> {
            ModCapabilitiesImpl.getFirstHatchCap(event.getPlayer()).ifPresent(newCap -> {
                newCap.setHatchedDinosaur(originalCap.hasHatchedDinosaur());
            });
        });
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        ServerPlayer serverPlayer = (ServerPlayer) event.getPlayer();
        if (event.getTarget() instanceof Animal animal) {
            ModCapabilitiesImpl.getMammalCap(animal).ifPresent(iMammalCap -> MessageHandler.CAP_CHANNEL.sendToPlayers(List.of(serverPlayer),
                    new S2CMammalCapMessage(animal, iMammalCap.getEmbryoProgress(), iMammalCap.getEmbryo())));
        }
    }

    @SubscribeEvent
    public static void allowDaySleep(PlayerSleepInBedEvent event) {
        if (ComfyBedEffect.canApply(event.getOptionalPos(), event.getPlayer().getLevel())) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void allowDaySleep(SleepingTimeCheckEvent event) {
        if (ComfyBedEffect.canApply(event.getSleepingLocation(), event.getPlayer().getLevel())) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void addComfyBedEffect(PlayerWakeUpEvent event) {
        if (ComfyBedEffect.canApply(event.getPlayer().getSleepingPos(), event.getPlayer().getLevel())) {
            event.getPlayer().addEffect(new MobEffectInstance(ModEffects.COMFY_BED.get(), 24000, 0));
        }
    }

    @SubscribeEvent
    public static void addComfyBedEffect(LivingEvent.LivingVisibilityEvent event) {
        if (event.getEntityLiving().hasEffect(ModEffects.COMFY_BED.get())) {
            event.modifyVisibility(0.5);
        }
    }

    @SubscribeEvent
    public static void onDatapackSyncEvent(OnDatapackSyncEvent event) {
        if (Platform.getEnv() == Dist.DEDICATED_SERVER) {
            MessageHandler.SYNC_CHANNEL.sendToPlayer(event.getPlayer(), new S2CSyncEntityInfoMessage(EntityDataManager.ENTITY_DATA.getEntities()));
        }
    }
}
