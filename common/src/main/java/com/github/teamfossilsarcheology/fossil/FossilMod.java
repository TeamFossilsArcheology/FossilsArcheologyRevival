package com.github.teamfossilsarcheology.fossil;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.client.particle.ModParticles;
import com.github.teamfossilsarcheology.fossil.enchantment.ModEnchantments;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategoryLoader;
import com.github.teamfossilsarcheology.fossil.entity.animation.ServerAnimationInfoLoader;
import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.event.ModEvents;
import com.github.teamfossilsarcheology.fossil.inventory.ModMenus;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.loot.ModLootItemFunctionTypes;
import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.github.teamfossilsarcheology.fossil.network.*;
import com.github.teamfossilsarcheology.fossil.network.debug.*;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.villager.ModVillagers;
import com.github.teamfossilsarcheology.fossil.world.dimension.ModDimensions;
import com.github.teamfossilsarcheology.fossil.world.effect.ModEffects;
import com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures;
import com.github.teamfossilsarcheology.fossil.world.feature.structures.ModStructures;
import com.github.teamfossilsarcheology.fossil.world.feature.village.ModVillages;
import com.mojang.logging.LogUtils;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;


public class FossilMod {
    public static final String MOD_ID = "fossil";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        ReloadListenerRegistry.register(PackType.SERVER_DATA, ServerAnimationInfoLoader.INSTANCE);
        ReloadListenerRegistry.register(PackType.SERVER_DATA, EntityDataLoader.INSTANCE);
        ReloadListenerRegistry.register(PackType.SERVER_DATA, AnimationCategoryLoader.INSTANCE);
        ModFluids.register(); //Before ModBlocks
        ModBlocks.register();
        ModEntities.register(); //Before ModItems
        ModSounds.register();
        ModItems.register();
        ModMenus.register();
        ModBlockEntities.register();
        ModParticles.register();
        ModFeatures.register();
        ModStructures.register();
        ModVillages.register();
        ModDimensions.register();
        ModEffects.register();
        ModLootItemFunctionTypes.register();
        ModRecipes.register();
        ModEnchantments.register();
        ModVillagers.register();
        ModEvents.init();

        MessageHandler.DEBUG_CHANNEL.register(C2SDisableAIMessage.class, C2SDisableAIMessage::write, C2SDisableAIMessage::new, C2SDisableAIMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SDiscardMessage.class, C2SDiscardMessage::write, C2SDiscardMessage::new, C2SDiscardMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SSlowMessage.class, C2SSlowMessage::write, C2SSlowMessage::new, C2SSlowMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SForceAnimationMessage.class, C2SForceAnimationMessage::write, C2SForceAnimationMessage::new, C2SForceAnimationMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SRotationMessage.class, C2SRotationMessage::write, C2SRotationMessage::new, C2SRotationMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(SyncDebugInfoMessage.class, SyncDebugInfoMessage::write, SyncDebugInfoMessage::new, SyncDebugInfoMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2STameMessage.class, C2STameMessage::write, C2STameMessage::new, C2STameMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(C2SStructureMessage.class, C2SStructureMessage::write, C2SStructureMessage::new, C2SStructureMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(InstructionMessage.class, InstructionMessage::write, InstructionMessage::new, InstructionMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(S2CCancelAnimationMessage.class, S2CCancelAnimationMessage::write, S2CCancelAnimationMessage::new, S2CCancelAnimationMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(S2CMarkMessage.class, S2CMarkMessage::write, S2CMarkMessage::new, S2CMarkMessage::apply);

        MessageHandler.CAP_CHANNEL.register(S2CMammalCapMessage.class, S2CMammalCapMessage::write, S2CMammalCapMessage::new, S2CMammalCapMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CMusicMessage.class, S2CMusicMessage::write, S2CMusicMessage::new, S2CMusicMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CSyncEntityInfoMessage.class, S2CSyncEntityInfoMessage::write, S2CSyncEntityInfoMessage::new, S2CSyncEntityInfoMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CSyncActiveAnimationMessage.class, S2CSyncActiveAnimationMessage::write, S2CSyncActiveAnimationMessage::new, S2CSyncActiveAnimationMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CSyncToyAnimationMessage.class, S2CSyncToyAnimationMessage::write, S2CSyncToyAnimationMessage::new, S2CSyncToyAnimationMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(C2SHitPlayerMessage.class, C2SHitPlayerMessage::write, C2SHitPlayerMessage::new, C2SHitPlayerMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(C2SRiderForceFlyingMessage.class, C2SRiderForceFlyingMessage::write, C2SRiderForceFlyingMessage::new, C2SRiderForceFlyingMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(C2SVerticalFlightMessage.class, C2SVerticalFlightMessage::write, C2SVerticalFlightMessage::new, C2SVerticalFlightMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(S2CActivateAttackBoxesMessage.class, S2CActivateAttackBoxesMessage::write, S2CActivateAttackBoxesMessage::new, S2CActivateAttackBoxesMessage::apply);
    }
}
