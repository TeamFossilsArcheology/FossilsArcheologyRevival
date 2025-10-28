package com.github.teamfossilsarcheology.fossil.client;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.VaseBlock;
import com.github.teamfossilsarcheology.fossil.block.entity.ModBlockEntities;
import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.client.gui.*;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.DebugScreen;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.PathingScreen;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.*;
import com.github.teamfossilsarcheology.fossil.client.gui.filters.CreativeTabFilters;
import com.github.teamfossilsarcheology.fossil.client.particle.*;
import com.github.teamfossilsarcheology.fossil.client.renderer.OverlayRenderer;
import com.github.teamfossilsarcheology.fossil.client.renderer.blockentity.*;
import com.github.teamfossilsarcheology.fossil.client.renderer.entity.*;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.Quagga;
import com.github.teamfossilsarcheology.fossil.entity.animation.ClientAnimationInfoLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.*;
import com.github.teamfossilsarcheology.fossil.inventory.ModMenus;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.network.C2SRiderForceFlyingMessage;
import com.github.teamfossilsarcheology.fossil.network.C2SVerticalFlightMessage;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.util.Version;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.FoliageColor;

import java.util.function.Function;

public class ClientInit {
    public static KeyMapping debugScreenKey;
    public static KeyMapping pathingScreenKey;
    public static KeyMapping debugRepathKey;
    public static KeyMapping debugAdvanceKey;
    public static KeyMapping debugReverseKey;
    public static KeyMapping debugHelpKey;

    public static final KeyMapping flyUpKey = new KeyMapping("key.fossil.fly_up", InputConstants.Type.KEYSYM, InputConstants.KEY_SPACE,
            "category.fossil.controls");
    public static final KeyMapping flyDownKey = new KeyMapping("key.fossil.fly_down", InputConstants.Type.KEYSYM, InputConstants.KEY_LALT,
            "category.fossil.controls");

    private static boolean jumpLastTick;
    private static int jumpTriggerTime;


