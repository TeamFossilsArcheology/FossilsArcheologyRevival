package com.fossil.fossil;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.client.DinopediaBioManager;
import com.fossil.fossil.enchantment.ModEnchantments;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.animation.GeoModelManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.data.EntityHitboxManager;
import com.fossil.fossil.event.ModEvents;
import com.fossil.fossil.inventory.ModMenus;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.loot.ModLootItemFunctionTypes;
import com.fossil.fossil.material.ModFluids;
import com.fossil.fossil.network.*;
import com.fossil.fossil.network.debug.*;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.villager.ModVillagers;
import com.fossil.fossil.world.dimension.ModDimensions;
import com.fossil.fossil.world.feature.ModFeatures;
import com.fossil.fossil.world.feature.structures.ModStructures;
import com.fossil.fossil.world.feature.village.ModVillages;
import com.mojang.logging.LogUtils;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;


public class Fossil {
    public static final String MOD_ID = "fossil";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        if (Minecraft.getInstance() != null) {
            ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, GeoModelManager.SKELETON_MODELS);
            ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, DinopediaBioManager.DINOPEDIA);
            ReloadListenerRegistry.register(PackType.SERVER_DATA, AnimationInfoManager.ANIMATIONS);
            ReloadListenerRegistry.register(PackType.SERVER_DATA, EntityDataManager.ENTITY_DATA);
            ReloadListenerRegistry.register(PackType.SERVER_DATA, EntityHitboxManager.HITBOX_DATA);
        }
        ModFluids.register(); //Before ModBlocks
        ModBlocks.register();
        ModEntities.register();
        ModSounds.register();
        ModItems.register();
        ModMenus.register();
        ModBlockEntities.register();
        ModFeatures.register();
        ModStructures.register();
        ModVillages.register();
        ModDimensions.register();
        ModLootItemFunctionTypes.register();
        ModRecipes.register();
        ModEnchantments.register();
        ModVillagers.register();
        ModEvents.init();

        MessageHandler.DEBUG_CHANNEL.register(C2SDisableAIMessage.class, C2SDisableAIMessage::write, C2SDisableAIMessage::new, C2SDisableAIMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SInstructionMessage.class, C2SInstructionMessage::write, C2SInstructionMessage::new, C2SInstructionMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SRotationMessage.class, C2SRotationMessage::write, C2SRotationMessage::new, C2SRotationMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SForceAnimationMessage.class, C2SForceAnimationMessage::write, C2SForceAnimationMessage::new, C2SForceAnimationMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2STameMessage.class, C2STameMessage::write, C2STameMessage::new, C2STameMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(S2CMarkMessage.class, S2CMarkMessage::write, S2CMarkMessage::new, S2CMarkMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SMoveMessage.class, C2SMoveMessage::write, C2SMoveMessage::new, C2SMoveMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(S2CVisionMessage.class, S2CVisionMessage::write, S2CVisionMessage::new, S2CVisionMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(S2CNewMarkMessage.class, S2CNewMarkMessage::write, S2CNewMarkMessage::new, S2CNewMarkMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SSyncDebugInfoMessage.class, C2SSyncDebugInfoMessage::write, C2SSyncDebugInfoMessage::new, C2SSyncDebugInfoMessage::apply);
        MessageHandler.CAP_CHANNEL.register(S2CMammalCapMessage.class, S2CMammalCapMessage::write, S2CMammalCapMessage::new, S2CMammalCapMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CSyncEntityInfoMessage.class, S2CSyncEntityInfoMessage::write, S2CSyncEntityInfoMessage::new, S2CSyncEntityInfoMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CSyncActiveAnimationMessage.class, S2CSyncActiveAnimationMessage::write, S2CSyncActiveAnimationMessage::new, S2CSyncActiveAnimationMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CSyncToyAnimationMessage.class, S2CSyncToyAnimationMessage::write, S2CSyncToyAnimationMessage::new, S2CSyncToyAnimationMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(C2SHitPlayerMessage.class, C2SHitPlayerMessage::write, C2SHitPlayerMessage::new, C2SHitPlayerMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CActivateAttackBoxesMessage.class, S2CActivateAttackBoxesMessage::write, S2CActivateAttackBoxesMessage::new, S2CActivateAttackBoxesMessage::apply);
    }
}
