package com.github.teamfossilsarcheology.fossil;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.client.particle.ModParticles;
import com.github.teamfossilsarcheology.fossil.enchantment.ModEnchantments;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfoLoader;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.variant.EntityVariantLoader;
import com.github.teamfossilsarcheology.fossil.entity.variant.VariantRegistry;
import com.github.teamfossilsarcheology.fossil.event.ModEvents;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.github.teamfossilsarcheology.fossil.food.FossilFoodMappings;
import com.github.teamfossilsarcheology.fossil.inventory.ModMenus;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.item.ModTabs;
import com.github.teamfossilsarcheology.fossil.loot.ModLootItemFunctionTypes;
import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.github.teamfossilsarcheology.fossil.network.*;
import com.github.teamfossilsarcheology.fossil.network.debug.*;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Version;
import com.github.teamfossilsarcheology.fossil.villager.ModVillagers;
import com.github.teamfossilsarcheology.fossil.world.dimension.ModDimensions;
import com.github.teamfossilsarcheology.fossil.world.effect.ModEffects;
import com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures;
import com.github.teamfossilsarcheology.fossil.world.feature.village.ModVillages;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FossilMod {
    public static final String MOD_ID = "fossil";
    public static final Logger LOGGER = LogManager.getLogger("Fossils Archeology");

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        ReloadListenerRegistry.register(PackType.SERVER_DATA, ServerAnimationInfoLoader.INSTANCE);
        ReloadListenerRegistry.register(PackType.SERVER_DATA, EntityDataLoader.INSTANCE);
        ReloadListenerRegistry.register(PackType.SERVER_DATA, FoodMappingsManager.INSTANCE);
        ReloadListenerRegistry.register(PackType.SERVER_DATA, EntityVariantLoader.INSTANCE);
        VariantRegistry.register();
        ModSounds.register();
        ModFluids.register(); //Before ModBlocks
        ModBlocks.register();
        ModParticles.register();
        ModEntities.register(); //Before ModItems
        ModItems.register();
        ModTabs.register();
        ModEnchantments.register();
        ModBlockEntities.register();
        ModMenus.register();
        ModRecipes.register();
        ModVillagers.register();
        ModFeatures.register();
        ModVillages.register();
        ModDimensions.register();
        ModEffects.register();
        ModLootItemFunctionTypes.register();
        ModEvents.init();
        FossilFoodMappings.register();
        MessageHandler.register();
    }

    public static void syncData(ServerPlayer player) {
        MessageHandler.SYNC_CHANNEL.sendToPlayer(player, new S2CSyncEntityInfoMessage(EntityDataLoader.INSTANCE.getEntities()));
        MessageHandler.SYNC_CHANNEL.sendToPlayer(player, FoodMappingsManager.INSTANCE.message());
        MessageHandler.SYNC_CHANNEL.sendToPlayer(player, new S2CSyncEntityVariantsMessage(EntityVariantLoader.INSTANCE.getVariants()));
        MessageHandler.SYNC_CHANNEL.sendToPlayer(player, new S2CSyncConfigMessage());
    }
}
