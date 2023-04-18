package com.fossil.fossil.fabric;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.villager.ModVillagers;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

public class ModRegistries {

    public static void register() {
        registerCustomTrades();
    }

    private static void registerCustomTrades() {
        var price = new ItemStack(Items.EMERALD, 1);
        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST.get(), 1, itemListings -> {
            itemListings.add((trader, random) -> new MerchantOffer(price, new ItemStack(ModItems.RELIC_SCRAP.get(), 1), 4, 12, 0.09f));
            itemListings.add((trader, random) -> new MerchantOffer(price, new ItemStack(ModBlocks.WORKTABLE.get(), 1), 4, 12, 0.09f));
        });
    }
}
