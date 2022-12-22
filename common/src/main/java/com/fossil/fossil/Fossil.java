package com.fossil.fossil;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;


@Mod("fossil")
public class Fossil
{

    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "fossil";


    public Fossil() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);

    }
    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BENNETTITALES_SMALL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BENNETTITALES_TALL.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CORDAITES_DOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CORDAITES_TRAPDOOR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CORDAITES_LEAVES.get(), RenderType.cutout());


    }

    private void setup(final FMLCommonSetupEvent event)
    {

        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

        InterModComms.sendTo("fossil", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {

        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {

            LOGGER.info("HELLO from Register Block");
        }
    }
}
