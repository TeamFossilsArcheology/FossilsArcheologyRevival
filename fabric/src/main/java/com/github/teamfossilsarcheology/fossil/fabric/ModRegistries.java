package com.github.teamfossilsarcheology.fossil.fabric;

import com.github.teamfossilsarcheology.fossil.villager.ModTrades;
import com.github.teamfossilsarcheology.fossil.villager.ModVillagers;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;

public class ModRegistries {

    public static void register() {
        registerCustomTrades();
    }

    private static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST.get(), 1, itemListings -> itemListings.addAll(ModTrades.getArcheoList(1)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST.get(), 2, itemListings -> itemListings.addAll(ModTrades.getArcheoList(2)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST.get(), 3, itemListings -> itemListings.addAll(ModTrades.getArcheoList(3)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST.get(), 4, itemListings -> itemListings.addAll(ModTrades.getArcheoList(4)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST.get(), 5, itemListings -> itemListings.addAll(ModTrades.getArcheoList(5)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PALEONTOLOGIST.get(), 1, itemListings -> itemListings.addAll(ModTrades.getPaleoList(1)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PALEONTOLOGIST.get(), 2, itemListings -> itemListings.addAll(ModTrades.getPaleoList(2)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PALEONTOLOGIST.get(), 3, itemListings -> itemListings.addAll(ModTrades.getPaleoList(3)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PALEONTOLOGIST.get(), 4, itemListings -> itemListings.addAll(ModTrades.getPaleoList(4)));
        TradeOfferHelper.registerVillagerOffers(ModVillagers.PALEONTOLOGIST.get(), 5, itemListings -> itemListings.addAll(ModTrades.getPaleoList(5)));
    }
}
