package com.fossil.fossil.forge;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.ClientInit;
import com.fossil.fossil.recipe.ModRecipes;
import com.mojang.logging.LogUtils;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Fossil.MOD_ID)
public class FossilForge {
    public static final Logger LOGGER = LogUtils.getLogger();

    public FossilForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        EventBuses.registerModEventBus(Fossil.MOD_ID, modEventBus);

        Fossil.init();

        modEventBus.addListener(this::onClient);
        modEventBus.addListener(this::onCommon);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientInit::immediate);
    }

    public void onClient(FMLClientSetupEvent event) {
        ClientInit.later();
    }
    public void onCommon(FMLCommonSetupEvent event) {
        ModRecipes.initRecipes();
    }
}
