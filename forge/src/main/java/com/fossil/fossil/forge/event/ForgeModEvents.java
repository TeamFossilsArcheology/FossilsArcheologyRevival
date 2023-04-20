package com.fossil.fossil.forge.event;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.capabilities.ModCapabilities;
import com.fossil.fossil.capabilities.forge.ModCapabilitiesImpl;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.event.ModEvents;
import com.fossil.fossil.forge.capabilities.mammal.MammalCapProvider;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.villager.ModVillagers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fossil.MOD_ID)
public class ForgeModEvents {
    @SubscribeEvent
    public static void onKnockBack(LivingKnockBackEvent event) {
        double newStrength = Prehistoric.beforeKnockBack(
                event.getEntityLiving(),
                event.getStrength(),
                event.getRatioX(),
                event.getRatioZ()
        );
        event.setStrength((float) newStrength);
    }


    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.ARCHEOLOGIST.get()) {
            var trades = event.getTrades();
            var price = new ItemStack(Items.EMERALD, 1);
            trades.get(1).add((trader, random) -> new MerchantOffer(price, new ItemStack(ModItems.RELIC_SCRAP.get(), 1), 4, 12, 0.09f));
            trades.get(1).add((trader, random) -> new MerchantOffer(price, new ItemStack(ModBlocks.WORKTABLE.get(), 1), 4, 12, 0.09f));
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Animal animal) {
            int currentProgress = ModCapabilitiesImpl.getEmbryoProgress(animal);
            if (currentProgress == 0) {
                return;
            }
            if (currentProgress >= 10000) {
                if (!animal.level.isClientSide) {
                    ModEvents.growEntity(ModCapabilitiesImpl.getEmbryo(animal), animal);
                    ModCapabilities.stopEmbryo(animal);
                }
            } else {
                ModCapabilitiesImpl.setEmbryoProgress(animal, currentProgress+1);
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
}
