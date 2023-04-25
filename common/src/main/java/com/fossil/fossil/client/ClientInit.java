package com.fossil.fossil.client;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.block.entity.ModBlockEntities;
import com.fossil.fossil.capabilities.ModCapabilities;
import com.fossil.fossil.client.gui.*;
import com.fossil.fossil.client.gui.filters.CreativeTabFilters;
import com.fossil.fossil.client.model.*;
import com.fossil.fossil.client.particle.BubbleParticle;
import com.fossil.fossil.client.particle.TarBubbleParticle;
import com.fossil.fossil.client.renderer.blockentity.*;
import com.fossil.fossil.client.renderer.entity.*;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.Dilophosaurus;
import com.fossil.fossil.entity.prehistoric.Therizinosaurus;
import com.fossil.fossil.entity.prehistoric.Triceratops;
import com.fossil.fossil.entity.prehistoric.Tropeognathus;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
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
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.world.entity.animal.Animal;

public class ClientInit {
    public static final KeyMapping DEBUG_SCREEN_KEY = new KeyMapping("key.fossil.debug_screen", InputConstants.Type.KEYSYM, InputConstants.KEY_Y,
            "category.fossil.debug");

    public static void immediate() {
        EntityRendererRegistry.register(ModEntities.DILOPHOSAURUS,
                context -> new RenderPrehistoricGeo<>(context, "dilophosaurus.geo.json", Dilophosaurus.ANIMATIONS)
        );
        EntityRendererRegistry.register(ModEntities.THERIZINOSAURUS,
                context -> new RenderPrehistoricGeo<>(context, "therizinosaurus.geo.json", Therizinosaurus.ANIMATIONS)
        );
        EntityRendererRegistry.register(ModEntities.TRICERATOPS,
                context -> new RenderPrehistoricGeo<>(context, "triceratops.geo.json", Triceratops.ANIMATIONS)
        );
        EntityRendererRegistry.register(ModEntities.TROPEOGNATHUS,
                context -> new RenderPrehistoricGeo<>(context, "fa.tropeognathus.geo.json", Tropeognathus.ANIMATIONS)
        );

        EntityRendererRegistry.register(ModEntities.ANU, context -> new AnuRenderer(context, new AnuModel()));
        EntityRendererRegistry.register(ModEntities.ANU_STATUE, context -> new AnuStatueEntityRenderer(context, new AnuStatueModel()));
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

    public static void later() {
        KeyMappingRegistry.register(DEBUG_SCREEN_KEY);
        for (PrehistoricPlantType type : PrehistoricPlantType.values()) {
            RenderTypeRegistry.register(RenderType.cutout(), type.getPlantBlock());
        }
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.AMPHORA_VASE_DAMAGED.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.KYLIX_VASE_DAMAGED.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.VOLUTE_VASE_DAMAGED.get());

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
                if (player.getItemInHand(hand).is(ModItems.DINOPEDIA.get()) && entity instanceof Animal animal) {
                    if (entity instanceof Prehistoric || (PrehistoricEntityType.isMammal(animal) && ModCapabilities.getEmbryoProgress(animal) > 0)) {
                        Minecraft.getInstance().setScreen(new DinopediaScreen(animal));
                    }
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
        BlockEntityRendererRegistry.register(ModBlockEntities.VASE.get(), VaseRenderer::new);
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
    }
}
