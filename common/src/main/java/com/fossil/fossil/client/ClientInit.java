package com.fossil.fossil.client;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.block.custom_blocks.VaseBlock;
import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.capabilities.ModCapabilities;
import com.fossil.fossil.client.gui.*;
import com.fossil.fossil.client.gui.debug.DebugScreen;
import com.fossil.fossil.client.gui.filters.CreativeTabFilters;
import com.fossil.fossil.client.model.*;
import com.fossil.fossil.client.particle.BubbleParticle;
import com.fossil.fossil.client.particle.TarBubbleParticle;
import com.fossil.fossil.client.renderer.blockentity.*;
import com.fossil.fossil.client.renderer.entity.*;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.*;
import com.fossil.fossil.entity.prehistoric.base.DinosaurEgg;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import com.fossil.fossil.entity.prehistoric.parts.PrehistoricPart;
import com.fossil.fossil.inventory.ModMenus;
import com.fossil.fossil.item.ModItems;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.FoliageColor;

public class ClientInit {
    public static final KeyMapping DEBUG_SCREEN_KEY = new KeyMapping("key.fossil.debug_screen", InputConstants.Type.KEYSYM, InputConstants.KEY_Y,
            "category.fossil.debug");

    public static void immediate() {
        registerFish(ModEntities.ALLIGATOR_GAR, "alligator_gar.geo.json", AlligatorGar.ANIMATIONS, "alligator_gar");
        registerDino(ModEntities.ALLOSAURUS, "allosaurus.geo.json", Allosaurus.ANIMATIONS);
        registerDino(ModEntities.ANKYLOSAURUS, "ankylosaurus.geo.json", Ankylosaurus.ANIMATIONS);
        registerDino(ModEntities.ARTHROPLEURA, "arthropleura.geo.json", Arthropleura.ANIMATIONS);
        registerDino(ModEntities.BRACHIOSAURUS, "brachiosaurus.geo.json", Brachiosaurus.ANIMATIONS);
        registerDino(ModEntities.CERATOSAURUS, "ceratosaurus.geo.json", Ceratosaurus.ANIMATIONS);
        registerDino(ModEntities.CITIPATI, "citipati.geo.json", Citipati.ANIMATIONS);
        registerFish(ModEntities.COELACANTH, "coelacanth.geo.json", Coelacanth.ANIMATIONS, "coelacanth");
        registerDino(ModEntities.COMPSOGNATHUS, "compsognathus.geo.json", Compsognathus.ANIMATIONS);
        registerDino(ModEntities.CONFUCIUSORNIS, "confuciusornis.geo.json", Confuciusornis.ANIMATIONS);
        registerDino(ModEntities.CRASSIGYRINUS, "crassigyrinus.geo.json", Crassigyrinus.ANIMATIONS);
        registerDino(ModEntities.DEINONYCHUS, "deinonychus.geo.json", Deinonychus.ANIMATIONS);
        registerDino(ModEntities.DILOPHOSAURUS, "dilophosaurus.geo.json", Dilophosaurus.ANIMATIONS);
        registerDino(ModEntities.DIPLOCAULUS, "diplocaulus.geo.json", Diplocaulus.ANIMATIONS);
        registerDino(ModEntities.DIPLODOCUS, "diplodocus.geo.json", Diplodocus.ANIMATIONS);
        registerDino(ModEntities.DODO, "dodo.geo.json", Dodo.ANIMATIONS);
        registerDino(ModEntities.DRYOSAURUS, "dryosaurus.geo.json", Dryosaurus.ANIMATIONS);
        registerDino(ModEntities.EDAPHOSAURUS, "edaphosaurus.geo.json", Edaphosaurus.ANIMATIONS);
        registerDino(ModEntities.ELASMOTHERIUM, "elasmotherium.geo.json", Elasmotherium.ANIMATIONS);
        registerDino(ModEntities.GALLIMIMUS, "gallimimus.geo.json", Gallimimus.ANIMATIONS);
        registerDino(ModEntities.GASTORNIS, "gastornis.geo.json", Gastornis.ANIMATIONS);
        registerDino(ModEntities.HENODUS, "henodus.geo.json", Henodus.ANIMATIONS);
        registerDino(ModEntities.ICHTYOSAURUS, "ichtyosaurus.geo.json", Ichtyosaurus.ANIMATIONS);
        registerDino(ModEntities.KELENKEN, "kelenken.geo.json", Kelenken.ANIMATIONS);
        registerDino(ModEntities.LIOPLEURODON, "liopleurodon.geo.json", Liopleurodon.ANIMATIONS);
        registerDino(ModEntities.MAMMOTH, "mammoth.geo.json", Mammoth.ANIMATIONS);
        registerDino(ModEntities.MEGALANIA, "megalania.geo.json", Megalania.ANIMATIONS);
        registerDino(ModEntities.MEGALOCEROS, "megaloceros.geo.json", Megaloceros.ANIMATIONS);
        registerDino(ModEntities.MEGALODON, "megalodon.geo.json", Megalodon.ANIMATIONS);
        registerDino(ModEntities.MEGALOGRAPTUS, "megalograptus.geo.json", Megalograptus.ANIMATIONS);
        registerDino(ModEntities.MEGANEURA, "meganeura.geo.json", Meganeura.ANIMATIONS);
        registerDino(ModEntities.MOSASAURUS, "mosasaurus.geo.json", Mosasaurus.ANIMATIONS);
        registerFish(ModEntities.NAUTILUS, "nautilus.geo.json", Nautilus.ANIMATIONS, "nautilus");
        registerDino(ModEntities.ORNITHOLESTES, "ornitholestes.geo.json", Ornitholestes.ANIMATIONS);
        registerDino(ModEntities.PACHYCEPHALOSAURUS, "pachycephalosaurus.geo.json", Pachycephalosaurus.ANIMATIONS);
        registerDino(ModEntities.PARASAUROLOPHUS, "parasaurolophus.geo.json", Parasaurolophus.ANIMATIONS);
        registerDino(ModEntities.PHORUSRHACOS, "phorusrhacos.geo.json", Phorusrhacos.ANIMATIONS);
        registerDino(ModEntities.PLATYBELODON, "platybelodon.geo.json", Platybelodon.ANIMATIONS);
        registerDino(ModEntities.PLESIOSAURUS, "plesiosaurus.geo.json", Plesiosaurus.ANIMATIONS);
        registerDino(ModEntities.PTERANODON, "pteranodon.geo.json", Pteranodon.ANIMATIONS);
        EntityRendererRegistry.register(ModEntities.QUAGGA, QuaggaRenderer::new);
        registerDino(ModEntities.SARCOSUCHUS, "sarcosuchus.geo.json", Sarcosuchus.ANIMATIONS);
        registerDino(ModEntities.SMILODON, "smilodon.geo.json", Smilodon.ANIMATIONS);
        registerDino(ModEntities.SPINOSAURUS, "spinosaurus.geo.json", Spinosaurus.ANIMATIONS);
        registerDino(ModEntities.STEGOSAURUS, "stegosaurus.geo.json", Stegosaurus.ANIMATIONS);
        registerFish(ModEntities.STURGEON, "sturgeon.geo.json", Sturgeon.ANIMATIONS, "sturgeon");
        registerDino(ModEntities.THERIZINOSAURUS, "therizinosaurus.geo.json", Therizinosaurus.ANIMATIONS);
        registerDino(ModEntities.TIKTAALIK, "tiktaalik.geo.json", Tiktaalik.ANIMATIONS);
        registerDino(ModEntities.TITANIS, "titanis.geo.json", Titanis.ANIMATIONS);
        registerDino(ModEntities.TRICERATOPS, "triceratops.geo.json", Triceratops.ANIMATIONS);
        registerDino(ModEntities.TROPEOGNATHUS, "tropeognathus.geo.json", Tropeognathus.ANIMATIONS);
        registerDino(ModEntities.TYRANNOSAURUS, "tyrannosaurus.geo.json", Tyrannosaurus.ANIMATIONS);
        registerDino(ModEntities.VELOCIRAPTOR, "velociraptor.geo.json", Velociraptor.ANIMATIONS);
        EntityRendererRegistry.register(ModEntities.DINOSAUR_EGG, context -> new DinosaurEggRenderer(context, new DinosaurEggModel()));

        EntityRendererRegistry.register(ModEntities.ANUBITE, context -> new AnubiteRenderer(context, new AnubiteModel()));
        EntityRendererRegistry.register(ModEntities.ANU_BOSS, context -> new AnuBossRenderer(context, new AnuBossModel()));
        EntityRendererRegistry.register(ModEntities.ANU_DEAD, context -> new AnuDeadRenderer(context, new AnuDeadModel()));
        EntityRendererRegistry.register(ModEntities.ANU_STATUE, context -> new AnuStatueEntityRenderer(context, new AnuStatueModel()));
        EntityRendererRegistry.register(ModEntities.SENTRY_PIGLIN, context -> new SentryPiglinRenderer(context, new SentryPiglinModel()));
        EntityRendererRegistry.register(ModEntities.STONE_TABLET, StoneTabletRenderer::new);
        EntityRendererRegistry.register(ModEntities.TAR_SLIME, TarSlimeRenderer::new);
        EntityRendererRegistry.register(ModEntities.TOY_BALL, context -> new ToyBallRenderer(context, new ToyBallModel()));
        EntityRendererRegistry.register(ModEntities.TOY_TETHERED_LOG, context -> new ToyTetheredLogRenderer(context, new ToyTetheredLogModel()));
        EntityRendererRegistry.register(ModEntities.TOY_SCRATCHING_POST, context -> new ToyScratchingPostRenderer(context, new ToyScratchingPostModel()));
        EntityRendererRegistry.register(ModEntities.JAVELIN, JavelinRenderer::new);
        EntityRendererRegistry.register(ModEntities.ANCIENT_LIGHTNING_BOLT, LightningBoltRenderer::new);
        EntityRendererRegistry.register(ModEntities.FRIENDLY_PIGLIN, context -> new FriendlyPiglinRenderer(context, new FriendlyPiglinModel()));
        ParticleProviderRegistry.register(ModBlockEntities.BUBBLE, BubbleParticle.Provider::new);
        ParticleProviderRegistry.register(ModBlockEntities.TAR_BUBBLE, TarBubbleParticle.Provider::new);
    }