    public static void immediate() {
        if (Version.debugEnabled()) {
            debugScreenKey = new KeyMapping("key.fossil.debug_screen", InputConstants.Type.KEYSYM, InputConstants.KEY_Y,
                    "category.fossil.debug");
            pathingScreenKey = new KeyMapping("key.fossil.pathing_screen", InputConstants.Type.KEYSYM, InputConstants.KEY_R,
                    "category.fossil.debug");
            debugRepathKey = new KeyMapping("key.fossil.debug_repath", InputConstants.Type.KEYSYM, InputConstants.KEY_V,
                    "category.fossil.debug");
            debugAdvanceKey = new KeyMapping("key.fossil.debug_advance", InputConstants.Type.KEYSYM, InputConstants.KEY_X,
                    "category.fossil.debug");
            debugReverseKey = new KeyMapping("key.fossil.debug_reverse", InputConstants.Type.KEYSYM, InputConstants.KEY_C,
                    "category.fossil.debug");
            debugHelpKey = new KeyMapping("key.fossil.debug_help", InputConstants.Type.KEYSYM, InputConstants.KEY_B,
                    "category.fossil.debug");
            KeyMappingRegistry.register(debugScreenKey);
            KeyMappingRegistry.register(pathingScreenKey);
            KeyMappingRegistry.register(debugRepathKey);
            KeyMappingRegistry.register(debugAdvanceKey);
            KeyMappingRegistry.register(debugReverseKey);
            KeyMappingRegistry.register(debugHelpKey);
        }
        if (Minecraft.getInstance() != null) {
            ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, SkeletonGeoModelLoader.INSTANCE);
            ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, ClientAnimationInfoLoader.INSTANCE);
            ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, DinopediaBioLoader.INSTANCE);
            ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, OptionalTextureLoader.INSTANCE);
        }
        registerEntityRenderers();
        ParticleProviderRegistry.register(ModParticles.VOLCANO_VENT_ASH, VolcanoVentAshParticle.Provider::new);
        ParticleProviderRegistry.register(ModParticles.VOLCANO_VENT_ASH_EMITTER, new VolcanoVentAshEmitterParticle.Provider());
        ParticleProviderRegistry.register(ModParticles.BUBBLE, BubbleParticle.Provider::new);
        ParticleProviderRegistry.register(ModParticles.TAR_BUBBLE, TarBubbleParticle.Provider::new);
        ParticleProviderRegistry.register(ModParticles.LASER_PARTICLE, LaserParticle.Provider::new);
        KeyMappingRegistry.register(flyUpKey);
        KeyMappingRegistry.register(flyDownKey);
    }

    public static void later() {
        if (Version.debugEnabled()) {
            ClientTickEvent.CLIENT_POST.register(minecraft -> {
                if (Minecraft.getInstance().isPaused()) return;
                while (ClientInit.debugHelpKey.consumeClick()) {
                    PathingDebug.showHelpMenu = !PathingDebug.showHelpMenu;
                }
                while (ClientInit.pathingScreenKey.consumeClick()) {
                    if (PathingDebug.showHelpMenu) {
                        minecraft.setScreen(new PathingScreen());
                    }
                }
                while (ClientInit.debugRepathKey.consumeClick()) {
                    if (PathingDebug.showHelpMenu) {
                        PathingDebug.rePath();
                    }
                }
                while (ClientInit.debugAdvanceKey.consumeClick()) {
                    if (PathingDebug.showHelpMenu) {
                        PathingRenderer.advanceIndex();
                    }
                }
                while (ClientInit.debugReverseKey.consumeClick()) {
                    if (PathingDebug.showHelpMenu) {
                        PathingRenderer.reverseIndex();
                    }
                }
                PathingDebug.tick();
                while (debugScreenKey.consumeClick()) {
                    Entity entity = DebugScreen.getHitResult(minecraft);
                    minecraft.setScreen(new DebugScreen(entity == null ? DebugScreen.entity : entity));
                }
            });
            ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(player -> {
                PathingDebug.pathNavigation1 = new PlayerPathNavigation(player, Minecraft.getInstance().level, "Base");
                PathingDebug.pathNavigation3 = new DebugCenteredPathNavigation(player, Minecraft.getInstance().level);
                PathingDebug.pathNavigation4 = new SweepPathNavigation(player, Minecraft.getInstance().level);
                PathingDebug.pathNavigation5 = new WaterPathNavigation(player, Minecraft.getInstance().level);
            });
            EntityEvent.ADD.register((entity, world) -> {
                if (entity == Minecraft.getInstance().player) {
                    ((Player) entity).displayClientMessage(Component.literal("You're running a development build of F/A: Revival").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), false);
                }
                return EventResult.pass();
            });
            ClientGuiEvent.RENDER_HUD.register((poseStack, v) -> PathingRenderer.renderOverlay(poseStack));
        }
        if (Version.isAlpha()) {
            ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(localPlayer -> {
                localPlayer.sendSystemMessage(Component.translatable("chat.fossil.alpha").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
            });
        }
        registerBlockRenderers();
        registerEventHandlers();
        registerItemProperties();
        MenuScreens.register(ModMenus.FEEDER.get(), FeederScreen::new);
        MenuScreens.register(ModMenus.SIFTER.get(), SifterScreen::new);
        MenuScreens.register(ModMenus.CULTURE_VAT.get(), CultureVatScreen::new);
        MenuScreens.register(ModMenus.ANALYZER.get(), AnalyzerScreen::new);
        MenuScreens.register(ModMenus.WORKTABLE.get(), WorktableScreen::new);
        CreativeTabFilters.register();
    }

    private static void registerItemProperties() {
        ItemPropertiesRegistry.register(ModItems.LASER_POINTER.get(),
                new ResourceLocation("using"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
    }

    private static void registerEntityRenderers() {
        registerFish(ModEntities.ALLIGATOR_GAR, "alligator_gar");
        registerDino(ModEntities.ALLOSAURUS, "allosaurus", RenderType::entityCutout);
        registerDino(ModEntities.ANKYLOSAURUS, "ankylosaurus", RenderType::entityCutout);
        registerDino(ModEntities.AQUILOLAMNA, "aquilolamna", RenderType::entityCutout);
        registerDino(ModEntities.ARTHROPLEURA, "arthropleura");
        registerDino(ModEntities.BRACHIOSAURUS, "brachiosaurus", RenderType::entityCutout);
        registerDino(ModEntities.CERATOSAURUS, "ceratosaurus", RenderType::entityCutout);
        registerDino(ModEntities.CITIPATI, "citipati");
        registerFish(ModEntities.COELACANTH, "coelacanth");
        registerDino(ModEntities.COMPSOGNATHUS, "compsognathus");
        registerDino(ModEntities.CONFUCIUSORNIS, "confuciusornis");
        registerDino(ModEntities.CRASSIGYRINUS, "crassigyrinus");
        registerDino(ModEntities.DEINONYCHUS, "deinonychus", RenderType::entityCutout);
        registerDino(ModEntities.DILOPHOSAURUS, "dilophosaurus", RenderType::entityCutout);
        registerDino(ModEntities.DIMETRODON, "dimetrodon", RenderType::entityCutout);
        registerDino(ModEntities.DIMORPHODON, "dimorphodon");
        registerDino(ModEntities.DIPLOCAULUS, "diplocaulus");
        registerDino(ModEntities.DIPLODOCUS, "diplodocus", RenderType::entityCutout);
        registerDino(ModEntities.DODO, "dodo");
        registerDino(ModEntities.DRYOSAURUS, "dryosaurus", RenderType::entityCutout);
        registerDino(ModEntities.EDAPHOSAURUS, "edaphosaurus", RenderType::entityCutout);
        registerDino(ModEntities.ELASMOTHERIUM, "elasmotherium");
        registerDino(ModEntities.GALLIMIMUS, "gallimimus");
        registerDino(ModEntities.GASTORNIS, "gastornis");
        registerDino(ModEntities.HENODUS, "henodus");
        registerDino(ModEntities.ICHTHYOSAURUS, "ichthyosaurus");
        registerDino(ModEntities.KELENKEN, "kelenken");
        registerDino(ModEntities.LIOPLEURODON, "liopleurodon");
        registerDino(ModEntities.MAMMOTH, "mammoth");
        registerDino(ModEntities.MEGALANIA, "megalania");
        registerDino(ModEntities.MEGALOCEROS, "megaloceros");
        registerDino(ModEntities.MEGALODON, "megalodon");
        registerDino(ModEntities.MEGALOGRAPTUS, "megalograptus");
        EntityRendererRegistry.register(ModEntities.MEGANEURA, MeganeuraRenderer::new);
        registerDino(ModEntities.MOSASAURUS, "mosasaurus", RenderType::entityCutout);
        registerFish(ModEntities.NAUTILUS, "nautilus");
        registerDino(ModEntities.ORNITHOLESTES, "ornitholestes");
        registerDino(ModEntities.PACHYCEPHALOSAURUS, "pachycephalosaurus", RenderType::entityCutout);
        registerDino(ModEntities.PACHYRHINOSAURUS, "pachyrhinosaurus");
        registerDino(ModEntities.PARASAUROLOPHUS, "parasaurolophus", RenderType::entityCutout);
        registerDino(ModEntities.PHORUSRHACOS, "phorusrhacos");
        registerDino(ModEntities.PLATYBELODON, "platybelodon");
        registerDino(ModEntities.PLESIOSAURUS, "plesiosaurus", RenderType::entityCutout);
        registerDino(ModEntities.POSTOSUCHUS, "postosuchus");
        registerDino(ModEntities.PROTOCERATOPS, "protoceratops", RenderType::entityCutout);
        registerDino(ModEntities.PSITTACOSAURUS, "psittacosaurus");
        registerDino(ModEntities.PTERANODON, "pteranodon");
        EntityRendererRegistry.register(ModEntities.QUAGGA, QuaggaRenderer::new);
        registerDino(ModEntities.QUETZALCOATLUS, "quetzalcoatlus");
        registerDino(ModEntities.SARCOSUCHUS, "sarcosuchus");
        registerDino(ModEntities.SMILODON, "smilodon");
        registerDino(ModEntities.SPINOSAURUS, "spinosaurus");
        registerDino(ModEntities.STEGOSAURUS, "stegosaurus", RenderType::entityCutout);
        registerFish(ModEntities.STURGEON, "sturgeon");
        registerDino(ModEntities.THERIZINOSAURUS, "therizinosaurus");
        registerDino(ModEntities.TIKTAALIK, "tiktaalik");
        registerDino(ModEntities.TITANIS, "titanis");
        registerDino(ModEntities.TRICERATOPS, "triceratops", RenderType::entityCutout);
        registerDino(ModEntities.TYRANNOSAURUS, "tyrannosaurus", RenderType::entityCutout);
        registerDino(ModEntities.VELOCIRAPTOR, "velociraptor", RenderType::entityCutout);

        registerTrilobite(ModEntities.DICRANURUS);
        registerTrilobite(ModEntities.LONCHODOMAS);
        registerTrilobite(ModEntities.SCOTOHARPES);
        registerTrilobite(ModEntities.WALLISEROPS);
        EntityRendererRegistry.register(ModEntities.DINOSAUR_EGG, DinosaurEggRenderer::new);
        EntityRendererRegistry.register(ModEntities.THROWN_BIRD_EGG, ThrownItemRenderer::new);

        EntityRendererRegistry.register(ModEntities.LASER_POINT, LaserPointRenderer::new);

        EntityRendererRegistry.register(ModEntities.ANUBITE, AnubiteRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANU_BOSS, AnuBossRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANU_DEAD, AnuDeadRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANU_TOTEM, AnuTotemRenderer::new);
        EntityRendererRegistry.register(ModEntities.FAILURESAURUS, FailuresaurusRenderer::new);
        EntityRendererRegistry.register(ModEntities.SENTRY_PIGLIN, SentryPiglinRenderer::new);
        EntityRendererRegistry.register(ModEntities.STONE_TABLET, StoneTabletRenderer::new);
        EntityRendererRegistry.register(ModEntities.TAR_SLIME, TarSlimeRenderer::new);
        EntityRendererRegistry.register(ModEntities.TOY_BALL, ToyBallRenderer::new);
        EntityRendererRegistry.register(ModEntities.TOY_TETHERED_LOG, ToyTetheredLogRenderer::new);
        EntityRendererRegistry.register(ModEntities.TOY_SCRATCHING_POST, ToyScratchingPostRenderer::new);
        EntityRendererRegistry.register(ModEntities.JAVELIN, JavelinRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANCIENT_LIGHTNING_BOLT, LightningBoltRenderer::new);
        EntityRendererRegistry.register(ModEntities.FRIENDLY_PIGLIN, FriendlyPiglinRenderer::new);
        EntityRendererRegistry.register(ModEntities.SKELETON, SkeletonRenderer::new);
    }

    private static <T extends Prehistoric> void registerTrilobite(RegistrySupplier<EntityType<T>> type) {
        EntityRendererRegistry.register(type, context -> new PrehistoricGeoRenderer<>(context, "trilobite", RenderType::entityCutoutNoCull));
    }

    private static <T extends Prehistoric> void registerDino(RegistrySupplier<EntityType<T>> type, String name, Function<ResourceLocation, RenderType> renderType) {
        EntityRendererRegistry.register(type, context -> new PrehistoricGeoRenderer<>(context, name, renderType));
    }

    private static <T extends Prehistoric> void registerDino(RegistrySupplier<EntityType<T>> type, String name) {
        registerDino(type, name, RenderType::entityCutoutNoCull);
    }

    private static <T extends PrehistoricFish> void registerFish(RegistrySupplier<EntityType<T>> type, String name) {
        EntityRendererRegistry.register(type, context -> new PrehistoricFishGeoRenderer<>(context, name));
    }

    private static void registerEventHandlers() {
        InteractionEvent.INTERACT_ENTITY.register((player, entity, hand) -> {
            if (player.getItemInHand(hand).is(ModItems.DINOPEDIA.get())) {
                if (player.level().isClientSide) {
                    if (entity instanceof LivingEntity livingEntity) {
                        if (entity instanceof Animal animal && PrehistoricEntityInfo.isMammal(animal) && ModCapabilities.hasEmbryo(animal)) {
                            Minecraft.getInstance().setScreen(new DinopediaScreen(animal));
                        } else if (entity instanceof DinosaurEgg || entity instanceof Prehistoric || entity instanceof PrehistoricFish || entity instanceof Quagga) {
                            Minecraft.getInstance().setScreen(new DinopediaScreen(livingEntity));
                        }
                    }
                    return EventResult.interruptTrue();
                } else if (entity instanceof Animal animal && PrehistoricEntityInfo.isMammal(animal) && ModCapabilities.hasEmbryo(animal)) {
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
        ClientGuiEvent.RENDER_HUD.register((poseStack, v) -> {
            Minecraft mc = Minecraft.getInstance();
            OverlayRenderer.renderHelmet(mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
        });
        ClientTickEvent.CLIENT_POST.register(instance -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.isPaused()) return;
            if (mc.player != null && mc.player.getVehicle() instanceof PrehistoricFlying flying) {
                if (!jumpLastTick && mc.options.keyJump.isDown()) {
                    boolean isFlying = flying.isFlying();
                    if (jumpTriggerTime == 0) {
                        jumpTriggerTime = 7;
                    } else {
                        if (!flying.isTakingOff() && !isFlying) {
                            MessageHandler.SYNC_CHANNEL.sendToServer(new C2SRiderForceFlyingMessage(flying.getId(), true));
                        } else if (isFlying) {
                            MessageHandler.SYNC_CHANNEL.sendToServer(new C2SRiderForceFlyingMessage(flying.getId(), false));
                        }
                    }
                }
                if (flying.isFlying()) {
                    if (mc.player.input.jumping) {
                        if (!flying.isFlyingUp()) {
                            MessageHandler.SYNC_CHANNEL.sendToServer(C2SVerticalFlightMessage.flyUp(flying.getId()));
                        }
                        flying.setFlyingUp(true);
                    } else if (flyDownKey.isDown()) {
                        if (!flying.isFlyingDown()) {
                            MessageHandler.SYNC_CHANNEL.sendToServer(C2SVerticalFlightMessage.flyDown(flying.getId()));
                        }
                        flying.setFlyingDown(true);
                    } else {
                        if (flying.isFlyingUp() || flying.isFlyingDown()) {
                            MessageHandler.SYNC_CHANNEL.sendToServer(C2SVerticalFlightMessage.stop(flying.getId()));
                        }
                        flying.setFlyingUp(false);
                        flying.setFlyingDown(false);
                    }
                }
            }
            if (jumpTriggerTime > 0) {
                jumpTriggerTime--;
            }
            jumpLastTick = mc.options.keyJump.isDown();
        });
    }

    private static void registerBlockRenderers() {
        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
            RenderTypeRegistry.register(RenderType.cutout(), info.getPlantBlock());
        }
        for (RegistrySupplier<VaseBlock> vase : ModBlocks.VASES) {
            RenderTypeRegistry.register(RenderType.cutout(), vase.get());
        }
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.ANU_FIGURINE_DESTROYED.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.ANU_FIGURINE_RESTORED.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.ANU_FIGURINE_PRISTINE.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.SKELETON_FIGURINE_RESTORED.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.SKELETON_FIGURINE_PRISTINE.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CALAMITES_DOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CALAMITES_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CALAMITES_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CALAMITES_TRAPDOOR.get());

        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CORDAITES_DOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CORDAITES_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CORDAITES_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CORDAITES_TRAPDOOR.get());

        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.MUTANT_TREE_DOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.MUTANT_TREE_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.MUTANT_TREE_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.MUTANT_TREE_TRAPDOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.MUTANT_TREE_TUMOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.MUTANT_TREE_VINE.get());

        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.PALM_DOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.PALM_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.PALM_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.PALM_TRAPDOOR.get());

        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SIGILLARIA_DOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SIGILLARIA_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SIGILLARIA_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SIGILLARIA_TRAPDOOR.get());

        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.TEMPSKYA_DOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.TEMPSKYA_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.TEMPSKYA_TOP.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.TEMPSKYA_LEAF.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.TEMPSKYA_TRAPDOOR.get());

        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SLIME_TRAIL.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.OBSIDIAN_SPIKES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.FERNS.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.ANCIENT_GLASS.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.REINFORCED_GLASS.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.CULTURE_VAT.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.ANU_PORTAL.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.HOME_PORTAL.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.AMBER_BLOCK.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.AMBER_CHUNK.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.AMBER_CHUNK_DOMINICAN.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.AMBER_CHUNK_MOSQUITO.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SHELL.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.COMFY_BED.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.LARGE_CHAIN_FENCE.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SMALL_CHAIN_FENCE.get());

        BlockEntityRendererRegistry.register(ModBlockEntities.ANU_STATUE.get(), AnuStatueRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ANUBITE_STATUE.get(), AnubiteStatueRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.SARCOPHAGUS.get(), SarcophagusRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.CULTURE_VAT.get(), CultureVatRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ANCIENT_CHEST.get(), AncientChestRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ANU_BARRIER.get(), AnuBarrierRenderer::new);

        ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return FoliageColor.getDefaultColor();
            }
            return BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos);
        }, ModBlocks.FERNS.get());
    }
}
