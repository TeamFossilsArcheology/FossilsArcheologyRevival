package com.fossil.fossil.forge.event;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.villager.ModVillagers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fossil.MOD_ID)
public class ModEvents {
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
}