    private static <T extends Prehistoric> void registerDino(RegistrySupplier<EntityType<T>> type, String modelFileName, String animFileName) {
        EntityRendererRegistry.register(type, context -> new PrehistoricGeoRenderer<>(context, modelFileName, animFileName));
    }

    private static <T extends PrehistoricFish> void registerFish(RegistrySupplier<EntityType<T>> type, String modelFileName, String animFileName, String textureFileName) {
        EntityRendererRegistry.register(type, context -> new PrehistoricFishGeoRenderer<>(context, modelFileName, animFileName, textureFileName));
    }

    public static void later() {
        KeyMappingRegistry.register(DEBUG_SCREEN_KEY);
        for (PrehistoricPlantType type : PrehistoricPlantType.values()) {
            RenderTypeRegistry.register(RenderType.cutout(), type.getPlantBlock());
        }
        for (RegistrySupplier<VaseBlock> vase : ModBlocks.VASES) {
            RenderTypeRegistry.register(RenderType.cutout(), vase.get());
        }
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.CORDAITES_DOOR.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.CORDAITES_TRAPDOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CORDAITES_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SIGILLARIA_LEAVES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CORDAITES_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SIGILLARIA_SAPLING.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SLIME_TRAIL.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.OBSIDIAN_SPIKES.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.ANCIENT_GLASS.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.REINFORCED_GLASS.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.CULTIVATE.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.ANU_PORTAL.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.HOME_PORTAL.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.AMBER_BLOCK.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.AMBER_CHUNK.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.AMBER_CHUNK_DOMINICAN.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.AMBER_CHUNK_MOSQUITO.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SHELL.get());
        MenuScreens.register(ModMenus.FEEDER.get(), FeederScreen::new);
        MenuScreens.register(ModMenus.SIFTER.get(), SifterScreen::new);
        MenuScreens.register(ModMenus.CULTIVATE.get(), CultivateScreen::new);
        MenuScreens.register(ModMenus.ANALYZER.get(), AnalyzerScreen::new);
        MenuScreens.register(ModMenus.WORKTABLE.get(), WorktableScreen::new);
        InteractionEvent.INTERACT_ENTITY.register((player, entity, hand) -> {
            if (player instanceof AbstractClientPlayer) {
                if (PrehistoricPart.isMultiPart(entity)) {
                    entity = PrehistoricPart.getParent(entity);
                }
                if (player.getItemInHand(hand).is(ModItems.DINOPEDIA.get())) {
                    if (entity instanceof Animal animal && PrehistoricEntityType.isMammal(animal) && ModCapabilities.getEmbryoProgress(animal) > 0) {
                        Minecraft.getInstance().setScreen(new DinopediaScreen(animal));
                    } else if (entity instanceof DinosaurEgg || entity instanceof Prehistoric) {
                        Minecraft.getInstance().setScreen(new DinopediaScreen((LivingEntity) entity));
                    }
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
        BlockEntityRendererRegistry.register(ModBlockEntities.FIGURINE.get(), context -> new FigurineRenderer());
        BlockEntityRendererRegistry.register(ModBlockEntities.ANU_STATUE.get(), AnuStatueRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ANUBITE_STATUE.get(), AnubiteStatueRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.SARCOPHAGUS.get(), SarcophagusRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.CULTIVATE.get(), CultivateRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ANCIENT_CHEST.get(), AncientChestRenderer::new);
        CreativeTabFilters.register();
        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (DEBUG_SCREEN_KEY.consumeClick()) {
                minecraft.setScreen(new DebugScreen(DebugScreen.getHitResult(minecraft)));
            }
        });
        ColorHandlerRegistry.registerBlockColors((blockState, blockAndTintGetter, blockPos, i) -> {
            if (blockAndTintGetter == null || blockPos == null) {
                return FoliageColor.getDefaultColor();
            }
            return BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos);
        }, ModBlocks.FERNS.get());
    }
}
