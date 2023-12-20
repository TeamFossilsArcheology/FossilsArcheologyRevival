package com.fossil.fossil;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.enchantment.ModEnchantments;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.animation.AnimationInfoManager;
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
import com.fossil.fossil.util.DisposableTask;
import com.fossil.fossil.villager.ModVillagers;
import com.fossil.fossil.world.dimension.ModDimensions;
import com.fossil.fossil.world.feature.ModFeatures;
import com.fossil.fossil.world.feature.structures.ModStructures;
import com.fossil.fossil.world.feature.village.ModVillages;
import com.mojang.logging.LogUtils;
import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.timers.TimerCallbacks;
import org.slf4j.Logger;


public class Fossil {
    public static final String MOD_ID = "fossil";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ReloadListenerRegistry.register(PackType.SERVER_DATA, AnimationInfoManager.ANIMATIONS);
        ReloadListenerRegistry.register(PackType.SERVER_DATA, EntityDataManager.ENTITY_DATA);
        ReloadListenerRegistry.register(PackType.SERVER_DATA, EntityHitboxManager.HITBOX_DATA);
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

        MessageHandler.DEBUG_CHANNEL.register(AIMessage.class, AIMessage::write, AIMessage::new, AIMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(RotationMessage.class, RotationMessage::write, RotationMessage::new, RotationMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(AnimationMessage.class, AnimationMessage::write, AnimationMessage::new, AnimationMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(MovementMessage.class, MovementMessage::write, MovementMessage::new, MovementMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(MarkMessage.class, MarkMessage::write, MarkMessage::new, MarkMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(VisionMessage.class, VisionMessage::write, VisionMessage::new, VisionMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(NewMarkMessage.class, NewMarkMessage::write, NewMarkMessage::new, NewMarkMessage::apply);
        MessageHandler.DEBUG_CHANNEL.register(SyncDebugInfoMessage.class, SyncDebugInfoMessage::write, SyncDebugInfoMessage::new, SyncDebugInfoMessage::apply);
        MessageHandler.CAP_CHANNEL.register(MammalCapMessage.class, MammalCapMessage::write, MammalCapMessage::new, MammalCapMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(SyncEntityInfoMessage.class, SyncEntityInfoMessage::write, SyncEntityInfoMessage::new, SyncEntityInfoMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(SyncActiveAnimationMessage.class, SyncActiveAnimationMessage::write, SyncActiveAnimationMessage::new, SyncActiveAnimationMessage::apply);
        MessageHandler.SYNC_CHANNEL.register(SyncToyAnimationMessage.class, SyncToyAnimationMessage::write, SyncToyAnimationMessage::new, SyncToyAnimationMessage::apply);

        TimerCallbacks.SERVER_CALLBACKS.register(new DisposableTask.Serializer());
    }
}
