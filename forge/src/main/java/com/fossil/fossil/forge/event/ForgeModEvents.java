package com.fossil.fossil.forge.event;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.capabilities.ModCapabilities;
import com.fossil.fossil.capabilities.forge.ModCapabilitiesImpl;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.event.ModEvents;
import com.fossil.fossil.forge.capabilities.mammal.MammalCapProvider;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.SyncEntityInfoMessage;
import com.fossil.fossil.villager.ModTrades;
import com.fossil.fossil.villager.ModVillagers;
import dev.architectury.platform.Platform;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
            int currentProgress = ModCapabilitiesImpl.getEmbryoProgress(animal);
            if (currentProgress == 0) {
                return;
            }
            if (currentProgress >= FossilConfig.getInt(FossilConfig.PREGNANCY_DURATION)) {
                if (!animal.level.isClientSide) {
                    ModEvents.growEntity(ModCapabilitiesImpl.getEmbryo(animal), animal);
                    ModCapabilities.stopPregnancy(animal);
                }
            } else {
                ModCapabilitiesImpl.setEmbryoProgress(animal, currentProgress + 1);
            }
        }
    }

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Animal animal && PrehistoricEntityType.isMammal(animal)) {
            MammalCapProvider mammalProvider = new MammalCapProvider();
            event.addListener(mammalProvider::invalidate);
            event.addCapability(MammalCapProvider.IDENTIFIER, mammalProvider);
        }
    }

    @SubscribeEvent
    public static void onDatapackSyncEvent(OnDatapackSyncEvent event) {
        if (Platform.getEnv() == Dist.DEDICATED_SERVER) {//TODO: How exactly does this work with LAN-Servers?
            MessageHandler.SYNC_CHANNEL.sendToPlayer(event.getPlayer(), new SyncEntityInfoMessage(EntityDataManager.ENTITY_DATA.getEntities()));
        }
    }
}
